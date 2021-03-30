import java.lang.reflect.Array;
import java.util.ArrayList;

public class Clasa {
    private String nume; //ex a XII-a F;
    private String diriginte;
    private Catalog catalog;

    public Clasa(String nume, String diriginte, Catalog catalog) {
        this.nume = nume;
        this.diriginte = diriginte;
        this.catalog = catalog;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDiriginte() {
        return diriginte;
    }

    public void setDiriginte(String diriginte) {
        this.diriginte = diriginte;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}

