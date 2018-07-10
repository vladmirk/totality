package com.vk.totality.acc;

import com.vk.totality.game.Game;
import com.vk.totality.game.UserTournament;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BetRepository extends PagingAndSortingRepository<Bet, Long> {
  Bet findBetByUserTournamentAndGame(UserTournament userTournament, Game game);

  Integer countByGameAndScore1IsNotNullAndScore2IsNotNull(Game game);
}
