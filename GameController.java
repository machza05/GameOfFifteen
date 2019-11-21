package com.company;

import javax.swing.*;
import java.util.Random;
import java.awt.Color;

import static com.company.GameModel.RANDOM;

public class GameController extends JPanel {
    GameModel md ;

    public GameController(GameModel gm){
        md = gm;
    }

    public void newGame() {
        do {
            reset(); // reset in intial state
            shuffle(); // shuffle
        } while (!isSolvable()); // make it until grid be solvable

        md.gameOver = false;
    }

    public void reset() {
        for (int i = 0; i < md.tiles.length; i++) {
            md.tiles[i] = (i + 1) % md.tiles.length;
        }

        // we set blank cell at the last
        md.blankPos = md.tiles.length - 1;
    }

    public void shuffle() {
        // don't include the blank tile in the shuffle, leave in the solved position
        int n = md.nbTiles;

        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            int tmp = md.tiles[r];
            md.tiles[r] = md.tiles[n];
            md.tiles[n] = tmp;
        }
    }

    // Only half permutations o the puzzle are solvable
    // Whenever a tile is preceded by a tile with higher value it counts
    // as an inversion. In our case, with the blank tile in the solved position,
    // the number of inversions must be even for the puzzle to be solvable
    public boolean isSolvable() {
        int countInversions = 0;

        for (int i = 0; i < md.nbTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (md.tiles[j] > md.tiles[i])
                    countInversions++;
            }
        }

        return countInversions % 2 == 0;
    }

    public boolean isSolved() {
        if (md.tiles[md.tiles.length - 1] != 0) // if blank tile is not in the solved position ==> not solved
            return false;

        for (int i = md.nbTiles - 1; i >= 0; i--) {
            if (md.tiles[i] != i + 1)
                return false;
        }

        return true;
    }
}
