package ui.listener;

import mahjong.model.ImageButton;
import ui.Constant;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PlayerListener extends MouseAdapter {


  private boolean drawPressed;
  private boolean selectPressed;
  private boolean touchPressed;
  private boolean mouseClick;
  public int clickX;
  public int clickY;

  public boolean isMouseClick() {
    return mouseClick;
  }


  public void resetFlag() {
    mouseClick = false;
    drawPressed = false;
    selectPressed = false;
    touchPressed = false;
  }

  public boolean isDrawPressed() {
    return drawPressed;
  }

  public boolean isSelectPressed() {
    return selectPressed;
  }

  public boolean isTouchPressed() {
    return touchPressed;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    clickX = e.getX();
    clickY = e.getY();
    mouseClick = true;

  }


  public void setMouseClick(boolean mouseClick) {
    this.mouseClick = mouseClick;
  }

  public boolean clickXYInRange(int x1, int y1, int x2, int y2) {
    return clickX >= x1 && clickX < x2 && clickY - Constant.BASE >= y1 && clickY - Constant.BASE < y2;
  }

  public void buttonPressed(ImageButton.ButtonType name) {
    if (name == ImageButton.ButtonType.DRAW) {
      drawPressed = true;
    } else if (name == ImageButton.ButtonType.SELECT) {
      selectPressed = true;
    } else if (name == ImageButton.ButtonType.TOUCH) {
      touchPressed = true;
    }
  }

  public void buttonReleasedOrExited(ImageButton.ButtonType name) {
    if (name == ImageButton.ButtonType.DRAW) {
      drawPressed = false;
    } else if (name == ImageButton.ButtonType.SELECT) {
      selectPressed = false;
    } else if (name == ImageButton.ButtonType.TOUCH) {
      touchPressed = false;
    }
  }
}
