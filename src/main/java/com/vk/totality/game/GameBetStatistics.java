package com.vk.totality.game;

public class GameBetStatistics {
  private Integer betCount;
  private Integer betFilledCount;

  public Integer getBetCount() {
    return betCount;
  }
  public void setBetCount(Integer betCount) {
    this.betCount = betCount;
  }
  public Integer getBetFilledCount() {
    return betFilledCount;
  }
  public void setBetFilledCount(Integer betFilledCount) {
    this.betFilledCount = betFilledCount;
  }

  public Integer getPercentage() {
    if (getBetFilledCount() == null || getBetFilledCount() == 0)
      return 0;
    if (getBetFilledCount() == getBetCount())
      return 100;
    return (int) Math.round((double) getBetFilledCount() / getBetCount() * 100);
  }
}
