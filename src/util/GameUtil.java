package util;

import mahjong.model.MahjongTile;

import java.util.Random;

public class GameUtil {
  public static String tile2ImgPath(MahjongTile tile) {
    return String.format("img/tile/%s/%s.png", tile.getType(), tile.getValue());
  }

  public static String pubTile2ImgPath(MahjongTile tile) {
    return String.format("img/tile/rotation/%s/%s.png", tile.getType(), tile.getValue());
  }

  public static String playerId2ImgPath(int id) {
    if (id == 0) {
      return "img/tile/tile_Face.png";
    } else if (id == 1) {
      return "img/tile/tile_lay2.png";
    } else if (id == 2) {
      return "img/tile/tile_Back.png";
    } else if (id == 3) {
      return "img/tile/tile_lay.png";
    } else if (id == 5) {
      return "img/tile/tile_Face_pub.png";
    }

    return "";
  }

  public static int randomInteger(int lowerBound, int upperBound) {
    return new Random().nextInt(upperBound - lowerBound + 1) + lowerBound;
  }
}
