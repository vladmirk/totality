package com.vk.totality.acc;

import com.vk.totality.game.BetResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BetResultItemRepository extends CrudRepository<BetResultItem, Long> {

    BetResultItem findFirstByBet(Bet bet);

    List<BetResultItem> findBetResultItemsByBetResult(BetResult betResult);

    Integer removeBetResultItemsByBetResult(BetResult betResult);

}
