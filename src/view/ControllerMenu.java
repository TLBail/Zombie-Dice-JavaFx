package view;

import javafx.beans.Observable;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

    public ControllerMenu(){
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

    }


    @FXML
    void onAjouterJoueurClick(ActionEvent event){
        if(nomJoueurTextField.getText() != null)
            joueurs.add(new Joueur(nomJoueurTextField.getText()));

    }

    @FXML
    void onLancerJeuClick(ActionEvent event){

        for (Difficulte difficulte: Difficulte.values()   ) {
            if(difficulte.toString().equals(choiceBox.getValue().toString())){
                gameManager.difficulte = difficulte;
                break;
            }
        }

        gameManager.joueurList = joueurs;

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
        gameManager.difficulte = Difficulte.NORMAL;
        for (Difficulte diffilcute: Difficulte.values() ) {
            choiceBox.getItems().add(diffilcute.toString());
        }

        graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.drawImage(imageMap.get((Object) "brain"),
                canvas.getWidth()/4 - imageMap.get((Object) "brain").getWidth()/2,
                canvas.getHeight()/8);
        graphicsContext.drawImage(imageMap.get((Object) "shotgun"),
                canvas.getWidth()/2 -imageMap.get((Object) "brain").getWidth()/2,
                canvas.getHeight()/8);
        graphicsContext.drawImage(imageMap.get((Object) "step"),
                canvas.getWidth()/2 + canvas.getWidth()/4 - imageMap.get((Object) "brain").getWidth()/2,
                canvas.getHeight()/8);

    }


    @FXML
    void onSelectDiffilcute(MouseEvent event){

        graphicsContext.clearRect(0, canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight(), canvas.getWidth(), canvas.getHeight());




        graphicsContext.setStroke(Color.WHITE);

        if(choiceBox.getValue() != null){

            for (Difficulte difficulte: Difficulte.values()   ) {
                if(difficulte.toString().equals(choiceBox.getValue().toString())){
                    gameManager.difficulte = difficulte;
                    break;
                }
            }
        }

        switch (gameManager.difficulte){
            case FACILE:
                graphicsContext.strokeText("3",
                        canvas.getWidth()/4 ,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth()/2 ,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("1",
                        canvas.getWidth()/2 + canvas.getWidth()/4,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);

                break;
            case NORMAL:
                graphicsContext.strokeText("2",
                        canvas.getWidth()/4 ,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth()/2,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth()/2 + canvas.getWidth()/4,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);

                break;
            case DIFFICILE:
                graphicsContext.strokeText("1",
                        canvas.getWidth()/4 ,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("2",
                        canvas.getWidth()/2 ,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);
                graphicsContext.strokeText("3",
                        canvas.getWidth()/2 + canvas.getWidth()/4 ,
                        canvas.getHeight()/8 + imageMap.get((Object) "brain").getHeight() * 1.5);

                break;
        }

    }






    @FXML
    void onSuprimerJoueurClick(ActionEvent event){
        joueurs.remove(listView.getSelectionModel().getSelectedItem());
    }

}
