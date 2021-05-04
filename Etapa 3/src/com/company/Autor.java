package com.company;

public class Autor extends Persoana implements Comparable<Autor>{
    private static int idUrmator = 1;
    private final int idAutor;
    private String tara;

    Autor(String nume, String prenume){
        super(nume, prenume);
        idAutor = idUrmator;
        idUrmator++;
    }

    Autor(String nume, String prenume, String tara){
        super(nume, prenume);
        idAutor = idUrmator;
        idUrmator++;
        this.tara = tara;
    }

    public int getIdAutor() { return idAutor; }

    public String getTara() {
        return tara;
    }

    public int compareTo(Autor a){
        if (this.nume.equalsIgnoreCase(a.nume)){
            return this.prenume.compareToIgnoreCase(a.prenume);
        }
        return this.nume.compareToIgnoreCase(a.nume);
    }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }
}
