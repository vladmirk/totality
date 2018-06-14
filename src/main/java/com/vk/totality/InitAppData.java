package com.vk.totality;

import com.vk.totality.acc.AccOperation;
import com.vk.totality.acc.Account;
import com.vk.totality.acc.AccountRepository;
import com.vk.totality.game.*;
import com.vk.totality.user.User;
import com.vk.totality.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.vk.totality.user.UserRoles.ADMIN;
import static com.vk.totality.user.UserRoles.USER;

@Service
public class InitAppData {

    @Bean
    @Profile("production")
    CommandLineRunner setUpProduction(final UserRepository userRepository, final TournamentRepository tournamentRepository,
                                      final TeamRepository teamRepository, final GameRepository gameRepository,
                                      final UserTournamentRepository userTournamentRepository, final AccountRepository accountRepository) {
        System.out.println("Init Production");
        return (args) -> {

            String adminName = "admin";
            User admin = userRepository.findUserByUserLogin(adminName);

            if (admin == null) {
                userRepository.save(new User(adminName, "Guardian$", true, USER.toString(), ADMIN.toString()));
            } else {
                if (!admin.getActive())
                    admin.setActive(true);
                userRepository.save(admin);
            }
        };
    }


    @Bean
    @Profile("dev")
    CommandLineRunner setUp(final UserRepository userRepository, final TournamentRepository tournamentRepository,
                            final TeamRepository teamRepository, final GameRepository gameRepository,
                            final UserTournamentRepository userTournamentRepository, final AccountRepository accountRepository) {
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
            List<Game> games = initGames(gameRepository, tournaments, teams);
            List<UserTournament> userTournaments = initUserTournaments(userTournamentRepository, users, tournaments);

            initUserAccount(accountRepository, userTournaments);
        };
    }

    private void initUserAccount(AccountRepository accountRepository, List<UserTournament> userTournaments) {
        assert !userTournaments.isEmpty();

        accountRepository.save(new Account(userTournaments.get(0), new BigDecimal(200), AccOperation.CASH_IN, new Date()));
    }

    private List<UserTournament> initUserTournaments(UserTournamentRepository repo, List<User> users, List<Tournament> tournaments) {
        assert (users.size() > 0);
        assert (tournaments.size() > 0);
        return Arrays.asList(repo.save(new UserTournament(users.get(0), tournaments.get(0), true)));
    }

    private List<Game> initGames(GameRepository gameRepository, List<Tournament> tournaments, List<Team> teams) {
        assert (teams.size() > 1);
        assert !tournaments.isEmpty();
        Game game = new Game(tournaments.get(0), new Date(), teams.get(0), teams.get(1), BigDecimal.valueOf(10));
        game = gameRepository.save(game);

        Game game2 = new Game(tournaments.get(0), new Date(), teams.get(2), teams.get(1), BigDecimal.valueOf(20));
        game2 = gameRepository.save(game2);


        return Arrays.asList(game, game2);
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
