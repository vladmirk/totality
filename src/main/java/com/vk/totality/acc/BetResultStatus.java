package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public enum BetResultStatus implements ResultStatusMatcher {
    SCORE("Счет", new ScoreResultMatcher(), BigDecimal.ONE),
    SCORE_DIFF("Разница голов", new ScoreDiffResultMatcher(), new BigDecimal(0.7)),
    OUTCOME("Исход", new OutcomeResultMatcher(), new BigDecimal(0.5)),
    LOOSE("Лузер", new LooseResultMatcher(), BigDecimal.ZERO);


    private String description;
    private ResultStatusMatcher matcher;
    private BigDecimal ratio;

    private BetResultStatus(String description, ResultStatusMatcher matcher, BigDecimal ratio) {
        this.description = description;
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

    public Integer percent() {
        return ratio().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static List<BetResultStatus> winStatuses() {
        List<BetResultStatus> s = new ArrayList<>();
        for (BetResultStatus status : values())
            if (status.ratio.compareTo(BigDecimal.ZERO) > 0)
                s.add(status);
        return s;
    }


    @Override
    public String toString() {
        return description;
    }
}
