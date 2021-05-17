package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.GameManager;
import sample.Joueur;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerGameView implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Joueur> joueurs;
    private GameManager gameManager;

    @FXML
    private ListView joueurListView;
    @FXML
    private ListView cervelListView;
    @FXML
    private Canvas canvas;

    @FXML
    private Label difficulter;

    private ObservableList cervels;

    private GraphicsContext graphicsContext;

    private Map<String, Image> imageMap;


    public ControllerGameView(){
        imageMap = new HashMap<>();
        imageMap.put("brain", new Image("../../ressources/brain.png"));
    }

    @FXML
    void onRevenirAuMenuClick(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameManager = Main.gameManager;
        joueurs = (ObservableList<Joueur>) gameManager.joueurList;

        joueurListView.setItems(joueurs);

        difficulter.setText(gameManager.difficulte.toString());

        cervels = FXCollections.observableArrayList();
        for (Joueur joueur: joueurs) {
            cervels.add(joueur.getCerveaux());
        }
        cervelListView.setItems(cervels);

        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.valueOf("#357DED"));
        graphicsContext.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());

        displayCervel();
    }


    private void displayCervel(){
        graphicsContext.setStroke(Color.valueOf("#FF9F1C"));

        int x = (int) (canvas.getWidth()/8);
        int y = (int) (canvas.getHeight()/8);

        graphicsContext.strokeText("joueur et cervels", x, y);

        for (Joueur joueur:joueurs   ) {
            y += 30;
            graphicsContext.strokeText(joueur.getNom() + "  |  " + joueur.getCerveaux(), x, y);
        }

    }



}
