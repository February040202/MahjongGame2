package Players;

import Mahjong.MahjongDeck;
import GameRules.Rules;

public class PlayerManager {
    private static Player[] players;
    private Rules rules;
    private MahjongDeck deck;
    public PlayerManager() {
        this.rules = new Rules(players);
        this.deck = new MahjongDeck();
        setPlayer();
    }

    public void setPlayer() {
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i, rules, deck);
        }
        rules.setPlayers(players);
    }

    public static Player[] getPlayers() {
        return players;
    }

}
