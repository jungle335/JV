package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private Map<Integer, Autor> autori = new HashMap<>(){{
        put(1, new Autor("Mihai", "Eminescu", "Romania"));
        put(2, new Autor("Mihail", "Drumes", "Romania"));
        put(3, new Autor("Karl", "May", "Germania"));
        put(4, new Autor("Ernest", "Hemingway", "Anglia"));
        put(5, new Autor("Hermann", "Hesse", "Germania"));
    }};
    private Map<Integer, Carte> carti = new HashMap<>(){{
        put(1, new Carte("Invitatie la vals", 2, 1, "Fictiune", 2020, 15));
        put(2, new Carte("Cazul Magheru", 2, 2, "Fictiune", 2019, 12));
        put(3, new Carte("Batranul si marea", 4, 3, "Fictiune", 2020, 5));
        put(4, new Carte("Old Surehand", 3, 3, "Aventura", 2018, 7));
        put(5, new Carte("Lupul de stepa", 5, 4, "Filosofie", 2017, 3));
        put(6, new Carte("Fiesta", 4, 3, "Beletristica", 2016, 2));
    }};
    private Map<Integer, Editura> edituri = new HashMap<>(){{
        put(1, new Editura("Art"));
        put(2, new Editura("Arthur"));
        put(3, new Editura("Polirom"));
        put(4, new Editura("Rao"));
    }};
    private List<Cititor> conturi = new ArrayList<>();

    private static final Biblioteca app = new Biblioteca();
    public static Biblioteca openB() { return app; }
    public Map<Integer, Autor> getAutori() {
        return autori;
    }
    public Map<Integer, Carte> getCarti() {
        return carti;
    }
    public Map<Integer, Editura> getEdituri() { return edituri; }

    void addCont(Cititor c){
        conturi.add(c);
    }
    public Map<Boolean, Cititor>  verificaCont(String utilizator, String parola){
        Map<Boolean, Cititor> local = new HashMap<>();
        for (Cititor c : conturi){
            String createUtil = c.getNume() + c.getPrenume() + c.getIdCititor();
            if(createUtil.equals(utilizator)){
                if(c.getParola().equals(parola)){
                    local.put(true, c);
                    return local;
                }
            }
        }
        local.put(false, new Cititor());
        return local;
    }
    void addAutor(Autor a){
        autori.put(a.getIdAutor(), a);
    }
    void addCarte(Carte c){
        carti.put(c.getIdCarte(), c);
    }
}
