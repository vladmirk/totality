package com.vk.totality.acc;

import com.vk.totality.game.Game;
import com.vk.totality.game.UserTournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BetService {

    private BetRepository betRepository;


    public Bet save(Bet bet) {
        return betRepository.save(bet);
    }

    @Autowired
    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet findOrCreateBet(Game game, UserTournament userTournament) {
        Bet bet = betRepository.findBetByUserTournamentAndGame(userTournament, game);
        if (bet == null) {
            bet = new Bet(userTournament, game, new Date());
            bet = betRepository.save(bet);
        }
        return bet;
    }

    public Optional<Bet> findById(Long id) {
        return betRepository.findById(id);
    }


}
