package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;

// We are going to create a Game of 15 Puzzle with Java 8 and Swing
// If you have some questions, feel free to ue comments ;)
// Model

public class GameModel extends JPanel {
    // our grid will be drawn in a dedicated Panel
    GameController gc;
    // Size of our Game of Fifteen instance
    public int size;
    // Number of tiles
    public int nbTiles;
    // Grid UI Dimension
    private int dimension;
    // Foreground Color
    public static final Color FOREGROUND_COLOR = new Color(239, 83, 80); // we use arbitrary color
    // Random object to shuffle tiles
    public static final Random RANDOM = new Random();
    // Storing the tiles in a 1D Array of integers
    public int[] tiles;
    // Size of tile on UI
    public int tileSize;
    // Position of the blank tile
    public int blankPos;
    // Margin for the grid on the frame
    public int margin;
    // Grid UI Size
    private int gridSize;
    public boolean gameOver; // true if game over, false otherwise

    public GameModel(int size, int dim, int mar) {
        gc = new GameController(this);
        this.size = size;
        dimension = dim;
        margin = mar;

        // init tiles
        nbTiles = size * size - 1; // -1 because we don't count blank tile
        tiles = new int[size * size];

        // calculate grid size and tile size
        gridSize = (dim - 2 * margin);
        tileSize = gridSize / size;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(FOREGROUND_COLOR);
        setFont(new Font("SansSerif", Font.BOLD, 60));

        gameOver = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // used to let users to interact on the grid by clicking
                // it's time to implement interaction with users to move tiles to solve the game !
                if (gameOver) {
                    gc.newGame();
                } else {
                    // get position of the click
                    int ex = e.getX() - margin;
                    int ey = e.getY() - margin;

                    // click in the grid ?
                    if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize)
                        return;

                    // get position in the grid
                    int c1 = ex / tileSize;
                    int r1 = ey / tileSize;

                    // get position of the blank cell
                    int c2 = blankPos % size;
                    int r2 = blankPos / size;

                    // we convert in the 1D coord
                    int clickPos = r1 * size + c1;

                    int dir = 0;

                    // we search direction for multiple tile moves at once
                    if (c1 == c2 && Math.abs(r1 - r2) > 0)
                        dir = (r1 - r2) > 0 ? size : -size;
                    else if (r1 == r2 && Math.abs(c1 - c2) > 0)
                        dir = (c1 - c2) > 0 ? 1 : -1;

                    if (dir != 0) {
                        // we move tiles in the direction
                        do {
                            int newBlankPos = blankPos + dir;
                            tiles[blankPos] = tiles[newBlankPos];
                            blankPos = newBlankPos;
                        } while (blankPos != clickPos);

                        tiles[blankPos] = 0;
                    }

                    // we check if game is solved
                    gameOver = gc.isSolved();
                }

                // we repaint panel
                repaint();
            }
        });

        gc.newGame();
    }
}
