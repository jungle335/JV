package com.company;

import java.util.*;

public class AutorService{
    private static int dim;
    private static Autor[] autori = new Autor[4];

    public static void adaugaAutor(Autor a){
        if (autori.length == dim){
            dim++;
            Autor[] nou = new Autor[dim];
            int j = 0;
            for(Autor at : autori){
                nou[j++] = at;
            }
            nou[j] = a;
            autori = nou.clone();
        }
        else{
            autori[dim++] = a;
        }
    }

    public static void stergeAutor(int id){
        try{
            int poz = -1;
            for(int i = 0; i < dim; i++){
                if (autori[i].getIdAutor() == id){
                    poz = i;
                }
            }
            if (poz == -1){
                throw new Exception("Id-ul este invalid!!!");
            }
            int cdim = dim, j = 0;
            dim--;
            Autor[] nou = new Autor[dim];
            for(int i = 0; i < cdim; i++){
                if (i != poz){
                    nou[j++] = autori[i];
                }
            }
            autori = nou.clone();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Autor obtineAutor (int id){
        for(Autor a : autori){
            if (a.getIdAutor() == id){
                return a;
            }
        }
        return null;
    }

    public static int obtineIdAutor(String nume, String prenume){
        for(Autor a : autori){
            if (Objects.equals(a.getNume(), nume) && Objects.equals(a.getPrenume(), prenume)){
                return a.getIdAutor();
            }
        }
        return 0;
    }

    public static Map<Integer, List<Integer>> autoriSiCarti(){
        Carte[] c = CarteService.getCarti();
        Map<Integer, List<Integer>> d = new HashMap<>();

        for(Carte ct : c){
            d.put(ct.getIdAutor(), new ArrayList<>());
        }
        for(Carte ct : c){
            List<Integer> l = d.get(ct.getIdAutor());
            l.add(ct.getIdCarte());
            d.put(ct.getIdAutor(), l);
        }
        return d;
    }

    public static Map<Integer, List<Integer>> autoriSiCarti(int idAutor){
        Map<Integer, List<Integer>> l = autoriSiCarti();
        return new HashMap<>(){{ put(idAutor, l.get(idAutor)); }};
    }

    public static void sorteaza(){
        Arrays.sort(autori);
    }

    public static void afis(){
        for(int i = 0; i < dim; i++){
            System.out.println(autori[i]);
        }
    }
}
