package com.vk.totality.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static com.vk.totality.HomeController.ADMIN_PATH;

@Controller
public class GameController {
    public static final String TOURNAMENT = "tournament";
    public static final String GAME = "game/";
    public static final String TEAMS = "teams";

    public static final String TOUR_FOLDER = TOURNAMENT + "/";
    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER)
    public ModelAndView tournament(ModelAndView model, Pageable pageable) {
        Page<Tournament> tournaments = service.findAllTournaments(pageable);
        model.addObject("page", tournaments);
        model.setViewName(GAME + TOURNAMENT);
        return model;
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER + "edit")
    public String editTournament(@RequestParam("id") Long id, Model model) {
        Tournament t = service.findTournament(id);
        if (t != null)
            model.addAttribute("tournament", t);
        return GAME + "editTournament";
    }

    @PostMapping(ADMIN_PATH + TOUR_FOLDER + "edit")
    public String checkInTournament(@Valid Tournament tournament, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            service.update(tournament);
            model.addAttribute("checkInResult", "Updated");
        }
        model.addAttribute("tournament", tournament);
        return GAME + "editTournament";
    }

    @GetMapping(ADMIN_PATH + TEAMS + "/")
    public ModelAndView teams(Pageable pageable, ModelAndView model) {
        Page<Team> teams = service.findAllTeams(pageable);
        model.addObject("teamPage", teams);
        model.setViewName(GAME + TEAMS);
        return model;
    }


    @GetMapping(ADMIN_PATH + TEAMS + "/edit")
    public String editTeam(@RequestParam("id") Long id, Model model) {
        Optional<Team> t = service.findTeam(id);
        if (t.isPresent())
            model.addAttribute("team", t.get());
        return GAME + "editTeam";
    }

    @PostMapping(ADMIN_PATH + TEAMS + "/edit")
    public String checkInTeam(@Valid Team team, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            service.update(team);
            model.addAttribute("checkInResult", "Updated");
        }
        model.addAttribute("team", team);
        return GAME + "editTeam";
    }


}
