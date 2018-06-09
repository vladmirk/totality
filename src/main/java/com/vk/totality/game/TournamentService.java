package com.vk.totality.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class TournamentService {

    private TournamentRepository tournamentRepository;
    private TeamRepository teamRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }

    public Page<Tournament> findAllTournaments(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }

    public Page<Team> findAllTeams(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    public Page<Tournament> findAllActive(Pageable pageable) {
        return tournamentRepository.findAllByActiveTrue(pageable);
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
}
