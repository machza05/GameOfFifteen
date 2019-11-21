package com.company;

import java.awt.*;
import javax.swing.*;

public class Main extends JPanel {

    static GameModel gm;

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Game of Fifteen");
            frame.setResizable(false);
            gm = new GameModel(4, 550, 30);
            frame.add(gm, BorderLayout.CENTER);
            frame.pack();
            // center on the screen
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void drawGrid(Graphics2D g) {
        for (int i = 0; i < gm.tiles.length; i++) {
            // we convert 1D coords to 2D coords given the size of the 2D Array
            int r = i / gm.size;
            int c = i % gm.size;
            // we convert in coords on the UI
            int x = gm.margin + c * gm.tileSize;
            int y = gm.margin + r * gm.tileSize;

            // check special case for blank tile
            if (gm.tiles[i] == 0) {
                if (gm.gameOver) {
                    g.setColor(gm.FOREGROUND_COLOR);
                    drawCenteredString(g, "\u2713", x, y);
                }

                continue;
            }

            // for other tiles
            g.setColor(getForeground());
            g.fillRoundRect(x, y, gm.tileSize, gm.tileSize, 25, 25);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, gm.tileSize, gm.tileSize, 25, 25);
            g.setColor(Color.WHITE);

            drawCenteredString(g, String.valueOf(gm.tiles[i]), x, y);
        }
    }

    public void drawStartMessage(Graphics2D g) {
        if (gm.gameOver) {
            g.setFont(getFont().deriveFont(Font.BOLD, 18));
            g.setColor(gm.FOREGROUND_COLOR);
            String s = "Click to start new game";
            g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2, getHeight() - gm.margin);
        }
    }

    public void drawCenteredString(Graphics2D g, String s, int x, int y) {
        // center string s for the given tile (x,y)
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int desc = fm.getDescent();
        g.drawString(s, x + (gm.tileSize - fm.stringWidth(s)) / 2,
                y + (asc + (gm.tileSize - (asc + desc)) / 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2D);
        drawStartMessage(g2D);
    }
}