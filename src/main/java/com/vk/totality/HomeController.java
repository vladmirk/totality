package com.vk.totality;

import com.vk.totality.acc.AccService;
import com.vk.totality.acc.Bet;
import com.vk.totality.acc.BetResultItem;
import com.vk.totality.acc.TournamentResultItem;
import com.vk.totality.acc.UserAccountOperationSummary;
import com.vk.totality.game.Game;
import com.vk.totality.game.GameBet;
import com.vk.totality.game.GameService;
import com.vk.totality.game.Tournament;
import com.vk.totality.game.UserTournament;
import com.vk.totality.user.User;
import com.vk.totality.user.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.vk.totality.game.GameController.GAME;

@Controller
public class HomeController {


  public static final String ADMIN_PATH = "/admin/";
  private GameService gameService;
  private UserService userService;
  private AccService accService;

  public HomeController(GameService gameService, UserService userService, AccService accService) {
    this.gameService = gameService;
    this.userService = userService;
    this.accService = accService;
  }

  @GetMapping({"/", "/index.html"})
  public String index(Model model) {
    if (!authed())
      return "login";

    List<UserTournament> tournaments = gameService.findUserActiveTournaments(user());
    if (tournaments.isEmpty()) {
      model.addAttribute("Какая печаль! Вы не участвуете ни в одном турнире!");
      return "index";
    }

    if (tournaments.size() == 1)
      return "redirect:/" + tournaments.get(0).getTournament().getId() + "/game/";

    List<UserTournament> userTournaments = gameService.findUserTournaments(user());
    model.addAttribute("userTournaments", userTournaments);

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
  public String tournamentGames(@PathVariable("tournamentId") Tournament tournament, Model model) {
    List<Game> games = gameService.findGamesByTournament(tournament);
    UserTournament userTournament = gameService.findUserTournament(user(), tournament);
    List<GameBet> gameBets = getGameBetList(games, userTournament);
    model.addAttribute("tournament", tournament);
    model.addAttribute("gameBets", gameBets);
    model.addAttribute("userCount", gameService.countUserTournaments(tournament));
    List<UserAccountOperationSummary> userAccountOperationSummaries = accService.calcAccBalance(tournament, userTournament.getUser());
    if (userAccountOperationSummaries.size() == 1)
      model.addAttribute("summary", userAccountOperationSummaries.get(0));

    return GAME + "index";
  }

  @GetMapping("/" + ID + "/tournamentResult")
  public String tournamentResult(@PathVariable("tournamentId") Tournament tournament, Model model) {
    List<TournamentResultItem> tournamentResultItems = accService.calcTournamentResult(tournament);
    model.addAttribute("tournament", tournament);
    model.addAttribute("tournamentResultItems", tournamentResultItems);
    model.addAttribute("tournamentBank", accService.calcTournamentTotalBalance(tournamentResultItems));
    return GAME + "tournamentResult";
  }

  @GetMapping("/" + ID + "/" + GAME + "gameResult")
  public String gameResult(@RequestParam("game") Long gameId, Model model) {
    Game game = gameService.findGame(gameId);
    if (game == null)
      return "redirect:/";

    List<BetResultItem> betResultItems = accService.findBetResultItems(game.getBetResult());
    model.addAttribute("betResultItems", betResultItems);
    model.addAttribute("game", game);

    return GAME + "gameResult";
  }

  private List<GameBet> getGameBetList(List<Game> games, UserTournament userTournament) {
    List<GameBet> gameBets = new ArrayList<>();
    for (Game game : games) {
      Bet bet = accService.findOrCreateBet(game, userTournament);
      BetResultItem betResultItem = accService.findBetResultItem(bet);
      gameBets.add(new GameBet(bet, game, betResultItem, accService.calcGameBetStatistics(game)));
    }

    return gameBets;
  }

  public static final String TEAM_ICON = "{icon:.+}";

  @RequestMapping(method = RequestMethod.GET, value = "/img/teamIcon/" + TEAM_ICON + "/raw")
  @ResponseBody
  public ResponseEntity<?> oneRawImage(@PathVariable String icon) {
    try {
      Resource file = gameService.findOneImage(icon);
      return ResponseEntity.ok().contentLength(file.contentLength()).contentType(MediaType.IMAGE_JPEG)
          .body(new InputStreamResource(file.getInputStream()));
    } catch (IOException e) {
      return ResponseEntity.badRequest().body("Couldn't find image : " + icon + " => " + e.getMessage());
    }
  }


}


