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
    int[] xy = {Constant.SCREEN_WIDTH / 2 - startGame.getIconWidth() / 2, Constant.SCREEN_HEIGHT / 2 - startGame.getIconHeight() / 3};

    Image image = startGame.getImage();
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
    g.drawImage(menuBackground, 0, 0, this);

    ImageIcon menuTitle = new ImageIcon("img/menuTitle.png");
    g.drawImage(menuTitle.getImage(), Constant.SCREEN_WIDTH / 2 - menuTitle.getIconWidth() / 2, 0, this);

    g.drawImage(startGame.getImage(), Constant.SCREEN_WIDTH / 2 - startGame.getIconWidth() / 2, Constant.SCREEN_HEIGHT / 2 - startGame.getIconHeight() / 3, this);


  }
}

