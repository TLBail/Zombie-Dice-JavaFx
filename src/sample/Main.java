package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ControllerGameView;
import view.ControllerMenu;

import javax.xml.transform.sax.SAXResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static GameManager gameManager;
    public static Stage primarySta;
    public static ControllerMenu controllerMenu;
    public static ControllerGameView controllerGameView;

    @Override
    public void start(Stage primaryStage) throws Exception{

        gameManager = new GameManager();
        this.primarySta = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Zombie dice");
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        Stage stage = primaryStage;
        primaryStage.show();

        ControllerMenu controllerMenu = fxmlLoader.getController();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
