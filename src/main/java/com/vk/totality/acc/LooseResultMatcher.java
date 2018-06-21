package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

public class LooseResultMatcher implements ResultStatusMatcher {

    @Override
    public boolean matches(Bet bet, BetResult betResult) {
        for (BetResultStatus s : BetResultStatus.winStatuses()) {
            if (s.matches(bet, betResult)) return false;
        }
        return true;
    }
}
