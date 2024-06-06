package mahjong.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MahjongDeck {// 基本不变
  List<MahjongTile> tilesLibrary;
  int curIndex;
  static MahjongTile nullTile;

  public MahjongDeck() {
    nullTile = new MahjongTile("null", "null", -1);
    initializeTiles();
    shuffleTiles();
  }

  private void initializeTiles() {
    tilesLibrary = new ArrayList<>();
    curIndex = 0;
    int id = 0;
    //初始化牌库
    for (int i = 1; i <= 9; i++) {
      for (int j = 0; j < 4; j++) {
        tilesLibrary.add(new MahjongTile("wan", "wan" + i, id++));
        tilesLibrary.add(new MahjongTile("tiao", "tiao" + i, id++));
        tilesLibrary.add(new MahjongTile("tong", "tong" + i, id++));
      }
    }
    String[] bonus = {"east", "fa", "north", "south", "west", "white", "zhong"};
    for (String b : bonus) {
      for (int i = 0; i < 4; i++) {
        tilesLibrary.add(new MahjongTile("bonus", b, id++));
      }
    }
  }

  public void shuffleTiles() {
    Collections.shuffle(tilesLibrary);
  }

  public MahjongTile getTile() {
    if (curIndex < tilesLibrary.size()) {
      return tilesLibrary.get(curIndex++);
    }
    return null;
  }

  public MahjongTile getNullTile() {
    return nullTile;
  }

}


