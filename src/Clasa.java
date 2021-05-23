import java.lang.reflect.Array;
import java.util.ArrayList;

public class Clasa {
    private String nume; //ex a XII-a F;
    private String numeDiriginte;
    private String prenumeDiriginte;
    private Catalog catalog;

    public Clasa(String nume, String numeDiriginte,String prenumeDiriginte, Catalog catalog) {
        this.nume = nume;
        this.numeDiriginte = numeDiriginte;
        this.prenumeDiriginte = prenumeDiriginte;
        this.catalog = catalog;
    }

    public Clasa(String nume, Catalog catalog) {
        this.nume = nume;
        this.numeDiriginte = numeDiriginte;
        this.prenumeDiriginte = prenumeDiriginte;
        this.catalog = catalog;
    }

    public String getNume() {
        return nume;
    }

    public String getNumeDiriginte() {
        return numeDiriginte;
    }

    public String getPrenumeDiriginte() {
        return prenumeDiriginte;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setNumeDiriginte(String numeDiriginte) {
        this.numeDiriginte = numeDiriginte;
    }

    public void setPrenumeDiriginte(String prenumeDiriginte) {
        this.prenumeDiriginte = prenumeDiriginte;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}

