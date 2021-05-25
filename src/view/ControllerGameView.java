package view;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
    private Button jetDeButton;
    @FXML
    private Button joueurSuivantButton;
    @FXML
    private Button tirageDeButton;


    public static final double W = 200; // canvas dimensions.
    public static final double H = 200;

    public static final double D = 20;  // diameter.


    private ObservableList cervels;

    private GraphicsContext graphicsContext;

    private Map<String, Image> imageMap;
    private Timeline timeline;
    private AnimationTimer timer;

    private Dice oldDETirer[];

    public ControllerGameView(){
        imageMap = new HashMap<>();

        imageMap.put("CERVAL", new Image(new File("ressources/brain.png").toURI().toString()));
        imageMap.put("SHOTGUN", new Image(new File("ressources/shotgun.png").toURI().toString()));
        imageMap.put("STEP", new Image(new File("ressources/step.png").toURI().toString()));
        imageMap.put(TypeDe.RED.toString(), new Image(new File("ressources/reddice.png").toURI().toString()));
        imageMap.put(TypeDe.YELLOW.toString(), new Image(new File("ressources/yellowdice.png").toURI().toString()));
        imageMap.put(TypeDe.GREEN.toString(), new Image(new File("ressources/greendice.png").toURI().toString()));
        imageMap.put("redshotgun", new Image(new File("ressources/redshotgun.png").toURI().toString()));

    }

    @FXML
    void onRevenirAuMenuClick(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1000, 600);
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

        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();


        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, 0),
                        new KeyValue(y, 0)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(x, 720),
                        new KeyValue(y, 100)
                )
        );
        //timeline.setAutoReverse(true);
        //timeline.setCycleCount(Timeline.INDEFINITE);

        final Canvas canvas = graphicsContext.getCanvas();
         timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                graphicsContext.clearRect(canvas.getWidth()/2-10,
                        canvas.getHeight()/2-10,
                        canvas.getWidth()/2 + imageMap.get("CERVAL").getWidth(),
                        canvas.getHeight()/2 + imageMap.get("CERVAL").getHeight());

                if(y.doubleValue() < 50){
                    int xa = (int) (canvas.getWidth()/2);
                    int yb = (int) (canvas.getHeight()/2);

                    System.out.println(oldDETirer[0].toString());
                    for (int i = 0; i < gameManager.desTirer.length;i++) {
                        //Tools.drawRotatedImage(graphicsContext,
                        //       imageMap.get((Object) oldDETirer[i].toString()), x.doubleValue() ,xa, yb);
                         //   xa += imageMap.get((Object) oldDETirer[i].toString()).getWidth() * 2;

                        }
                    }else {
                    int xa = (int) (canvas.getWidth()/2);
                    int yb = (int) (canvas.getHeight()/2);

                    for (FaceDe de: gameManager.faceDes) {
                        switch (de){
                            case CERVAL:
                                Tools.drawRotatedImage(graphicsContext,
                                        imageMap.get((Object) "CERVAL"), x.doubleValue() ,xa, yb);
                                break;
                            case SHOTGUN:
                                Tools.drawRotatedImage(graphicsContext,
                                        imageMap.get((Object) "SHOTGUN"), x.doubleValue(), xa , yb);
                                break;
                            case STEP:
                                Tools.drawRotatedImage(graphicsContext,
                                        imageMap.get((Object) "STEP"), x.doubleValue(), xa , yb);

                        }
                        xa += imageMap.get((Object) "CERVAL").getWidth() * 2;

                    }
                }




            }
        };

        displayCanva();

        opacitiButton();




    }






    private void displayCanva(){

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        graphicsContext.setStroke(Color.valueOf("#357DED"));
        graphicsContext.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());


        displayCervel();



        displayRemainingDice();


        timer.stop();
        timeline.stop();

    }


    private void displayCervel(){
        graphicsContext.setStroke(Color.valueOf("#FF9F1C"));

        int x = (int) (canvas.getWidth()/7);
        int y = (int) (canvas.getHeight()/8);

        graphicsContext.strokeText("joueur et cervels", x, y);
        y += 10;
        for (Joueur joueur:joueurs   ) {
            if(joueur.equals(gameManager.getActualJoueur())){
                graphicsContext.strokeText("joueur actuel : " + joueur.getNom() , x - new String("joueur actuel : " + joueur.getNom()).length() * 5 , y + imageMap.get((Object) "CERVAL").getHeight()/2 );

            }else{
                graphicsContext.strokeText(joueur.getNom() , x - joueur.getNom().length() * 5, y + imageMap.get((Object) "CERVAL").getHeight()/2 );

            }

            graphicsContext.drawImage(imageMap.get((Object) "CERVAL"),
                    x,
                    y);
            graphicsContext.strokeText(joueur.getCerveauxTotal() + "",
                    x + imageMap.get((Object) "CERVAL").getWidth() + 20,
                    y + imageMap.get((Object) "CERVAL").getHeight()/2);

            graphicsContext.setStroke(Color.valueOf("357DED"));
            graphicsContext.strokeText(joueur.getCerveauxDuTour() + "",
                    x + imageMap.get((Object) "CERVAL").getWidth() + 30,
                    y + imageMap.get((Object) "CERVAL").getHeight()/2);

            graphicsContext.setStroke(Color.valueOf("#FF9F1C"));
            for (int i = 0; i < joueur.getShotgun(); i++) {
                graphicsContext.drawImage(imageMap.get((Object) "redshotgun"),
                        x + joueur.getNom().length() * 2 + 40 + ((i + 1) * imageMap.get((Object) "redshotgun").getWidth()),
                        y);

            }

            y += imageMap.get((Object) "CERVAL").getWidth() + 10;

        }




    }

    private void displayDice(){

        /*
        int x = (int) (canvas.getWidth()/2);
        int y = (int) (canvas.getHeight()/2);

        for (FaceDe de: gameManager.faceDes) {
            switch (de){
                case CERVAL:
                    graphicsContext.drawImage(imageMap.get((Object) "CERVAL"), x , y);
                break;
                case SHOTGUN:
                    graphicsContext.drawImage(imageMap.get((Object) "SHOTGUN"), x , y);
                    break;
                case STEP:
                    graphicsContext.drawImage(imageMap.get((Object) "STEP"), x , y);

            }
            x += imageMap.get((Object) "CERVAL").getWidth() * 2;

        }
        */
        timer.start();
        timeline.play();


    }

    //Todo vrai lancer de de
    @FXML
    void onLancerDe(ActionEvent event){
        if(gameManager.isLancerDeDeDisponible()){

            gameManager.lancerDeDe();
            displayCanva();
            displayDice();

        }
        opacitiButton();

    }

    @FXML
    void onTirerDe(ActionEvent event){

        if(gameManager.isTirerDesDeDisponible()){
            gameManager.tirer3De();
            oldDETirer = gameManager.desTirer;
            displayCanva();
            afficherlesDeTirer();

        }
        opacitiButton();
    }

    @FXML
    void onJoueurSuivant(ActionEvent event){
        if(!gameManager.isLancerDeDeDisponible()){

            gameManager.joueurSuivant();
            displayCanva();

        }
        opacitiButton();

        for (Joueur joueur: gameManager.joueurList
             ) {

            if(joueur.getCerveauxTotal() >= 13){
                try {
                    root = FXMLLoader.load(getClass().getResource("ScoreView.fxml"));

                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void opacitiButton(){
        if(!gameManager.isTirerDesDeDisponible()){
            tirageDeButton.setOpacity(0.5f);
        }else{
            tirageDeButton.setOpacity(1);
        }
        if(!gameManager.isLancerDeDeDisponible()){
            jetDeButton.setOpacity(0.5f);
            joueurSuivantButton.setOpacity(1);
        }else{
            jetDeButton.setOpacity(1);
            joueurSuivantButton.setOpacity(0.5f);
        }

    }

    private void displayRemainingDice(){

        graphicsContext.drawImage(imageMap.get((Object) TypeDe.GREEN.toString()),
                canvas.getWidth() - imageMap.get((Object) TypeDe.GREEN.toString()).getWidth() * 6,
                canvas.getHeight()/16);
        graphicsContext.drawImage(imageMap.get((Object) TypeDe.RED.toString()),
                canvas.getWidth() - imageMap.get((Object) TypeDe.RED.toString()).getWidth() * 4,
                canvas.getHeight()/16);
        graphicsContext.drawImage(imageMap.get((Object) TypeDe.YELLOW.toString()),
                canvas.getWidth() - imageMap.get((Object) TypeDe.YELLOW.toString()).getWidth() * 2,
                canvas.getHeight()/16);

        graphicsContext.setStroke(Color.valueOf("#FF9F1C"));

        graphicsContext.strokeText("De restant : ", canvas.getWidth() - imageMap.get((Object) "CERVAL").getWidth() * 8, canvas.getHeight()/16 +  imageMap.get((Object) "CERVAL").getWidth() / 2);

         graphicsContext.strokeText(gameManager.getRemainingGreenDice() + "",
                        canvas.getWidth() - imageMap.get((Object) TypeDe.RED.toString()).getWidth() * 5.5,
                        canvas.getHeight()/16 + imageMap.get((Object) TypeDe.RED.toString()).getHeight() * 1.5);
                graphicsContext.strokeText(gameManager.getRemainingRedDice() + "",
                        canvas.getWidth() - imageMap.get((Object) TypeDe.RED.toString()).getWidth() * 3.5,
                        canvas.getHeight()/16 + imageMap.get((Object) TypeDe.RED.toString()).getHeight() * 1.5);
                graphicsContext.strokeText(gameManager.getRemainingYellowDice() +"",
                        canvas.getWidth() - imageMap.get((Object) TypeDe.RED.toString()).getWidth() * 1.5,
                        canvas.getHeight()/16 + imageMap.get((Object) TypeDe.RED.toString()).getHeight() * 1.5);


    }

    private void afficherlesDeTirer(){
        graphicsContext.setStroke(Color.WHITE);

        int x = (int) (canvas.getWidth()/2);
        int y = (int) (canvas.getHeight()/2);
        graphicsContext.strokeText("De tirer : ", x - new String("De tirer").length() * 5, y);




        for (int i = 0; i < gameManager.desTirer.length;i++) {
            graphicsContext.drawImage(imageMap.get((Object) gameManager.desTirer[i].toString()), x ,y);
            x += imageMap.get((Object) gameManager.desTirer[i].toString()).getWidth() * 2;

        }

    }

}
