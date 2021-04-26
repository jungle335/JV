package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;


public class Biblioteca {
    private Map<Integer, Editura> edituri = new HashMap<>(){{
        put(1, new Editura("Art"));
        put(2, new Editura("Arthur"));
        put(3, new Editura("Polirom"));
        put(4, new Editura("Rao"));
    }};

    public Map<Integer, Editura> getEdituri() { return edituri; }

    void addCont(Cititor c){
        String [] data = {c.getNume(), c.getPrenume(), c.getParola(), String.valueOf(c.getValabPermisCititor())};
        try {
            FileWriter fw = new FileWriter("conexiuni.csv", true);
            fw.append("\n");
            fw.append(String.join(",", data));
            fw.close();
        } catch (Exception e) {
            System.out.println("Eroare neprevazuta...");
            System.exit(0);
        }
    }

    public Map<Boolean, Cititor> verificaCont(String utilizator, String parola) { ;
        try {
            Map<Boolean, Cititor> rez = new HashMap<>() {};
            BufferedReader conturi = new BufferedReader(new FileReader("conexiuni.csv"));
            conturi.readLine();
            String row;
            while ((row = conturi.readLine()) != null) {
                String[] info = row.split(",");
                String user = info[0] + info[1];
                if (Objects.equals(utilizator, user)) {
                    conturi.close();
                    rez.put(Objects.equals(parola, info[2]), new Cititor(info[0], info[1], info[2], LocalDate.parse(info[3])));
                    return rez;
                }
            }
            conturi.close();
            return new HashMap<>(){{put(false, new Cititor());}};
        } catch (Exception e) {
            return new HashMap<>(){{put(false, new Cititor());}};
        }
    }

    void addAutor(Autor a){
        try{
            String [] data = {String.valueOf(a.getIdAutor()), a.getNume(), a.getPrenume()};
            FileWriter fw = new FileWriter("autori.csv", true);
            fw.append("\n");
            fw.append(String.join(",", data));
            fw.close();
        }
        catch (Exception e){
            return;
        }
    }

    void addCarte(Carte c){
        try{
            String [] data = {c.getTitlu(), String.valueOf(c.getIdAutor()), String.valueOf(c.getIdEditura()),
                    c.getCategorie(), String.valueOf(c.getAnPublicare()), String.valueOf(c.getNrBucati())};
            FileWriter fw = new FileWriter("carti.csv", true);
            fw.append("\n");
            fw.append(String.join(",", data));
            fw.close();
        } catch (Exception e) {
            return;
        }
    }

    public String [][] getAutori() {
        try {
            BufferedReader autori = new BufferedReader(new FileReader("autori.csv"));
            Path path = Paths.get("autori.csv");
            long nr_autori = Files.lines(path).count() - 1;
            String [][] info = new String[(int) nr_autori][(int) nr_autori];

            autori.readLine();
            String row;
            int i = 0;
            while ((row = autori.readLine()) != null) {
                String[] infoo = row.split(",");
                String [] obj = {infoo[1], infoo[2]};
                info[i++] = obj;
            }
            return info;
        } catch (Exception e) {
            return new String[][]{{"", "", ""}};
        }
    }

    public Autor getInfoAutor(int id_autor){
        try {
            BufferedReader br = new BufferedReader(new FileReader("autori.csv"));
            String row;
            while((row = br.readLine()) != null){
                String [] data = row.split(",");
                if (Integer.parseInt(data[0]) == id_autor){
                    return new Autor(data[1], data[2], data[3]);
                }
            }
            br.close();
        } catch (Exception e){
            return new Autor("", "", "");
        }
        return new Autor("", "", "");
    }

    public String [][] getCarti(){
        try {
            BufferedReader carti = new BufferedReader(new FileReader("carti.csv"));
            Path path = Paths.get("carti.csv");
            long nr_carti = Files.lines(path).count();
            String [][] info = new String[(int) nr_carti][(int) nr_carti];
            String row;
            int i = 0;
            while ((row = carti.readLine()) != null) {
                String[] data = row.split(",");
                Autor l = getInfoAutor(Integer.parseInt(data[2]));
                String [] obj = {data[0], data[1], l.getNume(), l.getPrenume(), data[4]};
                info[i++] = obj;
            }
            return info;
        } catch (Exception e) {
            return new String[][]{{"", "", ""}};
        }
    }
}
