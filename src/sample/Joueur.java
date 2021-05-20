package sample;

public class Joueur {

    private String nom;

    public Joueur(String nom){
        this.nom = nom;
    }

    private int cerveaux;
    private int shotgun;

    public int getCerveaux() {
        return cerveaux;
    }

    public String getNom() {
        return nom;
    }

    public void ajoutCerveaux(){
        cerveaux++;
    }

    @Override
    public String toString() {
        return "joueur : " + nom;
    }


    public int getShotgun() {
        return shotgun;
    }

    public void ajoutShotgun(){
        shotgun++;
    }
}
