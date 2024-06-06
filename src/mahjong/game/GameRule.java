package mahjong.game;

import mahjong.model.MahjongTile;
import mahjong.model.TileValueDict;

import java.util.*;

public class GameRule {

  public static TileValueDict tileValueDict = TileValueDict.getInstance();

  public static boolean canChow(MahjongTile a, MahjongTile b, MahjongTile c) {
    List<Integer> tiles = new ArrayList<>();
    tiles.add(tileValueDict.getValue(a.getKeyString()));
    tiles.add(tileValueDict.getValue(b.getKeyString()));
    tiles.add(tileValueDict.getValue(c.getKeyString()));
    Collections.sort(tiles);
    return tiles.get(0) + 1 == tiles.get(1) && tiles.get(1) + 1 == tiles.get(2);
  }

  public static boolean canPong(MahjongTile a, MahjongTile b, MahjongTile c) {
    List<Integer> tiles = new ArrayList<>();
    tiles.add(tileValueDict.getValue(a.getKeyString()));
    tiles.add(tileValueDict.getValue(b.getKeyString()));
    tiles.add(tileValueDict.getValue(c.getKeyString()));
    Collections.sort(tiles);
    return Objects.equals(tiles.get(0), tiles.get(1)) && Objects.equals(tiles.get(1), tiles.get(2));
  }

  public static boolean canGang(MahjongTile a, MahjongTile b, MahjongTile c, MahjongTile d) {
    List<Integer> tiles = new ArrayList<>();
    tiles.add(tileValueDict.getValue(a.getKeyString()));
    tiles.add(tileValueDict.getValue(b.getKeyString()));
    tiles.add(tileValueDict.getValue(c.getKeyString()));
    tiles.add(tileValueDict.getValue(d.getKeyString()));
    Collections.sort(tiles);
    return Objects.equals(tiles.get(0), tiles.get(1)) && Objects.equals(tiles.get(1), tiles.get(2)) && Objects.equals(tiles.get(2), tiles.get(3));
  }

  public static boolean canDraw(List<MahjongTile> list) {
    list.sort(MahjongTile::compareTo);
    List<Integer> tilesValue = new ArrayList<>();
    for (MahjongTile tile : list) {
      tilesValue.add(tileValueDict.getValue(tile.getKeyString()));
    }
    Collections.sort(tilesValue);

    // 7 pair
    if (isSevenParis(tilesValue)) {
      return true;
    }
    return canDrawDfs(tilesValue, false);

  }

  private static boolean canDrawDfs(List<Integer> tilesValue, boolean hasOnePair) {
    if (tilesValue.isEmpty()) {
      return hasOnePair;
    }
    boolean res = false;
    if (tilesValue.size() >= 2) {
      if (!hasOnePair && canDrawTwo(tilesValue.get(0), tilesValue.get(1))) {
        List<Integer> newTilesValue = new ArrayList<>(tilesValue.subList(2, tilesValue.size()));
        res |= canDrawDfs(newTilesValue, true);
      }
    }

    if (tilesValue.size() >= 3) {
      if (canDrawTwo(tilesValue.get(0), tilesValue.get(2))) {
        List<Integer> newTilesValue = new ArrayList<>(tilesValue.subList(3, tilesValue.size()));
        res |= canDrawDfs(newTilesValue, hasOnePair);
      }
    }

    if (tilesValue.size() >= 4) {
      if (canDrawTwo(tilesValue.get(0), tilesValue.get(3))) {
        List<Integer> newTilesValue = new ArrayList<>(tilesValue.subList(4, tilesValue.size()));
        res |= canDrawDfs(newTilesValue, hasOnePair);
      }
    }

    int cnt = 1;
    Set<Integer> vis = new HashSet<>();
    vis.add(0);
    for (int i = 1; i < tilesValue.size(); i++) {
      if (tilesValue.get(i) == tilesValue.get(0) + cnt) {
        vis.add(i);
        cnt += 1;
      }
      if (cnt == 3) {
        List<Integer> newTilesValue = new ArrayList<>();
        for (int j = 0; j < tilesValue.size(); j++) {
          if (!vis.contains(j)) {
            newTilesValue.add(tilesValue.get(j));
          }
        }
        res |= canDrawDfs(newTilesValue, hasOnePair);
        break;
      }
    }

    return res;
  }


  private static boolean canDrawTwo(Integer a, int b) {
    return a == b;
  }


  private static boolean isSevenParis(List<Integer> tilesValue) {
    if (tilesValue.size() == 14) {
      for (int i = 0; i < 14; i += 2) {
        if (!Objects.equals(tilesValue.get(i), tilesValue.get(i + 1))) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  public static void main(String[] args) {
    List<MahjongTile> list = new ArrayList<>();
    list.add(new MahjongTile("wan", "wan2", 1));
    list.add(new MahjongTile("wan", "wan2", 1));
    list.add(new MahjongTile("wan", "wan2", 1));
    list.add(new MahjongTile("wan", "wan3", 1));
    list.add(new MahjongTile("wan", "wan3", 1));
    list.add(new MahjongTile("wan", "wan4", 1));
    list.add(new MahjongTile("wan", "wan5", 1));
    list.add(new MahjongTile("wan", "wan6", 1));
    System.out.println(canDraw(list));
  }
}