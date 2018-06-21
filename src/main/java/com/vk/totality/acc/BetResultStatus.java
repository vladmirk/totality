package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public enum BetResultStatus implements ResultStatusMatcher {
    SCORE(new ScoreResultMatcher(), BigDecimal.ONE),
    SCORE_DIFF(new ScoreDiffResultMatcher(), new BigDecimal(0.7)),
    OUTCOME(new OutcomeResultMatcher(), new BigDecimal(0.5)),
    LOOSE(new LooseResultMatcher(), BigDecimal.ZERO);


    private ResultStatusMatcher matcher;
    private BigDecimal ratio;

    private BetResultStatus(ResultStatusMatcher matcher, BigDecimal ratio) {
        this.matcher = matcher;
        this.ratio = ratio;
    }

    @Override
    public boolean matches(Bet bet, BetResult betResult) {
        return matcher.matches(bet, betResult);
    }

    public BigDecimal ratio() {
        return ratio;
    }

    public static List<BetResultStatus> winStatuses() {
        List<BetResultStatus> s = new ArrayList<>();
        for (BetResultStatus status : values())
            if (status.ratio.compareTo(BigDecimal.ZERO) > 0)
                s.add(status);
        return s;
    }

}
