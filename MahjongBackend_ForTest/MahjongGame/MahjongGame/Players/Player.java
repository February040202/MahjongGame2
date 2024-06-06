package Players;

import Mahjong.MahjongDeck;
import Mahjong.MahjongTile;
import GameRules.Rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player {
    public List<MahjongTile> hand;
    public List<MahjongTile> rulesTiles;
    private static Player[] players;
    private int playerIndex;
    private MahjongTile discardTile;
    private Rules rules;
    private MahjongDeck deck;
    private Random random;
    //private MahjongGame.MahjongGame game;
    public Player(int playerIndex, Rules rules, MahjongDeck deck) {
        hand = new ArrayList<>();
        rulesTiles = new ArrayList<>();
        random = new Random();
        this.playerIndex = playerIndex;
        this.deck = deck;
        this.rules = rules;
        players = PlayerManager.getPlayers();
    }

    public static Player[] getPlayers() {
        return players;
    }

    public void dealPlayerTile(MahjongTile tile) {
        if (hand.contains(tile)) {
            hand.remove(tile);
        } else {
            //System.out.println("玩家手牌中没有这张牌：" + tile);
        }
    }

    public List<MahjongTile> getRulesTiles() {
        return rulesTiles;
    }
    public List<MahjongTile> getHand() {
        return hand;
    }

    public void drawPlayerTile(MahjongTile tile) {
        hand.add(tile);
    }

    public MahjongTile getDiscardTile() {
        return discardTile;
    }

    public void displayHand() {
        sortHand();
        for (MahjongTile tile : hand) {
            System.out.print(tile + " ");
        }
        System.out.println();
    }

    public void displayRulesTiles() {
        System.out.println("当前规则牌：");
        for (MahjongTile tile : rulesTiles) {
            System.out.print(tile + " ");
        }
        System.out.println();
    }

    public void sortHand() {
        Collections.sort(hand, (t1, t2) -> {
            int suitOrder1 = getSuitOrder(t1.getSuit());
            int suitOrder2 = getSuitOrder(t2.getSuit());
            if (suitOrder1 != suitOrder2) {
                return suitOrder1 - suitOrder2;
            }
            return t1.getValue() - t2.getValue();
        });
    }

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

    public int dice() {
        Random random = new Random();
        int randomNumber = random.nextInt(21) + 4;
        return randomNumber % 4;
    }

    public void updateHand() {
        int index = 0;
        for (Player player : players) {
            System.out.println("Player " + (index + 1) +" 's hand:");
            player.displayHand();
            player.displayRulesTiles();
            System.out.println();
            index += 1;
        }
    }


    public MahjongTile playerPlay() {
        playerIndex = getPlayerIndex();
        discardTile = rules.getDiscardTile();
        if (discardTile != null && rules.isGang()) {
            System.out.println("你已杠牌，进行出牌");
            rules.discardTile();//进行出牌操作
        } else if (discardTile != null && rules.isPong()) {
            System.out.println("你已碰牌，进行出牌");
            rules.discardTile();//进行出牌操作
        } else if (discardTile != null && rules.isChow()) {
            System.out.println("你已吃牌，进行出牌");
            rules.discardTile();//进行出牌操作
        } else {
            System.out.println("你没有进行任何操作，进行出牌");
            MahjongTile tile = deck.drawTile();
            System.out.println("摸到的牌：" + tile);
            drawPlayerTile(tile);
            rules.discardTile();
        }

        discardTile = rules.getDiscardTile();
        System.out.println("你打出的牌：" + discardTile);
        playerIndex = rules.updatePlayerIndex();
        return null;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

}
