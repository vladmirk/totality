package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

public class OutcomeResultMatcher implements ResultStatusMatcher {

    @Override
    public boolean matches(Bet bet, BetResult betResult) {
        if (bet.getScore1() == null || bet.getScore2() == null)
            return false;
        int compBet = bet.getScore1().compareTo(bet.getScore2());
        int compRes = betResult.getTeam1ResultScore().compareTo(betResult.getTeam2ResultScore());
        return compBet > 0 && compRes > 0 || compBet < 0 && compRes < 0 || compBet == 0 && compRes == 0;
    }
}
