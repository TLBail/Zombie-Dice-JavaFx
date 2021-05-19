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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


    private FaceDe[] Des;

    public ControllerGameView(){
        imageMap = new HashMap<>();
        try {
            FileInputStream inputstream = new FileInputStream("ressources/brain.png");
            imageMap.put("brain", new Image(inputstream));
            inputstream.close();
            inputstream = new FileInputStream("ressources/shotgun.png");
            imageMap.put("shotgun", new Image(inputstream));
            inputstream.close();
            inputstream = new FileInputStream("ressources/step.png");
            imageMap.put("step", new Image(inputstream));
            inputstream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Des = new FaceDe[3];
        Des[0] = FaceDe.CERVAL;
        Des[1] = FaceDe.SHOTGUN;
        Des[2] = FaceDe.STEP;
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
        difficulter.setText(gameManager.difficulte.toString());

        graphicsContext = canvas.getGraphicsContext2D();

        displayCanva();
    }


    private void displayCanva(){

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        graphicsContext.setStroke(Color.valueOf("#357DED"));
        graphicsContext.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());


        displayCervel();

        displayDice();
        displayDice();

    }


    private void displayCervel(){
        graphicsContext.setStroke(Color.valueOf("#FF9F1C"));

        int x = (int) (canvas.getWidth()/8);
        int y = (int) (canvas.getHeight()/8);

        graphicsContext.strokeText("joueur et cervels", x, y);
        y += 10;
        for (Joueur joueur:joueurs   ) {
            graphicsContext.strokeText(joueur.getNom() , x , y + imageMap.get((Object) "brain").getHeight()/2 );
            graphicsContext.drawImage(imageMap.get((Object) "brain"),
                    x + joueur.getNom().length() * 2 + 10,
                    y);
            graphicsContext.strokeText(joueur.getCerveaux() + "",
                    x + imageMap.get((Object) "brain").getWidth() + 20,
                    y + imageMap.get((Object) "brain").getHeight()/2);
            y += imageMap.get((Object) "brain").getWidth() + 10;
        }

    }

    private void displayDice(){
        int x = (int) (canvas.getWidth()/2);
        int y = (int) (canvas.getHeight()/2);

        for (FaceDe de: Des) {
            switch (de){
                case CERVAL:
                    graphicsContext.drawImage(imageMap.get((Object) "brain"), x , y);
                break;
                case SHOTGUN:
                    graphicsContext.drawImage(imageMap.get((Object) "shotgun"), x , y);
                    break;
                case STEP:
                    graphicsContext.drawImage(imageMap.get((Object) "step"), x , y);

            }
            x += imageMap.get((Object) "brain").getWidth() * 2;

        }

    }

    //Todo vrai lancer de de
    @FXML
    void onLancerDe(ActionEvent event){
        for (int i = 0; i < Des.length; i++) {
            Des[i] = FaceDe.values()[(int) (Math.random() * 4)];
        }
        displayCanva();
    }


}
