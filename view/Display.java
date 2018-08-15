package cs2410.assn8.view;/**
 * Created by Ryan on 11/23/2016.
 */

import cs2410.assn8.control.Controller;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Ryan
 * @version 1.0
 *
 * Responsible for GUI interface of the program
 * Sets up display and handles reset button
 */
public class Display extends Application
{
    private Controller controller = new Controller();
    private BorderPane main = new BorderPane();
    private TilePane board = new TilePane();

    @Override

    public void start(Stage primaryStage) throws Exception
    {
//        main = new BorderPane();

        controller.scoreBoard.setAlignment(Pos.CENTER);
        controller.scoreBoard.setPadding(new Insets(10, 10, 10, 10));

//        board = new TilePane();
        for (int i = 0; i < controller.gameBoard.bombList.size(); i++)
        {
            board.getChildren().add(controller.gameBoard.bombList.get(i));
            System.out.println("filling");
        }
        board.setPrefColumns(controller.gameBoard.BOARD_WIDTH);
        board.setAlignment(Pos.CENTER);

        System.out.println("---------\n# Cells: " + controller.gameBoard.bombList.size());
        System.out.println("# Bombs: " + controller.gameBoard.BOMB_COUNT + "\n---------\n");
        main = new BorderPane(board, controller.scoreBoard, null, null, null);
        primaryStage.setScene(new Scene(main));
        primaryStage.setTitle("Minesweeper...ish");
        primaryStage.setResizable(true);
        primaryStage.show();

        Bindings.bindBidirectional(controller.scoreBoard.timeText.textProperty(), controller.scoreBoard.gameTimer.currentTimeProperty, new NumberStringConverter());
        Bindings.bindBidirectional(controller.scoreBoard.bombText.textProperty(), controller.gameBoard.bombsRemaining, new NumberStringConverter());
        /**
         * Cleans up program for closing
         */
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                controller.scoreBoard.gameTimer.gameTime.cancel();
                System.exit(0);
            }
        });

        /**
         * Handles Reset button for game
         * Reloads the GUI
         */
        controller.scoreBoard.resetBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                controller.reset();
                board.getChildren().clear();

                for (int i = 0; i < controller.gameBoard.bombList.size(); i++)
                {
                    board.getChildren().add(controller.gameBoard.bombList.get(i));
                }
            }
        });
    }
}
