import java.util.ArrayList;

public class Materie {
    private String denumire;
    private int materieId;
    private Profesor profesor;

    public Materie(int materieId, String denumire, String nume, String prenume) {
        this.materieId = materieId;
        this.denumire = denumire;
        this.profesor = new Profesor(nume,prenume);
    }
    public Materie(){

    }
    public Materie(Materie other) {
        this.materieId = other.materieId;
        this.denumire = other.denumire;
        this.profesor = other.profesor;;
    }

    public int getMaterieId() { return materieId; }
    public String getDenumire() {
        return denumire;
    }
    public String getNumeProfesor() { return profesor.getNume();}
    public String getPrenumeProfesor() {return profesor.getPrenume();}

}
