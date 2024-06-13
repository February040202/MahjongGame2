package mahjong.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton
 */
/*
这段代码实现了一个使用单例模式（Singleton Pattern）的类 TileValueDict，该类用于将麻将牌转换为对应的数值。
这种设计模式确保一个类只有一个实例，并提供全局访问点。

This code implements a class, TileValueDict, that uses the Singleton Pattern to convert the mahjong cards to the corresponding value.
This design pattern ensures that there is only one instance of a class and provides a global access point.
 */
public class TileValueDict {//单例模式（assessment的要求） 把麻将转换成数字，后续让rule类更简洁
  private static TileValueDict instance;

  // wan(1-9) tiao(11-19) tong(21-29)
  // dong 31    west 33    south 35    north 37  zhong 39    fa 41    white 43
  private Map<String, Integer> tile2Value;

  // 私有构造器，防止外部直接使用new操作符创建实例
  private TileValueDict() {
    tile2Value = new HashMap<>();//创建一个HashMap对象tile2Value用于存储麻将牌和对应数值的映射关系。
    int value = 0;
    for (int i = 1; i <= 9; i++) {
      value++;
      tile2Value.put("wan-wan" + i, value);//i 是数字， value 用来控制种类
      tile2Value.put("tiao-tiao" + i, value + 10);
      tile2Value.put("tong-tong" + i, value + 20);
    }

    String[] bonus = {"east", "fa", "north", "south", "west", "white", "zhong"};
    for (String b : bonus) {
      tile2Value.put("bonus-" + b, value + 22);
      value += 2;
    }
    tile2Value.put("null-null", -1);
  }

  // 公有的静态方法，返回该类的唯一实例
  public static synchronized TileValueDict getInstance() {
    if (instance == null) {
      instance = new TileValueDict();
    }
    return instance;
  }

  // 从字典中获取条目的方法
  public Integer getValue(String key) {
    return tile2Value.get(key);
  }

}
