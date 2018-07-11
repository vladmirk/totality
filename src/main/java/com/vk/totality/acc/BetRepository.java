package com.vk.totality.acc;

import com.vk.totality.game.BetsPerGame;
import com.vk.totality.game.Game;
import com.vk.totality.game.Tournament;
import com.vk.totality.game.UserTournament;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetRepository extends PagingAndSortingRepository<Bet, Long> {
    Bet findBetByUserTournamentAndGame(UserTournament userTournament, Game game);

    Integer countByGameAndScore1IsNotNullAndScore2IsNotNull(Game game);

    @Query("SELECT  new com.vk.totality.game.BetsPerGame(b.game, count (b)) from Bet b where b.userTournament.tournament = :tournament AND b.score1 is not NULL AND b.score2 is not NULL " +
            "group by b.game")
    List<BetsPerGame> countBetsPerGame(@Param("tournament") Tournament tournament);
}
