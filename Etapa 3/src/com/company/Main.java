package com.company;

public class Main {

    public static void main(String[] args) throws Exception{
        //CreeareCont cont = new CreeareCont();
        //Loguri.init("logs.csv");
          Biblioteca b = new Biblioteca();
//        b.addAutor(new Autor("Mihai", "Eminescu", "Romania"));
//        b.addAutor(new Autor("Mihail", "Drumes", "Romania"));
//        b.addAutor(new Autor("Ernest", "Hemingway", "Anglia"));
//        b.addAutor(new Autor("Hermann", "Hesse", "Germania"));
//        b.addAutor(new Autor("Mihail", "Sadoveanu", "Romania"));
//        b.addAutor(new Autor("Jack", "London", "S.U.A."));
//        b.addAutor(new Autor("John", "Buchan", "Anglia"));
        System.out.println("Autorii sunt: ");
        String[][] autori = b.getAutori();
        for (String[] s : autori){
            for(String s1 : s){
                System.out.print("     " + s1 + " ");
            }
            System.out.println();
        }
        //b.deleteAutor(7);
        //b.modificaAutor(6, "tara", "Anglia");
//        b.addEdituri(new Editura("Art"));
//        b.addEdituri(new Editura("Arthur"));
//        b.addEdituri(new Editura("Polirom"));
//        b.addEdituri(new Editura("Rao"));
        System.out.println("\nEditurile sunt: " + b.getEdituri());
        //b.modificaEditura(3, "Polirom");
        //b.deleteEditura(1);
//        b.addCarte(new Carte("Invitatie la vals",2,1,"Fictiune",1936,15));
//        b.addCarte(new Carte("Cazul Magheru",2,2,"Fictiune",1930,12));
//        b.addCarte(new Carte("Batranul si marea",3,3,"Fictiune",1952,5));
//        b.addCarte(new Carte("Lupul de stepa",4,4,"Filosofie",1927,3));
//        b.addCarte(new Carte("Fiesta",3,3,"Beletristica",1926,2));
//        b.addCarte(new Carte("Lupul de mare",6,4,"Aventura",1904,3));
//        b.addCarte(new Carte("Colt alb",6,4,"Fictiune",1906,3));
//        b.addCarte(new Carte("Fratii Jderi",5,4,"Roman Istoric",1935,3));
//        b.addCarte(new Carte("Mantia verde",7,4,"Fictiune",1916,30));
        System.out.println("Cartile sunt: ");
        String[][] carti = b.getCarti();
        for (String[] c : carti){
            for(String c1 : c){
                System.out.print("     " + c1 + " ");
            }
            System.out.println();
        }
        //b.modificaCarte(8, "nrBucati", String.valueOf(30));
       // b.deleteCarte(8);
    }
}