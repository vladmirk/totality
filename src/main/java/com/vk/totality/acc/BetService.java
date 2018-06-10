package com.vk.totality.acc;

import com.vk.totality.game.Game;
import com.vk.totality.game.UserTournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BetService {

    private BetRepository betRepository;

    @Autowired
    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet findOrCreateBet(Game game, UserTournament userTournament) {
        Bet bet = betRepository.findBetByUserTournamentAndGame(userTournament, game);
        if (bet == null) {
            bet = new Bet(userTournament, game, new Date());
        }
        return bet;
    }
}
