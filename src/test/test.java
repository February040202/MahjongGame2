package test;

import mahjong.game.*;
import mahjong.model.*;
import ui.listener.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    private MahjongDeck deck;
    private Player[] players;
    private GameRule rules;

    public static void main(String[] args) {
        testInitPlayHandTile();
        testMahjongTile();
        testMahjongDeck();
    }

    // Verify that the number of cards in the player's hand is 13
    private static void testInitPlayHandTile() {
        PlayerListener listener = new PlayerListener();
        MahjongGame game = new MahjongGame(listener);

        Player[] players = game.players;
        boolean testPassed = true;
        for (Player player : players) {
            if (player.handTiles.size() != 13) {
                System.out.println("testInitPlayHandTile failed: Each player should have 13 tiles at the start");
                testPassed = false;
            }
            if (player.handTiles.stream().anyMatch(tile -> tile == null)) {
                System.out.println("testInitPlayHandTile failed: No tile should be null");
                testPassed = false;
            }
        }
        if (testPassed) {
            System.out.println("testInitPlayHandTile passed");
        }
    }

    public static void testMahjongTile() {
        MahjongTile tile = new MahjongTile("wan", "wan7", 7);
        System.out.println("Type: " + tile.getType());
        System.out.println("Value: " + tile.getValue());
        System.out.println("Tile: " + tile.getKeyString());
        System.out.println();
    }

    public static void testMahjongDeck() {
        MahjongDeck deck = new MahjongDeck();
        System.out.println("Number of tiles in the deck: " + deck.getTilesLibrary().size());
        System.out.println("Tiles in the deck:");

        int count = 0;
        for (MahjongTile tile : deck.getTilesLibrary()) {
            System.out.print(tile.getKeyString() + " ");
            count++;
            if (count % 4 == 0) {
                System.out.println();
            }
        }
        if (count % 4 != 0) {
            System.out.println();
        }
        System.out.println();
    }

//    public static void testDrawAndDiscard() {
//        // 初始化玩家和牌堆
//        PlayerListener playerListener = new PlayerListener();
//        Player[] players = new Player[4];
//        for (int i = 0; i < players.length; i++) {
//            players[i] = new Player(new ArrayList<>(), i);
//        }
//        MahjongDeck deck = new MahjongDeck();
//
//        // 模拟发牌过程
//        for (Player player : players) {
//            for (int i = 0; i < 13; i++) {
//                MahjongTile tile = deck.drawTile();
//                player.drawTile(tile);
//            }
//        }
//
//        Scanner scanner = new Scanner(System.in);
//
//        // 分别显示每个玩家的初始手牌并模拟其摸牌和出牌
//        for (int i = 0; i < players.length; i++) {
//            Player player = players[i];
//
//            // 显示当前玩家的初始手牌
//            System.out.println("玩家 " + (i + 1) + " 的初始手牌：");
//            displayHand(player.getHand());
//            System.out.println();
//
//            // 模拟摸牌
//            System.out.println("玩家 " + (i + 1) + " 的回合：");
//            System.out.println("请输入玩家 " + (i + 1) + " 要摸的牌 (格式: 1万, 3条, 5筒, 0东, 0南, 0西, 0北, 0中, 0发, 0白)：");
//            String tileInput = scanner.nextLine();
//            MahjongTile drawnTile = parseTile(tileInput);
//            System.out.println("玩家 " + (i + 1) + " 摸到了: " + drawnTile);
//            player.drawTile(drawnTile);
//            System.out.println();
//
//            // 显示摸牌后的手牌
//            System.out.println("玩家 " + (i + 1) + " 的新手牌为：");
//            displayHand(player.getHand());
//
//            // 模拟出牌
//            System.out.println("请输入玩家 " + (i + 1) + " 要打出的牌 (格式: 1万, 3条, 5筒, 0东, 0南, 0西, 0北, 0中, 0发, 0白)：");
//            tileInput = scanner.nextLine();
//            MahjongTile discardTile = parseTile(tileInput);
//            player.discardTile(discardTile);
//            System.out.println("玩家 " + (i + 1) + " 打出了: " + discardTile);
//
//            // 显示出牌后的手牌
//            System.out.println("玩家 " + (i + 1) + " 的新手牌为：");
//            displayHand(player.getHand());
//            System.out.println();
//            System.out.println("下一位：");
//        }
//
//        scanner.close();
//    }
//
//    private static void displayHand(List<MahjongTile> hand) {
//        for (MahjongTile tile : hand) {
//            System.out.print(tile.getKeyString() + " ");
//        }
//        System.out.println();
//    }
//
//    // Helper method to parse user input into MahjongTile
//    private static MahjongTile parseTile(String tileInput) {
//        int value;
//        String type;
//
//        if (Character.isDigit(tileInput.charAt(0))) {
//            value = Character.getNumericValue(tileInput.charAt(0));
//            type = tileInput.substring(1);
//
//            // 校验并处理字牌
//            if (value == 0) {
//                switch (type) {
//                    case "east":
//                    case "south":
//                    case "west":
//                    case "north":
//                    case "zhong":
//                    case "fa":
//                    case "white":
//                        break;
//                    default:
//                        throw new IllegalArgumentException("无效的牌型输入");
//                }
//            } else {
//                // 非字牌情况
//                switch (type) {
//                    case "wan":
//                    case "tiao":
//                    case "tong":
//                        break;
//                    default:
//                        throw new IllegalArgumentException("无效的牌型输入");
//                }
//            }
//        } else {
//            throw new IllegalArgumentException("无效的牌型输入");
//        }
//
//        return new MahjongTile(type, value);
//    }

}
