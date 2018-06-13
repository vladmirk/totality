package com.vk.totality;

import com.vk.totality.acc.Bet;
import com.vk.totality.acc.BetService;
import com.vk.totality.game.*;
import com.vk.totality.user.User;
import com.vk.totality.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.vk.totality.game.GameController.GAME;

@Controller
public class HomeController {


    public static final String ADMIN_PATH = "/admin/";
    private GameService gameService;
    private UserService userService;
    private BetService betService;

    public HomeController(GameService gameService, UserService userService, BetService betService) {
        this.gameService = gameService;
        this.userService = userService;
        this.betService = betService;
    }

    @GetMapping({"/", "/index.html"})
    public String index(Model model) {
        if (!authed())
            return "login";

        List<UserTournament> tournaments = gameService.findUserActiveTournaments(user());
        if (tournaments.isEmpty()) {
            model.addAttribute("Какая печаль! Вы не учавсвуете ни в одном турнире!");
            return "index";
        }

        if (tournaments.size() == 1)
            return "redirect:/" + tournaments.get(0).getTournament().getId() + "/game/";

        return "index";
    }


    @RequestMapping(ADMIN_PATH)
    public String admin() {
        return "redirect:" + ADMIN_PATH + "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    private boolean authed() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return true;
        }
        return false;
    }

    private User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())
            throw new RuntimeException("Похоже вы не залогинены");
        return userService.findUserByName(authentication.getName());
    }

    public static final String ID = "{tournamentId:.+}";

    @GetMapping("/" + ID + "/" + GAME)
    public String tournamentGames(@PathVariable Long tournamentId, Model model) {
        Tournament tournament = gameService.findTournament(tournamentId);
        List<Game> games = gameService.findGamesByTournament(tournament);
        UserTournament userTournament = gameService.findUserTournament(user(), tournament);
        List<GameBet> gameBets = getGameBetList(games, userTournament);
        model.addAttribute("tournament", tournament);
        model.addAttribute("gameBets", gameBets);
        return GAME + "index";
    }

    private List<GameBet> getGameBetList(List<Game> games, UserTournament userTournament) {
        List<GameBet> gameBets = new ArrayList<>();
        for (Game game : games) {
            Bet bet = betService.findOrCreateBet(game, userTournament);
            gameBets.add(new GameBet(bet, game));
        }

        return gameBets;
    }

    @PostMapping("/" + ID + "/" + GAME + "setBet")
    public String setBet(Bet bet, Model model) {
        Optional<Bet> found = betService.findById(bet.getId());
        if (!found.isPresent())
            throw new RuntimeException("Нет такой ставки " + bet.toString());

        Bet existing = found.get();
        GameBet gb = new GameBet(existing, existing.getGame());

        if (gb.getCanBet()) {
            existing.setScore1(bet.getScore1());
            existing.setScore2(bet.getScore2());
            existing.setBetDate(new Date());
            existing = betService.save(existing);
        }

        model.addAttribute("gameBet", gb);
        return "fragments/cardForm :: cardViewForm";
    }

}


