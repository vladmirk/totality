package com.vk.totality.game;

import com.vk.totality.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private TournamentRepository tournamentRepository;
    private TeamRepository teamRepository;
    private UserTournamentRepository userTournamentRepository;
    private GameRepository gameRepository;


    @Autowired
    public GameService(TournamentRepository tournamentRepository, TeamRepository teamRepository, UserTournamentRepository userTournamentRepository, final GameRepository gameRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.userTournamentRepository = userTournamentRepository;
        this.gameRepository = gameRepository;
    }

    public Page<Tournament> findAllTournaments(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }


    public Page<Team> findAllTeams(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    public List<Tournament> findAllActiveTournaments() {
        return tournamentRepository.findAllByActiveTrue();
    }

    public Tournament findTournament(Long id) {
        Optional<Tournament> t =
                tournamentRepository.findById(id);
        return t.isPresent() ? t.get() : null;
    }

    public Optional<Team> findTeam(Long id) {
        Optional<Team> t =
                teamRepository.findById(id);
        return t;
    }

    public void update(@Valid Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public void update(@Valid Team team) {
        teamRepository.save(team);
    }

    public List<UserTournament> findUserTournaments(User user) {
        return userTournamentRepository.findUserTournamentByUser(user);
    }

    public List<UserTournament> findUserActiveTournaments(User user) {
        return userTournamentRepository.findUserTournamentByUserAndActiveTrue(user);
    }

    public Optional<UserTournament> findUserTournament(Long id) {
        return userTournamentRepository.findById(id);
    }

    public UserTournament findUserTournament(User user, Tournament tournament) {
        return userTournamentRepository.findUserTournamentByUserAndTournament(user, tournament);
    }


    public UserTournament addTournamentToUser(User user, Tournament tournament) {
        return userTournamentRepository.save(new UserTournament(user, tournament, true));
    }

    public UserTournament inactivateUserTournament(UserTournament userTournament) {
        userTournament.setActive(false);
        return userTournamentRepository.save(userTournament);
    }

    public List<Game> findGamesByTournament(Tournament tournament) {
        return gameRepository.findGameByTournamentOrderByStartDate(tournament);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

}
