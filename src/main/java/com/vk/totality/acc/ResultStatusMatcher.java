package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

public interface ResultStatusMatcher {
    boolean matches(Bet bet, BetResult betResult);
}
