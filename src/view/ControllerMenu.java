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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Difficulte;
import sample.GameManager;
import sample.Joueur;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML
    private TextField nomJoueurTextField;

    @FXML
    private ListView listView;

    @FXML
    private ChoiceBox choiceBox;

    private ObservableList<Joueur> joueurs;

    private GameManager gameManager;
    public Stage stage;
    public Scene scene;
    public Parent root;

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
        for (Difficulte diffilcute: Difficulte.values() ) {
            choiceBox.getItems().add(diffilcute.toString());
        }


    }






    @FXML
    void onSuprimerJoueurClick(ActionEvent event){
        joueurs.remove(listView.getSelectionModel().getSelectedItem());
    }

}
