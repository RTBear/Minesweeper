package cs2410.assn8.control;/**
 * Created by Ryan on 12/5/2016.
 */

import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * @author Ryan
 * @version 1.0
 *
 * This class contains the basic layout and content for the scoreboard
 */
public class ScoreBoard extends HBox
{
    public ScoreBoard()
    {
        resetBtn.setDisable(true);
        System.out.println("scoreboard contructor called");
        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(40);
        this.getChildren().addAll(new Text("Time: "), timeText, resetBtn, new Text("Bombs Remaining:"), bombText);

    }

    /**
     * Resets scoreboard to default state
     */
    public void reset()
    {
        gameTimer.reset();
        this.getChildren().clear();
        resetBtn.setDisable(true);
        System.out.println("scoreboard reset called");
        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(40);
        this.getChildren().addAll(new Text("Time: "), timeText, resetBtn, new Text("Bombs Remaining:"), bombText);
    }

    public GameTimer gameTimer = new GameTimer();
    public Text timeText = new Text();
    public Text bombText = new Text();
    public Button resetBtn = new Button("Reset");
}
