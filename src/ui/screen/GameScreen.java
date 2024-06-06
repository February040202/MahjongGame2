package ui.screen;

import mahjong.game.MahjongGame;
import mahjong.model.ImageButton;
import mahjong.model.MahjongTile;
import mahjong.model.Player;
import ui.Constant;
import util.GameUtil;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class GameScreen extends JPanel {
  @Serial
  private static final long serialVersionUID = -8282302849760730222L;
  private final MahjongGame game;

  // (start, end , deltx, delty)
  public static int[][] playerXY = {{180, 850, 50, 0}, {110, 200, 0, 47}, {180, 100, 50, 0}, {830, 200, 0, 47}};
  public static int[][] playerWH = {{Constant.MAHJONG_WIDTH, Constant.MAHJONG_HEIGHT}, {Constant.MAHJONG_HEIGHT, Constant.MAHJONG_WIDTH}, {Constant.MAHJONG_WIDTH, Constant.MAHJONG_HEIGHT}, {Constant.MAHJONG_HEIGHT, Constant.MAHJONG_WIDTH}};
  static Image bgTile = new ImageIcon(GameUtil.playerId2ImgPath(0)).getImage();
  static Image pubBgTile = new ImageIcon(GameUtil.playerId2ImgPath(5)).getImage();

  public GameScreen(MahjongGame game) {
    this.game = game;
    addControllerButton();
  }

  private void addControllerButton() {
    setLayout(null);
    ImageButton draw = new ImageButton(ImageButton.ButtonType.DRAW, game.listener);
    draw.setImageButton("img/PlayScreen/button/draw.png", 300, 650, 80, 100);
    ImageButton select = new ImageButton(ImageButton.ButtonType.SELECT, game.listener);
    select.setImageButton("img/PlayScreen/button/select.png", 450, 650, 80, 100);
    ImageButton touch = new ImageButton(ImageButton.ButtonType.TOUCH, game.listener);
    touch.setImageButton("img/PlayScreen/button/touch.png", 600, 650, 80, 100);

    add(draw);
    add(select);
    add(touch);

  }


  protected void paintComponent(Graphics g) {

    g.setColor(Color.BLUE);

    drawBackground(g);
    drawPlayersIcon(g);
    drawPlayerHandTiles(g);
    drawPlayerPublicTiles(g);
    drawUsedTiles(g);
    if (game.isPlayerAlive()) {
      drawGameInfo(g);
    } else {
      drawWinBg(g);
    }

  }

  private void drawWinBg(Graphics g) {
    ImageIcon highScore = new ImageIcon("img/HighScore.png");
    g.drawImage(highScore.getImage(), Constant.SCREEN_WIDTH / 3, Constant.SCREEN_HEIGHT / 3, 300, 300, this);
  }

  private void drawGameInfo(Graphics g) {
    g.setFont(new Font("Arial", Font.BOLD, 30));
    g.setColor(Color.gray);
    g.drawString(game.getGameInfo(), 266, 221);
    g.setColor(Color.red);
    g.drawString(game.getGameInfo(), 265, 220);
  }

  private void drawUsedTiles(Graphics g) {
    int[] xy = {250, 250, 50, 80};
    for (int i = 0; i < game.usedTile.size(); i++) {
      MahjongTile curTile = game.usedTile.get(i);

      int x = xy[0] + (i % 10) * xy[2];
      int y = xy[1] + (i / 10 % 5) * xy[3];
      drawPublicBackgroundTile(g, x, y, Constant.MAHJONG_WIDTH, Constant.MAHJONG_HEIGHT);
      Image image = new ImageIcon(GameUtil.tile2ImgPath(curTile)).getImage();
      g.drawImage(image, x, y, Constant.MAHJONG_WIDTH, Constant.MAHJONG_HEIGHT, this);

      if (i == game.usedTile.size() - 1) {
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 4;
        g2d.setStroke(new BasicStroke(thickness));

        g2d.drawRect(x, y, Constant.MAHJONG_WIDTH, Constant.MAHJONG_HEIGHT);
      }
    }
  }

  private void drawPlayerHandTiles(Graphics g) {
    for (Player player : game.players) {
      int id = player.id;
      Image image;

      for (int i = 0; i < player.handTiles.size(); i++) {
        MahjongTile curTile = player.handTiles.get(i);
        int x = playerXY[id][0] + i * playerXY[id][2];
        int y = playerXY[id][1] + i * playerXY[id][3] - (curTile.isChoose() ? 30 : 0);


        if (player.isOwnerPlayer()) {
          image = new ImageIcon(GameUtil.tile2ImgPath(curTile)).getImage();
          drawBackgroundTile(g, x, y - 10, playerWH[id][0], playerWH[id][1] + 10);
        } else {
          image = new ImageIcon(GameUtil.playerId2ImgPath(id)).getImage();
        }

        g.drawImage(image, x, y, playerWH[id][0], playerWH[id][1], this);
      }
    }
  }

  private void drawPlayerPublicTiles(Graphics g) {
    int[][] diff = {{-20, -100}, {20, 0}, {0, 20}, {-20, 0}};
    if (!game.isPlayerAlive()) {
      diff[0][1] = 0;
    }

    Image image;
    for (int id = 0; id < 4; id++) {
      Player player = game.players[id];
      for (int i = 0; i < player.publicTiles.size(); i++) {
        MahjongTile curTile = player.publicTiles.get(i);
        int x = playerXY[id][0] + i * playerXY[id][2] + diff[id][0];
        int y = playerXY[id][1] + i * playerXY[id][3] + diff[id][1];
        drawPublicBackgroundTile(g, x, y, playerWH[id][0], playerWH[id][1]);

        if (id == 0 || id == 2) {
          image = new ImageIcon(GameUtil.tile2ImgPath(curTile)).getImage();
        } else {
          image = new ImageIcon(GameUtil.pubTile2ImgPath(curTile)).getImage();
        }
        g.drawImage(image, x, y, playerWH[id][0], playerWH[id][1], this);
      }
    }
  }


  private void drawPublicBackgroundTile(Graphics g, int x, int y, int dx, int dy) {
    g.drawImage(pubBgTile, x, y, dx, dy, this);
  }

  private void drawBackgroundTile(Graphics g, int x, int y, int dx, int dy) {
    g.drawImage(bgTile, x, y, dx, dy, this);
  }


  private void drawBackground(Graphics g) {
    Image playBackground = new ImageIcon("img/playBackground.png").getImage();
    g.drawImage(playBackground, 0, 0, this);

  }

  private void drawPlayersIcon(Graphics g) {
    Image player1 = new ImageIcon("img/PlayScreen/player1.png").getImage();
    Image player2 = new ImageIcon("img/PlayScreen/player2.png").getImage();
    Image player3 = new ImageIcon("img/PlayScreen/player3.png").getImage();
    Image player4 = new ImageIcon("img/PlayScreen/player4.png").getImage();

    g.drawImage(player1, 10, 10, 120, 100, this);
    g.drawImage(player2, 10, Constant.SCREEN_HEIGHT - 170, 100, 100, this);
    g.drawImage(player3, Constant.SCREEN_WIDTH - 130, Constant.SCREEN_HEIGHT - 170, 100, 100, this);
    g.drawImage(player4, Constant.SCREEN_WIDTH - 130, 10, 100, 100, this);
  }

}
