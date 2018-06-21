package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

public class ScoreResultMatcher implements ResultStatusMatcher {

    @Override
    public boolean matches(Bet bet, BetResult betResult) {
        if (bet.getScore1() == null || bet.getScore2() == null)
            return false;
        return bet.getScore1().equals(betResult.getTeam1ResultScore()) && bet.getScore2().equals(betResult.getTeam2ResultScore());
    }
}
