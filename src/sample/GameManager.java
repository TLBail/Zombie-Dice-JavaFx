package sample;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import view.FaceDe;

import java.util.List;

public class GameManager {

    private Difficulte difficulte;

    public List<Joueur> joueurList;

    private int remainingGreenDice;
    private int remainingRedDice;
    private int remainingYellowDice;

    public FaceDe des[];

    public GameManager(){
        des = new FaceDe[3];
        des[0] = FaceDe.CERVAL;
        des[1] = FaceDe.SHOTGUN;
        des[2] = FaceDe.STEP;
    }

    public int getRemainingGreenDice() {
        return remainingGreenDice;
    }

    public int getRemainingRedDice() {
        return remainingRedDice;
    }

    public int getRemainingYellowDice() {
        return remainingYellowDice;
    }


    public Difficulte getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulte difficulte) {
        this.difficulte = difficulte;
        switch (difficulte){
            case FACILE:
                remainingGreenDice = 8;
                remainingRedDice = 3;
                remainingYellowDice = 2;
                break;
            case NORMAL:
                remainingGreenDice = 6;
                remainingRedDice = 4;
                remainingYellowDice = 3;
                break;
            case DIFFICILE:
                remainingGreenDice = 4;
                remainingRedDice = 5;
                remainingYellowDice = 4;
                break;
        }
    }

    @FXML
    void onchoixDesDe(){

        Color totalde[] = new Color[remainingYellowDice + remainingRedDice + remainingGreenDice];
        for (int i = 0; i < remainingGreenDice; i++) {
            totalde[i] = Color.GREEN;
        }
        for (int i = 0; i < remainingGreenDice; i++) {
            totalde[i] = Color.GREEN;
        }
        for (int i = 0; i < remainingGreenDice; i++) {
            totalde[i] = Color.GREEN;
        }


    }


    public void lancerDeDe(){
        for (int i = 0; i < des.length; i++) {
            des[i] = FaceDe.values()[(int) (Math.random() * 3)];
        }



        for (int i = 0; i < 3; i++) {
            des[i] = FaceDe.values()[(int) (Math.random() * 3)];


        }


    }

    private void  tirerUnDeVert(){

        remainingGreenDice--;
    }

    private void tirerUnDeRouge(){

        remainingRedDice--;
    }

    private void tirerUnDeJaunne(){

        remainingYellowDice--;
    }

}
