package Mahjong;

import java.util.Objects;

//这个方法主要是用来创建麻将牌的类，每个麻将牌都有花色和数值。输出的是String的形式。
public class MahjongTile{
    //花色
    private String suit;
    //数值
    private int value;
    //image address

    //构造麻将牌

    public MahjongTile(int value, String suit) {
        this.suit = suit;
        this.value = value;
    }

    // 新增用于创建字牌（没有数值）的构造函数
    public MahjongTile(String suit) {
        this.suit = suit;
        this.value = 0; // 字牌没有数值
    }

    //获取花色
    public String getSuit() {
        return suit;
    }

    //获取数值
    public int getValue() {
        return value;
    }

    //重写toString方法，输出String形式
    @Override
    public String toString() {
        return value == 0 ? suit : value + suit;
    }

    //重写equals方法，比较两个麻将牌是否相等
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MahjongTile that = (MahjongTile) obj;
        return value == that.value && Objects.equals(suit, that.suit);
    }

    //重写hashCode方法
    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }

    public boolean isHonor() {
        return suit.equals("东") || suit.equals("南") ||
                suit.equals("西") || suit.equals("北") ||
                suit.equals("中") || suit.equals("发") ||
                suit.equals("白");
    }

    public boolean isRedDragon() {
        return suit.equals("中");
    }

    public boolean isGreenDragon() {
        return suit.equals("发");
    }

    public boolean isWhiteDragon() {
        return suit.equals("白");
    }

    public boolean isEastWind() {
        return suit.equals("东");
    }

    public boolean isSouthWind() {
        return suit.equals("南");
    }

    public boolean isWestWind() {
        return suit.equals("西");
    }

    public boolean isNorthWind() {
        return suit.equals("北");
    }
}