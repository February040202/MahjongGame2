package mahjong.model;

import ui.listener.PlayerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageButton extends JButton {
  ButtonType name;

  public ImageButton(ButtonType name, PlayerListener listener) {

    this.name = name;

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        listener.buttonPressed(name);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        listener.buttonReleasedOrExited(name);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        listener.buttonReleasedOrExited(name);
      }
    });
  }

  public void setImageButton(String imagePath, int x, int y, int w, int h) {
    setBounds(x, y, w, h);
    Image scaledInstance = new ImageIcon(imagePath).getImage().getScaledInstance(w + 5, h, Image.SCALE_SMOOTH);
    setIcon(new ImageIcon(scaledInstance));
    setBackground(Color.white);
  }

  public enum ButtonType {
    DRAW,
    SELECT,
    TOUCH
  }

}
