package cs2410.assn8.control;/**
 * Created by Ryan on 12/5/2016.
 */

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Collections;
import java.util.ListIterator;

/**
 * @author Ryan
 * @version 1.0
 *
 * Handles all of the game logic and handling of cells
 */
public class Controller
{
    public GameBoard gameBoard = new GameBoard();
    public ScoreBoard scoreBoard;

    /**
     * Default Constructor, sets up board
     */
    public Controller()
    {
        fillBombs();
        fillBoard();
        scoreBoard = new ScoreBoard();
    }

    /**
     * Responsible for resetting entire game to default state
     */
    public void reset()
    {
        gameBoard.reset();
        fillBombs();
        fillBoard();
        scoreBoard.reset();
    }

    /**
     * Runs end of game logic upon meeting Game Over conditions
     */
    public void endGame()
    {
        for (Cell c : gameBoard.bombList)
        {
            if (c.isBomb())
            {
                if (c.getText() == "X")
                {
                    c.setId("correctFlag");
                }
                else
                {
                    c.setId("bombClicked");
                }
            }
            else if (c.getText() == "X")
            {
                c.setId("incorrectFlag");
            }
            c.setDisable(true);
        }
        if (gameBoard.emptyRemaining == 0)
        {
            win();
        }
        else
        {
            lose();
        }
            System.out.println("Game Over...");
            scoreBoard.resetBtn.setDisable(false);
    }

    /**
     * Runs logic for win condition
     */
    public void win()
    {
        Alert inputConfirmation = new Alert(Alert.AlertType.INFORMATION);
        inputConfirmation.setTitle("Congratulations!");
        inputConfirmation.setHeaderText("Congratulations! You Win!");
        inputConfirmation.setGraphic(null);

        String contentText = ("You took " + scoreBoard.timeText.getText() + " seconds");
        inputConfirmation.setContentText(contentText);
        scoreBoard.gameTimer.gameTime.cancel();

        inputConfirmation.showAndWait();
    }

    /**
     * Runs logic for lose condition
     */
    public void lose()
    {
        Alert inputConfirmation = new Alert(Alert.AlertType.INFORMATION);
        inputConfirmation.setTitle("Game Over!");
        inputConfirmation.setHeaderText("You Lose!");
        inputConfirmation.setGraphic(null);
        scoreBoard.gameTimer.gameTime.cancel();

        inputConfirmation.showAndWait();
    }

    /**
     * Handles logic for when a cell that does not contain a bomb is clicked
     * @param cell The cell that was clicked on
     */

    public void initEmptyClick(Cell cell)
    {
        cell.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton() == MouseButton.PRIMARY && cell.active == true)
                {
                    updateCell(cell);
                    if (scoreBoard.gameTimer.timerStarted == false)
                    {
                        scoreBoard.gameTimer.startTimer();
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY)
                {
                    handleRightClick(cell);
                }
            }
        });
    }

    /**
     * Handles logic for clicking of a cell containing a bomb
     * @param cell The cell that was clicked on
     */
    public void initBombClick(Cell cell)
    {
        cell.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton() == MouseButton.PRIMARY && cell.active == true)
                {
                    cell.setId("bombClicked");
                    System.out.println("bomb clicked: " + gameBoard.emptyRemaining);
                    endGame();
                }
                if (event.getButton() == MouseButton.SECONDARY)
                {
                    handleRightClick(cell);
                }
            }
        });
    }

    /**
     * Handles specific logic for the right click of all cells
     * @param cell Cell that was clicked on
     */
    public void handleRightClick(Cell cell)
    {
        String state = cell.getText();
        if (state == "")
        {
            state = "X";
            cell.active = false;
            gameBoard.bombsRemaining.set(gameBoard.bombsRemaining.get() - 1);
        }
        else if (state == "X")
        {
            state = "?";
        }
        else if (state == "?")
        {
            state = "";
            cell.active = true;
            gameBoard.bombsRemaining.set(gameBoard.bombsRemaining.get() + 1);
        }
        cell.setText(state);
    }

    /**
     * Fills the board with cells (an ArrayList)
     * Used for front end display - not the actual game logic
     */
    public void fillBombs()
    {
        for (int i = 0; i < gameBoard.BOMB_COUNT; i++)
        {
            Cell tempCell = new Cell(true);
            tempCell.makeBomb();
            initBombClick(tempCell);
            gameBoard.bombList.add(tempCell);
        }
        for (int j = gameBoard.BOMB_COUNT; j < (gameBoard.BOARD_WIDTH * gameBoard.BOARD_WIDTH); j++)
        {
            Cell tempCell = new Cell(false);
            initEmptyClick(tempCell);
            gameBoard.bombList.add(tempCell);
        }
        Collections.shuffle(gameBoard.bombList);
    }

    /**
     * Reads the ArrayList holding the game cells into a 2D array to be used for actual game logic
     * Responsible for updating number of bombs near each cell
     */
    public void fillBoard()
    {
        //fill 2d button array with the cells from bombList
        ListIterator itr = gameBoard.bombList.listIterator();
        for (int y = 0; y < gameBoard.BOARD_WIDTH; y++)
        {
            for (int x = 0; x < gameBoard.BOARD_WIDTH; x++)
            {
                if (itr.hasNext())
                {
                    gameBoard.gameCells[y][x] = (Cell) itr.next();
                    gameBoard.gameCells[y][x].setX(x);
                    gameBoard.gameCells[y][x].setY(y);
                }
            }
        }
//        // **Debugging** print array to console
//        System.out.println("****** 2D Array Contents *******");
//        for (int y = 0; y < gameBoard.BOARD_WIDTH; y++)
//        {
//            for (int x = 0; x < gameBoard.BOARD_WIDTH; x++)
//            {
//                System.out.print(gameBoard.gameCells[y][x].getText());
//            }
//            System.out.print("\n");
//        }

        // tell nearby spaces im a bomb
        for (int y = 0; y < gameBoard.BOARD_WIDTH; y++)
        {
            for (int x = 0; x < gameBoard.BOARD_WIDTH; x++)
            {
                if (gameBoard.gameCells[y][x].isBomb())
                {
                    tellNearby(x, y);
                }
            }
        }
    }

    /**
     * Handles logic for the number of bombs near each cell
     * @param x Column of cell
     * @param y Row of cell
     */
    public void tellNearby(int x, int y)
    {
        // checks for nearby bombs
        for (int row = y - 1; row <= y + 1; row++)
        {
            for (int col = x - 1; col <= x + 1; col++)
            {
                try
                {
                    gameBoard.gameCells[row][col].addBombNearby();
                } catch (ArrayIndexOutOfBoundsException e)
                {
                }
            }
        }
    }

    /**
     * Responsible for Handling logic of empty cell clicks
     * @param cell
     */
    public void updateCell(Cell cell)
    {
        if (cell.isBomb() == false && cell.active)
        {
            if (cell.getBombsNearby() > 0)
            {
                cell.setText("" + cell.getBombsNearby());
                cell.setDisable(true);
            }
            else
            {
                cell.setDisable(true);
                activateNearby(cell.getX(), cell.getY());
            }
            gameBoard.emptyRemaining--;
        }
        System.out.println("update cell: " + gameBoard.emptyRemaining);
        if (gameBoard.emptyRemaining == 0)
        {
            endGame();
        }
    }

    /**
     * Updates cells surrounding cells with no bombs nearby
     * @param x Column of cell
     * @param y Row of Cell
     */
    public void activateNearby(int x, int y)
    {
        for (int row = y - 1; row <= y + 1; row++)
        {
            for (int col = x - 1; col <= x + 1; col++)
            {
                if (row != y || col != x)
                {
                    try
                    {
                        if (gameBoard.gameCells[row][col].isBomb() == false && gameBoard.gameCells[row][col].isDisable() == false)
                        {
                            updateCell(gameBoard.gameCells[row][col]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e)
                    {
                    }
                }
            }
        }
    }
}
