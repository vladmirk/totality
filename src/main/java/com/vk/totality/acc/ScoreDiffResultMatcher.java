package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

public class ScoreDiffResultMatcher implements ResultStatusMatcher {

    @Override
    public boolean matches(Bet bet, BetResult betResult) {
        if (bet.getScore1() == null || bet.getScore2() == null)
            return false;
        return bet.getScore1() - bet.getScore2() == betResult.getTeam1ResultScore() - betResult.getTeam2ResultScore();
    }
}
