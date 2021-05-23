import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Scanner;
import java.sql.*;


public class Menu {
    private HashMap<String, Clasa> clase = new HashMap<String, Clasa>();

    public HashMap<String, Clasa> getClase() {
        return clase;
    }

    public Connection connection;

    public void setClase(HashMap<String, Clasa> clase) {
        this.clase = clase;
    }

    public ArrayList<Elev> loadElevi(int clasaId) {
        ArrayList<Elev> elevi = new ArrayList<Elev>();
        try {
            Statement eleviStatement = this.connection.createStatement();
            ResultSet eleviSet = eleviStatement.executeQuery("SELECT * FROM elevi WHERE clasa_id = " + clasaId + ";");
            while (eleviSet.next()) {
                int elevId = eleviSet.getInt("elev_id");
                String nume = eleviSet.getString("Nume");
                String prenume = eleviSet.getString("Prenume");
                String dataNasterii = eleviSet.getString("DataNasterii");
                Boolean repetenet = eleviSet.getBoolean("Repetent");
                Boolean corigent = eleviSet.getBoolean("Corigent");
                int nrAbsenteNemotivate = eleviSet.getInt("NrAbsenteNemotivate");
                float medieGenerala = eleviSet.getFloat("MedieGenerala");
                Elev e = new Elev(elevId, nume, prenume, dataNasterii, repetenet, corigent, nrAbsenteNemotivate, medieGenerala);
                elevi.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elevi;
    }

    public ArrayList<Materie> loadMaterii(String clasa) {
        ArrayList<Materie> materii = new ArrayList<Materie>();
        String denumire, numeProfesor, prenumeProfesor;
        int materieId;
        try {
            Statement materiiStatement = this.connection.createStatement();
            Statement profesoriStatement = this.connection.createStatement();
            ResultSet materiiSet = materiiStatement.executeQuery("SELECT * FROM materii");
            while (materiiSet.next()) {
                denumire = materiiSet.getString("Denumire");
                materieId = materiiSet.getInt("materie_id");
                int profesorId = materiiSet.getInt("profesor_id");
                try {
                    ResultSet profesoriSet = profesoriStatement.executeQuery("SELECT NumeProfesor,PrenumeProfesor FROM profesori WHERE profesor_id = " + profesorId + ";");
                    while (profesoriSet.next()) {
                        numeProfesor = profesoriSet.getString("NumeProfesor");
                        prenumeProfesor = profesoriSet.getString("PrenumeProfesor");
                        Materie m = new Materie(materieId, denumire, numeProfesor, prenumeProfesor);
                        materii.add(m);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materii;
    }

    public void initConection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scoala", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Elev> loadGrades(ArrayList<Elev> elevi, int clasaId, ArrayList<Materie> materii) {
        Materie pentruNota = null;
        try {
            for (Elev e : elevi) {
                int elevId = e.getElevId();
                Statement noteStatement = this.connection.createStatement();
                ResultSet noteSet = noteStatement.executeQuery("SELECT * FROM note WHERE elev_id = " + elevId + ";");
                while (noteSet.next()) {
                    int idMaterie = noteSet.getInt("materie_id");
                    for (Materie m : materii) {
                        if (m.getMaterieId() == idMaterie)
                            pentruNota = m;
                    }
                    int nota = noteSet.getInt("nota");
                    if (e.getNote().containsKey(pentruNota)) {
                        ArrayList<Integer> noteleCurente = e.getNote().get(pentruNota);
                        noteleCurente.add(nota);
                        e.getNote().put(pentruNota, noteleCurente);
                    } else {
                        ArrayList<Integer> note = new ArrayList<Integer>();
                        note.add(nota);
                        e.getNote().put(pentruNota, note);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elevi;

    }

    public void loadClases() {
        try {
            Statement claseStatement = this.connection.createStatement();
            ResultSet resultSet = claseStatement.executeQuery("SELECT * FROM listaclaselor");
            while (resultSet.next()) {
                String denumire = resultSet.getString("Denumire");
                int clasaId = resultSet.getInt("clasa_id");
                String numeDiriginte = resultSet.getString("NumeDiriginte");
                String prenumeDiriginte = resultSet.getString("PrenumeDiriginte");
                ArrayList<Materie> materii = loadMaterii(denumire);
                ArrayList<Elev> elevi = loadElevi(clasaId);
                elevi = loadGrades(elevi, clasaId, materii);
                Catalog catalog = new Catalog(elevi, materii, "2021");
                Clasa clasa = new Clasa(denumire, numeDiriginte, prenumeDiriginte, catalog);
                this.clase.put(denumire, clasa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateElevi() {
        int repetent = 0, corigent = 0;
        try {
            Statement eleviStatement = this.connection.createStatement();
            for (String clasa : this.getClase().keySet()) {
                for (Elev e : this.getClase().get(clasa).getCatalog().getElevi()) {
                    try {
                        if(e.isRepetent())
                            repetent = 1;
                        if(e.isCorigent())
                            corigent = 1;
                        eleviStatement.executeUpdate(String.format("UPDATE elevi SET Nume = \"%s\", Prenume = \"%s\", DataNasterii = \"%s\", Repetent = %d, Corigent = %d, NrAbsenteNemotivate = %d, MedieGenerala = %f WHERE elev_id = %d;", e.getNume(), e.getPrenume(), e.getData_nasterii(), repetent, corigent, e.getNrAbsenteNemotivate(), e.getMedieGenerala(), e.getElevId()));
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public void adaugaNota() {
        String clasa, numeElev, prenumeElev, denumireMaterie;
        boolean found = false;
        Materie pentruNota = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clasa:");
        clasa = scanner.nextLine();
        System.out.println("Nume elev:");
        numeElev = scanner.nextLine();
        System.out.println("Prenume elev:");
        prenumeElev = scanner.nextLine();
        System.out.println("Materie:");
        denumireMaterie = scanner.next();
        Catalog catalog = this.clase.get(clasa).getCatalog();
        for (Materie materie : catalog.getMaterii())
            if (materie.getDenumire().equals(denumireMaterie)) {
                System.out.println(materie.getDenumire());
                pentruNota = materie;
                found = true;
            }
        if (!found) {
            System.out.println("ERROR: Nu s-a gasit aceasta materie!");
            menu();
        }
        found = false;
        System.out.println("Nota:");
        int nota = scanner.nextInt();
        if (nota <= 10) {
            for (Elev x : catalog.getElevi()) {
                System.out.println(x.getNume());
                if (x.getNume().equals(numeElev) && x.getPrenume().equals(prenumeElev)) {
                    catalog.adaugaNota(x, pentruNota, nota);
                    try {
                        Statement notaStatement = this.connection.createStatement();
                        notaStatement.executeUpdate(String.format("INSERT INTO note (elev_id,materie_id,nota) values (%d,%d,%d)", x.getElevId(), pentruNota.getMaterieId(), nota));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Test");
                    found = true;
                }
            }
            if (found == true) {
                System.out.println("Nota adaugata cu succes!");
                menu();
            } else
                System.out.println("ERROR: Nu s-a gasit elevul!");
        } else {
            System.out.println("Nota nu poate fi mai mare ca 10");
        }
    }

    public void adaugaAbsenta() {
        String clasa, numeElev, prenumeElev, denumireMaterie, data, stringMotivata;
        boolean motivata = false;
        boolean found = false;
        Materie pentruAbsenta = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clasa:");
        clasa = scanner.nextLine();
        System.out.println("Nume elev:");
        numeElev = scanner.nextLine();
        System.out.println("Prenume elev:");
        prenumeElev = scanner.nextLine();
        System.out.println("Materie:");
        denumireMaterie = scanner.next();
        System.out.println("Data(YYYY-MM-DD):");
        data = scanner.next();
        System.out.println("Motivata(DA/NU):");
        stringMotivata = scanner.next();
        if (stringMotivata.toLowerCase().equals("DA".toLowerCase()))
            motivata = true;
        else if (stringMotivata.toLowerCase().equals("NU".toLowerCase()))
            motivata = false;
        else
            System.out.println("Nu ati introdus datele corect.");
        Catalog catalog = this.clase.get(clasa).getCatalog();
        for (Materie materie : catalog.getMaterii())
            if (materie.getDenumire().equals(denumireMaterie)) {
                System.out.println(materie.getDenumire());
                pentruAbsenta = materie;
                found = true;
            }
        if (!found) {
            System.out.println("ERROR: Nu s-a gasit aceasta materie!");
            menu();
        }
        for (Elev x : catalog.getElevi()) {
            System.out.println(x.getNume());
            if (x.getNume().equals(numeElev) && x.getPrenume().equals(prenumeElev)) {
                catalog.adaugaAbsenta(x, pentruAbsenta, data, motivata);
                if (motivata == false)
                    catalog.cresteNrAbsenteNemotivate(x);
                found = true;
                menu();
                break;
            }
        }
    }

    public void calculeazaAbsenteNemotivate() {
        for (String denumireClasa : clase.keySet()) {
            System.out.println("----- Clasa " + denumireClasa + " -----");
            for (Elev x : clase.get(denumireClasa).getCatalog().getElevi()) {
                System.out.println("Elevul " + x.getNume() + " " + x.getPrenume() + " are " + x.getNrAbsenteNemotivate() + " absente nemotivate");
            }
        }
        System.out.println("");
        menu();
    }

    public void calculeazaMediaGenerala() {
        for (String denumireClasa : clase.keySet())
            for (Elev x : clase.get(denumireClasa).getCatalog().getElevi())
                x.setMedieGenerala(clase.get(denumireClasa).getCatalog().calculeazaMedieGenerala(x));
        menu();
    }

    public void afiseazaMedieMaterie() {
        String clasa, denumireMaterie;
        Materie pentruCalculat = null;
        boolean found = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clasa: ");
        clasa = scanner.nextLine();
        System.out.println("Materie: ");
        denumireMaterie = scanner.nextLine();
        for (Materie materie : clase.get(clasa).getCatalog().getMaterii())
            if (materie.getDenumire().equals(denumireMaterie)) {
                pentruCalculat = materie;
                found = true;
            }

        if (!found) {
            System.out.println("ERROR: Nu s-a gasit aceasta materie!");
            menu();
        }
        for (Elev x : clase.get(clasa).getCatalog().getElevi()) {
            System.out.println(x.getNume() + " " + x.getPrenume() + ": " + clase.get(clasa).getCatalog().calculareMedieMaterie(x, pentruCalculat));
        }
        menu();

    }

    public void sorteazaAlfabetic() {
        for (String denumireClasa : clase.keySet()) {
            System.out.println("------ Clasa " + denumireClasa + " ------- ");
            clase.get(denumireClasa).getCatalog().sortareDupaNume();
        }
        menu();
    }

    public void sorteazaDupaMedie() {
        for (String denumireClasa : clase.keySet()){
            System.out.println("------ Clasa " + denumireClasa + " ------- ");
            clase.get(denumireClasa).getCatalog().sortareDupaMedie();
        }
        menu();
    }

    public void listaEleviCorigenti() {
        for (String denumireClasa : clase.keySet()) {
            System.out.println("---- " + denumireClasa + " ----");
            for (Materie m : clase.get(denumireClasa).getCatalog().getMaterii())
                clase.get(denumireClasa).getCatalog().afisareEleviCorigenti(m);
        }
        menu();
    }

    public void listaEleviRepetenti() {
        for (String denumireClasa : clase.keySet()) {
            System.out.println("---- " + denumireClasa + " ----");
            clase.get(denumireClasa).getCatalog().afisareEleviRepetenti();
        }
        menu();
    }

    public void informatiiElev() {
        String clasa, numeElev, prenumeElev;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clasa: ");
        clasa = scanner.nextLine();
        System.out.println("Nume elev: ");
        numeElev = scanner.nextLine();
        System.out.println("Prenume elev: ");
        prenumeElev = scanner.nextLine();
        boolean found = false;
        for (Elev x : clase.get(clasa).getCatalog().getElevi())
            if (x.getNume().equals(numeElev) && x.getPrenume().equals(prenumeElev)) {
                clase.get(clasa).getCatalog().toString(x);
                found = true;
                menu();
                break;
            }
        if (!found) {
            System.out.println("Nu s-a gasit elevul!");
            menu();
        }

    }

    public void update() {
        updateElevi();
        System.out.println("Baza de date a fost updata cu succes!");

    }

    public void stergeElev(){
        String numeElev, prenumeElev;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nume elev: ");
        numeElev = scanner.nextLine();
        System.out.println("Prenume elev: ");
        prenumeElev = scanner.nextLine();
        try{
            Statement stergeElev = this.connection.createStatement();
            stergeElev.executeUpdate(String.format("DELETE from elevi WHERE Nume =\"%s\" AND Prenume = \"%s\"",numeElev,prenumeElev));
            System.out.println("Elevul a fost sters cu succes!");
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }

    public void menu() {
        int action = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("--- Catalog ---");
        System.out.println("1. Adauga o nota in catalog");
        System.out.println("2. Adauga o absenta in catalog");
        System.out.println("3. Calculeaza nr. absente nemotivate pentru toti elevii");
        System.out.println("4. Calculeaza media generala pentru toti elevii");
        System.out.println("5. Afiseaza media tuturor elevilor dintr-o clasa la o anumita materie");
        System.out.println("6. Sorteaza elevii in ordine alfabetica");
        System.out.println("7. Sorteaza elevii dupa medie");
        System.out.println("8. Lista cu toti elevii corigenti");
        System.out.println("9. Lista cu toti elevii repetenti");
        System.out.println("10. Informatii despre un anumit elev");
        System.out.println("11. Sterge un elev");
        System.out.println("12. Iesire");
        while (action != 9) {
            action = input.nextInt();
            if (action == 1)
                adaugaNota();
            if (action == 2)
                adaugaAbsenta();
            if (action == 3)
                calculeazaAbsenteNemotivate();
            if (action == 4)
                calculeazaMediaGenerala();
            if (action == 5)
                afiseazaMedieMaterie();
            if (action == 6)
                sorteazaAlfabetic();
            if (action == 7)
                sorteazaDupaMedie();
            if (action == 8)
                listaEleviCorigenti();
            if (action == 9)
                listaEleviRepetenti();
            if (action == 10)
                informatiiElev();
            if (action == 11)
                stergeElev();
            if (action == 12) {
                update();
                break;
            }
        }

    }
}







