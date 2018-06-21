package com.vk.totality.game;

import org.springframework.data.repository.CrudRepository;

public interface BetResultRepository extends CrudRepository<BetResult, Long> {
    BetResult findFirstByGame(Game game);
}
