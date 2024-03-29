package com.vk.totality.game;

import com.vk.totality.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private ResourceLoader resource;


    @Autowired
    public GameService(TournamentRepository tournamentRepository, TeamRepository teamRepository, UserTournamentRepository userTournamentRepository, final GameRepository gameRepository, ResourceLoader resource) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.userTournamentRepository = userTournamentRepository;
        this.gameRepository = gameRepository;
        this.resource = resource;
    }

    public Page<Tournament> findAllTournaments(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }


    public Page<Team> findAllTeams(Pageable pageable) {
        if (pageable.isPaged()) {
            pageable = PageRequest.of(pageable.getPageNumber(), 100, getTeamSorter());
        } else {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, getTeamSorter());
        }
        return teamRepository.findAll(pageable);
    }

    private Sort getTeamSorter() {
        return Sort.by(Sort.Direction.ASC, "name");
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

    public List<UserTournament> findUserTournaments(Tournament tournament) {
        return userTournamentRepository.findUserTournamentByTournamentAndActiveTrue(tournament);
    }

    public int countUserTournaments(Tournament tournament) {
        return userTournamentRepository.countUserTournamentByTournamentAndActiveTrue(tournament);
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

    public Game findGame(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        return g.isPresent() ? g.get() : null;
    }

    private final static String TEAM_ICON_ROOT = "img/teams/";

    public Resource findOneImage(String name) {
        String path = TEAM_ICON_ROOT + name;
        for (String ext : new String[]{".png", ".jpg", ".jpeg", ".svg"}) {
            Resource file = resource.getResource("file:" + path + ext);
            if (file.exists())
                return file;
        }
        return getDefaultTeamIcon();
    }

    public Resource getDefaultTeamIcon() {
        return resource.getResource("file:" + TEAM_ICON_ROOT + "blank.jpg");
    }
}
