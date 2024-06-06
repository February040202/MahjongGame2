package engine;

import javax.swing.*;
import java.awt.*;

public final class GameManager {
  private JFrame mainWindow;
  private JPanel mainPanel;
  private JPanel gameScreen;
  private MenuCommands listener;
  private CardLayout cardLayout;
  private boolean exit = false;
  private Game game;
  private ScreenEnum screenEnum = ScreenEnum.MAIN_MENU_SCREEN;
  private static final double TARGET_TIME_BETWEEN_RENDERS = 1000000000.0 / 60;
  private static final double TIME_BETWEEN_UPDATES = 1000000000.0 / 60;
  private static final int MAX_UPDATES_BEFORE_RENDER = 5;

  /**
   * This class will control the execution of the menu and the game. Once the object is created, you only need to call the run method.
   *
   * @param game              The game that is to be played
   * @param applicationWindow The root of the JFrame that is going to be displayed on the screen. You should not add any of the JPanels to the frame, this will be done by the game manager.
   * @param menuListener      This object will allow the game manager to query for key presses made by the user to navigate the menu.
   * @param menuScr           This object will be used to draw the menu screen
   * @param gameScr           This object will be used to draw the game screen
   */
  public GameManager(Game game, JFrame applicationWindow, MenuCommands menuListener, JPanel menuScr, JPanel gameScr) {
    this.mainWindow = applicationWindow;
    this.listener = menuListener;
    this.game = game;
    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    mainWindow.getContentPane().add(mainPanel);
    mainPanel.add(menuScr, "Main Menu");
    mainPanel.add(gameScr, "Game Screen");
    this.gameScreen = gameScr;
    showMainMenuScreen();
  }

  private void showMainMenuScreen() {
    listener.resetKeyPresses();
    cardLayout.show(mainPanel, "Main Menu");
    screenEnum = ScreenEnum.MAIN_MENU_SCREEN;
  }


  private void showGameScreen() {
    listener.resetKeyPresses();
    cardLayout.show(mainPanel, "Game Screen");
    screenEnum = ScreenEnum.GAME_SCREEN;
  }

  /**
   * This method should be called only after all of the screens that make up the menu have been added. Once executed the method repeatedly checks for input from the player to navigate the menus.
   * When the game has been started, the execution is started from here and the method will block waiting until the game has been completed before recording the high score and returning to the menu.
   */
  public void run() {
    new Thread() {
      public void run() {
        try {
          Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
    double now = System.nanoTime();
    double lastUpdateTime = System.nanoTime();
    double lastRenderTime;
    while (!exit) {
      switch (screenEnum) {
        case GAME_SCREEN:
          while (!game.isGameOver()) {
            if (game.isPlayerAlive()) {
              int updateCount = 0;
              while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                game.updateGame();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
              }
              gameScreen.paintImmediately(new Rectangle(0, 0, game.getScreenWidth(), game.getScreenHeight()));
              lastRenderTime = now;
              while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS) {
                try {
                  Thread.sleep(1);
                } catch (Exception e) {
                }
                now = System.nanoTime();
              }
            } else {
              lastUpdateTime = System.nanoTime();
              gameScreen.paintImmediately(new Rectangle(0, 0, game.getScreenWidth(), game.getScreenHeight()));

              try {
                Thread.sleep(5);
              } catch (InterruptedException e) {
              }
            }
          }

          break;
        case MAIN_MENU_SCREEN:
          if (listener.hasPressedExit()) {
            exitGame();
          } else if (listener.hasPressedNewGame()) {
            game.startNewGame();
            showGameScreen();
          }
          break;
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    exit = false;
  }

  private void exitGame() {
    exit = true;
    System.exit(0);
  }
}
