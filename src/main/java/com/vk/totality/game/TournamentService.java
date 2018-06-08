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

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Page<Tournament> findAll(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }

    public Page<Tournament> findAllActive(Pageable pageable) {
        return tournamentRepository.findAllByActiveTrue(pageable);
    }

    public Tournament find(Long id) {
        Optional<Tournament> t =
                tournamentRepository.findById(id);
        return t.isPresent() ? t.get() : null;
    }

    public void update(@Valid Tournament tournament) {
        tournamentRepository.save(tournament);
    }
}
