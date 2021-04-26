package com.company;

public class Carte {
    private static int idUrmator = 1;
    private final int idCarte, idAutor, idEditura, anPublicare, nrBucati;
    private final String titlu, categorie;

    Carte(){
        this.idCarte = 0;
        this.idAutor = 0;
        this.idEditura = 0;
        this.anPublicare = 0;
        this.nrBucati = 0;
        this.titlu = "";
        this.categorie = "";
    }

    Carte(String titlu, int idAutor, int idEditura, String categorie, int anPublicare, int nrBucati){
        idCarte = idUrmator;
        idUrmator++;
        this.titlu = titlu;
        this.idAutor = idAutor;
        this.idEditura = idEditura;
        this.categorie = categorie;
        this.anPublicare = anPublicare;
        this.nrBucati  = nrBucati;
    }

    public int getIdCarte() { return idCarte; }
    public int getIdAutor() { return idAutor; }
    public int getIdEditura() { return idEditura; }
    public String getTitlu() { return titlu; }
    public String getCategorie() { return categorie; }
    public int getAnPublicare() { return anPublicare; }
    public int getNrBucati() { return nrBucati; }

    @Override
    public String toString() {
        return idCarte + " " + idAutor + " " + idEditura + " " + titlu;
    }
}