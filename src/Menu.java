import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Scanner;


public class Menu {
    private HashMap<String, Clasa> clase = new HashMap<String, Clasa>();

    public HashMap<String, Clasa> getClase() {
        return clase;
    }

    public void setClase(HashMap<String, Clasa> clase) {
        this.clase = clase;
    }

    public ArrayList<Elev> loadElevi(String clasa) {
        ArrayList<Elev> elevi = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/Catalog/" + clasa + "/Elevi.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Elev a = new Elev(data[0], data[1], data[2],Boolean.parseBoolean(data[3]),Boolean.parseBoolean(data[4]),Integer.parseInt(data[5]),Float.parseFloat(data[6]));
                elevi.add(a);
            }
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        return elevi;
    }

    public static ArrayList<Materie> loadMaterii(String clasa) {
        ArrayList<Materie> materii = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/Catalog/" + clasa + "/Materii.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Materie a = new Materie(data[0], data[1], data[2]);
                materii.add(a);
            }
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        return materii;
    }

    public void loadClases() {
        try (BufferedReader br = new BufferedReader(new FileReader("./src/Catalog/ListaClaselor.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String denumire = data[0];
                String numeDiriginte = data[1];
                String prenumeDiriginte = data[2];
                ArrayList<Materie> materii = loadMaterii(denumire);
                ArrayList<Elev> elevi = loadElevi(denumire);
                elevi = loadGrades(elevi,denumire,materii);
                Catalog catalog = new Catalog(elevi, materii, "2021");
                Clasa clasa = new Clasa(denumire, numeDiriginte, prenumeDiriginte, catalog);
                this.clase.put(denumire, clasa);
            }
        } catch (IOException ex) {
            System.out.println("File not found");
        }
    }

    public ArrayList<Elev> loadGrades(ArrayList<Elev> elevi,String clasa,ArrayList<Materie> materii){
        Materie materieNota = null;
        for(Elev x : elevi){
            try (BufferedReader br = new BufferedReader(new FileReader("./src/Catalog/"+clasa+"/Note/"+x.getNume()+" "+x.getPrenume()+".csv"))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    String materie = data[0];
                    for(Materie m : materii)
                        if(m.getDenumire().equals(materie))
                            materieNota = m;
                    int nota = Integer.parseInt(data[1]);
                    if (x.getNote().containsKey(materieNota)) {
                        ArrayList<Integer> noteleCurente = x.getNote().get(materieNota);
                        noteleCurente.add(nota);
                        x.getNote().put(materieNota,noteleCurente);
                    }
                    else{
                        ArrayList<Integer> note = new ArrayList<Integer>();
                        note.add(nota);
                        x.getNote().put(materieNota,note);
                    }
                }
            } catch (IOException ex) {
                System.out.println("ERROR: Fisier negasit!");
            }
        }
        return elevi;
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
                    System.out.println("Test");
                    found = true;
                }
            }
            if (found == true) {
                System.out.println("Nota adaugata cu succes!");
                menu();
            }
            else
                System.out.println("ERROR: Nu s-a gasit elevul!");
        }
        else{
            System.out.println("Nota nu poate fi mai mare ca 10");
        }
    }
    public void adaugaAbsenta(){
        String clasa, numeElev, prenumeElev, denumireMaterie, data,stringMotivata;
        boolean motivata=false;
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
        System.out.println("Data(XX-XX-XXXX):");
        data = scanner.next();
        System.out.println("Motivata(DA/NU):");
        stringMotivata = scanner.next();
        if(stringMotivata.toLowerCase().equals("DA".toLowerCase()))
            motivata = true;
        else if(stringMotivata.toLowerCase().equals("NU".toLowerCase()))
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
                catalog.adaugaAbsenta(x,pentruAbsenta,data,motivata);
                if(motivata == false)
                    catalog.cresteNrAbsenteNemotivate(x);
                found = true;
                menu();
                break;
            }
        }
    }
    public void calculeazaAbsenteNemotivate(){
        for(String denumireClasa : clase.keySet()) {
            System.out.println("----- Clasa " + denumireClasa + " -----");
            for (Elev x : clase.get(denumireClasa).getCatalog().getElevi()) {
                clase.get(denumireClasa).getCatalog().calculareAbsenteNemotivate(x);
                System.out.println("Elevul " + x.getNume() + " " + x.getPrenume() + " are " + x.getNrAbsenteNemotivate() + " absente nemotivate");
            }
        }
        System.out.println("");
        menu();
    }
    public void calculeazaMediaGenerala(){
        for(String denumireClasa : clase.keySet())
            for(Elev x : clase.get(denumireClasa).getCatalog().getElevi())
                    x.setMedieGenerala(clase.get(denumireClasa).getCatalog().calculeazaMedieGenerala(x));
        menu();
    }
    public void afiseazaMedieMaterie(){
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
        for(Elev x : clase.get(clasa).getCatalog().getElevi()){
            System.out.println(x.getNume() + " " + x.getPrenume() + ": "+clase.get(clasa).getCatalog().calculareMedieMaterie(x,pentruCalculat));
        }
        menu();

    }
    public void sorteazaAlfabetic(){
        for(String denumireClasa : clase.keySet())
            clase.get(denumireClasa).getCatalog().sortareDupaNume();
        menu();
    }
    public void sorteazaDupaMedie(){
        for(String denumireClasa : clase.keySet())
            clase.get(denumireClasa).getCatalog().sortareDupaMedie();
        menu();
    }

    public void listaEleviCorigenti(){
        for(String denumireClasa : clase.keySet()) {
            System.out.println("---- " + denumireClasa + " ----");
            for (Materie m : clase.get(denumireClasa).getCatalog().getMaterii())
                clase.get(denumireClasa).getCatalog().afisareEleviCorigenti(m);
        }
        menu();
    }
    public void listaEleviRepetenti(){
        for(String denumireClasa : clase.keySet()){
            System.out.println("---- " + denumireClasa + " ----");
            clase.get(denumireClasa).getCatalog().afisareEleviRepetenti();
        }
        menu();
    }
    public void informatiiElev(){
        String clasa, numeElev, prenumeElev;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clasa: ");
        clasa=scanner.nextLine();
        System.out.println("Nume elev: ");
        numeElev = scanner.nextLine();
        System.out.println("Prenume elev: ");
        prenumeElev = scanner.nextLine();
        boolean found = false;
        for(Elev x : clase.get(clasa).getCatalog().getElevi())
            if(x.getNume().equals(numeElev) && x.getPrenume().equals(prenumeElev)) {
                clase.get(clasa).getCatalog().toString(x);
                found = true;
                menu();
                break;
            }
        if(!found) {
            System.out.println("Nu s-a gasit elevul!");
            menu();
        }

    }
    public void updateElevi(){
        String path = "./src/Catalog/";
        File[] files = new File(path).listFiles();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    FileWriter eleviWriter = new FileWriter(file.getAbsolutePath() + "\\Elevi.csv");
                    eleviWriter.append("Nume,Prenume,Data nasterii,Repetent,Corigent,Nr. absente nemotivate,Medie generala\n");
                    for (Elev x : this.clase.get(file.getName()).getCatalog().getElevi()) {
                        eleviWriter.append(x.getNume() + "," + x.getPrenume() + "," + x.getData_nasterii() + "," + x.isRepetent() + "," + x.isCorigent() + "," + x.getNrAbsenteNemotivate() + "," + x.getMedieGenerala() + "\n");
                    }
                    eleviWriter.flush();
                    eleviWriter.close();

                }
            }
        }
        catch(IOException exc){
            System.out.println("ERROR: Eroare la update!");
        }
    }
    public void updateNote() {
        String path = "./src/Catalog/";
        File[] files = new File(path).listFiles();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    FileWriter noteWriter;
                    for (Elev x : this.clase.get(file.getName()).getCatalog().getElevi()) {
                        noteWriter = new FileWriter(file.getAbsolutePath()+"\\Note\\"+x.getNume()+" "+x.getPrenume()+".csv");
                        noteWriter.append("Materie,Nota\n");
                        for(Materie m : x.getNote().keySet())
                            for(Integer nota : x.getNote().get(m)) {
                                //System.out.println(m.getDenumire() + " " + nota);
                                noteWriter.append(m.getDenumire() + "," + nota + "\n");
                            }
                        noteWriter.flush();
                        noteWriter.close();
                    }
                }
            }
        }
        catch(IOException exc){
            System.out.println(exc);
        }
    }
    public void update(){
        updateElevi();
        updateNote();

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
        System.out.println("11. Iesire");
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
            if (action==11) {
                update();
                break;
            }
        }

    }

}


