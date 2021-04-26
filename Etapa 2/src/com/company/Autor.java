package com.company;

public class Autor extends Persoana{
    private static int idUrmator = 1;
    private final int idAutor;
    private final String tara;

    Autor(String nume, String prenume, String tara){
        super(nume, prenume);
        idAutor = idUrmator;
        idUrmator++;
        this.tara = tara;
    }

    public int getIdAutor() { return idAutor; }
    public String getTara() { return tara; }

    @Override
    public String toString() {
        return nume + " " + prenume + " " + tara;
    }
}
