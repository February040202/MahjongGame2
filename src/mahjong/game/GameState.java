package mahjong.game;

import mahjong.model.MahjongTile;

/**
 * 状态机
 */

// 这个 GameState 类实现了一个简单的麻将游戏状态管理器，通过时间戳、玩家轮次和触摸选择状态来管理游戏的进行。
// 它提供了方法来获取游戏状态信息、判断是否可以进行特定操作以及更新游戏状态。
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
  private static int turn;

  public GameState(long tick, MahjongTile last) {
    this.last = last;
    this.tick = tick;
    turn = 0;
    touchSelectState = 1;

  }


  public static int getTurn() {
    return turn;
  }

  public String getGameInfo() {
    gameInfo = "player " + turn + "'s turn!!!  tile played is " + last.getKeyString().substring(last.getKeyString().indexOf('-') + 1);
    return gameInfo;
  }
  public int getplayerTurn() {
    return turn;
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
