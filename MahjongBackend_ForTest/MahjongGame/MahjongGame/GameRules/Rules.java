package GameRules;

import Mahjong.MahjongTile;
import Players.Player;
import Mahjong.MahjongGameManager;

import java.util.*;

public class Rules {
    private Scanner scanner;
    private Player[] players;
    public MahjongTile discardTile;
    private int playerIndex;
    private MahjongTile secondTile;
    private MahjongTile thirdTile;
    List<MahjongTile> rulesTiles;
    private MahjongTile fourthTile;

    public Rules(Player[] players) {
        scanner = new Scanner(System.in);
        this.players = players;
    }
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public MahjongTile getDiscardTile() {
        return discardTile;
    }
    public List<MahjongTile> getRulesTiles() {
        return players[playerIndex].getRulesTiles();
    }

    /**
     * 对规则牌进行排序
     * @param rulesTiles 规则牌列表
     */
    public void sortRulesTiles(List<MahjongTile> rulesTiles) {
        rulesTiles.sort((t1, t2) -> {
            int suitOrder1 = getSuitOrder(t1.getSuit());
            int suitOrder2 = getSuitOrder(t2.getSuit());
            if (suitOrder1 != suitOrder2) {
                return suitOrder1 - suitOrder2;
            }
            return t1.getValue() - t2.getValue();
        });
    }

    /**
     * 获取花色的排序顺序
     * @param suit 花色
     * @return 排序顺序
     */
    private int getSuitOrder(String suit) {
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

    //------------TouchDeal-----------
    public void discardTile() {
        Player currentPlayer = players[playerIndex];
        System.out.println("请输入您要出的牌（值 牌面）：");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            System.out.println("输入格式不正确，请重新输入！");
            discardTile();
            return;
        }

        int value;
        try {
            value = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            System.out.println("牌值必须为整数，请重新输入！");
            discardTile();
            return;
        }

        String suit = parts[1];
        MahjongTile Tile = new MahjongTile(value, suit);

        if (!isTileInHand(Tile, currentPlayer)) {
            System.out.println("您手上的牌中没有该牌，请重新输入！");
            discardTile();
            return;
        }

        discardTile = Tile;
        MahjongGameManager.usedTiles.add(Tile);
        MahjongGameManager.ifDiscardTiles = true;

        currentPlayer.dealPlayerTile(Tile);
        System.out.println("Player " + (playerIndex + 1) + "出了一张牌：" + Tile);
    }

    /**
     * 检查牌是否在手牌中
     * @param tile 牌
     * @param player 玩家
     * @return 如果牌在手牌中返回true，否则返回false
     */
    private boolean isTileInHand(MahjongTile tile, Player player) {
        for (MahjongTile tile2 : player.getHand()) {
            if (tile2.getValue() == tile.getValue() && tile2.getSuit().equals(tile.getSuit())) {
                return true;
            }
        }
        return false;
    }

    //----------------------------------------------------Chow--------------------------------------------------------

    public int updatePlayerIndex() {
        playerIndex = (playerIndex + 1) % 4;
        return playerIndex;
    }

    public boolean canChow(MahjongTile discardedTile, int playerIndex, Player[] players) {
        //System.out.println("出的牌是：---->>>" + discardedTile);

        if (discardedTile != null) {
            int value = discardedTile.getValue();
            String suit = discardedTile.getSuit();
            // 检测一下读取的牌是否正确
            System.out.println("所出手牌为: " + value + suit);

            // 获取当前玩家或电脑的手牌
            List<MahjongTile> hand = players[playerIndex].getHand();

            //System.out.println("Hand: " + hand);

            // 检查是否为万、筒、条中的一种
            if (!suit.equals("万") && !suit.equals("筒") && !suit.equals("条")) {
                return false;
            }

            for (MahjongTile tile : hand) {
                if (tile.getSuit().equals(suit)) {
                    // 如果值为1，查找是否有值为2和3的牌
                    if (value == 1 && tileExists(2, suit, playerIndex, players) && tileExists(3, suit, playerIndex, players)) {
                        secondTile = new MahjongTile(2, suit);
                        thirdTile = new MahjongTile(3, suit);
                        System.out.println("[从1查 " + value + secondTile + thirdTile + "]");
                        return true;
                    }
                    // 如果值为9，查找是否有值为8和7的牌
                    else if (value == 9 && tileExists(8, suit, playerIndex, players) && tileExists(7, suit, playerIndex, players)) {
                        secondTile = new MahjongTile(8, suit);
                        thirdTile = new MahjongTile(7, suit);
                        System.out.println("[从9查 " + value + secondTile + thirdTile + "]");
                        return true;
                    }
                    // 如果值为2，分两部分查，第一查（1,3），第二查（3,4）
                    else if (value == 2) {
                        if (tileExists(1, suit, playerIndex, players) && tileExists(3, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(1, suit);
                            thirdTile = new MahjongTile(3, suit);
                            System.out.println("[2查（1,3） " + value + secondTile + thirdTile + "]");
                            return true;
                        } else if (tileExists(3, suit, playerIndex, players) && tileExists(4, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(3, suit);
                            thirdTile = new MahjongTile(4, suit);
                            System.out.println("[2查（3,4） " + value + secondTile + thirdTile + "]");
                            return true;
                        }
                        return false;
                    }
                    // 如果值为8，分两部分查，第一查（7,9），第二查（6,7）
                    else if (value == 8) {
                        if (tileExists(7, suit, playerIndex, players) && tileExists(9, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(7, suit);
                            thirdTile = new MahjongTile(9, suit);
                            System.out.println("[8查（7,9） " + value + secondTile + thirdTile + "]");
                            return true;
                        } else if (tileExists(6, suit, playerIndex, players) && tileExists(7, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(6, suit);
                            thirdTile = new MahjongTile(7, suit);
                            System.out.println("[8查（6,7） " + value + secondTile + thirdTile + "]");
                            return true;
                        }
                        return false;
                    }
                    // 如果值为3~7，分三部分查，第一查（value-1和value-2），第二查（value+1和value+2），第三查（value-1和value+1）
                    if (value >= 3 && value <= 7) {
                        // 检查顺子 -1 和 -2
                        if (tileExists(value - 1, suit, playerIndex, players) && tileExists(value - 2, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(value - 1, suit);
                            thirdTile = new MahjongTile(value - 2, suit);
                            System.out.println("[-1-2 " + value + secondTile + thirdTile + "]");
                            return true;
                        }
                        // 检查顺子 +1 和 +2
                        if (tileExists(value + 1, suit, playerIndex, players) && tileExists(value + 2, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(value + 1, suit);
                            thirdTile = new MahjongTile(value + 2, suit);
                            System.out.println("[+1+2 " + value + secondTile + thirdTile + "]");
                            return true;
                        }
                        // 检查顺子 -1 和 +1
                        if (tileExists(value - 1, suit, playerIndex, players) && tileExists(value + 1, suit, playerIndex, players)) {
                            secondTile = new MahjongTile(value - 1, suit);
                            thirdTile = new MahjongTile(value + 1, suit);
                            System.out.println("[-1+1 " + value + secondTile + thirdTile + "]");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean tileExists(int value, String suit, int playerIndex, Player[] players) {
        List<MahjongTile> hand = players[playerIndex].getHand();
        for (MahjongTile tile : hand) {
            if (tile.getValue() == value && tile.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    public void chowTile(MahjongTile discardedTile, int playerIndex) {
        List<MahjongTile> rulesTile = getRulesTiles();
        // 添加顺子牌到新的列表中
        rulesTile.add(discardedTile);
        rulesTile.add(secondTile);
        rulesTile.add(thirdTile);
        sortRulesTiles(rulesTile);
        players[playerIndex].sortHand();
        //System.out.println(playerIndex + 1);
        // 输出吃牌信息
        System.out.println("玩家" + (playerIndex + 1) + "吃牌成功：" + rulesTile);
        rulesTiles = rulesTile;
        players[playerIndex].dealPlayerTile(secondTile);
        players[playerIndex].dealPlayerTile(thirdTile);
    }

    public boolean isChow() {
        //System.out.println("进入判断isChow");
        //System.out.println("在isChow方法中discardedTile是: " + discardTile);

        if (canChow(discardTile, playerIndex, players)) {
            System.out.println("可以吃牌");
            chowTile(discardTile, playerIndex);
            return true;
        } else {
            System.out.println("不可以吃牌");
            return false;
        }
    }

//-----------------------------------------------Pong---------------------------------------------------

    public boolean canPong(MahjongTile discardedTile, int playerIndex, Player[] players) {
        //System.out.println("出的牌是：---->>>" + discardedTile);

        if (discardedTile != null) {
            int value = discardedTile.getValue();
            String suit = discardedTile.getSuit();
            // 检测一下读取的牌是否正确
            System.out.println("所出手牌为: " + value + suit);

            // 获取当前玩家或电脑的手牌
            List<MahjongTile> hand = players[playerIndex].getHand();

            int count = 0;
            for (MahjongTile tile : hand) {
                if (Objects.equals(tile.getSuit(), suit) && tile.getValue() == value) {
                    secondTile = tile;
                    thirdTile = tile;
                    count++;
                }
                if (count >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    // 进行碰牌操作
    public void PongTile(MahjongTile discardedTile, int playerIndex) {
        List<MahjongTile> rulesTile = players[playerIndex].getRulesTiles();
        // 添加顺子牌到新的列表中
        rulesTile.add(discardedTile);
        rulesTile.add(secondTile);
        rulesTile.add(thirdTile);
        sortRulesTiles(rulesTile);
        players[playerIndex].sortHand();
        //System.out.println(playerIndex + 1);
        // 输出吃牌信息
        System.out.println("玩家" + (playerIndex + 1) + "碰牌成功：" + discardedTile);
        rulesTiles = rulesTile;
        players[playerIndex].dealPlayerTile(secondTile);
        players[playerIndex].dealPlayerTile(thirdTile);
    }

    public boolean isPong() {
        if (canPong(discardTile, playerIndex, players)) {
            System.out.println("可以碰牌");
            PongTile(discardTile, playerIndex);
            return true;
        } else {
            System.out.println("不可以碰牌");
            return false;
        }
    }

    //----------------------------------Gang----------------------------------
    // 检查是否可以杠牌
    public boolean canGang(MahjongTile discardedTile, int playerIndex, Player[] players) {
        int value = discardedTile.getValue();
        String suit = discardedTile.getSuit();
        List<MahjongTile> hand = players[playerIndex].getHand();

        int count = 0;
        for (MahjongTile tile : hand) {
            if (Objects.equals(tile.getSuit(), suit) && tile.getValue() == value) {
                if (count == 0) {
                    secondTile = tile;
                } else if (count == 1) {
                    thirdTile = tile;
                } else if (count == 2) {
                    fourthTile = tile;
                }
                count++;
            }
        }
        // 如果手中有3张，且出牌与这些牌相同，则可以杠
        if (count == 3) {
            return true;
        }

        // 检查规则牌中是否有3张相同的牌
        List<MahjongTile> rulesTiles = players[playerIndex].getRulesTiles();
        count = 0;
        for (MahjongTile tile : rulesTiles) {
            if (Objects.equals(tile.getSuit(), suit) && tile.getValue() == value) {
                count++;
            }
        }

        // 如果规则牌中有3张，且自己摸到第4张，则可以杠
        if (count == 3 && hand.contains(discardedTile)) {
            fourthTile = discardedTile;
            return true;
        }

        return false;
    }

    // 进行杠牌操作
    public void gangTile(MahjongTile discardedTile, int playerIndex) {
        List<MahjongTile> rulesTile = players[playerIndex].getRulesTiles();

        // 检查是否是通过碰牌凑成的杠
        int value = discardedTile.getValue();
        String suit = discardedTile.getSuit();
        int count = 0;
        for (MahjongTile tile : rulesTile) {
            if (Objects.equals(tile.getSuit(), suit) && tile.getValue() == value) {
                count++;
            }
        }

        if (count == 3) {
            rulesTile.add(fourthTile);
            players[playerIndex].dealPlayerTile(fourthTile);
        } else {
            rulesTile.add(discardedTile);
            rulesTile.add(secondTile);
            rulesTile.add(thirdTile);
            rulesTile.add(fourthTile);
            players[playerIndex].dealPlayerTile(secondTile);
            players[playerIndex].dealPlayerTile(thirdTile);
            players[playerIndex].dealPlayerTile(fourthTile);
        }

        sortRulesTiles(rulesTile);
        players[playerIndex].sortHand();
        System.out.println("玩家" + (playerIndex + 1) + "杠牌成功：" + rulesTile);
        rulesTiles = rulesTile;
    }

    public boolean isGang() {
        if (canGang(discardTile, playerIndex, players)) {
            System.out.println("可以杠牌");
            gangTile(discardTile, playerIndex);
            return true;
        } else {
            System.out.println("不可以杠牌");
            return false;
        }
    }

    //----------------------------------Win-------------------------------------
    public enum WinType {
        SEVEN_PAIRS, // 七对
        STRAIGHT,    // 平胡
        ALL_PONGS,    // 将对
        PURE_ONE_SUIT, // 清一色
        MIXED_ONE_SUIT, // 混一色
        LITTLE_THREE_DRAGONS, // 小三元
        BIG_THREE_DRAGONS, // 大三元
        LITTLE_FOUR_WINDS, // 小四喜
        BIG_FOUR_WINDS, // 大四喜
        THIRTEEN_ORPHANS// 十三幺
        // 其他胡牌方式可以继续添加
    }

    /**
     * 判断是否胡牌
     * @param hand 手牌列表
     * @return 如果胡牌返回true，否则返回false
     */
    public boolean isWin(List<MahjongTile> hand) {
        // 遍历所有的胡牌方式
        for (WinType type : WinType.values()) {
            // 检查当前胡牌方式是否满足胡牌条件
            if (checkWin(hand, type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据胡牌类型检查是否胡牌
     * @param hand 手牌列表
     * @param type 胡牌类型
     * @return 如果满足胡牌条件返回true，否则返回false
     */
    private boolean checkWin(List<MahjongTile> hand, WinType type) {
        switch (type) {
            case SEVEN_PAIRS:
                return checkSevenPairs(hand);
            case STRAIGHT:
                return checkStraight(hand);
            case ALL_PONGS:
                return checkAllPongs(hand);
            case PURE_ONE_SUIT:
                return checkPureOneSuit(hand);
            case MIXED_ONE_SUIT:
                return checkMixedOneSuit(hand);
            case BIG_THREE_DRAGONS:
                return checkBigThreeDragons(hand);
            case LITTLE_FOUR_WINDS:
                return checkLittleFourWinds(hand);
            case THIRTEEN_ORPHANS:
                return checkThirteenOrphans(hand);
            case LITTLE_THREE_DRAGONS:
                return checkLittleThreeDragons(hand);
            case BIG_FOUR_WINDS:
                return checkBigFourWinds(hand);
            // 添加其他胡牌方式的判断条件
            default:
                return false;
        }
    }

    /**
     * 检查大四喜胡牌
     * @param hand 手牌列表
     * @return 如果满足大四喜胡牌条件返回true，否则返回false
     */
    private boolean checkBigFourWinds(List<MahjongTile> hand) {
        if (hand.size() != 14) {
            return false;
        }

        // Count the occurrences of each wind tile
        int countEast = 0;
        int countSouth = 0;
        int countWest = 0;
        int countNorth = 0;

        for (MahjongTile tile : hand) {
            if (tile.getSuit().equals("东")) {
                countEast++;
            } else if (tile.getSuit().equals("南")) {
                countSouth++;
            } else if (tile.getSuit().equals("西")) {
                countWest++;
            } else if (tile.getSuit().equals("北")) {
                countNorth++;
            }
        }

        // Check for four sets of three identical wind tiles
        return countEast >= 3 && countSouth >= 3 && countWest >= 3 && countNorth >= 3;
    }

    /**
     * 检查小三元胡牌
     * @param hand 手牌列表
     * @return 如果满足小三元胡牌条件返回true，否则返回false
     */
    private boolean checkLittleThreeDragons(List<MahjongTile> hand) {
        if (hand.size() != 14) {
            return false;
        }

        // Count the occurrences of each dragon tile
        int countRed = 0;
        int countGreen = 0;
        int countWhite = 0;

        for (MahjongTile tile : hand) {
            if (tile.isRedDragon()) {
                countRed++;
            } else if (tile.isGreenDragon()) {
                countGreen++;
            } else if (tile.isWhiteDragon()) {
                countWhite++;
            }
        }

        // Check for two sets of three identical dragon tiles and one pair of the third dragon tile
        int tripletCount = 0;
        int pairCount = 0;

        if (countRed >= 3) tripletCount++;
        if (countGreen >= 3) tripletCount++;
        if (countWhite >= 3) tripletCount++;

        if (countRed == 2) pairCount++;
        if (countGreen == 2) pairCount++;
        if (countWhite == 2) pairCount++;

        return tripletCount == 2 && pairCount == 1 && checkAllPongs(hand);
    }


    /**
     * 检查清一色胡牌
     * @param hand 手牌列表
     * @return 如果满足清一色胡牌条件返回true，否则返回false
     */
    private boolean checkPureOneSuit(List<MahjongTile> hand) {
        // 检查手牌是否全为同一种花色
        String suit = hand.get(0).getSuit();
        for (MahjongTile tile : hand) {
            if (!tile.getSuit().equals(suit)) {
                return false;
            }
        }
        // 检查是否满足平胡条件
        return checkStraight(hand);
    }

    /**
     * 检查混一色胡牌
     * @param hand 手牌列表
     * @return 如果满足混一色胡牌条件返回true，否则返回false
     */
    private boolean checkMixedOneSuit(List<MahjongTile> hand) {
        // 检查手牌是否包含一种花色和字牌
        boolean hasHonor = false;
        String suit = null;
        for (MahjongTile tile : hand) {
            if (tile.isHonor()) {
                hasHonor = true;
            } else {
                if (suit == null) {
                    suit = tile.getSuit();
                } else if (!tile.getSuit().equals(suit)) {
                    return false;
                }
            }
        }
        return hasHonor && suit != null && checkStraight(hand);
    }

    /**
     * 检查大三元胡牌
     * @param hand 手牌列表
     * @return 如果满足大三元胡牌条件返回true，否则返回false
     */
    private boolean checkBigThreeDragons(List<MahjongTile> hand) {
        // 检查手牌是否包含三个刻子的中发白
        int countRed = 0;
        int countGreen = 0;
        int countWhite = 0;

        for (MahjongTile tile : hand) {
            if (tile.isRedDragon()) {
                countRed++;
            } else if (tile.isGreenDragon()) {
                countGreen++;
            } else if (tile.isWhiteDragon()) {
                countWhite++;
            }
        }

        return countRed >= 3 && countGreen >= 3 && countWhite >= 3 && checkAllPongs(hand);
    }

    /**
     * 检查小四喜胡牌
     * @param hand 手牌列表
     * @return 如果满足小四喜胡牌条件返回true，否则返回false
     */
    private boolean checkLittleFourWinds(List<MahjongTile> hand) {
        if (hand.size() != 14) {
            return false;
        }

        // Count the occurrences of each wind tile
        int countEast = 0;
        int countSouth = 0;
        int countWest = 0;
        int countNorth = 0;

        for (MahjongTile tile : hand) {
            if (tile.getSuit().equals("东")) {
                countEast++;
            } else if (tile.getSuit().equals("南")) {
                countSouth++;
            } else if (tile.getSuit().equals("西")) {
                countWest++;
            } else if (tile.getSuit().equals("北")) {
                countNorth++;
            }
        }

        // Check for three sets of three identical wind tiles and one pair of the fourth wind tile
        int tripletCount = 0;
        int pairCount = 0;

        if (countEast >= 3) tripletCount++;
        if (countSouth >= 3) tripletCount++;
        if (countWest >= 3) tripletCount++;
        if (countNorth >= 3) tripletCount++;

        if (countEast == 2) pairCount++;
        if (countSouth == 2) pairCount++;
        if (countWest == 2) pairCount++;
        if (countNorth == 2) pairCount++;

        return tripletCount == 3 && pairCount == 1;
    }


    /**
     * 检查十三幺胡牌
     * @param hand 手牌列表
     * @return 如果满足十三幺胡牌条件返回true，否则返回false
     */
    private boolean checkThirteenOrphans(List<MahjongTile> hand) {
        // 检查手牌是否包含所有的幺九牌和其中的一对
        Set<String> requiredTiles = new HashSet<>(Arrays.asList(
                "1筒", "9筒",
                "1条", "9条",
                "1万", "9万",
                "东", "南", "西", "北",
                "中", "发", "白"
        ));

        Map<String, Integer> tileCount = new HashMap<>();
        for (MahjongTile tile : hand) {
            String tileKey = tile.toString();
            tileCount.put(tileKey, tileCount.getOrDefault(tileKey, 0) + 1);
        }

        boolean hasPair = false;
        for (String requiredTile : requiredTiles) {
            if (!tileCount.containsKey(requiredTile)) {
                return false;
            } else if (tileCount.get(requiredTile) == 2) {
                hasPair = true;
            }
        }

        return hasPair;
    }


    /**
     * 检查七对胡牌
     * @param hand 手牌列表
     * @return 如果满足七对胡牌条件返回true，否则返回false
     */
    private boolean checkSevenPairs(List<MahjongTile> hand) {
        // 七对需要手牌中的每张牌都有一对
        if (hand.size() != 14) {
            return false;
        }

        // 检查手牌中的每两张牌是否一样
        for (int i = 0; i < 14; i += 2) {
            if (!hand.get(i).equals(hand.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查平胡
     * @param hand 手牌列表
     * @return 如果满足平胡条件返回true，否则返回false
     */
    public boolean checkStraight(List<MahjongTile> hand) {
        // 平胡需要手牌中的牌符合一定的顺子或刻子的条件
        if (hand.size() != 14) {
            return false;
        }

        // 将手牌按照花色和数值排序
        hand.sort((t1, t2) -> {
            int suitOrder1 = getSuitOrder(t1.getSuit());
            int suitOrder2 = getSuitOrder(t2.getSuit());
            if (suitOrder1 != suitOrder2) {
                return suitOrder1 - suitOrder2;
            }
            return t1.getValue() - t2.getValue();
        });

        // 检查所有可能的组合
        return checkCombinations(hand, 4, 0) ||
                checkCombinations(hand, 3, 1) ||
                checkCombinations(hand, 2, 2) ||
                checkCombinations(hand, 1, 3) ||
                checkCombinations(hand, 0, 4);
    }

    /**
     * 检查是否满足指定数量的顺子和刻子的组合
     * @param hand 手牌列表
     * @param numSequences 顺子的数量
     * @param numTriplets 刻子的数量
     * @return 如果满足条件返回true，否则返回false
     */
    private boolean checkCombinations(List<MahjongTile> hand, int numSequences, int numTriplets) {
        if (hand.size() != 14) {
            return false;
        }

        List<MahjongTile> remainingHand = new ArrayList<>(hand);

        // 尝试找到一对将
        for (int i = 0; i < remainingHand.size() - 1; i++) {
            if (remainingHand.get(i).equals(remainingHand.get(i + 1))) {
                List<MahjongTile> handWithoutPair = new ArrayList<>(remainingHand);
                handWithoutPair.remove(i);
                handWithoutPair.remove(i);

                if (canFormValidSets(handWithoutPair, numSequences, numTriplets)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查剩余手牌是否能够组成有效的顺子和刻子
     * @param hand 剩余的手牌列表
     * @param numSequences 顺子的数量
     * @param numTriplets 刻子的数量
     * @return 如果能够组成有效的顺子和刻子返回true，否则返回false
     */
    private boolean canFormValidSets(List<MahjongTile> hand, int numSequences, int numTriplets) {
        if (hand.isEmpty() && numSequences == 0 && numTriplets == 0) {
            return true;
        }

        if (numSequences > 0 && canFormSequence(hand)) {
            List<MahjongTile> remainingHand = removeSequence(hand);
            if (canFormValidSets(remainingHand, numSequences - 1, numTriplets)) {
                return true;
            }
        }

        if (numTriplets > 0 && canFormTriplet(hand)) {
            List<MahjongTile> remainingHand = removeTriplet(hand);
            if (canFormValidSets(remainingHand, numSequences, numTriplets - 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查手牌中是否有顺子
     * @param hand 手牌列表
     * @return 如果有顺子返回true，否则返回false
     */
    private boolean canFormSequence(List<MahjongTile> hand) {
        if (hand.size() < 3) {
            return false;
        }

        MahjongTile firstTile = hand.get(0);
        MahjongTile secondTile = new MahjongTile(firstTile.getValue() + 1, firstTile.getSuit());
        MahjongTile thirdTile = new MahjongTile(firstTile.getValue() + 2, firstTile.getSuit());

        return hand.contains(secondTile) && hand.contains(thirdTile);
    }

    /**
     * 从手牌中移除顺子
     * @param hand 手牌列表
     * @return 移除顺子后的剩余手牌列表
     */
    private List<MahjongTile> removeSequence(List<MahjongTile> hand) {
        List<MahjongTile> remainingHand = new ArrayList<>(hand);
        MahjongTile firstTile = hand.get(0);
        MahjongTile secondTile = new MahjongTile(firstTile.getValue() + 1, firstTile.getSuit());
        MahjongTile thirdTile = new MahjongTile(firstTile.getValue() + 2, firstTile.getSuit());

        remainingHand.remove(firstTile);
        remainingHand.remove(secondTile);
        remainingHand.remove(thirdTile);

        return remainingHand;
    }

    /**
     * 检查手牌中是否有刻子
     * @param hand 手牌列表
     * @return 如果有刻子返回true，否则返回false
     */
    private boolean canFormTriplet(List<MahjongTile> hand) {
        if (hand.size() < 3) {
            return false;
        }

        MahjongTile firstTile = hand.get(0);
        int count = 0;
        for (MahjongTile tile : hand) {
            if (tile.equals(firstTile)) {
                count++;
            }
        }

        return count >= 3;
    }

    /**
     * 从手牌中移除刻子
     * @param hand 手牌列表
     * @return 移除刻子后的剩余手牌列表
     */
    private List<MahjongTile> removeTriplet(List<MahjongTile> hand) {
        List<MahjongTile> remainingHand = new ArrayList<>(hand);
        MahjongTile firstTile = hand.get(0);

        remainingHand.remove(firstTile);
        remainingHand.remove(firstTile);
        remainingHand.remove(firstTile);

        return remainingHand;
    }

    /**
     * 检查将对（全部为刻子）
     * @param hand 手牌列表
     * @return 如果满足将对胡牌条件返回true，否则返回false
     */
    private boolean checkAllPongs(List<MahjongTile> hand) {
        if (hand.size() != 14) {
            return false;
        }

        List<MahjongTile> remainingHand = new ArrayList<>(hand);

        // 尝试找到一对将
        for (int i = 0; i < remainingHand.size() - 1; i++) {
            if (remainingHand.get(i).equals(remainingHand.get(i + 1))) {
                List<MahjongTile> handWithoutPair = new ArrayList<>(remainingHand);
                handWithoutPair.remove(i);
                handWithoutPair.remove(i);
                if (canFormAllTriplets(handWithoutPair)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查手牌是否全部为刻子
     * @param hand 剩余的手牌列表
     * @return 如果全部为刻子返回true，否则返回false
     */
    private boolean canFormAllTriplets(List<MahjongTile> hand) {
        if (hand.isEmpty()) {
            return true;
        }

        MahjongTile firstTile = hand.get(0);
        int count = 0;
        for (MahjongTile tile : hand) {
            if (tile.equals(firstTile)) {
                count++;
            }
        }

        if (count >= 3) {
            List<MahjongTile> remainingHand = new ArrayList<>(hand);
            remainingHand.remove(firstTile);
            remainingHand.remove(firstTile);
            remainingHand.remove(firstTile);

            return canFormAllTriplets(remainingHand);
        }

        return false;
    }
}
