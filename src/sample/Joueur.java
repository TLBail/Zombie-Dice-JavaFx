package sample;

public class Joueur {

    private String nom;

    public Joueur(String nom){
        this.nom = nom;
    }

    private int cerveaux;

    public int getCerveaux() {
        return cerveaux;
    }

    public String getNom() {
        return nom;
    }

    public void ajoutCerveaux(int nbcerveau){
        cerveaux += nbcerveau;
    }

    @Override
    public String toString() {
        return "joueur : " + nom;
    }




}
