package Test;

import GameRules.Rules;
import Mahjong.MahjongDeck;
import Mahjong.MahjongTile;
import Players.Player;
import Players.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static GameRules.Rules.WinType.THIRTEEN_ORPHANS;

public class TestForRule {
    private MahjongDeck deck;
    private Player[] players;
    private Rules rules;

    public TestForRule() {
        PlayerManager playerManager = new PlayerManager();
        players = playerManager.getPlayers();
        deck = new MahjongDeck();
        rules = new Rules(players);
    }

    // Method to deal tiles automatically from the deck
    protected void dealTiles() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 13; j++) {
                MahjongTile tile = deck.drawTile();
                players[i].drawPlayerTile(tile);
            }
        }
    }

    // New method to set tiles manually for testing
    protected void setPlayerTiles(Player player, List<MahjongTile> tiles) {
        player.getHand().clear();
        // Clear any existing tiles in the player's hand
        for (MahjongTile tile : tiles) {
            player.drawPlayerTile(tile);
        }
    }

    public void startGame() {
        // 自动发牌
        dealTiles();

        // 修改手牌以便测试规则
        manualDealTiles();

        // 打印修改后的手牌
        System.out.println("修改后的手牌：");
        displayAllHands();

        // 检验碰规则
        //testPongRule();

        //检验杠规则
        //testGangRule();

        //验证吃牌
        //testChowRule();

        //验证胡牌
        testWinRule();
    }

    // 打印所有玩家的手牌
    public void displayAllHands() {
        for (int i = 0; i < players.length - 1; i++) {
            System.out.println("玩家 " + (i + 1) + " 的手牌：");
            displayHand(players[i].getHand());
            displayRulesTiles(players[i].getRulesTiles());
            System.out.println();
        }
    }

    // 打印单个玩家的手牌
    public void displayHand(List<MahjongTile> hand) {
        for (MahjongTile tile : hand) {
            System.out.print(tile + " ");
        }
        System.out.println();
    }

    // 打印单个玩家的规则牌
    public void displayRulesTiles(List<MahjongTile> rulesTiles) {
        System.out.print("规则牌：");
        for (MahjongTile tile : rulesTiles) {
            System.out.print(tile + " ");
        }
        System.out.println();
    }

    // Method to manually set the initial tiles for each player
    public void manualDealTiles() {
        // 设置示例手牌用于测试
        List<MahjongTile> player1Tiles = new ArrayList<>();
        player1Tiles.add(new MahjongTile(1, "条"));
        player1Tiles.add(new MahjongTile(1, "条"));
        player1Tiles.add(new MahjongTile(1, "条"));
        player1Tiles.add(new MahjongTile(4, "条"));
        player1Tiles.add(new MahjongTile(4, "条"));
        player1Tiles.add(new MahjongTile(4, "条"));
        player1Tiles.add(new MahjongTile(1, "万"));
        player1Tiles.add(new MahjongTile(2, "万"));
        player1Tiles.add(new MahjongTile(3, "万"));
        player1Tiles.add(new MahjongTile(1, "筒"));
        player1Tiles.add(new MahjongTile(2, "筒"));
        player1Tiles.add(new MahjongTile(3, "筒"));
        player1Tiles.add(new MahjongTile(6, "条"));

        List<MahjongTile> player2Tiles = new ArrayList<>();
        player2Tiles.add(new MahjongTile(1, "条"));
        player2Tiles.add(new MahjongTile(1, "条"));
        player2Tiles.add(new MahjongTile(4, "条"));
        player2Tiles.add(new MahjongTile(4, "条"));
        player2Tiles.add(new MahjongTile(5, "条"));
        player2Tiles.add(new MahjongTile(7, "条"));
        player2Tiles.add(new MahjongTile(7, "条"));
        player2Tiles.add(new MahjongTile(8, "条"));
        player2Tiles.add(new MahjongTile(9, "条"));
        player2Tiles.add(new MahjongTile(1, "筒"));
        player2Tiles.add(new MahjongTile(2, "筒"));
        player2Tiles.add(new MahjongTile(3, "筒"));
        player2Tiles.add(new MahjongTile(4, "筒"));

        List<MahjongTile> player3Tiles = new ArrayList<>();
        player3Tiles.add(new MahjongTile(1, "筒"));
        player3Tiles.add(new MahjongTile(1, "筒"));
        player3Tiles.add(new MahjongTile(1, "筒"));
        player3Tiles.add(new MahjongTile(4, "筒"));
        player3Tiles.add(new MahjongTile(4, "筒"));
        player3Tiles.add(new MahjongTile(6, "筒"));
        player3Tiles.add(new MahjongTile(1, "万"));
        player3Tiles.add(new MahjongTile(2, "万"));
        player3Tiles.add(new MahjongTile(3, "万"));
        player3Tiles.add(new MahjongTile(1, "条"));
        player3Tiles.add(new MahjongTile(2, "条"));
        player3Tiles.add(new MahjongTile(3, "条"));
        player3Tiles.add(new MahjongTile(4, "条"));


        // Set each player's hand to these tiles
        setPlayerTiles(players[0], player1Tiles);
        setPlayerTiles(players[1], player2Tiles);
        setPlayerTiles(players[2], player3Tiles);
    }

    // Method to test Pong rule
//    public void testPongRule() {
//        System.out.println("----测试碰牌---- ");
//
//        System.out.println("----玩家2是否能碰玩家1的牌？----");
//        // 模拟玩家1出牌，此时玩家2吃牌
//        rules.setPlayerIndex(0);  // 设置当前玩家索引为0，即玩家1
//        MahjongTile discardTile = new MahjongTile(1, "条");
//        players[0].dealPlayerTile(discardTile);
//        rules.discardTile = discardTile;
//        System.out.println("玩家1出了一张牌：" + discardTile);
//
//        // 检查玩家2是否可以碰
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        if (!rules.isPong()) {
//            System.out.println("玩家2不可以碰牌：" + discardTile);
//            // 检查玩家3是否可以碰
//            rules.setPlayerIndex(2);  // 设置当前玩家索引为2，即玩家3
//            if (!rules.isPong()) {
//                System.out.println("玩家3不可以碰牌：" + discardTile);
//            }
//        }
//
//        // 打印碰牌后的手牌
//        displayAllHands();
//
//        System.out.println("----玩家3是否能碰玩家1的牌----");
//        // 模拟玩家1出牌，此时玩家3吃牌
//        rules.setPlayerIndex(0);  // 设置当前玩家索引为0，即玩家1
//        MahjongTile discardTile1 = new MahjongTile(4, "筒");
//        players[0].dealPlayerTile(discardTile1);
//        rules.discardTile = discardTile1;
//        System.out.println("玩家1出了一张牌：" + discardTile1);
//
//        // 检查玩家2是否可以碰
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        if (!rules.isPong()) {
//            System.out.println("玩家2不可以碰牌：" + discardTile1);
//            // 检查玩家3是否可以碰
//            rules.setPlayerIndex(2);  // 设置当前玩家索引为2，即玩家3
//            if (!rules.isPong()) {
//                System.out.println("玩家3不可以碰牌：" + discardTile1);
//            }
//        }
//
//        // 打印碰牌后的手牌
//        displayAllHands();
//    }

    // Method to test Pong rule
    public void testPongRule() {
        System.out.println("----测试碰牌----");

        // 测试玩家2是否能碰玩家1的牌
        testPongForPlayer(0, 1, new MahjongTile(1, "条"));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家3是否能碰玩家1的牌
        testPongForPlayer(0, 2, new MahjongTile(4, "筒"));

        // 打印所有玩家手牌
        displayAllHands();
    }

    private void testPongForPlayer(int discardingPlayerIndex, int targetPlayerIndex, MahjongTile discardTile) {
        System.out.println("----玩家" + (targetPlayerIndex + 1) + "是否能碰玩家" + (discardingPlayerIndex + 1) + "的牌？----");

        // 设置出牌玩家和出牌
        rules.setPlayerIndex(discardingPlayerIndex);
        players[discardingPlayerIndex].dealPlayerTile(discardTile);
        rules.discardTile = discardTile;
        System.out.println("玩家" + (discardingPlayerIndex + 1) + "出了一张牌：" + discardTile);

        // 检查目标玩家是否可以碰
        rules.setPlayerIndex(targetPlayerIndex);
        if (!rules.isPong()) {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "不可以碰牌：" + discardTile);

            // 如果目标玩家不能碰，检查下一个玩家
            int nextPlayerIndex = (targetPlayerIndex + 1) % players.length;
            rules.setPlayerIndex(nextPlayerIndex);
            if (!rules.isPong()) {
                System.out.println("玩家" + (nextPlayerIndex + 1) + "不可以碰牌：" + discardTile);
            }
        }
        System.out.println(" ");
    }


    // Method to test Gang rule
//    public void testGangRule() {
//        System.out.println("----测试杠牌规则---- ");
//
//        System.out.println("----玩家3在已经抽到三张一样的牌的前提下能否杠牌----");
//        // 模拟玩家2出牌
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        MahjongTile discardTile = new MahjongTile(1, "筒");
//        rules.discardTile = discardTile;
//        players[1].dealPlayerTile(discardTile);  // 从玩家1的手牌中移除出掉的牌
//        System.out.println("玩家2出了一张牌：" + discardTile);
//
//        // 检查玩家3是否可以杠
//        rules.setPlayerIndex(2);  // 设置当前玩家索引为2，即玩家3
//        if (rules.isGang()) {
//            System.out.println("玩家3可以杠牌：" + discardTile);
//        } else {
//            System.out.println("玩家3不可以杠牌：" + discardTile);
//        }
//
//        // 打印杠牌后的手牌
//        displayAllHands();
//
//
//        System.out.println("----玩家2在完成碰牌的前提下能否通过摸牌来杠牌----");
//        // 模拟玩家2摸牌
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        MahjongTile discardTile1 = new MahjongTile(1, "条");
//        rules.discardTile = discardTile1;
//        players[1].drawPlayerTile(discardTile1);  // 从玩家1的手牌中移除出掉的牌
//        System.out.println("玩家2抽了一张牌：" + discardTile1);
//
//        // 检查玩家2是否可以杠
//        if (rules.isGang()) {
//            System.out.println("玩家2可以杠牌：" + discardTile1);
//        } else {
//            System.out.println("玩家2不可以杠牌：" + discardTile1);
//        }
//
//        // 打印杠牌后的手牌
//        displayAllHands();
//
//
//        System.out.println("----玩家1在已经抽到三张一样的牌的前提下能否通过上家出牌来杠牌----");
//
//        // 模拟玩家2出牌
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        MahjongTile discardTile3 = new MahjongTile(4, "条");
//        rules.discardTile = discardTile3;
//        players[1].dealPlayerTile(discardTile3);  // 从玩家的手牌中移除出掉的牌
//        System.out.println("玩家2出了一张牌：" + discardTile3);
//
//        // 检查玩家3,1是否可以杠
//        rules.setPlayerIndex(2);
//        if (rules.isGang()) {
//            System.out.println("玩家3可以杠牌：" + discardTile3);
//        } else {
//            System.out.println("玩家3不可以杠牌：" + discardTile3);
//            rules.setPlayerIndex(0);
//            if (rules.isGang()) {
//                System.out.println("玩家1可以杠牌：" + discardTile3);
//            } else {
//                System.out.println("玩家1不可以杠牌：" + discardTile3);
//            }
//        }
//
//        // 打印杠牌后的手牌
//        displayAllHands();
//
//    }

    // Method to test Gang rule
    public void testGangRule() {
        System.out.println("----测试杠牌规则----");

        // 测试玩家3在已经抽到三张一样的牌的前提下能否杠牌
        testGangForPlayer(1, 2, new MahjongTile(1, "筒"));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家2在完成碰牌的前提下能否通过摸牌来杠牌
        testGangForDrawnTile(1, new MahjongTile(1, "条"));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家1在已经抽到三张一样的牌的前提下能否通过上家出牌来杠牌
        testGangForPlayer(1, 0, new MahjongTile(4, "条"));

        // 打印所有玩家手牌
        displayAllHands();
    }

    private void testGangForPlayer(int discardingPlayerIndex, int targetPlayerIndex, MahjongTile discardTile) {
        System.out.println("----玩家" + (targetPlayerIndex + 1) + "在已经抽到三张一样的牌的前提下能否杠牌----");

        // 设置出牌玩家和出牌
        rules.setPlayerIndex(discardingPlayerIndex);
        players[discardingPlayerIndex].dealPlayerTile(discardTile);
        rules.discardTile = discardTile;
        System.out.println("玩家" + (discardingPlayerIndex + 1) + "出了一张牌：" + discardTile);

        // 检查目标玩家是否可以杠
        rules.setPlayerIndex(targetPlayerIndex);
        if (rules.isGang()) {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "杠牌成功：" + discardTile);
        } else {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "杠牌失败：" + discardTile);
        }
        System.out.println(" ");
    }

    private void testGangForDrawnTile(int playerIndex, MahjongTile drawnTile) {
        System.out.println("----玩家" + (playerIndex + 1) + "在完成碰牌的前提下能否通过摸牌来杠牌----");

        // 设置玩家并模拟摸牌
        rules.setPlayerIndex(playerIndex);
        players[playerIndex].drawPlayerTile(drawnTile);
        rules.discardTile = drawnTile;
        System.out.println("玩家" + (playerIndex + 1) + "抽了一张牌：" + drawnTile);

        // 检查玩家是否可以杠
        if (rules.isGang()) {
            System.out.println("玩家" + (playerIndex + 1) + "杠牌成功：" + drawnTile);
        } else {
            System.out.println("玩家" + (playerIndex + 1) + "杠牌失败：" + drawnTile);
        }
        System.out.println(" ");
    }

//    // Method to test Chow rule
//    public void testChowRule() {
//        System.out.println("----测试吃牌规则---- ");
//
//        System.out.println("----玩家2能否吃牌（上家出的6条）----");
//        // 模拟玩家1出牌
//        rules.setPlayerIndex(0);  // 设置当前玩家索引为0，即玩家1
//        MahjongTile discardTile = new MahjongTile(6, "条");
//        rules.discardTile = discardTile;
//        players[0].dealPlayerTile(discardTile);  // 从玩家1的手牌中移除出掉的牌
//        System.out.println("玩家1出了一张牌：" + discardTile);
//
//        // 检查玩家2是否可以吃
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        if (rules.isChow()) {
//            System.out.println("玩家2可以吃牌：" + discardTile);
//        } else {
//            System.out.println("玩家2不可以吃牌：" + discardTile);
//        }
//
//        // 打印杠牌后的手牌
//        displayAllHands();
//
//
//        System.out.println("----玩家3能否吃牌（上家出的1万）----");
//        // 模拟玩家2抽牌
//        rules.setPlayerIndex(1);  // 设置当前玩家索引为1，即玩家2
//        MahjongTile discardTile1 = new MahjongTile(1, "万");
//        rules.discardTile = discardTile1;
//        players[1].drawPlayerTile(discardTile1);
//        System.out.println("玩家2抽了一张牌：" + discardTile1);
//
//        // 检查玩家3是否可以吃
//        rules.setPlayerIndex(2);  // 设置当前玩家索引为2，即玩家3
//        if (rules.isChow()) {
//            System.out.println("玩家3可以吃牌：" + discardTile1);
//        } else {
//            System.out.println("玩家3不可以吃牌：" + discardTile1);
//        }
//
//        // 打印杠牌后的手牌
//        displayAllHands();
//    }

    public void testChowRule() {
        System.out.println("----测试吃牌规则----");

        // 测试玩家2能否吃牌（上家出的6条）
        testChowForPlayer(0, 1, new MahjongTile(6, "条"));

        // 打印所有玩家手牌
        displayAllHands();

        // 测试玩家3能否吃牌（上家出的1万）
        testChowForPlayer(1, 2, new MahjongTile(1, "万"));

        // 打印所有玩家手牌
        displayAllHands();
    }

    private void testChowForPlayer(int discardingPlayerIndex, int targetPlayerIndex, MahjongTile discardTile) {
        System.out.println("----玩家" + (targetPlayerIndex + 1) + "能否吃牌（上家出的" + discardTile + "）----");

        // 设置出牌玩家和出牌
        rules.setPlayerIndex(discardingPlayerIndex);
        players[discardingPlayerIndex].dealPlayerTile(discardTile);
        rules.discardTile = discardTile;
        System.out.println("玩家" + (discardingPlayerIndex + 1) + "出了一张牌：" + discardTile);

        // 检查目标玩家是否可以吃
        rules.setPlayerIndex(targetPlayerIndex);
        if (rules.isChow()) {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "可以吃牌：" + discardTile);
        } else {
            System.out.println("玩家" + (targetPlayerIndex + 1) + "不可以吃牌：" + discardTile);
        }
        System.out.println(" ");
    }

    public void testWinRule() {
        System.out.println("----测试胡牌规则----");

        // 测试玩家1的七对胡牌
        testWinForPlayer(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7}, "条", Rules.WinType.SEVEN_PAIRS);

        // 测试玩家2的平胡
        testWinForPlayer(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1, 1, 2, 2}, "混合", Rules.WinType.STRAIGHT);

        // 测试玩家3的将对胡牌
         testWinForPlayer(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7}, "筒", Rules.WinType.ALL_PONGS);

        // 测试清一色胡牌
        testWinForPlayer(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1, 2, 2, 3}, "万", Rules.WinType.PURE_ONE_SUIT);

        // 测试混一色胡牌
        testWinForMixedOneSuit();

        // 测试大三元胡牌
        testWinForBigThreeDragons();

        // 测试小三元胡牌
        testWinForLittleThreeDragons();

        // 测试大四喜胡牌
        testWinForBigFourWinds();

        // 测试小四喜胡牌
        testWinForLittleFourWinds();

        // 测试十三幺胡牌
        testWinForThirteenOrphans();

    }

    private void testWinForPlayer(int[] values, String suit, Rules.WinType winType) {
        System.out.println("----玩家的" + winType + "胡牌规则测试----");

        // 模拟玩家手牌
        List<MahjongTile> hand = new ArrayList<>();
        if ("混合".equals(suit)) {
            for (int i = 0; i < values.length; i++) {
                String currentSuit;
                if (i < 9) {
                    currentSuit = "万";
                } else if (i < 12) {
                    currentSuit = "条";
                } else {
                    currentSuit = "筒";
                }
                hand.add(new MahjongTile(values[i], currentSuit));
            }
        } else {
            for (int value : values) {
                hand.add(new MahjongTile(value, suit));
            }
        }

        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：" + winType);
        } else {
            System.out.println("玩家未胡牌，胡牌类型：" + winType);
        }
        System.out.println(" ");

    }

    private void testWinForMixedOneSuit() {
        List<MahjongTile> hand = new ArrayList<>(Arrays.asList(
                new MahjongTile("东"), // 东
                new MahjongTile("东"), // 东
                new MahjongTile("发"), // 发
                new MahjongTile("发"), // 发
                new MahjongTile("发"), // 发
                new MahjongTile(1, "万"),
                new MahjongTile(2, "万"),
                new MahjongTile(3, "万"),
                new MahjongTile(4, "万"),
                new MahjongTile(5, "万"),
                new MahjongTile(6, "万"),
                new MahjongTile(7, "万"),
                new MahjongTile(8, "万"),
                new MahjongTile(9, "万")

        ));
        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：混一色");
        } else {
            System.out.println("玩家未胡牌，胡牌类型：混一色");
        }
        System.out.println(" ");
    }

    private void testWinForBigFourWinds() {
        List<MahjongTile> hand = new ArrayList<>(Arrays.asList(
                new MahjongTile("东"), // 东
                new MahjongTile("东"), // 东
                new MahjongTile("东"), // 东
                new MahjongTile("南"), // 南
                new MahjongTile("南"), // 南
                new MahjongTile("南"), // 南
                new MahjongTile("西"), // 西
                new MahjongTile("西"), // 西
                new MahjongTile("西"), // 西
                new MahjongTile("北"), // 北
                new MahjongTile("北"), // 北
                new MahjongTile("北"), // 北
                new MahjongTile(1, "筒"), // 任意一个对子
                new MahjongTile(1, "筒")  // 任意一个对子
        ));
        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：大四喜");
        } else {
            System.out.println("玩家未胡牌，胡牌类型：大四喜");
        }
        System.out.println(" ");
    }

    private void testWinForLittleFourWinds() {
        List<MahjongTile> hand = new ArrayList<>(Arrays.asList(
                new MahjongTile("东"), // 东
                new MahjongTile("东"), // 东
                new MahjongTile("东"), // 东
                new MahjongTile("南"), // 南
                new MahjongTile("南"), // 南
                new MahjongTile("南"), // 南
                new MahjongTile("西"), // 西
                new MahjongTile("西"), // 西
                new MahjongTile("西"), // 西
                new MahjongTile("北"), // 北
                new MahjongTile("北"), // 北
                new MahjongTile(1, "筒"), // 任意一个对子
                new MahjongTile(1, "筒"), // 任意一个对子
                new MahjongTile(1, "筒")  // 任意一个对子
        ));
        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：小四喜");
        } else {
            System.out.println("玩家未胡牌，胡牌类型：小四喜");
        }
        System.out.println(" ");
    }

    private void testWinForBigThreeDragons() {
        List<MahjongTile> hand = new ArrayList<>(Arrays.asList(
                new MahjongTile("中"), // 中
                new MahjongTile("中"), // 中
                new MahjongTile("中"), // 中
                new MahjongTile("发"), // 发
                new MahjongTile("发"), // 发
                new MahjongTile("发"), // 发
                new MahjongTile("白"), // 白
                new MahjongTile("白"), // 白
                new MahjongTile("白"), // 白
                new MahjongTile("东"), // 任意一个对子
                new MahjongTile("东"), // 任意一个对子
                new MahjongTile(1, "筒"), // 任意一个对子
                new MahjongTile(2, "筒"), // 任意一个对子
                new MahjongTile(3, "筒")  // 任意一个对子
        ));
        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：大三元");
        } else {
            System.out.println("玩家未胡牌，胡牌类型：大三元");
        }
        System.out.println(" ");
    }

    private void testWinForLittleThreeDragons() {
        List<MahjongTile> hand = new ArrayList<>(Arrays.asList(
                new MahjongTile("中"), // 中
                new MahjongTile("中"), // 中
                new MahjongTile("中"), // 中
                new MahjongTile("发"), // 发
                new MahjongTile("发"), // 发
                new MahjongTile("发"), // 发
                new MahjongTile("白"), // 东
                new MahjongTile("白"), // 东
                new MahjongTile(1, "筒"), // 其他牌形成顺子或刻子
                new MahjongTile(2, "筒"), // 其他牌形成顺子或刻子
                new MahjongTile(3, "筒"), // 其他牌形成顺子或刻子
                new MahjongTile(4, "条"), // 其他牌形成顺子或刻子
                new MahjongTile(4, "条"), // 其他牌形成顺子或刻子
                new MahjongTile(4, "条")  // 其他牌形成顺子或刻子
        ));
        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：小三元");
        } else {
            System.out.println("玩家未胡牌，胡牌类型：小三元");
        }
        System.out.println(" ");
    }

    private void testWinForThirteenOrphans() {
        List<MahjongTile> hand = new ArrayList<>(Arrays.asList(
                new MahjongTile(1, "筒"), // 1筒
                new MahjongTile(9, "筒"), // 9筒
                new MahjongTile(1, "条"), // 1条
                new MahjongTile(9, "条"), // 9条
                new MahjongTile(1, "万"), // 1万
                new MahjongTile(9, "万"), // 9万
                new MahjongTile("东"), // 东
                new MahjongTile("南"), // 南
                new MahjongTile("西"), // 西
                new MahjongTile("北"), // 北
                new MahjongTile("中"), // 中
                new MahjongTile("发"), // 发
                new MahjongTile("白"), // 白
                new MahjongTile(1, "筒") // 对1筒
        ));
        // 打印手牌
        System.out.println("玩家的手牌：" + hand);

        // 检查玩家是否胡牌
        if (rules.isWin(hand)) {
            System.out.println("玩家胡牌成功，胡牌类型：十三幺");
        } else {
            System.out.println("玩家未胡牌，胡牌类型：十三幺");
        }
        System.out.println(" ");
    }

    public static void main(String[] args) {
        TestForRule testForRule = new TestForRule();
        testForRule.startGame();
    }
}
