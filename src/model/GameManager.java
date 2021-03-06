package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private int nbPersonalizedYellowDice;

    private int comboOfActualjoueur;

    private List<Dice> diceInCaseOfIlyApuDice;

    public boolean isLancerDeDeDisponible() {
        return lancerDeDeDisponible;
    }

    public boolean isTirerDesDeDisponible() {
        return tirerDesDeDisponible;
    }

    public void setPersonlizedNumberOfDice(int nb, TypeDe typeDe){
        if(TypeDe.RED == typeDe){
            nbPersonalizedRedDice = nb;
            for (int i = 0; i < nb; i++) {
                totalDe.add(new Dice(Dice.REDDICEFACES));
            }

        }
        if(TypeDe.YELLOW == typeDe){
            nbPersonalizedYellowDice = nb;
            for (int i = 0; i < nb; i++) {
                totalDe.add(new Dice(Dice.YELLOWDICEFACES));
            }

        }
        if(TypeDe.GREEN == typeDe){
            nbPersonalizedGrenneDice = nb;
            for (int i = 0; i < nb; i++) {
                totalDe.add(new Dice(Dice.GREENDICEFACES));
            }

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
        nbPersonalizedYellowDice = 0;

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
                if(nbPersonalizedRedDice != 0 && nbPersonalizedGrenneDice != 0 && nbPersonalizedYellowDice != 0){
                    greenDice = nbPersonalizedGrenneDice;
                    redDice = nbPersonalizedRedDice;
                    yellowDice = nbPersonalizedYellowDice;
                    System.out.println(nbPersonalizedGrenneDice);

                }else{
                    windowNewDice(TypeDe.GREEN);
                    windowNewDice(TypeDe.YELLOW);
                    windowNewDice(TypeDe.RED);

                    //on set juste les  valeur par d??fault
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

    //m??thode qui affiche une fen??tre pour choisir le type du de
    private void windowNewDice( TypeDe typeDe){
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");


        Label secondLabel = new Label("D?? : " + typeDe.toString());

        final Spinner<Integer> spinner = new Spinner<Integer>();

        final int initialValue = 3;

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, initialValue);

        spinner.setValueFactory(valueFactory);

        Button button = new Button("valid??");
        button.setOnAction(event -> {
            Main.gameManager.setPersonlizedNumberOfDice(spinner.getValue(), typeDe);
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

        //on tire 3 de
        for (int i = 0; i < 3; i++) {
            //on tire un nouveau d?? que si on n'a pas eu de face emprunte et donc que le d?? est donc rest??
            if(desTirer[i] == null){
                do{
                    desTirer[i] = totalDe.get((int) (Math.random() * totalDe.size()));
                }while(desTirer[i] == null);
                totalDe.remove(desTirer[i]);
            }
        }
        lancerDeDeDisponible = true;
        tirerDesDeDisponible = false;

        //on v??rifie que il n'y a pas de d?? null
        for (int i = 0; i <3; i++) {
            if(desTirer[i] == null){
                System.err.println("err un des de est null");
            }
        }

    }

    public void joueurSuivant(){
        //ajout des cerveaux gagn??
        actualJoueur.ajouterLesCerveauxDuTour();
        //on remet le joueur a zero
        actualJoueur.remettreAzero();


        //s??lection du joueur suivant
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


    }


    public void lancerDeDe(){

        if(!lancerDeDeDisponible){
            System.out.println("le lancer de d?? n'est pas disponible");
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



        //on regarde si 3 fusils ?? pompe ont ??t?? roul??s
        if(actualJoueur.getShotgun() >= 3){
            //on donne que la possibilit?? de passer au joueur suivant
            actualJoueur.remettreAzeroLesCerveauxDuTour();
            lancerDeDeDisponible = false;
            tirerDesDeDisponible = false;
        }else{

            //on actualise le combo
            comboOfActualjoueur++;
            if(comboOfActualjoueur > actualJoueur.getNbLancerSuccesif()){
                actualJoueur.setNbLancerSuccesif(comboOfActualjoueur);
            }
        }
    }

}
