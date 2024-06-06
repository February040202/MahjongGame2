package engine;

public interface Game {


  /**
   * This is the method that will be called repeatedly to make the game progress. It will be called getTargetFPS() times every second.
   * In this method you should move all of the items on the screen that should be moved, calculate if any collisions have occurred,
   * reduce the lives of the player if they are hit, check for user input and perform any other calculations necessary to determine what the new state of the game will be.
   * <br>
   * Note: The amount that items move on the screen are based on both the distance they move when this method is called and the number of times that this method is called every second.
   * So if the player moves by 3 pixels in a single step, and the getTargetFPS() method returns the value 25, then the player will move by 75 pixels in a second. This can be changed both
   * by changing the distance moved and by changing the number of times the game  is updated.
   * <br>
   * Note: You should not do any drawing as a result of this method being called.
   */
  public void updateGame();


  /**
   * This method is called when the game is first started and additionally if the user decides to play the game again after finishing. T
   * his should do all the required preparation for a game to be played.
   */
  public void startNewGame();


  /**
   * This tells us if the player is currently alive. Alive in this context only means that the player has not been destroyed. In games with multiple lives,
   * the player may not be alive when it has been destroyed but still has lives remaining.
   *
   * @return If the player is alive, true for alive, false for dead
   */
  public boolean isPlayerAlive();

  /**
   * This method should tell the game runner if this game is finished, this could be because the player has usd all of their lives,
   * they have completed all of the available levels or any other condition that you consider important.
   *
   * @return true if the game is finished, false otherwise
   */
  public boolean isGameOver();

  /**
   * This should return the width of the screen in pixels, this will be used to determine which parts of the screen should
   * be drawn after the game has been updated.
   *
   * @return the width of the screen in pixels
   */
  public int getScreenWidth();

  /**
   * This should return the height of the screen in pixels, this will be used to determine which parts of the screen should
   * be drawn after the game has been updated.
   *
   * @return the height of the screen in pixels
   */
  public int getScreenHeight();
}
