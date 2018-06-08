package com.vk.totality;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    public static final String ADMIN_PATH = "/admin/";

    @GetMapping({"/", "/index.html"})
    public String index() {
        if (authed())
            return "index";

        return "login";
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
}
