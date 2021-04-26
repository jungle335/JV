package com.company;

public class Editura {
    private static int idUrmator = 1;
    private final int idEditura;
    private String denumire;

    Editura(String denumire){
        idEditura = idUrmator;
        idUrmator++;
        this.denumire = denumire;
    }

    public int getIdEditura() { return idEditura; }
    public String getDenumire() { return denumire; }
    public void setDenumire(String denumire) { this.denumire = denumire; }

    @Override
    public String toString() {
        return idEditura + " " + denumire;
    }
}
