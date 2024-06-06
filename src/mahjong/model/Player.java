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
}
