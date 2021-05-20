package sample;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private Difficulte difficulte;

    public List<Joueur> joueurList;

    private int remainingGreenDice;
    private int remainingRedDice;
    private int remainingYellowDice;

    public FaceDe faceDes[];

    public TypeDe desTirer[];


    private static final List<FaceDe> GREENDICE = new ArrayList<FaceDe>() {{
       add(FaceDe.CERVAL);
       add(FaceDe.CERVAL);
       add(FaceDe.CERVAL);

       add(FaceDe.STEP);
       add(FaceDe.STEP);

       add(FaceDe.SHOTGUN);
    }};
    private static final List<FaceDe> REDDICE = new ArrayList<FaceDe>() {{
       add(FaceDe.CERVAL);

       add(FaceDe.STEP);
       add(FaceDe.STEP);

       add(FaceDe.SHOTGUN);
       add(FaceDe.SHOTGUN);
       add(FaceDe.SHOTGUN);
    }};
    private static final List<FaceDe> YELLOWDICE = new ArrayList<FaceDe>() {{
       add(FaceDe.CERVAL);
       add(FaceDe.CERVAL);

       add(FaceDe.STEP);
       add(FaceDe.STEP);

       add(FaceDe.SHOTGUN);
       add(FaceDe.SHOTGUN);
    }};



    public GameManager(){
        faceDes = new FaceDe[3];
        faceDes[0] = FaceDe.CERVAL;
        faceDes[1] = FaceDe.SHOTGUN;
        faceDes[2] = FaceDe.STEP;

        desTirer = new TypeDe[3];
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

    public void tirer3De(){

        //on créer un tableau avec tout les de
        List<TypeDe> totalde = new ArrayList<>();
        for (int i = 0; i < remainingGreenDice; i++) {
            totalde.add(TypeDe.GREEN);
        }
        for (int i = 0; i < remainingRedDice; i++) {
            totalde.add(TypeDe.GREEN);
        }
        for (int i = 0; i < remainingYellowDice; i++) {
            totalde.add(TypeDe.YELLOW);
        }

        //on tire 3 de
        for (int i = 0; i < 3; i++) {
            desTirer[i] = totalde.get((int) (Math.random() * totalde.size()) );
            switch (desTirer[i]){
                case RED:
                    remainingRedDice--;
                    break;
                case GREEN:
                    remainingGreenDice--;
                    break;
                case YELLOW:
                    remainingYellowDice--;
                    break;
            }
            totalde.remove(desTirer);

        }


    }


    public void lancerDeDe(){


        for (int i = 0; i < 3; i++) {
            faceDes[i] = getFaceDeLancer(desTirer[i]);
            if(faceDes[i] == FaceDe.CERVAL){
                joueurList.get(0).ajoutCerveaux();
            }
            if(faceDes[i] == FaceDe.SHOTGUN){
                joueurList.get(0).ajoutShotgun();
            }

        }




    }

    private FaceDe  getFaceDeLancer(TypeDe typeDe){
        switch (typeDe){
            case GREEN:
                return GREENDICE.get((int) (Math.random() * GREENDICE.size()));
            case YELLOW:
                return YELLOWDICE.get((int) (Math.random() * YELLOWDICE.size()));
            case RED:
                 return REDDICE.get((int) (Math.random() * REDDICE.size()));
        }
        return null;

    }


}
