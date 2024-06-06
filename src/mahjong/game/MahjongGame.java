package mahjong.game;

import engine.Game;
import mahjong.model.ImageButton;
import mahjong.model.MahjongDeck;
import mahjong.model.MahjongTile;
import mahjong.model.Player;
import ui.Constant;
import ui.listener.PlayerListener;
import util.GameUtil;

import java.util.ArrayList;
import java.util.List;

import static ui.screen.GameScreen.playerWH;
import static ui.screen.GameScreen.playerXY;

public class MahjongGame implements Game {
  public final PlayerListener listener;
  public Player[] players;
  public List<MahjongTile> usedTile;
  MahjongDeck mahjongDeck;
  GameState gameState;
  boolean isWin = false;
  boolean gameOver = false;

  public MahjongGame(PlayerListener listener) {


    this.listener = listener;
    startNewGame();
  }

  private void initPlayHandTile() {
    players = new Player[4];
    for (int i = 0; i < 4; i++) {
      players[i] = new Player(new ArrayList<>(), i);
      for (int j = 0; j < 13; j++) {
        players[i].handTiles.add(mahjongDeck.getTile());
      }//在为玩家添加完所有牌后，根据MahjongTile的值进行排序。
      players[i].handTiles.sort(MahjongTile::compareTo);//表示引用了MahjongTile类中的compareTo方法
    }
  }

  @Override
  public void updateGame() {
    playMove();
  }

  private void playMove() {
    //    System.out.println(Integer.toBinaryString(gameState.touchSelectState));

    gameState.nextTick();

    handleDraw();

    if (gameState.getTurn() == 0) {

      handleMouseClick();
      handleSelect();
      handleTouch();


    }
    // other player
    else {
      otherPlayerMove();
    }
  }

  private void handleDraw() {
    if (listener.isDrawPressed()) {
      // draw other
      if (gameState.canTouch()) {
        if (gameState.last != mahjongDeck.getNullTile()) {
          List<MahjongTile> cur = new ArrayList<>(players[0].handTiles);
          cur.add(gameState.last);

          if (GameRule.canDraw(cur)) {
            players[0].publicTiles.add(gameState.last);
            usedTile.remove(usedTile.size() - 1);
            playersOver();
          }
        }
      }
      // draw self
      else {
        if (GameRule.canDraw(players[0].handTiles)) {
          playersOver();
        }
      }

      listener.buttonReleasedOrExited(ImageButton.ButtonType.DRAW);
    }
  }

  private void handleMouseClick() {
    List<MahjongTile> ownerPlayerHand = players[0].handTiles;
    if (listener.isMouseClick()) {
      for (int i = 0; i < ownerPlayerHand.size(); i++) {
        MahjongTile curTile = ownerPlayerHand.get(i);
        int x = playerXY[0][0] + i * playerXY[0][2];
        int y = playerXY[0][1] + i * playerXY[0][3] - (curTile.isChoose() ? 30 : 0);
        if (listener.clickXYInRange(x, y, x + playerWH[0][0], y + playerWH[0][1])) {
          curTile.filpChoose();
        }
      }

      listener.setMouseClick(false);
    }
  }

  private void handleSelect() {
    List<MahjongTile> ownerPlayerHand = players[0].handTiles;
    if (listener.isSelectPressed()) {
      List<MahjongTile> chooseTiles = new ArrayList<>();
      for (MahjongTile curTile : ownerPlayerHand) {
        if (curTile.isChoose()) {
          chooseTiles.add(curTile);
        }
      }

      // select one tile to play
      if (chooseTiles.size() == 1 && gameState.canSelect(1)) {
        usedTile.addAll(chooseTiles);
        ownerPlayerHand.removeAll(chooseTiles);
        ownerPlayerHand.sort(MahjongTile::compareTo);

        listener.buttonReleasedOrExited(ImageButton.ButtonType.SELECT);
        // 01101
        gameState.goNextState(13);
        gameState.nextTurn();
      }

      // select two tiles to play
      else if (chooseTiles.size() == 2 && gameState.canSelect(2)) {
        if (GameRule.canChow(gameState.last, chooseTiles.get(0), chooseTiles.get(1)) || GameRule.canPong(gameState.last, chooseTiles.get(0), chooseTiles.get(1))) {
          players[0].publicTiles.addAll(chooseTiles);
          players[0].publicTiles.add(gameState.last);

          gameState.setLastTile(mahjongDeck.getNullTile());

          usedTile.remove(usedTile.size() - 1);
          ownerPlayerHand.removeAll(chooseTiles);
          listener.buttonReleasedOrExited(ImageButton.ButtonType.SELECT);
          // 10010
          gameState.goNextState(18);

        }
      }
      // select three tiles to play
      else if (chooseTiles.size() == 3 && gameState.canSelect(3)) {
        if (GameRule.canGang(gameState.last, chooseTiles.get(0), chooseTiles.get(1), chooseTiles.get(2))) {
          players[0].publicTiles.addAll(chooseTiles);
          players[0].publicTiles.add(gameState.last);

          gameState.setLastTile(mahjongDeck.getNullTile());

          usedTile.remove(usedTile.size() - 1);
          ownerPlayerHand.removeAll(chooseTiles);
          // 00001
          gameState.goNextState(1);
        }
      }

      // select four tiles to play
      else if (chooseTiles.size() == 4 && gameState.canSelect(4)) {
        if (GameRule.canGang(chooseTiles.get(0), chooseTiles.get(1), chooseTiles.get(2), chooseTiles.get(3))) {
          players[0].publicTiles.addAll(chooseTiles);

          ownerPlayerHand.removeAll(chooseTiles);
          // 00001
          gameState.goNextState(1);
        }
      }

      listener.buttonReleasedOrExited(ImageButton.ButtonType.SELECT);
    }
  }

  private void handleTouch() {
    List<MahjongTile> ownerPlayerHand = players[0].handTiles;
    if (gameState.canTouch() && listener.isTouchPressed()) {
      MahjongTile touchTile = mahjongDeck.getTile();
      if (touchTile == null) {
        playersOver();
        return;
      }
      ownerPlayerHand.add(touchTile);
      listener.buttonReleasedOrExited(ImageButton.ButtonType.TOUCH);
      // 10010
      gameState.goNextState(18);
    }
  }

  private void otherPlayerMove() {
    int turn = gameState.getTurn();
    if (gameState.getTick() % 60 == 0) {
      MahjongTile tile = mahjongDeck.getTile();
      if (tile == null) {
        playersOver();
        return;
      }
      players[turn].handTiles.add(tile);
      int selectTileIdx = GameUtil.randomInteger(0, players[turn].handTiles.size() - 1);
      MahjongTile selectTile = players[turn].handTiles.get(selectTileIdx);
      usedTile.add(selectTile);
      gameState.setLastTile(selectTile);
      players[turn].handTiles.remove(selectTile);
      players[turn].handTiles.sort(MahjongTile::compareTo);

      gameState.nextTurn();
    }
  }

  private void playersOver() {
    for (Player player : players) {
      player.publicTiles.addAll(player.handTiles);
      player.handTiles.clear();
    }
    isWin = true;
  }

  @Override
  public void startNewGame() {
    listener.resetFlag();
    mahjongDeck = new MahjongDeck();
    usedTile = new ArrayList<>();

    gameState = new GameState(0, mahjongDeck.getNullTile());

    initPlayHandTile();

  }


  @Override
  public boolean isPlayerAlive() {
    return !isWin;
  }


  @Override
  public boolean isGameOver() {

    return gameOver;
  }

  @Override
  public int getScreenWidth() {
    return Constant.SCREEN_WIDTH;
  }

  @Override
  public int getScreenHeight() {
    return Constant.SCREEN_HEIGHT;
  }

  public String getGameInfo() {
    return gameState.getGameInfo();
  }

  public int getTurn(){
    return gameState.getplayerTurn();
  }
}
