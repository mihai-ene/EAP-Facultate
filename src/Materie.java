import java.util.ArrayList;

public class Materie {
    private String denumire;
    private Profesor profesor;


    public Materie(String denumire, Profesor profesor) {
        this.denumire = denumire;
        this.profesor = profesor;

    }


    public String getDenumire() {
        return denumire;
    }

}
