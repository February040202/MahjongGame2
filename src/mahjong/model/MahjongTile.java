package mahjong.model;


import java.util.Objects;

/*
这个类应用的设计模式是：值对象模式（Value Object Pattern），
因为它封装了麻将牌的类型、值和唯一标识符，并且重写了‘equals’和‘hashCode’方法，
以便在集合中正确地判断两个麻将牌是否相等或计算它们的哈希值。

The design Pattern applied by this class is the Value Object Pattern,
because it encapsulates the type, value, and unique identifier of the mahjong cards,
and overrides the 'equals' and' hashCode 'methods
to correctly determine whether two mahjong cards are equal in the set or calculate their hash value.
*/
public class MahjongTile {
  private int id; // 每张麻将牌的唯一标识符（Unique identifier for each set）
  private final String type; // 麻将牌的类型（Types of mahjong sets）
  private final String value; // 麻将牌的具体值（The specific value of the mahjong set）
  private boolean choose; // 表示这张牌是否被选择（Indicates whether the card is selected）


  // 构造方法，初始化麻将牌的类型、值和唯一标识符
  public MahjongTile(String type, String value, int id) {
    this.id = id;
    this.type = type;
    this.value = value;
    choose = false;
  }

//  public MahjongTile(String type, int id) {
//    this.type = type;
//    this.id = id;
//  }

  // 返回麻将牌的标识字符串，格式为"type-value"
  public String getKeyString() {
    return type + "-" + value;
  }

  // 切换choose的状态（从true变为false，或从false变为true）
  public void filpChoose() {
    this.choose = !choose;
  }

  // 返回choose的当前状态
  public boolean isChoose() {
    return choose;
  }

  // 返回麻将牌的类型
  public String getType() {
    return type;
  }

  // 返回麻将牌的值
  public String getValue() {
    return value;
  }


  // 重写equals方法，用于比较两张麻将牌是否相等
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

  // 重写hashCode方法，用于生成麻将牌的哈希值
  @Override
  public int hashCode() {
    return Objects.hash(id, type, value);
  }

  // 用于比较两张麻将牌的顺序，先按类型比较，再按值比较
  public int compareTo(MahjongTile mahjongTile) {
    int d = type.compareTo(mahjongTile.type);
    if (d == 0) {
      return value.compareTo(mahjongTile.value);
    }
    return d;
  }
}
