package ui.screen;

import ui.Constant;
import ui.listener.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

public class MenuScreen extends JPanel {
  @Serial
  private static final long serialVersionUID = 1616386874546775416L;
  ImageIcon startGame;
  MenuListener listener;

  public MenuScreen(MenuListener menuListener) {
    listener = menuListener;

    startGame = new ImageIcon("img/startGame.png");
    Image image = startGame.getImage().getScaledInstance(Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT, Image.SCALE_SMOOTH);
    startGame = new ImageIcon(image);
    int[] xy = {Constant.SCREEN_WIDTH / 2 - Constant.BUTTON_WIDTH / 2, (3 * Constant.SCREEN_HEIGHT / 4) - Constant.BUTTON_HEIGHT / 2};
//    Image image = startGame.getImage();
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // 检查点击位置是否在图片上
        if (e.getX() >= xy[0] && e.getX() <= xy[0] + image.getWidth(null) && e.getY() >= xy[1] && e.getY() <= xy[1] + image.getHeight(null)) {
          // 在这里处理图片点击事件
          listener.setNewGame(true);
        }
      }
    });
  }

  public void paintComponent(Graphics g) {
    drawBackground(g);
  }

  private void drawBackground(Graphics g) {
    Image menuBackground = new ImageIcon("img/menuBackground.png").getImage();
    g.drawImage(menuBackground, 0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, this);

    ImageIcon menuTitle = new ImageIcon("img/menuTitle.png");
    int menuTitleX = Constant.SCREEN_WIDTH / 2 - menuTitle.getIconWidth() / 2;
    int menuTitleY = (Constant.SCREEN_HEIGHT / 4) - menuTitle.getIconHeight() / 2;
    g.drawImage(menuTitle.getImage(), menuTitleX, menuTitleY, this);


    // 调整startGame按钮位置，使其位于中间偏下的部分
    int startGameX = Constant.SCREEN_WIDTH / 2 - Constant.BUTTON_WIDTH / 2;
    int startGameY = (3 * Constant.SCREEN_HEIGHT / 4) - Constant.BUTTON_HEIGHT / 2;
    g.drawImage(startGame.getImage(), startGameX, startGameY, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT, this);
  }
}