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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML
    private TextField nomJoueurTextField;

    @FXML
    private ListView listView;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Canvas canvas;

    private ObservableList<Joueur> joueurs;

    private GameManager gameManager;
    public Stage stage;
    public Scene scene;
    public Parent root;

    private Map<String, Image> imageMap;
    private GraphicsContext graphicsContext;
    private Difficulte difficulte;

    public ControllerMenu(){
        Main.controllerMenu = this;
        imageMap = new HashMap<>();
        imageMap.put("brain", new Image(new File("ressources/brain.png").toURI().toString()));
        imageMap.put("shotgun", new Image(new File("ressources/shotgun.png").toURI().toString()));
        imageMap.put("step", new Image(new File("ressources/step.png").toURI().toString()));
        imageMap.put("reddice", new Image(new File("ressources/reddice.png").toURI().toString()));
        imageMap.put("yellowdice", new Image(new File("ressources/yellowdice.png").toURI().toString()));
        imageMap.put("greendice", new Image(new File("ressources/greendice.png").toURI().toString()));

    }


    @FXML
    void onAjouterJoueurClick(ActionEvent event){
        if(nomJoueurTextField.getText() != null){
            if (nomJoueurTextField.getText().length() < 1){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention !");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("le nom du joueur ne doit pas être vide");

                alert.showAndWait();

                return;
            }

            if (joueurs.size() >= GameManager.NBJOUEURMAX){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention !");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("5 joueur maximum !");

                alert.showAndWait();


                return;
            }

            joueurs.add(new Joueur(nomJoueurTextField.getText()));

        }

    }

    @FXML
    void onLancerJeuClick(ActionEvent event){

        if(choiceBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention !");

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("on ta demandé de choisir une difficulté !!!!!");

            alert.showAndWait();

            return;
        }

        if(joueurs == null || joueurs.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention !");

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("rajoute au moins un joueur stp");

            alert.showAndWait();

            return;

        }

        for (Difficulte difficulte: Difficulte.values()   ) {
            if(difficulte.toString().equals(choiceBox.getValue().toString())){
                gameManager.setDifficulte(difficulte);
                break;
            }
        }

        gameManager.joueurList = joueurs;
        gameManager.setActualJoueur(joueurs.get(0));

        try {
            root = FXMLLoader.load(getClass().getResource("gameView.fxml"));

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
        joueurs = FXCollections.observableArrayList();
        listView.setItems(joueurs);

        gameManager = Main.gameManager;
        gameManager.setDifficulte(Difficulte.NORMAL);
        for (Difficulte diffilcute: Difficulte.values() ) {
            choiceBox.getItems().add(diffilcute.toString());
        }
        graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.drawImage(imageMap.get((Object) "greendice"),
                canvas.getWidth()/4 - imageMap.get((Object) "brain").getWidth()/2,
                canvas.getHeight()/8);
        graphicsContext.drawImage(imageMap.get((Object) "reddice"),
                canvas.getWidth()/2 -imageMap.get((Object) "brain").getWidth()/2,
                canvas.getHeight()/8);
        graphicsContext.drawImage(imageMap.get((Object) "yellowdice"),
                canvas.getWidth()/2 + canvas.getWidth()/4 - imageMap.get((Object) "brain").getWidth()/2,
                canvas.getHeight()/8);

        choiceBox.setOnAction(event  -> {
            onSelectDiffilcute(event);
        });

    }



    private void onSelectDiffilcute(Event event){


        if(choiceBox.getValue() != null){
            for (Difficulte difficulte: Difficulte.values()   ) {
                if(difficulte.toString().equals(choiceBox.getValue().toString())){
                    this.difficulte = difficulte;
                    gameManager.setDifficulte(difficulte);
                    break;
                }
            }
        }

        actualiserNbDice();


    }

    public void actualiserNbDice(){
        graphicsContext.clearRect(0, canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight(), canvas.getWidth(), canvas.getHeight());
        graphicsContext.setStroke(Color.WHITE);

        graphicsContext.strokeText(gameManager.getRemainingGreenDice() + "",
                canvas.getWidth()/4 ,
                canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
        graphicsContext.strokeText(gameManager.getRemainingRedDice() + "",
                canvas.getWidth()/2 ,
                canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
        graphicsContext.strokeText(gameManager.getRemainingYellowDice() + "",
                canvas.getWidth()/2 + canvas.getWidth()/4,
                canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);

    }



    @FXML
    void onVoirLesScoreclick(ActionEvent event){
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


    @FXML
    void onSuprimerJoueurClick(ActionEvent event){
        joueurs.remove(listView.getSelectionModel().getSelectedItem());
    }

}
