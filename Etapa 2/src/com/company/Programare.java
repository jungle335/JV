package com.company;

import java.util.Date;

public class Programare {
    private static int idUrmator = 1;
    private final int idProgramare, nrLocuri;
    private Date dataProgramare;

    Programare(){
        this.idProgramare = idUrmator;
        idUrmator++;
        nrLocuri = 50;
    }
}
