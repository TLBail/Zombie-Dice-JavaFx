package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public static final int NBJOUEURMAX = 5;

    private Difficulte difficulte;

    public List<Joueur> joueurList;

    private Joueur actualJoueur;

    public Joueur getActualJoueur() {
        return actualJoueur;
    }

    public FaceDe faceDes[];

    public List<Dice> totalDe;

    public Dice desTirer[];

    private boolean lancerDeDeDisponible;
    private boolean tirerDesDeDisponible;

    private int nbPersonalizedGrenneDice;
    private int nbPersonalizedRedDice;
    private int getNbPersonalizedYellowDice;

    private int comboOfActualjoueur;

    private List<Dice> diceInCaseOfIlyApuDice;

    public boolean isLancerDeDeDisponible() {
        return lancerDeDeDisponible;
    }

    public boolean isTirerDesDeDisponible() {
        return tirerDesDeDisponible;
    }

    public void setPersonlizedNumberOfDice(int nb, List<FaceDe> faceDes){
        for (int i = 0; i < nb; i++) {
            totalDe.add(new Dice(faceDes));
        }
        if(faceDes == Dice.REDDICEFACES){
            nbPersonalizedRedDice = nb;
        }
        if(faceDes == Dice.YELLOWDICEFACES){
            getNbPersonalizedYellowDice = nb;
        }
        if(faceDes == Dice.GREENDICEFACES){
            nbPersonalizedGrenneDice = nb;
        }

    }


    public GameManager(){
        faceDes = new FaceDe[3];
        faceDes[0] = FaceDe.CERVAL;
        faceDes[1] = FaceDe.SHOTGUN;
        faceDes[2] = FaceDe.STEP;

        desTirer = new Dice[3];

        lancerDeDeDisponible = false;
        tirerDesDeDisponible = true;

        totalDe = new ArrayList<>();

        nbPersonalizedRedDice = 0;
        nbPersonalizedGrenneDice = 0;
        getNbPersonalizedYellowDice = 0;

        comboOfActualjoueur = 0;

        diceInCaseOfIlyApuDice = new ArrayList<>();
    }

    public void setActualJoueur(Joueur actualJoueur) {
        this.actualJoueur = actualJoueur;
    }

    public int getRemainingGreenDice() {
        int sum = 0;
        for (Dice de : totalDe) {
            if(de.getTypeDe() == TypeDe.GREEN) sum++;
        }
        return sum;
    }

    public int getRemainingRedDice() {

        int sum = 0;
        for (Dice de : totalDe) {
            if(de.getTypeDe() == TypeDe.RED) sum++;
        }
        return sum;
    }

    public int getRemainingYellowDice() {

        int sum = 0;
        for (Dice de : totalDe) {
            if(de.getTypeDe() == TypeDe.YELLOW) sum++;
        }
        return sum;
    }


    public Difficulte getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulte difficulte) {

        totalDe = new ArrayList<>();
        this.difficulte = difficulte;
        int greenDice, yellowDice, redDice;
        switch (difficulte){
            case FACILE:
                greenDice = 8;
                redDice = 3;
                yellowDice = 2;
                break;
            case DIFFICILE:
                greenDice = 4;
                redDice = 5;
                yellowDice = 4;
                break;
            case PERSONALISE:
                if(nbPersonalizedRedDice != 0 && nbPersonalizedGrenneDice != 0 && getNbPersonalizedYellowDice != 0){
                    greenDice = nbPersonalizedGrenneDice;
                    redDice = nbPersonalizedRedDice;
                    yellowDice = getNbPersonalizedYellowDice;
                    System.out.println(nbPersonalizedGrenneDice);

                }else{
                    windowNewDice("De vert", Dice.GREENDICEFACES);
                    windowNewDice("De Jaunne", Dice.YELLOWDICEFACES);
                    windowNewDice("De rouge", Dice.REDDICEFACES);

                    greenDice = 0;
                    redDice = 0;
                    yellowDice = 0;
                }
                break;
            default: //case NORMAL ou autre
                greenDice = 6;
                redDice = 4;
                yellowDice = 3;
                break;

        }
        for (int i = 0; i < redDice; i++) {
            totalDe.add(new Dice(Dice.REDDICEFACES));
        }
        for (int i = 0; i < greenDice; i++) {
            totalDe.add(new Dice(Dice.GREENDICEFACES));
        }
        for (int i = 0; i < yellowDice; i++) {
            totalDe.add(new Dice(Dice.YELLOWDICEFACES));
        }
    }

    private void windowNewDice(String typeDe, List<FaceDe> faceDes){
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");


        Label secondLabel = new Label(typeDe);

        final Spinner<Integer> spinner = new Spinner<Integer>();

        final int initialValue = 3;

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, initialValue);

        spinner.setValueFactory(valueFactory);

        Button button = new Button("validé");
        button.setOnAction(event -> {
            Main.gameManager.setPersonlizedNumberOfDice(spinner.getValue(), faceDes);
            Main.controllerMenu.actualiserNbDice();
            newWindow.close();
        });


        AnchorPane secondaryLayout = new AnchorPane();
        secondaryLayout.getChildren().addAll(secondLabel, spinner, button);


        AnchorPane.setLeftAnchor(secondLabel, 50d);
        AnchorPane.setRightAnchor(secondLabel, 50d);
        AnchorPane.setTopAnchor(spinner, 50d);
        AnchorPane.setTopAnchor(button, 80d);
        AnchorPane.setLeftAnchor(spinner, 10d);
        AnchorPane.setRightAnchor(spinner, 10d);
        AnchorPane.setRightAnchor(button, 10d);
        AnchorPane.setLeftAnchor(button, 10d);
        Scene secondScene = new Scene(secondaryLayout, 230, 120);

        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(Main.primarySta.getX() +400 );
        newWindow.setY(Main.primarySta.getY() + 200);

        newWindow.show();
    }


    public void tirer3De(){

        for (Dice de: totalDe
             ) {
            if(de == null){
                System.err.println("un des de est null");
            }
        }

        //on tire 3 de
        for (int i = 0; i < 3; i++) {
            if(desTirer[i] == null){
                do{
                    desTirer[i] = totalDe.get((int) (Math.random() * totalDe.size()));
                }while(desTirer[i] == null);
                totalDe.remove(desTirer[i]);
            }
        }
        lancerDeDeDisponible = true;
        tirerDesDeDisponible = false;

        for (int i = 0; i <3; i++) {
            if(desTirer[i] == null){
                System.err.println("err un des de est null");
            }
        }

    }

    public void joueurSuivant(){
        //ajout des cerveaux gagné
        actualJoueur.ajouterLesCerveauxDuTour();
        //on remet le joueur a zero
        actualJoueur.remettreAzero();


        //sélection du joueur suivant
        if(joueurList.indexOf(actualJoueur) == joueurList.size() - 1){
            actualJoueur = joueurList.get(0);
        }else {
            actualJoueur = joueurList.get( joueurList.indexOf(actualJoueur) + 1);
        }

        //remise a zero des variable
        setDifficulte(difficulte);
        tirerDesDeDisponible = true;
        comboOfActualjoueur = 0;
        diceInCaseOfIlyApuDice.clear();


        //on check si un des joueur a plus de 13 cerval


    }


    public void lancerDeDe(){

        if(!lancerDeDeDisponible){
            System.out.println("le lancer de dé n'est pas disponible");
            return;
        }

        //on jete 3 de
        for (int i = 0; i < 3; i++) {
            faceDes[i] = desTirer[i].getFaceDeLancer();
            switch (faceDes[i]){
                case STEP:
                    break;
                case CERVAL:
                    actualJoueur.ajoutCerveaux();
                    diceInCaseOfIlyApuDice.add(desTirer[i]);
                    desTirer[i] = null;
                break;
                case SHOTGUN:
                    actualJoueur.ajoutShotgun();
                    desTirer[i] = null;
                break;
            }

        }


        //on compte les de  qui son reste
        int derestant = 0;
        for (int i = 0; i < 3; i++) {

            if(desTirer[i] != null){
                derestant++;
            }
        }

        if(totalDe.size() + derestant >= 3){
            tirerDesDeDisponible = true;
        }else{
            totalDe.addAll(diceInCaseOfIlyApuDice);
            tirerDesDeDisponible = true;
        }
        lancerDeDeDisponible = false;



        //on regarde si 3 fusils à pome ont été roulés
        if(actualJoueur.getShotgun() >= 3){
            //on donne que la possibilité de passer au joueur suivant
            actualJoueur.remettreAzeroLesCerveauxDuTour();
            lancerDeDeDisponible = false;
            tirerDesDeDisponible = false;
        }

        comboOfActualjoueur++;
        if(comboOfActualjoueur > actualJoueur.getNbLancerSuccesif()){
            actualJoueur.setNbLancerSuccesif(comboOfActualjoueur);
        }


    }

}
