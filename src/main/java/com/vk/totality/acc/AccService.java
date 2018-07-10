package com.vk.totality.acc;

import com.vk.totality.game.BetResult;
import com.vk.totality.game.BetResultRepository;
import com.vk.totality.game.Game;
import com.vk.totality.game.GameBetStatistics;
import com.vk.totality.game.GameService;
import com.vk.totality.game.ScoreResult;
import com.vk.totality.game.Tournament;
import com.vk.totality.game.UserTournament;
import com.vk.totality.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccService {

  private BetRepository betRepository;
  private AccountRepository accountRepository;
  private BetResultRepository betResultRepository;
  private GameService gameService;
  private BetResultItemRepository betResultItemRepository;


  @Autowired
  public AccService(BetRepository betRepository, AccountRepository accountRepository, BetResultRepository betResultRepository,
      GameService gameService, BetResultItemRepository betResultItemRepository) {
    this.betRepository = betRepository;
    this.accountRepository = accountRepository;
    this.betResultRepository = betResultRepository;
    this.gameService = gameService;
    this.betResultItemRepository = betResultItemRepository;
  }

  public Bet save(Bet bet) {
    return betRepository.save(bet);
  }

  public Account save(Account account) {
    return accountRepository.save(account);
  }

  public void deleteAccount(Account account) {
    accountRepository.delete(account);
  }

  public Bet findOrCreateBet(Game game, UserTournament userTournament) {
    Bet bet = betRepository.findBetByUserTournamentAndGame(userTournament, game);
    if (bet == null) {
      bet = new Bet(userTournament, game, new Date());
      bet = betRepository.save(bet);
    }
    return bet;
  }

  public BetResultItem findBetResultItem(Bet bet) {
    return betResultItemRepository.findFirstByBet(bet);
  }

  public Optional<Bet> findBetById(Long id) {
    return betRepository.findById(id);
  }

  public Optional<Account> findAccountById(Long id) {
    return accountRepository.findById(id);
  }

  public List<Account> findAccounts(UserTournament ut) {
    return accountRepository.findAccountByUserTournamentOrderByEventDateDesc(ut);
  }

  public List<AccOperation> accOperationsForMaintenance() {
    List<AccOperation> operations = new ArrayList<>();
    for (AccOperation o : AccOperation.values()) {
      if (o.name().startsWith("CASH"))
        operations.add(o);
    }
    return operations;
  }

  @Transactional
  public BetResult processScoreResult(Game game, ScoreResult scoreResult) {
    if (game == null || !scoreResult.valid())
      return null;

    // find Bet Result by Game
    BetResult betResult = findBetResultOrCreateNew(game);

    // check if score id different
    if (betResult.same(scoreResult))
      return betResult;
    removeOldTransactions(betResult);
    betResult = updateBetResult(betResult, scoreResult);

    // Ensure that all bets are created for all active TournamentUsers
    List<Bet> gameBets = findAllBetsForGame(game);
    List<BetResultItem> resultItems = createDraftBetResultItems(betResult, gameBets);

    // Calculate Result for each bet
    calcGameOutcome(resultItems, betResult);

    return betResult;
  }

  public List<BetResultItem> findBetResultItems(BetResult betResult) {
    return betResultItemRepository.findBetResultItemsByBetResultOrderByBet_UserTournament_User_UserLogin(betResult);
  }

  private void removeOldTransactions(BetResult betResult) {
    List<BetResultItem> resultItems = findBetResultItems(betResult);
    for (BetResultItem i : resultItems) {
      accountRepository.removeAccountByBetResultItem(i);
    }
    betResultItemRepository.removeBetResultItemsByBetResult(betResult);
  }

  private void calcGameOutcome(List<BetResultItem> resultItems, BetResult betResult) {
    BigDecimal gameBudget = calcGameBudget(resultItems);
    betResult.setGameBudget(gameBudget);
    betResultRepository.save(betResult);
    Map<BetResultStatus, List<BetResultItem>> resMap = new HashMap<>();
    for (BetResultItem i : resultItems) {
      List<BetResultItem> l = resMap.get(i.getBetResultStatus());
      if (l == null) {
        l = new ArrayList<>();
        resMap.put(i.getBetResultStatus(), l);
      }
      l.add(i);
    }

    for (BetResultStatus status : BetResultStatus.winStatuses()) {
      List<BetResultItem> items = resMap.get(status);
      if ((items != null) && !items.isEmpty()) {
        BigDecimal winAmount = gameBudget.multiply(status.ratio()).setScale(0, RoundingMode.FLOOR)
            .divide(BigDecimal.valueOf(items.size()), RoundingMode.FLOOR).setScale(0);
        for (BetResultItem i : items) {
          i.setWinAmount(winAmount);
        }
        break;
      }
    }

    Iterable<BetResultItem> resItems = betResultItemRepository.saveAll(resultItems);
    createAccountRecords(resItems);
  }

  private void createAccountRecords(Iterable<BetResultItem> resultItems) {
    for (BetResultItem i : resultItems) {
      Account acc = new Account(i.getBet().getUserTournament(), i.getRate(), AccOperation.BET_OUT, new Date(), i);
      accountRepository.save(acc);

      if (i.getWinAmount().compareTo(BigDecimal.ZERO) > 0) {
        Account win = new Account(i.getBet().getUserTournament(), i.getWinAmount(), AccOperation.BET_WIN, new Date(), i);
        accountRepository.save(win);
      }
    }
  }

  private BigDecimal calcGameBudget(List<BetResultItem> resultItems) {
    BigDecimal gameBudget = BigDecimal.ZERO;
    for (BetResultItem item : resultItems) {
      gameBudget = gameBudget.add(item.getRate());
    }
    return gameBudget;
  }


  private List<BetResultItem> createDraftBetResultItems(BetResult betResult, List<Bet> gameBets) {
    List<BetResultItem> resultItems = new ArrayList<>();
    for (Bet bet : gameBets) {
      BetResultItem draftBetResultItem = createDraftBetResultItem(bet, betResult);
      resultItems.add(draftBetResultItem);
    }
    return resultItems;
  }

  private BetResultItem createDraftBetResultItem(Bet bet, BetResult betResult) {
    BetResultItem item = betResultItemRepository.findFirstByBet(bet);
    if (item == null)
      item = new BetResultItem(betResult, bet);

    item.setRate(betResult.getGame().getRate());
    item.setWinAmount(BigDecimal.ZERO);

    item.setBetResultStatus(BetResultStatus.LOOSE);  // default to LOOSE
    for (BetResultStatus status : BetResultStatus.winStatuses()) {
      if (status.matches(item.getBet(), betResult)) {
        item.setBetResultStatus(status);
        return item;
      }
    }
    return item;
  }

  private List<Bet> findAllBetsForGame(Game game) {
    List<UserTournament> userTournaments = gameService.findUserTournaments(game.getTournament());
    List<Bet> gameBets = new ArrayList<>();
    for (UserTournament ut : userTournaments) {
      Bet bet = findOrCreateBet(game, ut);
      gameBets.add(bet);
    }
    return gameBets;
  }

  private BetResult updateBetResult(BetResult betResult, ScoreResult scoreResult) {
    betResult.setTeam1ResultScore(scoreResult.getTeam1ResultScore());
    betResult.setTeam2ResultScore(scoreResult.getTeam2ResultScore());
    betResult.setResultDate(new Date());
    betResult = betResultRepository.save(betResult);
    return betResult;
  }

  private BetResult findBetResultOrCreateNew(Game game) {
    BetResult betResult = betResultRepository.findFirstByGame(game);
    if (betResult == null)
      betResult = betResultRepository.save(new BetResult((game)));

    return betResult;
  }

  public List<TournamentResultItem> calcTournamentResult(Tournament tournament) {
    return betResultItemRepository.calcTournamentResult(tournament);
  }

  public BigDecimal calcTournamentTotalBalance(List<TournamentResultItem> resultItems) {
    BigDecimal sum = BigDecimal.ZERO;
    for (TournamentResultItem item : resultItems) {
      sum = sum.add(item.balance().negate());
    }
    return sum;
  }

  public List<UserAccountOperationSummary> calcAccBalance(Tournament tournament) {
    return calcAccBalance(tournament, null);
  }

  public TournamentBalance calcTournamentBalance(List<UserAccountOperationSummary> summaries) {
    TournamentBalance balance = new TournamentBalance();
    for (UserAccountOperationSummary summary : summaries) {
      balance.addToBePaid(summary.getToBePaidOutAmount());
      balance.addGetMoney(summary.getPaymentMissing());
      balance.addActual(summary.getCashBalance());
    }
    return balance;
  }

  public List<UserAccountOperationSummary> calcAccBalance(Tournament tournament, User user) {
    List<UserAccountOperationSummary> userOperations = new ArrayList();

    List<UserAccountItemOperation> userAccountItemOperations = betResultItemRepository
        .calcTournamentBalances(tournament, user == null ? "%" : user.getUserLogin());
    UserTournament currentUserTournament = null;
    UserAccountOperationSummary summary = null;
    for (UserAccountItemOperation o : userAccountItemOperations) {
      if (currentUserTournament == null || !currentUserTournament.equals(o.getUserTournament())) {
        summary = new UserAccountOperationSummary();
        summary.setUserTournament(o.getUserTournament());
        userOperations.add(summary);
        currentUserTournament = summary.getUserTournament();
      }
      if (o.getAccOperation() != null)
        summary.add(o.getAccOperation(), o.getAmount());
    }

    return userOperations;
  }

  public GameBetStatistics calcGameBetStatistics(Game game) {
    GameBetStatistics statistics = new GameBetStatistics();
    statistics.setBetFilledCount(betRepository.countByGameAndScore1IsNotNullAndScore2IsNotNull(game));
    statistics.setBetCount(gameService.countUserTournaments(game.getTournament()));
    return statistics;
  }
}
