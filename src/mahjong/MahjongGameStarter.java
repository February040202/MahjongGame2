package mahjong;

import engine.GameManager;
import mahjong.game.MahjongGame;
import ui.Constant;
import ui.listener.MenuListener;
import ui.listener.PlayerListener;
import ui.screen.GameScreen;
import ui.screen.MenuScreen;

import javax.swing.*;

public class MahjongGameStarter {
  public static void main(String[] args) {
    JFrame mainWindow = new JFrame();
    mainWindow.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
    mainWindow.setResizable(false);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.setTitle(Constant.GAME_NAME);
    mainWindow.setLocationRelativeTo(null);

    PlayerListener playerListener = new PlayerListener();
    mainWindow.addMouseListener(playerListener);

    MenuListener menuListener = new MenuListener();

    MahjongGame game = new MahjongGame(playerListener);
    GameScreen gameScreen = new GameScreen(game);
    MenuScreen menuScreen = new MenuScreen(menuListener);

    GameManager mmm = new GameManager(game, mainWindow, menuListener, menuScreen, gameScreen);
    mainWindow.setVisible(true);
    mmm.run();
  }
}
