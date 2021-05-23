import java.lang.ref.PhantomReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Catalog {
    private ArrayList<Elev> elevi = new ArrayList<Elev>();
    private String anScolar; //ex 2020-2021
    private ArrayList<Materie> materii = new ArrayList<Materie>();

    public Catalog(){

    }
    public ArrayList<Materie> getMaterii() {
        return materii;
    }

    public void setMaterii(ArrayList<Materie> materii) {
        this.materii = materii;
    }

    public ArrayList<Elev> getElevi() {
        return elevi;
    }

    public void setElevi(ArrayList<Elev> elevi) {
        this.elevi = elevi;
    }

    public String getAnScolar() {
        return anScolar;
    }

    public void setAnScolar(String anScolar) {
        this.anScolar = anScolar;
    }

    public Catalog(ArrayList<Elev> elevi, ArrayList<Materie> materii, String anScolar) {
        this.elevi = elevi;
        this.materii = materii;
        this.anScolar = anScolar;
    }

    public void adaugaElev(Elev x) {
        this.elevi.add(x);
    }


    public void adaugaNota(Elev x, Materie m, int nota) {
        if (x.getNote().containsKey(m)) {
            ArrayList<Integer> noteleCurente = x.getNote().get(m);
            noteleCurente.add(nota);
            x.getNote().put(m,noteleCurente);
        }
        else{
            ArrayList<Integer> note = new ArrayList<Integer>();
            note.add(nota);
            x.getNote().put(m,note);
        }
    }

    public void adaugaAbsenta(Elev x, Materie m,String data,boolean motivata){
        Absenta absentaNoua = new Absenta(data,m,motivata);
        x.getAbsente().add(absentaNoua);
    }
    public void afisareNoteMaterie(Elev x,Materie m){
        if (x.getNote().containsKey(m)) {
            System.out.println("-----------"+"Notele lui "+x.getNume()+" "+x.getPrenume()+" la "+m.getDenumire()+"---------------");
            for (int nota : x.getNote().get(m))
                System.out.println(nota);
            System.out.println();
            System.out.println("--------------------------");
            System.out.println();
        }
        else{
            System.out.println(x.getNume() + " nu are note la " + m.getDenumire());
        }
    }
    public void afisareAbsente(Elev x){
        System.out.println("--------------------------");
        System.out.println(x.getNume() + " " + x.getPrenume() + " are " + x.getAbsente().size() + " absente");
        for( Absenta absenta : x.getAbsente()) {
            System.out.println("Materia: " + absenta.getMaterie().getDenumire() + "; Data: " + absenta.getData() + "; Motivata: " + absenta.isMotivata());
        }
        System.out.println("--------------------------");
        System.out.println();
    }

    public int calculareMedieMaterie(Elev x, Materie m) {
        if (x.getNote().containsKey(m)) {
            float sumanote = 0;
            for (int nota : x.getNote().get(m))
                sumanote += nota;
            return Math.round(sumanote / x.getNote().get(m).size());
        }
        return 0;
    }


    public void afisareMedieMaterie(Elev x, Materie m){
            System.out.println(x.getNume() + " " + x.getPrenume() + " are media " + calculareMedieMaterie(x,m) + " la " + m.getDenumire());
    }

    public float calculeazaMedieGenerala(Elev x){
        float mediematerii = 0;
        for (Materie m: x.getNote().keySet()){
            mediematerii+= calculareMedieMaterie(x,m);
        }
        return(mediematerii/x.getNote().keySet().size());
    }

   /* public void calculareAbsenteNemotivate(Elev x){
        int nr_absente = 0;
        for( Absenta absenta : x.getAbsente())
            if(!absenta.isMotivata())
                nr_absente++;
        x.setNrAbsenteNemotivate(nr_absente+x.getNrAbsenteNemotivate());
    }*/
    public void afisareEleviCorigenti(Materie m){
        ArrayList<Elev> corigenti = new ArrayList<Elev>();
        for(Elev elev : this.elevi)
            if(calculareMedieMaterie(elev,m)<5) {
                corigenti.add(elev);
                elev.setCorigent(true);
            }
        if(corigenti.size()==0)
            System.out.println("Nu sunt elevi corigenti la " + m.getDenumire()+"\n");
        else {
            System.out.println("Elevi corigenti la " + m.getDenumire() + ":");
            for (Elev elev : corigenti) {
                System.out.println("- "+elev.getNume() + " " + elev.getPrenume());
            }
            System.out.println();
        }
    }

    public void afisareEleviRepetenti(){
            ArrayList<Elev> eleviRepetenti = new ArrayList<Elev>();
            for(Elev elev : this.elevi){
                int nr_absente = 0;
                int nr_corigente = 0;
                for (Materie m: elev.getNote().keySet())
                    if(calculareMedieMaterie(elev,m)<5)
                        nr_corigente++;
                if(nr_corigente>=1) {
                    eleviRepetenti.add(elev);

                }
                for( Absenta absenta : elev.getAbsente())
                    if(!absenta.isMotivata())
                        nr_absente++;
                if(nr_absente>40 && !eleviRepetenti.contains(elev)) {
                    eleviRepetenti.add(elev);
                    elev.setRepetent(true);
                }
            }
            if(eleviRepetenti.size()>0) {
                System.out.println("Elevi repetenti: ");
                for (Elev elev : eleviRepetenti) {
                    System.out.println(elev.getNume() + " " + elev.getPrenume());
                }
                System.out.println();
            }
            else
                System.out.println("Nu sunt elevi repetenti\n");
    }

    public void sortareDupaMedie(){
        for(Elev x : this.elevi){
            float medie=0;
            for (Materie m: x.getNote().keySet()) {
                medie = medie + calculareMedieMaterie(x, m);
            }
            float medieGenerala = medie / x.getNote().keySet().size();
            x.setMedieGenerala(medieGenerala);
        }
        Comparator<Elev> c = (s1, s2) -> (int) (s2.getMedieGenerala() - s1.getMedieGenerala());
        this.elevi.sort(c);
        for(Elev x : this.elevi){
            System.out.println(x.getNume() + " " + x.getPrenume() + ": " + x.getMedieGenerala());
        }
        System.out.println("--------------------------");
        System.out.println();
    }

    public void sortareDupaNume(){
        Comparator<Elev> c = (s1, s2) -> (int) ((s1.getNume() + " " + s1.getPrenume()).compareTo(s2.getNume() + " " + s2.getPrenume()));
        this.elevi.sort(c);
        for(Elev x : this.elevi){
            System.out.println(x.getNume() + " " + x.getPrenume() + ": " + x.getMedieGenerala());
        }
        System.out.println("--------------------------");
        System.out.println();
    }

    public void cresteNrAbsenteNemotivate(Elev x){
        int nr_absente = x.getNrAbsenteNemotivate();
        x.setNr_absente_nemotivate(nr_absente+1);
    }

    public void toString(Elev x){
        System.out.println("--------------------------");
        System.out.println("Nume: " + x.getNume());
        System.out.println("Prenume: " + x.getPrenume());
        System.out.println("Data nasterii: " + x.getData_nasterii());
        System.out.println("Nr. absente nemotivate: " + x.getNrAbsenteNemotivate());
        System.out.println("Medie generala: " + x.getMedieGenerala());
        System.out.println("Repetent: " + x.isRepetent());
        System.out.println("Corigent: " + x.isCorigent());
        System.out.println("--------------------------");
        System.out.println();
    }

}



