package com.company;

public class Cititor extends Persoana{
    private static int idUrmator = 1;
    private final int idCititor, valabilitatePermisCititor = 3;
    private String parola;

    Cititor(){
        this.idCititor = idUrmator;
        idUrmator++;
    }
    Cititor(String nume, String prenume, String parola){
        super(nume, prenume);
        this.idCititor = idUrmator;
        idUrmator++;
        this.parola = parola;
    }

    public int getIdCititor() { return idCititor; }
    public String getParola() { return parola; }
    public int getValabilitatePermisCititor() { return valabilitatePermisCititor; }

    @Override
    public String toString() {
        return "Cititor{" +
                "idCititor=" + idCititor +
                ", valabilitatePermisCititor=" + valabilitatePermisCititor +
                ", parola='" + parola + '\'' +
                '}';
    }
}
