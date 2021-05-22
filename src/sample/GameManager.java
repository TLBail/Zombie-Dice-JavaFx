package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

    public boolean isLancerDeDeDisponible() {
        return lancerDeDeDisponible;
    }

    public boolean isTirerDesDeDisponible() {
        return tirerDesDeDisponible;
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

    public void tirer3De(){



        //todo penser a rajouter les de jaune dans la list
        //on tire 3 de
        if(totalDe.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                desTirer[i] = totalDe.get((int) (Math.random() * totalDe.size()));
                totalDe.remove(desTirer[i]);

            }
            lancerDeDeDisponible = true;

        }
        tirerDesDeDisponible = false;


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
                    totalDe.add(new Dice(Dice.YELLOWDICEFACES));
                break;
                case CERVAL:
                    actualJoueur.ajoutCerveaux();
                break;
                case SHOTGUN:
                    actualJoueur.ajoutShotgun();
                break;
            }
        }

        if(totalDe.size() >= 3){
            tirerDesDeDisponible = true;
        }
        lancerDeDeDisponible = false;



        //on regarde si 3 fusils à pome ont été roulés
        if(actualJoueur.getShotgun() >= 3){
            actualJoueur.remettreAzero();  //remet les cerveaux du tour et le nombre de shotgun a zero
            //on donne que la possibilité de passer au joueur suivant
            lancerDeDeDisponible = false;
            tirerDesDeDisponible = false;
        }


    }

}
