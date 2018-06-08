package com.vk.totality.user;

import com.vk.totality.ValidationException;
import com.vk.totality.user.form.UserListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.vk.totality.HomeController.ADMIN_PATH;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public static final String USER_FOLDER = "user/";
    public static final String USER_PATH = "/" + USER_FOLDER;
    public static final String ID = "{id:.+}";


    @RequestMapping(USER_PATH + "newUser")
    public String user(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return USER_FOLDER + "newUser";
    }

    @RequestMapping(ADMIN_PATH + "editUser/" + ID)
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        if (user == null)
            throw new RuntimeException("User with id " + id + " not found");

        model.addAttribute("userDTO", userService.toDTO(user));
        return USER_FOLDER + "editUser";
    }


    @GetMapping(USER_PATH + "userResult")
    public String userResult() {
        return USER_FOLDER + "userResult";
    }


    @RequestMapping(method = RequestMethod.POST, value = "checkInUser")
    public String checkInUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        String editTemplate = newUser(userDTO) ? USER_FOLDER + "newUser" : "/admin/editUser/" + userDTO.getId();
        if (bindingResult.hasErrors())
            return editTemplate;

        User user = getUser(userDTO);
        try {
            userService.update(user, userDTO);
            model.addAttribute("checkInResult", "Updated");
        } catch (ValidationException e) {
            model.addAttribute("validation", e.getMessage());
            return editTemplate;
        }
        return newUser(userDTO) ? "redirect:/" + USER_FOLDER + "userResult" : USER_FOLDER + "editUser";
    }

    @GetMapping(ADMIN_PATH + USER_FOLDER)
    public String users(Model model) {
        UserListForm form = new UserListForm();
        model.addAttribute("userForm", form);
        List<UserDTO> userDtos = new ArrayList<>();
        for (User u : userService.findAll()) {
            userDtos.add(userService.toDTO(u));
        }
        model.addAttribute("userList", userDtos);
        return USER_FOLDER + "users";
    }

    @PostMapping(ADMIN_PATH + USER_FOLDER)
    public String users(UserListForm form, Model model) {
        if (form.getLoginId() == null) {
            model.addAttribute("warnMessage", "User Not Found");
            return users(model);
        }

        return "redirect:" + ADMIN_PATH + "editUser/" + form.getLoginId();


    }


    private User getUser(UserDTO userDTO) {
        if (newUser(userDTO))
            return new User();
        User u = userService.findUserById(userDTO.getId());
        if (u == null)
            throw new RuntimeException("User is not found " + u.toString());
        return u;
    }

    private boolean newUser(UserDTO userDTO) {
        return userDTO.getId() == null;
    }


}
