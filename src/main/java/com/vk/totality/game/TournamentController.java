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

import static com.vk.totality.HomeController.ADMIN_PATH;

@Controller
public class TournamentController {
    public static final String TOURNAMENT = "tournament";
    public static final String GAME = "game/";
    public static final String TOUR_FOLDER = TOURNAMENT + "/";
    private TournamentService service;

    @Autowired
    public TournamentController(TournamentService service) {
        this.service = service;
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER)
    public ModelAndView tournament(ModelAndView model, Pageable pageable) {
        Page<Tournament> tournaments = service.findAll(pageable);
        model.addObject("page", tournaments);
        model.setViewName(GAME + TOURNAMENT);
        return model;
    }

    @GetMapping(ADMIN_PATH + TOUR_FOLDER + "edit")
    public String editTournament(@RequestParam("id") Long id, Model model) {
        Tournament t = service.find(id);
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

}
