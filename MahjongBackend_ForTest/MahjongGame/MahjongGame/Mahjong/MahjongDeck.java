package Mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MahjongDeck {
    //牌库
    final List<MahjongTile> tilesLibrary;
    //随机数生成器
    private Random random;

    public MahjongDeck() {
        tilesLibrary = new ArrayList<>();
        random = new Random();
        initializeTiles();
    }

    private void initializeTiles() {
        //初始化牌库
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                MahjongTile tile1 = new MahjongTile(i, "万");
                tilesLibrary.add(tile1);

                MahjongTile tile2 = new MahjongTile(i, "条");
                tilesLibrary.add(tile2);

                MahjongTile tile3 = new MahjongTile(i, "筒");
                tilesLibrary.add(tile3);
            }
        }
        String[] honors = {"东", "南", "西", "北", "中", "发", "白"};
        for (String honor : honors) {
            for (int i = 0; i < 4; i++) {
                MahjongTile tile4 = new MahjongTile(0, honor);
                tilesLibrary.add(tile4);
            }
        }
    }


    //从牌库中随机抽一张牌，并且从牌库中移除该牌，返回该牌。！！这一部分应该在出摸牌中实现。！！
    public MahjongTile drawTile() {
        if (tilesLibrary.isEmpty()) {
            return null;
        }
        int index = random.nextInt(tilesLibrary.size());
        return tilesLibrary.remove(index);
    }

    public List<MahjongTile> getTilesLibrary() {
        return tilesLibrary;
    }


    //这部分是用来测试的，出现了重复牌的问题，但不是这个牌堆的问题
    public static void main(String[] args) {
        MahjongDeck deck = new MahjongDeck();
        List<MahjongTile> hand = deck.getTilesLibrary();
        Collections.sort(hand, (t1, t2) -> {
            int suitOrder1 = getSuitOrder(t1.getSuit());
            int suitOrder2 = getSuitOrder(t2.getSuit());
            if (suitOrder1 != suitOrder2) {
                return suitOrder1 - suitOrder2;
            }
            return t1.getValue() - t2.getValue();
        });
        printHand(hand);
    }

    private static int getSuitOrder(String suit) {
        return switch (suit) {
            case "万" -> 0;
            case "条" -> 1;
            case "筒" -> 2;
            case "东" -> 3;
            case "西" -> 4;
            case "南" -> 5;
            case "北" -> 6;
            case "中" -> 7;
            case "发" -> 8;
            default -> 9;
        };
    }

    private static void printHand(List<MahjongTile> hand) {
        int count = 0;
        for (MahjongTile tile : hand) {
            System.out.print(tile + " ");
            count++;
            if (count % 4 == 0) {
                System.out.println();  // 每四个值进行一次换行
            }
        }
        if (count % 4 != 0) {
            System.out.println();  // 打印后续的换行符，如果最后一行不足四个值
        }
    }

}

