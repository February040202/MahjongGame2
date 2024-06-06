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

  public static boolean canWin(List<MahjongTile> list, MahjongTile lastTile) {
    list.sort(MahjongTile::compareTo);
    List<Integer> tilesValue = new ArrayList<>();
    for (MahjongTile tile : list) {
      tilesValue.add(tileValueDict.getValue(tile.getKeyString()));
    }
    Collections.sort(tilesValue);

    // 7 pair
    if (isSevenParis(tilesValue, lastTile != null ? tileValueDict.getValue(lastTile.getKeyString()) : null)) {
      return true;
    }
    // 13 orphans
    else if (isThirteenOrphans(list)) {
      return true;
    }
    // Normal
    return canWinDfs(tilesValue, false);

  }

  private static boolean canWinDfs(List<Integer> tilesValue, boolean hasOnePair) {
    if (tilesValue.isEmpty()) {
      return hasOnePair;
    }
    boolean res = false;
    if (tilesValue.size() >= 2) {
      if (!hasOnePair && canDrawTwo(tilesValue.get(0), tilesValue.get(1))) {
        List<Integer> newTilesValue = new ArrayList<>(tilesValue.subList(2, tilesValue.size()));
        res |= canWinDfs(newTilesValue, true);
      }
    }

    if (tilesValue.size() >= 3) {
      if (canDrawTwo(tilesValue.get(0), tilesValue.get(2))) {
        List<Integer> newTilesValue = new ArrayList<>(tilesValue.subList(3, tilesValue.size()));
        res |= canWinDfs(newTilesValue, hasOnePair);
      }
    }

    if (tilesValue.size() >= 4) {
      if (canDrawTwo(tilesValue.get(0), tilesValue.get(3))) {
        List<Integer> newTilesValue = new ArrayList<>(tilesValue.subList(4, tilesValue.size()));
        res |= canWinDfs(newTilesValue, hasOnePair);
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
        res |= canWinDfs(newTilesValue, hasOnePair);
        break;
      }
    }

    return res;
  }


  private static boolean canDrawTwo(Integer a, int b) {
    return a == b;
  }


  private static boolean isSevenParis(List<Integer> tilesValue, Integer lastTile) {
    if (tilesValue.size() == 13 && lastTile != null) {
      List<Integer> sevenParisValue = new ArrayList<>(tilesValue);
      sevenParisValue.add(lastTile);
      Collections.sort(sevenParisValue);
      return isSevenPairsHelper(sevenParisValue);
    } else if (tilesValue.size() == 14) {
      return isSevenPairsHelper(tilesValue);
    }
    return false;
  }

  private static boolean isSevenPairsHelper(List<Integer> tilesValue) {
      for (int i = 0; i < 14; i += 2) {
        if (!Objects.equals(tilesValue.get(i), tilesValue.get(i + 1))) {
          return false;
        }
      }
      return true;
  }

  private static boolean isThirteenOrphans(List<MahjongTile> tiles) {
    if (tiles.size() != 14) {
      return false;
    }

    Set<String> requiredTiles = new HashSet<>(Arrays.asList(
            "wan-wan1", "wan-wan9", "tiao-tiao1", "tiao-tiao9", "tong-tong1", "tong-tong9",
            "bonus-east", "bonus-south", "bonus-west", "bonus-north", "bonus-zhong", "bonus-fa", "bonus-white"
    ));

    Map<String, Integer> tileCount = new HashMap<>();
    for (MahjongTile tile : tiles) {
      String key = tile.getKeyString();
      tileCount.put(key, tileCount.getOrDefault(key, 0) + 1);
      requiredTiles.remove(key);
    }

    // Check if all required tiles are present
    if (!requiredTiles.isEmpty()) {
      return false;
    }

    // Check for the pair
    boolean hasPair = false;
    for (int count : tileCount.values()) {
      if (count == 2) {
        hasPair = true;
        break;
      }
    }

    return hasPair;
  }

  public static void main(String[] args) {
    List<MahjongTile> tiles = new ArrayList<>();
    tiles.add(new MahjongTile("wan", "wan1", 1));
    tiles.add(new MahjongTile("wan", "wan9", 9));
    tiles.add(new MahjongTile("tiao", "tiao1", 11));
    tiles.add(new MahjongTile("tiao", "tiao9", 19));
    tiles.add(new MahjongTile("tong", "tong1", 21));
    tiles.add(new MahjongTile("tong", "tong9", 29));
    tiles.add(new MahjongTile("bonus", "east", 31));
    tiles.add(new MahjongTile("bonus", "south", 35));
    tiles.add(new MahjongTile("bonus", "west", 33));
    tiles.add(new MahjongTile("bonus", "north", 37));
    tiles.add(new MahjongTile("bonus", "zhong", 39));
    tiles.add(new MahjongTile("bonus", "fa", 41));
    tiles.add(new MahjongTile("bonus", "white", 43));
    tiles.add(new MahjongTile("wan", "wan1", 1)); // Pair

    System.out.println(isThirteenOrphans(tiles)); // should print true
  }


}