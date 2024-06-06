package mahjong.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
  public List<MahjongTile> handTiles;
  public List<MahjongTile> publicTiles;
  public int id;

  public Player(List<MahjongTile> handTiles, int id) {
    this.handTiles = handTiles;
    this.id = id;
    publicTiles = new ArrayList<>();
  }

  public boolean isOwnerPlayer() {
    return id == 0;
  }

  public List<MahjongTile> getHand() {
    return handTiles;
  }

  public void drawTile(MahjongTile tile) {
    handTiles.add(tile);
  }

  public void discardTile(MahjongTile tile) {
    handTiles.remove(tile);
  }
}

