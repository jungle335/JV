package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Biblioteca b = new Biblioteca();
        Scanner sc = new Scanner(System.in);
        String spatiu = "                            ";

        while (true){
            System.out.println("Pentru interfata grafica, apasati 1");
            System.out.println("Pentru a afisa toti autorii, apasati 2");
            System.out.println("Pentru a adauga un autor, apasati 3");
            System.out.println("Pentru a modifica un autor, apasati 4");
            System.out.println("Pentru a sterge un autor, apasati 5");
            System.out.println("Pentru a afisa toate editurile, apasati 6");
            System.out.println("Pentru a adauga o editura, apasati 7");
            System.out.println("Pentru a modifica o editura, apasati 8");
            System.out.println("Pentru a sterge o editura, apasati 9");
            System.out.println("Pentru a afisa toate cartile, apasati 10");
            System.out.println("Pentru a adauga o carte, apasati 11");
            System.out.println("Pentru a modifica o carte, apasati 12");
            System.out.println("Pentru a sterge o carte, apasati 13");
            System.out.println("Pentru a modifica parola, apasati 14");
            System.out.println("Pentru a sterge contul, apasati 15");
            System.out.println("Pentru a inchide meniul, apasati 16");

            int op = sc.nextInt();
            switch (op){
                case 1:
                    CreeareCont cont = new CreeareCont();
                    Loguri.init("logs.csv");
                    break;

                case 2:
                    System.out.println("Autorii sunt: ");
                    String[][] autori = b.getAutori();
                    for (String[] s : autori){
                        for(String s1 : s){
                            System.out.print(s1 + spatiu.substring(0, spatiu.length() - s1.length()));
                        }
                        System.out.println();
                    }
                    break;

                case 3:
                    System.out.println("Nume autor:");
                    String nume = sc.next();
                    System.out.println("Prenume autor:");
                    String prenume = sc.next();
                    System.out.println("Tara autor:");
                    String tara = sc.next();
                    b.addAutor(new Autor(nume, prenume, tara));
                    break;

                case 4:
                    System.out.println("Id autor");
                    int id = sc.nextInt();
                    System.out.println("Camp pentru modificare autor:");
                    String camp = sc.next();
                    System.out.println("Valoarea pentru modificare autor:");
                    String val = sc.next();
                    b.modificaAutor(id, camp, val);
                    break;

                case 5:
                    System.out.println("Id autor");
                    b.deleteAutor(sc.nextInt());
                    break;

                case 6:
                    System.out.println("\nEditurile sunt: " + b.getEdituri());
                    break;

                case 7:
                    System.out.println("Denumire editura:");
                    b.addEdituri(new Editura(sc.next()));
                    break;

                case 8:
                    System.out.println("Id editura:");
                    int idEd = sc.nextInt();
                    System.out.println("Denumire noua:");
                    String denum = sc.next();
                    b.modificaEditura(idEd, denum);
                    break;

                case 9:
                    b.deleteEditura(sc.nextInt());
                    break;

                case 10:
                    System.out.println("Cartile sunt: ");
                    String[][] carti = b.getCarti();
                    for (String[] c : carti){
                        for(String c1 : c){
                            System.out.print(c1 + spatiu.substring(0, spatiu.length() - c1.length()));
                        }
                        System.out.println();
                    }
                    break;

                case 11:
                    System.out.println("Titlu:");
                    sc.nextLine();
                    String titlu = sc.nextLine();
                    System.out.println("Id autor:");
                    int idA = sc.nextInt();
                    System.out.println("Id Editura:");
                    int ide = sc.nextInt();
                    System.out.println("Categorie:");
                    String den = sc.next();
                    System.out.println("An Publicare:");
                    int anp = sc.nextInt();
                    System.out.println("Cantitate:");
                    int cant = sc.nextInt();
                    b.addCarte(new Carte(titlu, idA, ide, den, anp, cant));
                    break;

                case 12:
                    System.out.println("Id carte:");
                    int idC = sc.nextInt();
                    System.out.println("Camp pentru modificare:");
                    String f = sc.next();
                    System.out.println("Valoare noua:");
                    String x = sc.next();
                    b.modificaCarte(idC, f, String.valueOf(x));
                    break;

                case 13:
                    b.deleteCarte(sc.nextInt());
                    break;

                case 14:
                    System.out.println("Id utilizator:");
                    int uId = sc.nextInt();
                    System.out.println("Parola noua:");
                    String pass = sc.next();
                    b.modificareParola(uId, pass);
                    break;

                case 15:
                    System.out.println("Id utilizator:");
                    int uIdU = sc.nextInt();
                    b.deleteUtilizator(uIdU);
                    break;

                case 16:
                    System.exit(0);
                    break;
            }
        }

//        b.addCarte(new Carte("Invitatie la vals",2,1,"Fictiune",1936,15));
//        b.addCarte(new Carte("Cazul Magheru",2,2,"Fictiune",1930,12));
//        b.addCarte(new Carte("Batranul si marea",3,3,"Fictiune",1952,5));
//        b.addCarte(new Carte("Lupul de stepa",4,4,"Filosofie",1927,3));
//        b.addCarte(new Carte("Fiesta",3,3,"Beletristica",1926,2));
//        b.addCarte(new Carte("Lupul de mare",6,4,"Aventura",1904,3));
//        b.addCarte(new Carte("Colt alb",6,4,"Fictiune",1906,3));
//        b.addCarte(new Carte("Fratii Jderi",5,4,"Istorie",1935,3));
//        b.addCarte(new Carte("Mantia verde",7,4,"Fictiune",1916,30));


    }
}