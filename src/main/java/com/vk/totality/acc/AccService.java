package com.vk.totality.acc;

import com.vk.totality.game.Game;
import com.vk.totality.game.UserTournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccService {

    private BetRepository betRepository;
    private AccountRepository accountRepository;


    public Bet save(Bet bet) {
        return betRepository.save(bet);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    @Autowired
    public AccService(BetRepository betRepository, AccountRepository accountRepository) {
        this.betRepository = betRepository;
        this.accountRepository = accountRepository;
    }

    public Bet findOrCreateBet(Game game, UserTournament userTournament) {
        Bet bet = betRepository.findBetByUserTournamentAndGame(userTournament, game);
        if (bet == null) {
            bet = new Bet(userTournament, game, new Date());
            bet = betRepository.save(bet);
        }
        return bet;
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
}
