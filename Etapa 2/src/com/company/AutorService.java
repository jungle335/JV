package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static int obtineIdAutor(String nume, String prenume){
        for(Autor a : autori){
            if (Objects.equals(a.getNume(), nume) && Objects.equals(a.getPrenume(), prenume)){
                return a.getIdAutor();
            }
        }
        return 0;
    }

    public static List<String>getTitluri(int idAutor){
        List<String> lc = new ArrayList<>();
        Carte[] c = CarteService.getCarti();
        for(Carte ct : c){
            if (ct.getIdAutor() == idAutor){
                lc.add(ct.getTitlu());
            }
        }
        return lc;
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
