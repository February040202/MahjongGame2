package ui.listener;

import engine.MenuCommands;


public class MenuListener implements MenuCommands {
  private boolean exit;
  private boolean newGame;


  @Override
  public boolean hasPressedNewGame() {
    return newGame;
  }

  @Override
  public boolean hasPressedExit() {
    return exit;
  }

  @Override
  public void resetKeyPresses() {
    newGame = false;
    exit = false;
  }

  public void setNewGame(boolean newGame) {
    this.newGame = newGame;
  }


}