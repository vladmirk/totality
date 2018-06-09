package com.vk.totality;

import com.vk.totality.game.*;
import com.vk.totality.user.User;
import com.vk.totality.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.vk.totality.user.UserRoles.ADMIN;
import static com.vk.totality.user.UserRoles.USER;

@Service
public class InitAppData {

    @Bean
    @Profile("dev")
    CommandLineRunner setUp(final UserRepository userRepository, final TournamentRepository tournamentRepository,
                            final TeamRepository teamRepository, final GameRepository gameRepository,
                            final UserTournamentRepository userTournamentRepository) {
        System.out.println("Init");


        //        FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));
//        Files.createDirectories(Paths.get(UPLOAD_ROOT));
//
//
        return (args) -> {
//
            List<User> users = initUsers(userRepository);
            List<Tournament> tournaments = initTournaments(tournamentRepository);
            List<Team> teams = initTeams(teamRepository);
            List<Game> games = initGames(gameRepository, teams);
            List<UserTournament> userTournaments = initUserTournaments(userTournamentRepository, users, tournaments);


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

    private List<UserTournament> initUserTournaments(UserTournamentRepository repo, List<User> users, List<Tournament> tournaments) {
        assert (users.size() > 0);
        assert (tournaments.size() > 0);
        return Arrays.asList(repo.save(new UserTournament(users.get(0), tournaments.get(0), true)));
    }

    private List<Game> initGames(GameRepository gameRepository, List<Team> teams) {
        assert (teams.size() > 1);
        Game game = new Game(new Date(), teams.get(0), teams.get(1), 3, 4);
        gameRepository.save(game);
        return Arrays.asList(game);
    }

    private List<Team> initTeams(TeamRepository teamRepository) {
        List<Team> teams = new ArrayList<>();

        teams.add(teamRepository.save(new Team("Англия", true)));
        teams.add(teamRepository.save(new Team("Германия", true)));
        teams.add(teamRepository.save(new Team("Россия", true)));
        return teams;
    }

    private List<Tournament> initTournaments(TournamentRepository tournamentRepository) {
        return Arrays.asList(
                tournamentRepository.save(new Tournament("Фифа", true)),
                tournamentRepository.save(new Tournament("Домашний", true)),
                tournamentRepository.save(new Tournament("Никакой", false)));
    }

    private List<User> initUsers(UserRepository userRepository) {
        List<User> users = new ArrayList<>();
        users.add(userRepository.save(new User("aaa", "pass", true, USER.toString(), ADMIN.toString())));
        users.add(userRepository.save(new User("bbb", "pass", true, USER.toString())));
        users.add(userRepository.save(new User("ccc", "pass", false, USER.toString())));
        return users;
    }
}
