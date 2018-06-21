package com.vk.totality.acc;

import com.vk.totality.game.GameBet;
import com.vk.totality.game.GameService;
import com.vk.totality.game.UserTournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.vk.totality.game.GameController.GAME;

@Controller
public class AccController {

    private AccService accService;
    private GameService gameService;

    @Autowired
    public AccController(AccService accService, GameService gameService) {
        this.accService = accService;
        this.gameService = gameService;
    }

    public static final String ID = "{tournamentId:.+}";

    @PostMapping("/" + ID + "/" + GAME + "setBet")
    public String setBet(Bet bet, Model model) {
        Optional<Bet> found = accService.findBetById(bet.getId());
        if (!found.isPresent())
            throw new RuntimeException("Нет такой ставки " + bet.toString());

        Bet existing = found.get();
        GameBet gb = new GameBet(existing, existing.getGame(), accService.findBetResultItem(existing));

        if (gb.getCanBet()) {
            existing.setScore1(bet.getScore1());
            existing.setScore2(bet.getScore2());
            existing.setBetDate(new Date());
            existing = accService.save(existing);
        }

        model.addAttribute("gameBet", gb);
        return "fragments/cardForm :: cardViewForm";
    }

    public static final String UT_ID = "{utId:.+}";
    public static final String ACC_PATH = "/admin/acc/userTournament/" + UT_ID;

    @GetMapping(ACC_PATH)
    public String userBalance(@PathVariable Long utId, Model model) {
        Optional<UserTournament> userTournament = gameService.findUserTournament(utId);
        if (!userTournament.isPresent())
            throw new RuntimeException("Cannot find User Tournament for id " + utId);

        model.addAttribute("ut", userTournament.get());
        List<Account> accounts = accService.findAccounts(userTournament.get());
        model.addAttribute("accounts", accounts);
        model.addAttribute("accountBalance", calcBalance(accounts));
        model.addAttribute("accOperations", accService.accOperationsForMaintenance());

        return "acc/userTournamentBalance";
    }

    private AccBalance calcBalance(List<Account> accounts) {
        BigDecimal bal = BigDecimal.ZERO;
        for (Account a : accounts) {
            bal = bal.add(a.getAmount());
        }
        return new AccBalance(bal);
    }

    @PostMapping(ACC_PATH + "/addAccountOperation")
    public String addAccOperation(@PathVariable Long utId, @Valid Account account, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
//            assert utId.equals(account.getUserTournament().getId());

            account.setEventDate(new Date());
            accService.save(account);
        }
        return "redirect:" + "/admin/acc/userTournament/" + utId;
    }

    @GetMapping("/admin/acc/userTournament/deleteAcc")
    public String deleteUSAccountItem(@RequestParam("id") Long id) {
        Optional<Account> account = accService.findAccountById(id);
        if (account.isPresent()) {
            accService.deleteAccount(account.get());
            return "redirect:" + "/admin/acc/userTournament/" + account.get().getUserTournament().getId();
        }

        return "redirect:" + "/";
    }

}
