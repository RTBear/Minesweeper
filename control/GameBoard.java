package cs2410.assn8.control;/**
 * Created by Ryan on 11/23/2016.
 */


import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

/**
 * @author Ryan
 * @version XXX
 *
 * This class handles Basic settings for the game board itself
 */
public class GameBoard
{
    public static final int BOARD_WIDTH = 20;
    public static final int BOMB_PERCENT = 25;
    public static final int BOMB_COUNT = (int) (((double) BOMB_PERCENT / 100) * (BOARD_WIDTH * BOARD_WIDTH));

    public int emptyRemaining = (BOARD_WIDTH * BOARD_WIDTH) - BOMB_COUNT;
    public SimpleIntegerProperty bombsRemaining = new SimpleIntegerProperty(BOMB_COUNT);

    public Cell[][] gameCells = new Cell[BOARD_WIDTH][BOARD_WIDTH];
    public ArrayList<Cell> bombList = new ArrayList<>(0);

    /**
     * Resets the gameboard to its default values
     */
    public void reset()
    {
        gameCells = new Cell[BOARD_WIDTH][BOARD_WIDTH];
        bombList = new ArrayList<>(0);
        bombsRemaining.set(BOMB_COUNT);
        emptyRemaining = (BOARD_WIDTH * BOARD_WIDTH) - BOMB_COUNT;
    }
}
