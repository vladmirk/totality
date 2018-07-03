package com.vk.totality.acc;

import com.vk.totality.game.BetResult;
import com.vk.totality.game.Tournament;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetResultItemRepository extends CrudRepository<BetResultItem, Long> {

    BetResultItem findFirstByBet(Bet bet);

    List<BetResultItem> findBetResultItemsByBetResult(BetResult betResult);

    Integer removeBetResultItemsByBetResult(BetResult betResult);

    //order by 4 desc
//    select u.user_login, sum(bti.win_amount) Win, sum(bti.rate) as Cost,  sum(bti.win_amount) -  sum(bti.rate)  Game_Balance
    @Query(" select new com.vk.totality.acc.TournamentResultItem(u.userLogin, sum(bti.winAmount), sum (bti.rate)) " +
            " from BetResultItem bti " +
            " inner join bti.bet b " +
            " inner join b.userTournament ut " +
            " inner join ut.user u " +
            " where ut.tournament = :tournament" +
            " group by u.userLogin " +
            " ORDER BY (sum(bti.winAmount) - sum(bti.rate)) desc"
    )
    List<TournamentResultItem> calcTournamentResult(@Param("tournament") Tournament tournament);


}
