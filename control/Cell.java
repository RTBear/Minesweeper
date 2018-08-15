package cs2410.assn8.control;/**
 * Created by Ryan on 11/24/2016.
 */

import javafx.scene.control.Button;

/**
 * @author Ryan
 * @version 1.0
 * Each cell in the gameboard
 * Contains variables relevant to its role in the game
 */
public class Cell extends Button
{
    public Cell(boolean bombCell)
    {
        this.getStylesheets().addAll("cs2410/assn8/css/custom.css");
        this.setMinSize(30, 30);
        this.setMaxSize(30, 30);
        if (bombCell)
        {
//            this.setText("");
//            this.setId("bomb");
            bomb = true;
        }
    }

    public boolean isBomb()
    {
        return bomb;
    }

    public void makeBomb()
    {
        bomb = true;
    }

    public int getBombsNearby()
    {
        return bombsNearby;
    }

    public void addBombNearby()
    {
        if (bombsNearby < 9)
        {
            bombsNearby++;
        }
    }

    public void removeBombNearby()
    {
        if (bombsNearby > 0)
        {
            bombsNearby--;
        }
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean active = true;

    private boolean bomb;
    private int bombsNearby;
    private int x;
    private int y;
}
