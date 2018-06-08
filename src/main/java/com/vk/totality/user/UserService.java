package com.vk.totality.user;

import com.vk.totality.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.vk.totality.user.User.EMPTY_PASS;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

//    public void createUser(UserDTO userDTO) throws ValidationException {
//        validateUser(userDTO);
//        User user = new User(userDTO);
//        userRepository.save(user);
//    }

    public void update(User user, UserDTO byUserDTO) throws ValidationException {
        validateUser(byUserDTO);
        user = updateInfo(user, byUserDTO);
        userRepository.save(user);
    }

    private User updateInfo(User user, UserDTO userDTO) {
        user.setId(userDTO.getId());
        user.setActive(userDTO.getActive());
        user.setUserLogin(userDTO.getUserLogin());
        if (!EMPTY_PASS.equals(userDTO.getUserPassword1()))
            user.setUserPassword(userDTO.getUserPassword1());
        user.setActive(userDTO.getActive());
        if (userDTO.getAdmin())
            user.setRoles(new String[]{UserRoles.USER.toString(), UserRoles.ADMIN.toString()});
        else
            user.setRoles(new String[]{UserRoles.USER.toString()});
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setAdmin(Arrays.asList(user.getRoles()).contains(UserRoles.ADMIN.toString()));
        dto.setActive(user.getActive());
        dto.setUserLogin(user.getUserLogin());
        dto.setUserPassword1(EMPTY_PASS);
        dto.setUserPassword2(EMPTY_PASS);

        return dto;
    }


    public void validateUser(UserDTO userDTO) throws ValidationException {
        if (userDTO.getId() == null && userRepository.existsUserByUserLogin(userDTO.getUserLogin())) {
            throw new ValidationException(String.format("Такой логин уже используется. Придумайте другой"));
        }

        if (!userDTO.getUserPassword1().equals(userDTO.getUserPassword2()))
            throw new ValidationException(String.format("Не совпадают пароли"));

    }


}
