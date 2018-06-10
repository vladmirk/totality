package com.vk.totality;

import com.vk.totality.game.GameService;
import com.vk.totality.game.UserTournament;
import com.vk.totality.user.User;
import com.vk.totality.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.vk.totality.game.GameController.GAME;

@Controller
public class HomeController {


    public static final String ADMIN_PATH = "/admin/";
    private GameService gameService;
    private UserService userService;

    public HomeController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
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
        return GAME + "index";
    }
}


