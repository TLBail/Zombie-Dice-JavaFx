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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.GameManager;
import sample.Joueur;
import sample.Main;
import sample.Score;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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

    @FXML
    private ListView comboListView;

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

        chargerScoreview();

    }

    private void chargerScoreview(){
        if(gameManager.joueurList != null){
            Collections.sort(gameManager.joueurList, Collections.reverseOrder());
            joueurListView.setItems((ObservableList) gameManager.joueurList);


            ObservableList<String> scoreList = FXCollections.observableArrayList();
            ObservableList<String> comboList = FXCollections.observableArrayList();

            for (Joueur joueur: gameManager.joueurList) {
                scoreList.add(joueur.getCerveauxTotal() + "");
                comboList.add(joueur.getNbLancerSuccesif() + "");
            }
            scoreListView.setItems(scoreList);
            comboListView.setItems(comboList);

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

     @FXML
    void onSaveButtonClick(ActionEvent event){
         FileChooser fileChooser = new FileChooser();

         FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
         fileChooser.getExtensionFilters().add(extentionFilter);

         fileChooser.setTitle("Enregistrer les score");


         String userDirectoryString = System.getProperty("user.home");
         File userDirectory = new File(userDirectoryString);
         if(!userDirectory.canRead()) {
             userDirectory = new File("c:/");
         }
         fileChooser.setInitialDirectory(userDirectory);

         //on recupère le fichier choisi
         File chosenFile = fileChooser.showSaveDialog(null);
         //on verif que le fichier est pas nul
         String path;
         if(chosenFile != null) {
             path = chosenFile.getPath();
         } else {
             //default return value
             path = null;
         }

         Score score = new Score();
         score.joueurList = new Joueur[gameManager.joueurList.size()];
         int i = 0;
         for (Joueur jouer: gameManager.joueurList
              ) {
             score.joueurList[i] = jouer;
             i++;
         }



         ObjectOutputStream oos = null;
         final FileOutputStream fichier;
         try {
             fichier = new FileOutputStream(chosenFile.getPath() + ".scr");
             oos = new ObjectOutputStream(fichier);
             oos.writeObject(score);
             oos.flush();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
                 if (oos != null) {
                     oos.flush();
                     oos.close();
                 }
             } catch (final IOException ex) {
                 ex.printStackTrace();
             }
         }
     }


     @FXML
    void onChargerClick(ActionEvent event){
         FileChooser chooser = new FileChooser();
         File scoreFile = chooser.showOpenDialog(null);

        // ouverture d'un flux sur un fichier
         ObjectInputStream ois = null;
         try {
             ois = new ObjectInputStream(new FileInputStream(scoreFile.getPath()));

             // désérialization de l'objet
             Score score = (Score) ois.readObject();
             for (Joueur joueur: score.joueurList
                  ) {
                 if(gameManager.joueurList == null) gameManager.joueurList  = FXCollections.observableArrayList();
                 gameManager.joueurList.add(joueur);
             }
             System.out.println(score);

         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
         }
         chargerScoreview();
     }
}
