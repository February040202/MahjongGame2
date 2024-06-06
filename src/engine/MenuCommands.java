package engine;

public interface MenuCommands {
  /**
   * This method will be called when the menu manager want to know if the user has pressed the new game button or key.
   * The object should return true if the button has been pressed since the last time the resetKeyPresses method was called.
   * It does not matter if the button is not currently being pressed, only that it was pressed. After any input has been processed, the resetKeyPresses method will be called.
   *
   * @return true if the new game button has been pressed, false otherwise
   */
  public boolean hasPressedNewGame();

  /**
   * This method will be called when the menu manager want to know if the user has pressed the exit score button or key.
   * The object should return true if the button has been pressed since the last time the resetKeyPresses method was called.
   * It does not matter if the button is not currently being pressed, only that it was pressed. After any input has been processed, the resetKeyPresses method will be called.
   *
   * @return true if the exit button has been pressed, false otherwise
   */
  public boolean hasPressedExit();

  /**
   * This method will be called after any other input has been processed. This should reset the condition of all input to false until another button or key has been pressed.
   */
  public void resetKeyPresses();
}
