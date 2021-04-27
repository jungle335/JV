package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class FisierIN {
    private String numeFisier, separator;

    public FisierIN(String nume, String separator){
        this.numeFisier = nume;
        this.separator = separator;
    }

    public String[] getInfo(int id){
        try {
            BufferedReader br = new BufferedReader(new FileReader(numeFisier));
            String row;
            while ((row = br.readLine()) != null) {
                String[] data = row.split(separator);
                if (Integer.parseInt(data[0]) == id) {
                    return data;
                }
            }
            br.close();
        } catch (Exception e){
            return "".split("");
        }
        return "".split("");
    }

    public void writeInFile(List <String> loc){
        try{
            FileWriter fw = new FileWriter(numeFisier);
            for(String s : loc){
                fw.append(String.join(",", s));
                fw.append("\n");
            }
            fw.close();
        } catch (Exception e){
            return;
        };
    }

    public void writeInFile(List <String> loc, boolean flag){
        try{
            FileWriter fw = new FileWriter(numeFisier, flag);
            for(String s : loc){
                fw.append(String.join(",", s));
                fw.append("\n");
            }
            fw.close();
        } catch (Exception e){
            return;
        };
    }
}
