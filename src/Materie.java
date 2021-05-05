import java.util.ArrayList;

public class Materie {
    private String denumire;
    private Profesor profesor;

    public Materie(String denumire, String nume, String prenume) {
        this.denumire = denumire;
        this.profesor = new Profesor(nume,prenume);
    }
    public Materie(){

    }
    public Materie(Materie other) {
        this.denumire = other.denumire;
        this.profesor = other.profesor;;
    }


    public String getDenumire() {
        return denumire;
    }
    public String getNumeProfesor() { return profesor.getNume();}
    public String getPrenumeProfesor() {return profesor.getPrenume();}

}
