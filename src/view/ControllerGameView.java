package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import sample.Difficulte;
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
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));

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
        graphicsContext = canvas.getGraphicsContext2D();

        displayCanva();



    }


    private void displayCanva(){

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        graphicsContext.setStroke(Color.valueOf("#357DED"));
        graphicsContext.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());


        displayCervel();

        displayDice();

        displayDiffilcute();

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
            Des[i] = FaceDe.values()[(int) (Math.random() * 3)];
        }
        displayCanva();
    }







    private void displayDiffilcute(){

        graphicsContext.drawImage(imageMap.get((Object) "brain"),
                canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 6,
                canvas.getHeight()/16);
        graphicsContext.drawImage(imageMap.get((Object) "shotgun"),
                canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 4,
                canvas.getHeight()/16);
        graphicsContext.drawImage(imageMap.get((Object) "step"),
                canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 2,
                canvas.getHeight()/16);

        graphicsContext.setStroke(Color.WHITE);

        graphicsContext.strokeText("Diffilcuté : " + gameManager.difficulte, canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 8, canvas.getHeight()/16 +  imageMap.get((Object) "brain").getWidth() / 2);


        switch (gameManager.difficulte){
            case FACILE:
                graphicsContext.strokeText("3",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 5.5,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 3.5,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("1",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 1.5,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);

                break;
            case NORMAL:
                graphicsContext.strokeText("2",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 5.5 ,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 3.5,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 1.5,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);

                break;
            case DIFFICILE:
                graphicsContext.strokeText("1",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 5.5 ,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 3.5 ,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("3",
                        canvas.getWidth() - imageMap.get((Object) "brain").getWidth() * 1.5,
                        canvas.getHeight()/16 + imageMap.get((Object) "brain").getHeight() * 1.5);

                break;
        }

    }


}
