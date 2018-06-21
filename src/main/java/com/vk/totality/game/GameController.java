package com.vk.totality.game;

import com.vk.totality.acc.AccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

import static com.vk.totality.HomeController.ADMIN_PATH;

@Controller
public class GameController {
    public static final String TOURNAMENT = "tournament";
    public static final String GAME = "game/";
    public static final String TEAMS = "teams";

    public static final String TOUR_FOLDER = TOURNAMENT + "/";
    private GameService gameService;
    private AccService accService;

    @Autowired
    public GameController(GameService gameService, AccService accService) {
        this.gameService = gameService;
        this.accService = accService;
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER)
    public ModelAndView tournament(ModelAndView model, Pageable pageable) {
        Page<Tournament> tournaments = gameService.findAllTournaments(pageable);
        model.addObject("page", tournaments);
        model.setViewName(GAME + TOURNAMENT);
        return model;
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER + "edit")
    public String editTournament(@RequestParam("id") Long id, Model model) {
        Tournament t = gameService.findTournament(id);
        if (t != null)
            model.addAttribute("tournament", t);
        return GAME + "editTournament";
    }

    @PostMapping(ADMIN_PATH + TOUR_FOLDER + "edit")
    public String checkInTournament(@Valid Tournament tournament, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            gameService.update(tournament);
            model.addAttribute("checkInResult", "Updated");
        }
        model.addAttribute("tournament", tournament);
        return GAME + "editTournament";
    }

    @GetMapping(ADMIN_PATH + TEAMS + "/")
    public ModelAndView teams(Pageable pageable, ModelAndView model) {
        Page<Team> teams = gameService.findAllTeams(pageable);
        model.addObject("teamPage", teams);
        model.setViewName(GAME + TEAMS);
        return model;
    }


    @GetMapping(ADMIN_PATH + TEAMS + "/edit")
    public String editTeam(@RequestParam("id") Long id, Model model) {
        Optional<Team> t = gameService.findTeam(id);
        if (t.isPresent())
            model.addAttribute("team", t.get());
        return GAME + "editTeam";
    }

    @PostMapping(ADMIN_PATH + TEAMS + "/edit")
    public String checkInTeam(@Valid Team team, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            gameService.update(team);
            model.addAttribute("checkInResult", "Updated");
        }
        model.addAttribute("team", team);
        return GAME + "editTeam";
    }

    public static final String ID = "{id:.+}";

    @GetMapping(ADMIN_PATH + TOUR_FOLDER + ID + "/" + GAME)
    public String tournamentGames(@PathVariable Long id, Model model) {
        Tournament t = gameService.findTournament(id);
        model.addAttribute("tournament", t);
        model.addAttribute("games", gameService.findGamesByTournament(t));
        Page<Team> teams = gameService.findAllTeams(Pageable.unpaged());
        model.addAttribute("teams", teams);
        return GAME + "tournamentGames";
    }

    @PostMapping(ADMIN_PATH + TOUR_FOLDER + ID + "/" + GAME + "editGame")
//    @ResponseBody
    public String editTournamentGames(Game game, BindingResult bindingResult, ScoreResult scoreResult, Model model) {

        BetResult betResult = accService.processScoreResult(game, scoreResult);
        game.setBetResult(betResult);

        Game savedGame = gameService.save(game);
        model.addAttribute("tournament", savedGame.getTournament());
        Page<Team> teams = gameService.findAllTeams(Pageable.unpaged());
        model.addAttribute("teams", teams);
        model.addAttribute("aGame", savedGame);
        model.addAttribute("gameUpdated", new Date());
        return "fragments/cardForm :: cardEditForm";
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER + ID + "/" + GAME + "newGame")
    public String newGame(@PathVariable Long id, Model model) {
        Tournament t = gameService.findTournament(id);
        Game savedGame = gameService.save(new Game(t));
        return "redirect:";
    }
}
