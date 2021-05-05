import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Elev {
    private String nume;
    private String prenume;
    private String dataNasterii;
    private boolean repetent = false;
    private boolean corigent = false;
    private int nr_absente_nemotivate=0;
    private float medieGenerala =0;
    private HashMap<Materie,ArrayList<Integer>> note = new HashMap<Materie,ArrayList<Integer>>();
    private ArrayList<Absenta> absente = new ArrayList<Absenta>();

    public int getNr_absente_nemotivate() {
        return nr_absente_nemotivate;
    }

    public void setNr_absente_nemotivate(int nr_absente_nemotivate) {
        this.nr_absente_nemotivate = nr_absente_nemotivate;
    }

    public Elev(String nume, String prenume, String dataNasterii, boolean repetent, boolean corigent, int nr_absente_nemotivate, float medieGenerala){
        this.nume = nume;
        this.prenume = prenume;
        this.dataNasterii = dataNasterii;
        this.corigent = corigent;
        this.repetent = repetent;
        this.nr_absente_nemotivate = nr_absente_nemotivate;
        this.medieGenerala = medieGenerala;
    }
    public Elev(){

    }
    public Elev(Elev other){
        this.nume = other.nume;
        this.prenume = other.prenume;
        this.dataNasterii = other.dataNasterii;
    }
    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getData_nasterii() {
        return dataNasterii;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }


    public ArrayList<Absenta> getAbsente() {
        return absente;
    }

    public void setAbsente(ArrayList<Absenta> absente) {
        this.absente = absente;
    }

    public HashMap<Materie, ArrayList<Integer>> getNote() {
        return note;
    }

    public void setNote(HashMap<Materie, ArrayList<Integer>> note) {
        this.note = note;
    }

    public float getMedieGenerala() {
        return medieGenerala;
    }

    public void setMedieGenerala(float medieGenerala) {
        this.medieGenerala = medieGenerala;
    }

    public boolean isRepetent() {
        return repetent;
    }

    public void setRepetent(boolean repetent) {
        this.repetent = repetent;
    }

    public boolean isCorigent() {
        return corigent;
    }

    public void setCorigent(boolean corigent) {
        this.corigent = corigent;
    }

    public int getNrAbsenteNemotivate() {
        return nr_absente_nemotivate;
    }

    public void setNrAbsenteNemotivate(int nr_absente_nemotivate) {
        this.nr_absente_nemotivate = nr_absente_nemotivate;
    }
}

