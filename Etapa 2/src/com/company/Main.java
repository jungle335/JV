package com.company;

import javax.swing.plaf.LabelUI;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
         //CreeareCont cont = new CreeareCont();
         //Loguri.init("logs.csv");
        AutorService.adaugaAutor(new Autor("Mihai", "Eminescu", "Romania"));
        AutorService.adaugaAutor(new Autor("Mihail", "Drumes", "Romania"));
        AutorService.adaugaAutor(new Autor("Karl", "May", "Germania"));
        AutorService.adaugaAutor(new Autor("Ernest", "Hemingway", "Anglia"));
        AutorService.adaugaAutor(new Autor("Hermann", "Hesse", "Germania"));
        AutorService.adaugaAutor(new Autor("Mihail", "Sadoveanu", "Romania"));
        AutorService.adaugaAutor(new Autor("Jack", "London", "S.U.A."));
        AutorService.adaugaAutor(new Autor("John", "Buchan", "Anglia"));
        AutorService.afis();
        AutorService.stergeAutor(1);
        AutorService.stergeAutor(3);
        System.out.println("----");
        AutorService.afis();
        System.out.println("-----");
        AutorService.sorteaza();
        AutorService.afis();

        System.out.println("\n--------------\n");
        CarteService.adaugaCarti(new Carte("Invitatie la vals",2,1,"Fictiune",1936,15));
        CarteService.adaugaCarti(new Carte("Cazul Magheru",2,2,"Fictiune",1930,12));
        CarteService.adaugaCarti(new Carte("Batranul si marea",4,3,"Fictiune",1952,5));
        CarteService.adaugaCarti(new Carte("Old Surehand",3,3,"Aventura",1965,7 ));
        CarteService.adaugaCarti(new Carte("Lupul de stepa",5,4,"Filosofie",1927,3));
        CarteService.adaugaCarti(new Carte("Fiesta",4,3,"Beletristica",1926,2));
        CarteService.adaugaCarti(new Carte("Lupul de mare",7,4,"Aventura",1904,3));
        CarteService.adaugaCarti(new Carte("Colt alb",7,4,"Fictiune",1906,3));
        CarteService.adaugaCarti(new Carte("Mantia verde",8,3,"Fictiune",1916,2));
        CarteService.afis();
        System.out.println("------");
        CarteService.stergeCarti(3);
        CarteService.stergeCarti(4);
        CarteService.afis();
        System.out.println("-------");
        CarteService.sorteaza();
        CarteService.afis();
        System.out.println();
        System.out.println("Cartile care fac din categoria fictiune sunt");
        for(Carte c : CarteService.toateGen("Fictiune")) {
            System.out.println(c);
        }
        System.out.println();

        int id = AutorService.obtineIdAutor("Mihail", "Drumes");
        if (id != 0){
            System.out.println("Cartile scrise de Mihail Drumes sunt");
            List <Integer> idCarti = AutorService.autoriSiCarti(id).get(id);
            for(int i = 0; i < idCarti.size(); i++){
                System.out.println("    " + CarteService.obtineCarte(idCarti.get(i)).getTitlu());
            }
        }else{
            System.out.println("Nu avem carti scrise de autorul dat");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nIntroduceti id-ul cartii");
        int idCarte = sc.nextInt();
        Carte c = CarteService.obtineCarte(idCarte);

        if(c == null){
            System.out.println("Id-ul cartii este invalid!!!");
        } else{
            System.out.println("\nCartea cu id-ul " + idCarte + " este: " + c);
        }
        System.out.println();

        for(Map.Entry<Integer, List<Integer>> e: AutorService.autoriSiCarti().entrySet()){
            Autor a = AutorService.obtineAutor(e.getKey());
            System.out.print(a.getNume() + " " + a.getPrenume() + ": ");
            for(Integer s : e.getValue()){
                Carte cl = CarteService.obtineCarte(s);
                System.out.print(cl.getTitlu() + " ");
            }
            System.out.println();
        }
    }
}