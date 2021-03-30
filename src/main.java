import java.util.*;

public class main {
    public static void main(String[] args) {
       Elev Andrei = new Elev("Andrei","Constantinescu","14/10/2000");
       Elev Mihai = new Elev("Mihai", "Ene","15/07/1999");

        Catalog c = new Catalog("2020-2021");
        c.adaugaElev(Andrei);
        c.adaugaElev(Mihai);

        Profesor paun = new Profesor("Mazilu","Gheorghe");
        Materie matematica = new Materie("Matematica",paun);

        c.adaugaNota(Andrei,matematica,3);
        c.adaugaNota(Andrei,matematica,5);
        c.adaugaNota(Andrei,matematica,7);

        c.adaugaNota(Mihai,matematica,10);
        c.afisareNoteMaterie(Andrei,matematica);

        Clasa f12 = new Clasa("a XII-a F","Sotiriu Doina",c);
        c.adaugaAbsenta(Andrei,matematica,"14/10/2000",false);
        c.adaugaAbsenta(Andrei,matematica,"15/10/2000",false);

        for(int i=0;i<42;i++)
            c.adaugaAbsenta(Andrei,matematica,"15/10/2000",true);

        c.afisareAbsente(Andrei);

        c.afisareMedieMaterie(Andrei,matematica);

        Materie romana = new Materie("Romana",paun);

        c.adaugaNota(Andrei,romana,8);
        c.adaugaNota(Andrei,romana,7);

        c.afisareMedieMaterie(Andrei,romana);

        c.afisareMedieGenerala(Andrei);

        c.afisareEleviCorigenti(matematica);

        c.afisareEleviRepetenti();

        c.sortareDupaMedie();

        c.sortareDupaNume();

        c.toString(Andrei);

    }
}

