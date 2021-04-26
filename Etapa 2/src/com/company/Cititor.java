package com.company;

import java.time.LocalDate;


public class Cititor extends Persoana{
    private LocalDate valabPermisCititor;
    private String parola;

    Cititor(){}

    Cititor(String nume, String prenume, String parola, LocalDate valabilitate){
        super(nume, prenume);
        this.parola = parola;
        this.valabPermisCititor = valabilitate;
    }

    public String getParola() { return parola; }

    public LocalDate getValabPermisCititor() { return valabPermisCititor; }

    @Override
    public String toString() { return nume + " " + prenume + " " + valabPermisCititor; }
}
