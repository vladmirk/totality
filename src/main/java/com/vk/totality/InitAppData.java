package com.vk.totality;

import com.vk.totality.game.Team;
import com.vk.totality.game.TeamRepository;
import com.vk.totality.game.Tournament;
import com.vk.totality.game.TournamentRepository;
import com.vk.totality.user.User;
import com.vk.totality.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.vk.totality.user.UserRoles.ADMIN;
import static com.vk.totality.user.UserRoles.USER;

@Service
public class InitAppData {

    @Bean
    @Profile("dev")
    CommandLineRunner setUp(final UserRepository userRepository, final TournamentRepository tournamentRepository, final TeamRepository teamRepository
    ) {
        System.out.println("Init");


        //        FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));
//        Files.createDirectories(Paths.get(UPLOAD_ROOT));
//
//
        return (args) -> {
//
            User user1 = userRepository.save(new User("aaa", "pass", true, USER.toString(), ADMIN.toString()));
            User user2 = userRepository.save(new User("bbb", "pass", true, USER.toString()));
            User user3 = userRepository.save(new User("ccc", "pass", false, USER.toString()));

            tournamentRepository.save(new Tournament("Фифа", true));
            tournamentRepository.save(new Tournament("Домашний", true));
            tournamentRepository.save(new Tournament("Никакой", false));

            teamRepository.save(new Team("Англия", true));
            teamRepository.save(new Team("Германия", true));
            teamRepository.save(new Team("Россия", true));
//
//
//            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/test"));
//            imageRepository.save(new Image("test", user1));
//
//            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/test2"));
//            imageRepository.save(new Image("test2", user1));
//
//            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/test3"));
//            imageRepository.save(new Image("test3", user2));
        };
    }
}
