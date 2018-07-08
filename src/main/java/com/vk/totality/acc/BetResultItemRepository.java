package com.vk.totality.acc;

import com.vk.totality.game.BetResult;
import com.vk.totality.game.Tournament;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetResultItemRepository extends CrudRepository<BetResultItem, Long> {

    BetResultItem findFirstByBet(Bet bet);

    List<BetResultItem> findBetResultItemsByBetResultOrderByBet_UserTournament_User_UserLogin(BetResult betResult);

    Integer removeBetResultItemsByBetResult(BetResult betResult);

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

    //t.name Tournament, u.user_login User, sum(a.amount) CASH_IN_Amount
//    "order by 3,2"

    @Query("select  new com.vk.totality.acc.UserAccountItemOperation(ut, a.accOperation,sum (a.amount)) " +
            "FROM UserTournament ut " +
            "inner join ut.tournament t on ut.active = 1 " +
            "inner join ut.user u " +
            "left join Account a on a.userTournament = ut " + //AND a.accOperation = 'CASH_IN' " +
            "WHERE t  = :tournament AND  ut.active = 1  AND u.userLogin like :userLogin " +
            "group by  ut, a.accOperation " +
            "order by ut.user "
    )
    List<UserAccountItemOperation> calcTournamentBalances(@Param("tournament") Tournament tournament, @Param("userLogin") String userLogin);

}
