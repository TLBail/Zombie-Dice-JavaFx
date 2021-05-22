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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import sample.GameManager;
import sample.Joueur;
import sample.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerScoreView implements Initializable {

    private static final String OPENINGVIDEOPATH = "ressources/zombieOpening.mp4";
    private static final String LOOPVIDEOPATH = "ressources/zombieLoop.mp4";

    private Media media;


    @FXML
    private  MediaView mediaView;

    @FXML
    private ListView joueurListView;
    @FXML
    private ListView scoreListView;


    private Parent root;
    private Stage stage;
    private Scene scene;

    private GameManager gameManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Media openingmedia = new Media(new File(OPENINGVIDEOPATH).toURI().toString());
        Media loopmedia = new Media(new File(LOOPVIDEOPATH).toURI().toString());

        MediaPlayer player = new MediaPlayer(openingmedia);
        final MediaView mediaViewfinal = mediaView;
        final MediaPlayer nextMediaPlayer = new MediaPlayer(loopmedia);

        mediaView.setMediaPlayer(player);

        player.play();

        player.setOnEndOfMedia( () -> {
            mediaViewfinal.setMediaPlayer(nextMediaPlayer);
            nextMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            nextMediaPlayer.play();
        });

        gameManager = Main.gameManager;

        if(gameManager.joueurList != null){
            joueurListView.setItems((ObservableList) gameManager.joueurList);

            ObservableList<String> scoreList = FXCollections.observableArrayList();

            for (Joueur joueur: gameManager.joueurList) {
                scoreList.add(joueur.getCerveauxTotal() + "");
            }

            scoreListView.setItems(scoreList);
        }

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

}
