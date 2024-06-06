package Test;

import Mahjong.MahjongDeck;
import Mahjong.MahjongGameManager;
import Mahjong.MahjongTile;
import Players.Player;

import java.util.List;
import java.util.Scanner;


public class Test {
    public static void main(String[] args) {
        testMahjongTile();
        testMahjongDeck();
        testDrawAndDiscard();
    }

    public static void testMahjongTile() {
        MahjongTile tile = new MahjongTile(1, "万");
        System.out.println("Suit: " + tile.getSuit());
        System.out.println("Value: " + tile.getValue());
        System.out.println("Tile: " + tile);
        System.out.println();
    }

    public static void testMahjongDeck() {
        MahjongDeck deck = new MahjongDeck();
        System.out.println("Number of tiles in the deck: " + deck.getTilesLibrary().size());
        System.out.println("Tiles in the deck:");

        int count = 0;
        for (MahjongTile tile : deck.getTilesLibrary()) {
            System.out.print(tile + " ");
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

    public static void testDrawAndDiscard() {
        // 初始化游戏管理器
        MahjongGameManager gameManager = new MahjongGameManager();
        Scanner scanner = new Scanner(System.in);

        // 初始化玩家和牌堆
        Player[] players = gameManager.getPlayers();
        MahjongDeck deck = new MahjongDeck();

        // 模拟发牌过程
        for (Player player : players) {
            for (int i = 0; i < 13; i++) {
                MahjongTile tile = deck.drawTile();
                player.drawPlayerTile(tile);
            }
        }

        // 分别显示每个玩家的初始手牌并模拟其摸牌和出牌
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];

            // 显示当前玩家的初始手牌
            System.out.println("玩家 " + (i + 1) + " 的初始手牌：");
            List<MahjongTile> sortedHand = MahjongGameManager.sortTiles_pre(player.getHand());
            player.getHand().clear();
            player.getHand().addAll(sortedHand);
            gameManager.displayHand_test(player.getHand());
            System.out.println();

            // 模拟摸牌
            System.out.println("玩家 " + (i + 1) + " 的回合：");
            System.out.println("请输入玩家 " + (i + 1) + " 要摸的牌 (格式: 1万, 3条, 5筒, 0东, 0南, 0西, 0北, 0中, 0发, 0白)：");
            String tileInput = scanner.nextLine();
            MahjongTile drawnTile = parseTile(tileInput);
            System.out.println("玩家 " + (i + 1) + " 摸到了: " + drawnTile);
            player.drawPlayerTile(drawnTile);
            System.out.println();

            // 显示摸牌后的手牌
            sortedHand = MahjongGameManager.sortTiles_pre(player.getHand());
            player.getHand().clear();
            player.getHand().addAll(sortedHand);
            System.out.println("玩家 " + (i + 1) + " 的新手牌为：");
            gameManager.displayHand_test(player.getHand());

            // 模拟出牌
            System.out.println("请输入玩家 " + (i + 1) + " 要打出的牌 (格式: 1万, 3条, 5筒, 0东, 0南, 0西, 0北, 0中, 0发, 0白)：");
            tileInput = scanner.nextLine();
            MahjongTile discardTile = parseTile(tileInput);
            player.dealPlayerTile(discardTile);
            System.out.println("玩家 " + (i + 1) + " 打出了: " + discardTile);

            // 显示出牌后的手牌
            sortedHand = MahjongGameManager.sortTiles_pre(player.getHand());
            player.getHand().clear();
            player.getHand().addAll(sortedHand);
            gameManager.displayHand_test(player.getHand());
            System.out.println();
            System.out.println("下一位：");
        }

        scanner.close();
    }

    // Helper method to parse user input into MahjongTile
    private static MahjongTile parseTile(String tileInput) {
        int value;
        String suit;

        if (Character.isDigit(tileInput.charAt(0))) {
            value = Character.getNumericValue(tileInput.charAt(0));
            suit = tileInput.substring(1);

            // 校验并处理字牌
            if (value == 0) {
                switch (suit) {
                    case "东":
                    case "南":
                    case "西":
                    case "北":
                    case "中":
                    case "发":
                    case "白":
                        break;
                    default:
                        throw new IllegalArgumentException("无效的牌型输入");
                }
            } else {
                // 非字牌情况
                switch (suit) {
                    case "万":
                    case "条":
                    case "筒":
                        break;
                    default:
                        throw new IllegalArgumentException("无效的牌型输入");
                }
            }
        } else {
            throw new IllegalArgumentException("无效的牌型输入");
        }

        return new MahjongTile(value, suit);
    }

}
