package test;

import mahjong.game.GameRule;
import mahjong.model.MahjongDeck;
import mahjong.model.MahjongTile;
import mahjong.model.Player;
import mahjong.model.TileValueDict;
import ui.listener.PlayerListener;

import java.util.ArrayList;
import java.util.List;

public class GameRuleTester {
    private MahjongDeck deck;
    private Player[] players;

    public GameRuleTester(PlayerListener listener) {
        players = new Player[3];
        for (int i = 0; i < 3; i++) {
            players[i] = new Player(new ArrayList<>(), i);
        }
        deck = new MahjongDeck();
    }

    public void startGame() {
        // 手动设置每个玩家的手牌
        manualDealTiles();

        // 打印所有玩家的初始手牌
        System.out.println("初始手牌：");
        displayAllHands();

        //检查peng
        testPongRule();

        //检查gang
        testGangRule();

        // 检查chow
        testChowRule();

        // 检查Win
        testWinRule();

        // 模拟游戏过程
//        simulateGame();
    }

    private void manualDealTiles() {
        // 设置示例手牌用于测试
        List<MahjongTile> player1Tiles = new ArrayList<>();
        player1Tiles.add(new MahjongTile("tiao", "tiao1", 1));
        player1Tiles.add(new MahjongTile("tiao", "tiao1", 1));
        player1Tiles.add(new MahjongTile("tiao", "tiao1", 1));
        player1Tiles.add(new MahjongTile("tiao", "tiao4", 4));
        player1Tiles.add(new MahjongTile("tiao", "tiao4", 4));
        player1Tiles.add(new MahjongTile("tiao", "tiao4", 4));
        player1Tiles.add(new MahjongTile("wan", "wan1", 1));
        player1Tiles.add(new MahjongTile("wan", "wan2", 2));
        player1Tiles.add(new MahjongTile("wan", "wan3", 3));
        player1Tiles.add(new MahjongTile("tong", "tong1", 1));
        player1Tiles.add(new MahjongTile("tong", "tong2", 2));
        player1Tiles.add(new MahjongTile("tong", "tong3", 3));
        player1Tiles.add(new MahjongTile("tiao", "tiao6", 6));

        List<MahjongTile> player2Tiles = new ArrayList<>();
        player2Tiles.add(new MahjongTile("tiao", "tiao1", 1));
        player2Tiles.add(new MahjongTile("tiao", "tiao1", 1));
        player2Tiles.add(new MahjongTile("tiao", "tiao4", 4));
        player2Tiles.add(new MahjongTile("tiao", "tiao4", 4));
        player2Tiles.add(new MahjongTile("tiao", "tiao5", 5));
        player2Tiles.add(new MahjongTile("tiao", "tiao7", 7));
        player2Tiles.add(new MahjongTile("tiao", "tiao7", 7));
        player2Tiles.add(new MahjongTile("tiao", "tiao8", 8));
        player2Tiles.add(new MahjongTile("wan", "wan9", 9));
        player2Tiles.add(new MahjongTile("tong", "tong1", 1));
        player2Tiles.add(new MahjongTile("tong", "tong2", 2));
        player2Tiles.add(new MahjongTile("tong", "tong3", 3));
        player2Tiles.add(new MahjongTile("tong", "tong4", 4));

        List<MahjongTile> player3Tiles = new ArrayList<>();
        player3Tiles.add(new MahjongTile("tong", "tong1", 1));
        player3Tiles.add(new MahjongTile("tong", "tong1", 1));
        player3Tiles.add(new MahjongTile("tong", "tong1", 1));
        player3Tiles.add(new MahjongTile("tong", "tong4", 4));
        player3Tiles.add(new MahjongTile("tong", "tong4", 4));
        player3Tiles.add(new MahjongTile("tong", "tong6", 6));
        player3Tiles.add(new MahjongTile("wan", "wan1", 1));
        player3Tiles.add(new MahjongTile("wan", "wan2", 2));
        player3Tiles.add(new MahjongTile("wan", "wan3", 2));
        player3Tiles.add(new MahjongTile("tiao", "tiao1", 1));
        player3Tiles.add(new MahjongTile("tiao", "tiao2", 2));
        player3Tiles.add(new MahjongTile("tiao", "tiao3", 3));
        player3Tiles.add(new MahjongTile("tiao", "tiao4", 4));

        // 设置每个玩家的手牌
        setPlayerTiles(players[0], player1Tiles);
        setPlayerTiles(players[1], player2Tiles);
        setPlayerTiles(players[2], player3Tiles);
    }

    private void setPlayerTiles(Player player, List<MahjongTile> tiles) {
        player.getHand().clear();
        for (MahjongTile tile : tiles) {
            player.drawTile(tile);
        }
    }

    private void displayAllHands() {
        for (int i = 0; i < players.length; i++) {
            System.out.println("玩家 " + (i + 1) + " 的手牌：");
            displayHand(players[i].getHand());
            System.out.println();
        }
    }

    private void displayHand(List<MahjongTile> hand) {
        for (MahjongTile tile : hand) {
            System.out.print(tile.getKeyString() + " ");
        }
        System.out.println();
    }

    // Method to test Pong rule
    public void testPongRule() {
        System.out.println("----测试碰牌----");

        // 测试玩家2是否能碰玩家1的牌
        testPongForPlayer(0, 1, new MahjongTile("tiao", "tiao1", 1));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家3是否能碰玩家1的牌
        testPongForPlayer(0, 2, new MahjongTile("tong", "tong4", 4));

        // 打印所有玩家手牌
        displayAllHands();
    }

    private void testPongForPlayer(int discardingPlayerIndex, int targetPlayerIndex, MahjongTile discardTile) {
        System.out.println("----玩家" + (targetPlayerIndex + 1) + "是否能碰玩家" + (discardingPlayerIndex + 1) + "的牌？----");

        // 设置出牌玩家和出牌
        players[discardingPlayerIndex].discardTile(discardTile);
        System.out.println("玩家" + (discardingPlayerIndex + 1) + "出了一张牌：" + discardTile.getKeyString());

        // 检查目标玩家是否可以碰
        if (GameRule.canPong(discardTile, players[targetPlayerIndex].getHand().get(0), players[targetPlayerIndex].getHand().get(1))) {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "可以碰牌：" + discardTile.getKeyString());
        } else {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "不可以碰牌：" + discardTile.getKeyString());

            // 如果目标玩家不能碰，检查下一个玩家
            int nextPlayerIndex = (targetPlayerIndex + 1) % players.length;
            if (GameRule.canPong(discardTile, players[nextPlayerIndex].getHand().get(0), players[nextPlayerIndex].getHand().get(1))) {
                System.out.println("玩家" + (nextPlayerIndex + 1) + "可以碰牌：" + discardTile.getKeyString());
            } else {
                System.out.println("玩家" + (nextPlayerIndex + 1) + "不可以碰牌：" + discardTile.getKeyString());
            }
        }
        System.out.println(" ");
    }


    public void testGangRule() {
        System.out.println("----测试杠牌规则----");

        // 测试玩家3在已经抽到三张一样的牌的前提下能否杠牌
        testGangForPlayer(1, 2, new MahjongTile("tong", "tong1", 1));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家2在完成碰牌的前提下能否通过摸牌来杠牌
        testGangForDrawnTile(1, new MahjongTile("tiao", "tiao1", 1));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家1在已经抽到三张一样的牌的前提下能否通过上家出牌来杠牌
        testGangForPlayer(1, 0, new MahjongTile("tiao", "tiao4", 4));

        // 打印所有玩家手牌
        displayAllHands();
    }


    private void testGangForPlayer(int discardingPlayerIndex, int targetPlayerIndex, MahjongTile discardTile) {
        System.out.println("----玩家" + (targetPlayerIndex + 1) + "在已经抽到三张一样的牌的前提下能否杠牌----");

        // 设置出牌玩家和出牌
        players[discardingPlayerIndex].discardTile(discardTile);
        System.out.println("玩家" + (discardingPlayerIndex + 1) + "出了一张牌：" + discardTile.getKeyString());

        // 检查目标玩家是否可以杠
        if (GameRule.canGang(players[targetPlayerIndex].getHand().get(0), players[targetPlayerIndex].getHand().get(1), players[targetPlayerIndex].getHand().get(2), discardTile)) {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "可以杠牌：" + discardTile.getKeyString());
        } else {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "不能杠牌：" + discardTile.getKeyString());

            // 如果目标玩家不能杠，检查下一个玩家
            int nextPlayerIndex = (targetPlayerIndex + 1) % players.length;
            if (GameRule.canGang(players[nextPlayerIndex].getHand().get(0), players[nextPlayerIndex].getHand().get(1), players[nextPlayerIndex].getHand().get(2), discardTile)) {
                System.out.println("玩家" + (nextPlayerIndex + 1) + "可以杠牌：" + discardTile.getKeyString());
            } else {
                System.out.println("玩家" + (nextPlayerIndex + 1) + "不能杠牌：" + discardTile.getKeyString());
            }
        }
        System.out.println(" ");
    }

    private void testGangForDrawnTile(int playerIndex, MahjongTile drawnTile) {
        System.out.println("----玩家" + (playerIndex + 1) + "在完成碰牌的前提下能否通过摸牌来杠牌----");

        // 设置玩家并模拟摸牌
        players[playerIndex].drawTile(drawnTile);
        System.out.println("玩家" + (playerIndex + 1) + "抽了一张牌：" + drawnTile.getKeyString());

        // 检查玩家是否可以杠
        if (GameRule.canGang(players[playerIndex].getHand().get(0), players[playerIndex].getHand().get(1), players[playerIndex].getHand().get(2), drawnTile)) {
            System.out.println("玩家" + (playerIndex + 1) + "可以杠牌：" + drawnTile.getKeyString());
        } else {
            System.out.println("玩家" + (playerIndex + 1) + "不能杠牌：" + drawnTile.getKeyString());
        }
        System.out.println(" ");
    }


    public void testChowRule() {
        System.out.println("----测试吃牌规则----");

        // 测试玩家2能否吃牌（上家出的6条）
        testChowForPlayer(0, 1, new MahjongTile("tiao", "tiao6", 6));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家3能否吃牌（上家出的1万）
        testChowForPlayer(1, 2, new MahjongTile("wan", "wan1", 1));

        // 打印所有玩家手牌
        displayAllHands();
    }

    private void testChowForPlayer(int discardingPlayerIndex, int targetPlayerIndex, MahjongTile discardTile) {
        System.out.println("----玩家" + (targetPlayerIndex + 1) + "能否吃牌（上家出的" + discardTile.getKeyString() + "）----");

        // 设置出牌玩家和出牌
        players[discardingPlayerIndex].discardTile(discardTile);
        System.out.println("玩家" + (discardingPlayerIndex + 1) + "出了一张牌：" + discardTile.getKeyString());

        // 检查目标玩家是否可以吃
        if (GameRule.canChow(discardTile, players[targetPlayerIndex].getHand().get(0), players[targetPlayerIndex].getHand().get(1))) {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "可以吃牌：" + discardTile.getKeyString());
        } else {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "不可以吃牌：" + discardTile.getKeyString());
        }
        System.out.println(" ");
    }

    private void testWinRule() {
        System.out.println("----测试胡牌规则----");

        // 首先测试七小对
        testSevenPairs();

        // 打印所有玩家手牌
        displayAllHands();

        // 然后测试十三幺
        testThirteenOrphans();

        // 打印所有玩家手牌
        displayAllHands();

        // 最后测试普通胡牌
        testNormalWin();

        // 打印所有玩家手牌
        displayAllHands();
    }

    private void testThirteenOrphans() {
        List<MahjongTile> tiles = new ArrayList<>();
        MahjongTile lastTile = null;

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

        System.out.println(GameRule.canWin(tiles, lastTile)); // should print true
    }

    private void testSevenPairs() {
        for (int i = 0; i < 2; i++) {
            List<MahjongTile> tiles = new ArrayList<>();
            if (i == 0) {//自摸七小对
                System.out.println("自摸七小对");
                MahjongTile lastTile = null;

                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tong", "tong3", 22));
                tiles.add(new MahjongTile("tong", "tong3", 22));
                tiles.add(new MahjongTile("bonus", "east", 31));
                tiles.add(new MahjongTile("bonus", "east", 31));
                tiles.add(new MahjongTile("bonus", "west", 33));
                tiles.add(new MahjongTile("bonus", "west", 33));
                tiles.add(new MahjongTile("bonus", "north", 37));
                tiles.add(new MahjongTile("bonus", "north", 37));
                tiles.add(new MahjongTile("bonus", "zhong", 39));
                tiles.add(new MahjongTile("bonus", "zhong", 39));

                System.out.println(GameRule.canWin(tiles, lastTile)); // should print true

            } else {//非自摸七小对
                System.out.println("非自摸七小对");
                MahjongTile lastTile = new MahjongTile("tong", "tong3", 22);

                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tong", "tong3", 22));
                //tiles.add(new MahjongTile("tong", "tong3", 22));
                tiles.add(new MahjongTile("bonus", "east", 31));
                tiles.add(new MahjongTile("bonus", "east", 31));
                tiles.add(new MahjongTile("bonus", "west", 33));
                tiles.add(new MahjongTile("bonus", "west", 33));
                tiles.add(new MahjongTile("bonus", "north", 37));
                tiles.add(new MahjongTile("bonus", "north", 37));
                tiles.add(new MahjongTile("bonus", "zhong", 39));
                tiles.add(new MahjongTile("bonus", "zhong", 39));

                //System.out.println("Tiles: " + tiles.size());
                System.out.println(GameRule.canWin(tiles, lastTile)); // should print true
            }
        }
    }

    private void testNormalWin() {
        for (int i = 0; i < 2; i++) {
            List<MahjongTile> tiles = new ArrayList<>();
            if (i == 0) {//自摸
                MahjongTile lastTile = null;

                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("wan", "wan1", 1));

                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));

                tiles.add(new MahjongTile("tong", "tong3", 22));
                tiles.add(new MahjongTile("tong", "tong4", 23));
                tiles.add(new MahjongTile("tong", "tong5", 24));

                tiles.add(new MahjongTile("tong", "tong6", 25));
                tiles.add(new MahjongTile("tong", "tong7", 26));
                tiles.add(new MahjongTile("tong", "tong8", 27));

                tiles.add(new MahjongTile("bonus", "east", 31));
                tiles.add(new MahjongTile("bonus", "east", 31));

                System.out.println(GameRule.canWin(tiles, lastTile)); // should print true

            } else {//非自摸
                MahjongTile lastTile = new MahjongTile("tiao", "tiao2", 12);

                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("wan", "wan1", 1));
                tiles.add(new MahjongTile("wan", "wan1", 1));

                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                tiles.add(new MahjongTile("tiao", "tiao2", 12));
                //tiles.add(new MahjongTile("tiao", "tiao2", 12));

                tiles.add(new MahjongTile("tong", "tong3", 22));
                tiles.add(new MahjongTile("tong", "tong4", 23));
                tiles.add(new MahjongTile("tong", "tong5", 24));

                tiles.add(new MahjongTile("tong", "tong6", 25));
                tiles.add(new MahjongTile("tong", "tong7", 26));
                tiles.add(new MahjongTile("tong", "tong8", 27));

                tiles.add(new MahjongTile("bonus", "east", 31));
                tiles.add(new MahjongTile("bonus", "east", 31));

                System.out.println(GameRule.canWin(tiles, lastTile)); // should print true
            }
        }





    }

//    private void simulateGame() {
//        // 玩家1出牌，玩家2判断能否吃、碰、杠
//        MahjongTile discardTile1 = new MahjongTile("wan", "wan1", 1);
//        players[0].discardTile(discardTile1);
//        System.out.println("玩家1出了一张牌：" + discardTile1.getKeyString());
//
//        if (GameRule.canChow(players[1].getHand().get(0), players[1].getHand().get(1), discardTile1)) {
//            System.out.println("玩家2可以吃牌：" + discardTile1.getKeyString());
//        } else if (GameRule.canPong(players[1].getHand().get(0), players[1].getHand().get(1), discardTile1)) {
//            System.out.println("玩家2可以碰牌：" + discardTile1.getKeyString());
//        } else if (GameRule.canGang(players[1].getHand().get(0), players[1].getHand().get(1), players[1].getHand().get(2), discardTile1)) {
//            System.out.println("玩家2可以杠牌：" + discardTile1.getKeyString());
//        } else {
//            System.out.println("玩家2不能吃、碰、杠这张牌：" + discardTile1.getKeyString());
//        }
//
//        // 玩家2摸牌并出牌，玩家3判断能否吃、碰、杠
//        MahjongTile drawTile2 = deck.getTile();
//        players[1].drawTile(drawTile2);
//        System.out.println("玩家2摸了一张牌：" + drawTile2.getKeyString());
//        MahjongTile discardTile2 = new MahjongTile("wan", "wan2", 2);
//        players[1].discardTile(discardTile2);
//        System.out.println("玩家2出了一张牌：" + discardTile2.getKeyString());
//
//        if (GameRule.canChow(players[2].getHand().get(0), players[2].getHand().get(1), discardTile2)) {
//            System.out.println("玩家3可以吃牌：" + discardTile2.getKeyString());
//        } else if (GameRule.canPong(players[2].getHand().get(0), players[2].getHand().get(1), discardTile2)) {
//            System.out.println("玩家3可以碰牌：" + discardTile2.getKeyString());
//        } else if (GameRule.canGang(players[2].getHand().get(0), players[2].getHand().get(1), players[2].getHand().get(2), discardTile2)) {
//            System.out.println("玩家3可以杠牌：" + discardTile2.getKeyString());
//        } else {
//            System.out.println("玩家3不能吃、碰、杠这张牌：" + discardTile2.getKeyString());
//        }
//
//        // 玩家3摸牌并出牌，玩家1判断能否吃、碰、杠
//        MahjongTile drawTile3 = deck.getTile();
//        players[2].drawTile(drawTile3);
//        System.out.println("玩家3摸了一张牌：" + drawTile3.getKeyString());
//        MahjongTile discardTile3 = new MahjongTile("wan", "wan3", 3);
//        players[2].discardTile(discardTile3);
//        System.out.println("玩家3出了一张牌：" + discardTile3.getKeyString());
//
//        if (GameRule.canChow(players[0].getHand().get(0), players[0].getHand().get(1), discardTile3)) {
//            System.out.println("玩家1可以吃牌：" + discardTile3.getKeyString());
//        } else if (GameRule.canPong(players[0].getHand().get(0), players[0].getHand().get(1), discardTile3)) {
//            System.out.println("玩家1可以碰牌：" + discardTile2.getKeyString());
//        } else if (GameRule.canGang(players[0].getHand().get(0), players[0].getHand().get(1), players[0].getHand().get(2), discardTile3)) {
//            System.out.println("玩家1可以杠牌：" + discardTile2.getKeyString());
//        } else {
//            System.out.println("玩家1不能吃、碰、杠这张牌：" + discardTile2.getKeyString());
//        }
//    }

    public static void main(String[] args) {
        PlayerListener playerListener = new PlayerListener();
        GameRuleTester testForRule = new GameRuleTester(playerListener);
        testForRule.startGame();
    }
}