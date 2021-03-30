public class Absenta {
    private String data;
    private Materie materie;
    private boolean motivata;

    public Absenta(String data, Materie materie, boolean motivata) {
        this.data = data;
        this.materie = materie;
        this.motivata = motivata;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Materie getMaterie() {
        return materie;
    }

    public void setMaterie(Materie materie) {
        this.materie = materie;
    }

    public boolean isMotivata() {
        return motivata;
    }

    public void setMotivata(boolean motivata) {
        this.motivata = motivata;
    }
}
