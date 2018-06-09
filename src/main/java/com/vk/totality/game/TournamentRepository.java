package com.vk.totality.game;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TournamentRepository extends PagingAndSortingRepository<Tournament, Long> {

    List<Tournament> findAllByActiveTrue();

    Tournament findTournamentById(Long id);
}
