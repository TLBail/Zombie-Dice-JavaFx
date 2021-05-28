package model;

import java.io.Serializable;

public class Joueur implements Comparable<Joueur>, Serializable {

    private String nom;

    public Joueur(String nom){
        this.nom = nom;
    }

    private int cerveauxTotal;
    private int cerveauxDuTour;
    private int shotgun;
    private int nbTourSaute;
    private int nbLancerSuccesif;

    public int getNbTourSaute() {
        return nbTourSaute;
    }

    public int getNbLancerSuccesif() {
        return nbLancerSuccesif;
    }

    public int getCerveauxDuTour() {
        return cerveauxDuTour;
    }

    public int getCerveauxTotal() {
        return cerveauxTotal;
    }

    public String getNom() {
        return nom;
    }

    public void ajoutCerveaux(){
        cerveauxDuTour++;
    }

    public void setNbLancerSuccesif(int nbLancerSuccesif) {
        this.nbLancerSuccesif = nbLancerSuccesif;
    }

    public void setNbTourSaute(int nbTourSaute) {
        this.nbTourSaute = nbTourSaute;
    }

    @Override
    public String toString() {
        return "joueur : " + nom;
    }


    public void ajouterLesCerveauxDuTour(){
        cerveauxTotal += cerveauxDuTour;
        cerveauxDuTour = 0;
    }

    public void remettreAzero(){
        cerveauxDuTour = 0;
        shotgun = 0;
    }

    public void remettreAzeroLesCerveauxDuTour(){
        cerveauxDuTour = 0;
    }

    public int getShotgun() {
        return shotgun;
    }

    public void ajoutShotgun(){
        shotgun++;
    }

    @Override
    public int compareTo(Joueur emp) {
        return (this.cerveauxTotal - emp.cerveauxTotal);
    }

}
