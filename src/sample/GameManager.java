package sample;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private Difficulte difficulte;

    public List<Joueur> joueurList;

    private int remainingGreenDice;
    private int remainingRedDice;
    private int remainingYellowDice;

    private Joueur actualJoueur;

    public Joueur getActualJoueur() {
        return actualJoueur;
    }

    public FaceDe faceDes[];

    public TypeDe desTirer[];


    private boolean lancerDeDeDisponible;
    private boolean tirerDesDeDisponible;

    public boolean isLancerDeDeDisponible() {
        return lancerDeDeDisponible;
    }

    public boolean isTirerDesDeDisponible() {
        return tirerDesDeDisponible;
    }


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

        lancerDeDeDisponible = false;
        tirerDesDeDisponible = true;
    }

    public void setActualJoueur(Joueur actualJoueur) {
        this.actualJoueur = actualJoueur;
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
            totalde.add(TypeDe.RED);
        }
        for (int i = 0; i < remainingYellowDice; i++) {
            totalde.add(TypeDe.YELLOW);
        }

        //on tire 3 de
        if(totalde.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                desTirer[i] = totalde.get((int) (Math.random() * totalde.size()));
                switch (desTirer[i]) {
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
                totalde.remove(desTirer[i]);

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
            faceDes[i] = getFaceDeLancer(desTirer[i]);
            if(faceDes[i] == FaceDe.CERVAL){
                actualJoueur.ajoutCerveaux();
            }
            if(faceDes[i] == FaceDe.SHOTGUN){
                actualJoueur.ajoutShotgun();
            }

        }

        if((remainingYellowDice + remainingGreenDice + remainingRedDice) >= 3){
            tirerDesDeDisponible = true;
        }
        lancerDeDeDisponible = false;



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
