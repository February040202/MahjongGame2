package Mahjong;

import GameRules.Rules;
import Players.Player;
import Players.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class MahjongGameManager {
    private MahjongDeck deck;
    public static Player[] players; // players connect with GameScreen
    private Rules rules;
    private int playerIndex;
    public static List<MahjongTile> Player_hand; // connect with Game Screen

    public static List<MahjongTile> computer1_hand;
    public static List<MahjongTile> computer2_hand;
    public static List<MahjongTile> computer3_hand;

    public static Player player;

    public static boolean ifDealTiles = false; // connect with Game Screen

    public static boolean ifDiscardTiles = false; //

    public static List<MahjongTile> usedTiles = new ArrayList<>();

    public MahjongGameManager() {
        PlayerManager playerManager = new PlayerManager();
        players = playerManager.getPlayers();
        deck = new MahjongDeck();
        rules = new Rules(players);
        playerIndex = 0;
    }

    public void startGame() {
        System.out.println("游戏开始！");
        dealTiles();
        update_static_parameter();
        firstRoundHandTile();
        while (!isGameOver()) {
            for (Player player : players) {
                update_static_parameter();
                playerTurn(player);
            }
        }
        System.out.println("游戏结束！");
    }

    public void playerTurn(Player player) {
        System.out.println("\nPlayer " + (playerIndex + 1) + " 的回合：");
        player.updateHand();
        MahjongTile discardedTile = player.playerPlay();
        if (discardedTile != null) {
            player.getHand().remove(discardedTile);
            usedTiles.add(discardedTile);
            ifDiscardTiles = true;
        }
        if (playerIndex == 0) {
            setPlayer(player); // 当player index=0 更新Game Screen上玩家的手牌
        }
        playerIndex = rules.updatePlayerIndex();
        System.out.println();
    }

    public static Player[] getPlayers() {
        return players;
    }

    public List<MahjongTile> getPlayer_hand() {
        return Player_hand;
    }

    public boolean isIfDealTiles() {
        return ifDealTiles;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        MahjongGameManager.player = player;
    }

    public static List<MahjongTile> sortTiles_pre(List<MahjongTile> hand) {
        List<MahjongTile> new_hand_1 = new ArrayList<>();
        Integer[] sequence_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        // 遍历排序后的顺序
        for (int value : sequence_1) {
            // 遍历玩家手牌
            for (MahjongTile tile : hand) {
                // 如果牌的值与当前顺序值相同，将其添加到新手牌列表中
                if (tile.getValue() == value) {
                    new_hand_1.add(tile);
                }
            }
        }

        List<MahjongTile> new_hand_2 = new ArrayList<>();
        String[] sequence = {"万", "条", "筒", "东", "南", "西", "北", "中", "发", "白"};
        for (int j = 0; j < 10; j++) {
            for (MahjongTile tile : new_hand_1) {
                if (tile.getSuit().equals(sequence[j])) {
                    new_hand_2.add(tile);
                }
            }
        }

        return new_hand_2;
    }

    public void displayHand_pre(List<MahjongTile> hand) {
        System.out.println("Player: ");
        for (MahjongTile tile : hand) {
            System.out.print(tile.toString() + " ");
        }
        System.out.println();
    }

    public void displayHand_test(List<MahjongTile> hand) {
        for (MahjongTile tile : hand) {
            System.out.print(tile.toString() + " ");
        }
        System.out.println();
    }

    public void displayHand_pre_computer(List<MahjongTile> hand) {
        System.out.println("Computer: ");
        for (MahjongTile tile : hand) {
            System.out.print(tile.toString() + " ");
        }
        System.out.println();
    }

    public void update_static_parameter() {
        ifDealTiles = true;

        if (!players[0].hand.isEmpty()) {
            Player_hand = sortTiles_pre(players[0].hand);
            players[0].hand = Player_hand;
            displayHand_pre(Player_hand);
        }

        if (!players[1].hand.isEmpty()) {
            computer1_hand = sortTiles_pre(players[1].hand);
            players[1].hand = computer1_hand;
            displayHand_pre_computer(computer1_hand);
        }

        if (!players[2].hand.isEmpty()) {
            computer2_hand = sortTiles_pre(players[2].hand);
            players[2].hand = computer2_hand;
            displayHand_pre_computer(computer2_hand);
        }

        if (!players[3].hand.isEmpty()) {
            computer3_hand = sortTiles_pre(players[3].hand);
            players[3].hand = computer3_hand;
            displayHand_pre_computer(computer3_hand);
        }

        // 确保player对象更新为第一个玩家对象
        player = players[0];
    }

    public void firstRoundHandTile() {
        for (Player player : players) {
            for (int j = 0; j < 13; j++) {
                MahjongTile tile = deck.drawTile();
                player.drawPlayerTile(tile);
            }
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < 4; i++) {
            if (players[i].getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // 画出初始手牌
    protected void dealTiles() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                MahjongTile tile = deck.drawTile();
                players[i].dealPlayerTile(tile);
            }
        }
    }

    public static void main(String[] args) {
        MahjongGameManager gameManager = new MahjongGameManager();
        gameManager.startGame(); // 调用抽象方法
    }
}
