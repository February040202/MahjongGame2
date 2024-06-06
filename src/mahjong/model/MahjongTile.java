package mahjong.model;


import java.util.Objects;

public class MahjongTile {
  int id;
  private final String type;
  private final String value;

  private boolean choose;

  public MahjongTile(String type, String value, int id) {
    this.id = id;
    this.type = type;
    this.value = value;
    choose = false;
  }

  public String getKeyString() {
    return type + "-" + value;
  }

  public void filpChoose() {
    this.choose = !choose;
  }

  public boolean isChoose() {
    return choose;
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MahjongTile that)) {
      return false;
    }
    return id == that.id && Objects.equals(type, that.type) && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, value);
  }

  public int compareTo(MahjongTile mahjongTile) {
    int d = type.compareTo(mahjongTile.type);
    if (d == 0) {
      return value.compareTo(mahjongTile.value);
    }
    return d;
  }
}
