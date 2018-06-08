package com.vk.totality.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TournamentRepository extends PagingAndSortingRepository<Tournament, Long> {

    Page<Tournament> findAllByActiveTrue(Pageable pageable);

    Tournament findTournamentById(Long id);
}
