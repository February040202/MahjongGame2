package mahjong.game;

import mahjong.model.MahjongTile;

/**
 * 状态机
 */
public class GameState {//为了监控麻将什么时候出几张牌

  public long getTick() {
    return tick;
  }

  private long tick;

  public void setLastTile(MahjongTile last) {
    this.last = last;
  }

  MahjongTile last;
  String gameInfo;
  // 00000 (4select,3select,2select,,1select,touch)
  // 00001 can touch
  // 00010 can 1select one to play
  // 00100 can 2select like chow and pong
  // 01000 can 3select like gang other
  // 10000 can 4select like gang self
  int touchSelectState;
  private int turn;

  public GameState(long tick, MahjongTile last) {
    this.last = last;
    this.tick = tick;
    turn = 0;
    touchSelectState = 1;

  }


  public int getTurn() {
    return turn;
  }

  public String getGameInfo() {
    gameInfo = "player " + turn + "'s turn!!!  tile played is " + last.getKeyString().substring(last.getKeyString().indexOf('-') + 1);
    return gameInfo;
  }

  public boolean canTouch() {
    return (touchSelectState & 1) == 1;
  }


  public void goNextState(int state) {
    touchSelectState = state;

  }

  public void nextTick() {
    tick++;
    if (tick > 100000000000L) {
      tick = 1;
    }
  }

  public void nextTurn() {
    turn = (turn + 1) % 4;
  }


  public boolean canSelect(int num) {
    return (touchSelectState >> num & 1) == 1;
  }
}
