package com.vk.totality.game;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    List<Game> findGameByTournamentOrderByStartDate(Tournament tournament);
}
