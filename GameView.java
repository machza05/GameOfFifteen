package com.company;

import javax.swing.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GameView extends JPanel {
    GameModel md ;

    public GameView(GameModel gm){
        md = gm;
    }

    public void drawGrid(Graphics2D g) {
        for (int i = 0; i < md.tiles.length; i++) {
            // we convert 1D coords to 2D coords given the size of the 2D Array
            int r = i / md.size;
            int c = i % md.size;
            // we convert in coords on the UI
            int x = md.margin + c * md.tileSize;
            int y = md.margin + r * md.tileSize;

            // check special case for blank tile
            if (md.tiles[i] == 0) {
                if (md.gameOver) {
                    g.setColor(md.FOREGROUND_COLOR);
                    drawCenteredString(g, "\u2713", x, y);
                }

                continue;
            }

            // for other tiles
            g.setColor(getForeground());
            g.fillRoundRect(x, y, md.tileSize, md.tileSize, 25, 25);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, md.tileSize, md.tileSize, 25, 25);
            g.setColor(Color.WHITE);

            drawCenteredString(g, String.valueOf(md.tiles[i]), x, y);
        }
    }

    public void drawStartMessage(Graphics2D g) {
        if (md.gameOver) {
            g.setFont(getFont().deriveFont(Font.BOLD, 18));
            g.setColor(md.FOREGROUND_COLOR);
            String s = "Click to start new game";
            g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2, getHeight() - md.margin);
        }
    }

    public void drawCenteredString(Graphics2D g, String s, int x, int y) {
        // center string s for the given tile (x,y)
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int desc = fm.getDescent();
        g.drawString(s, x + (md.tileSize - fm.stringWidth(s)) / 2,
                y + (asc + (md.tileSize - (asc + desc)) / 2));
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

