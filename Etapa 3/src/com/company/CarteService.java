package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CarteService {
    private static int dim;
    private static Carte[] carti = new Carte[4];

    public static Carte[] getCarti() {
        return carti;
    }

    public static void adaugaCarti(Carte c){
        if (carti.length == dim){
            dim++;
            Carte[] nou = new Carte[dim];
            int j = 0;
            for(Carte ct : carti){
                nou[j++] = ct;
            }
            nou[j] = c;
            carti = nou.clone();
        }
        else{
            carti[dim++] = c;
        }
    }

    public static void stergeCarti(int id){
        try{
            int poz = -1;
            for(int i = 0; i < dim; i++){
                if (carti[i].getIdCarte() == id){
                    poz = i;
                }
            }
            if (poz == -1){
                throw new Exception("Id-ul este invalid!!!");
            }
            int cdim = dim, j = 0;
            dim--;
            Carte[] nou = new Carte[dim];
            for(int i = 0; i < cdim; i++){
                if (i != poz){
                    nou[j++] = carti[i];
                }
            }
            carti = nou.clone();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Carte obtineCarte(int id){
        for(Carte c : carti){
            if (c.getIdCarte() == id){
                return c;
            }
        }
        return null;
    }

    public static List<Carte> toateGen(String categorie){
        List<Carte> categ = new ArrayList<>();
        for(Carte c : carti){
            if (Objects.equals(c.getCategorie(), categorie)){
                categ.add(c);
            }
        }
        return categ;
    }

    public static void sorteaza(){
        Arrays.sort(carti);
    }

    public static void afis(){
        for(int i = 0; i < dim; i++){
            System.out.println(carti[i]);
        }
    }
}
