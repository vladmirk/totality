package com.vk.totality.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {

    Page<Team> findAllByActiveTrue(Pageable pageable);

    Team findTournamentById(Long id);
}
