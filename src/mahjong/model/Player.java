package mahjong.model;

import java.util.ArrayList;
import java.util.List;

// 封装（Encapsulation），命令模式（Command Pattern）和角色识别（Role Identification）
public class Player {
  // 玩家手牌列表
  public List<MahjongTile> handTiles;
  // 玩家公开牌列表
  public List<MahjongTile> publicTiles;
  // 玩家唯一标识符
  public int id;

  // 构造函数，初始化玩家手牌和ID
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

