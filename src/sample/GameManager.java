package sample;

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
        if(joueurList.indexOf(actualJoueur) == joueurList.size() - 1){
            actualJoueur = joueurList.get(0);
        }else {
            actualJoueur = joueurList.get( joueurList.indexOf(actualJoueur) + 1);
        }

        setDifficulte(difficulte);

        tirerDesDeDisponible = true;

    }


    public void lancerDeDe(){

        if(!lancerDeDeDisponible){
            System.out.println("le lancer de dé n'est pas disponible");
            return;

        }
        for (int i = 0; i < 3; i++) {
            faceDes[i] = desTirer[i].getFaceDeLancer();
            if(faceDes[i] == FaceDe.CERVAL){
                actualJoueur.ajoutCerveaux();
            }
            if(faceDes[i] == FaceDe.SHOTGUN){
                actualJoueur.ajoutShotgun();
            }
            if(faceDes[i] == FaceDe.STEP){
                totalDe.add(new Dice(Dice.YELLOWDICEFACES));
            }


        }

        if(totalDe.size() >= 3){
            tirerDesDeDisponible = true;
        }
        lancerDeDeDisponible = false;



    }

}
