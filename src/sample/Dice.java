package sample;

import java.util.ArrayList;
import java.util.List;

public class Dice {



    public static final List<FaceDe> GREENDICEFACES = new ArrayList<FaceDe>() {{
        add(FaceDe.CERVAL);
        add(FaceDe.CERVAL);
        add(FaceDe.CERVAL);

        add(FaceDe.STEP);
        add(FaceDe.STEP);

        add(FaceDe.SHOTGUN);
    }};

    public static final List<FaceDe> REDDICEFACES = new ArrayList<FaceDe>() {{
        add(FaceDe.CERVAL);

        add(FaceDe.STEP);
        add(FaceDe.STEP);

        add(FaceDe.SHOTGUN);
        add(FaceDe.SHOTGUN);
        add(FaceDe.SHOTGUN);
    }};

    public static final List<FaceDe> YELLOWDICEFACES = new ArrayList<FaceDe>() {{
        add(FaceDe.CERVAL);
        add(FaceDe.CERVAL);

        add(FaceDe.STEP);
        add(FaceDe.STEP);

        add(FaceDe.SHOTGUN);
        add(FaceDe.SHOTGUN);
    }};


    private List<FaceDe> facesDe;

    private TypeDe typeDe;

    public TypeDe getTypeDe() {
        return typeDe;
    }

    public Dice(List<FaceDe> faceDes){
        this.facesDe = faceDes;
        if(facesDe == GREENDICEFACES){
            typeDe = TypeDe.GREEN;
        }
        if(facesDe == REDDICEFACES){
            typeDe = TypeDe.RED;
        }
        if(facesDe == YELLOWDICEFACES){
            typeDe = TypeDe.YELLOW;
        }
    }

    public Dice(List<FaceDe> faceDes, TypeDe typeDe){
        super();
        this.typeDe = typeDe;
        this.facesDe = faceDes;
    }

    public FaceDe  getFaceDeLancer(){
        return facesDe.get((int) (Math.random() * facesDe.size()));
    }

    @Override
    public String toString() {
        return typeDe + "";
    }
}
