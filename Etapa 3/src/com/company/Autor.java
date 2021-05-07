package com.company;

public class Autor extends Persoana implements Comparable<Autor>{
    private final int idAutor = 0;
    private String tara;

    Autor(String nume, String prenume){
        super(nume, prenume);
    }

    Autor(String nume, String prenume, String tara){
        super(nume, prenume);
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
