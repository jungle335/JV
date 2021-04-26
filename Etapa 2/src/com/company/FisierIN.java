package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class FisierIN <T> {
    final Class<T> tip;
    private String numeFis, separator;

    public FisierIN(final Class<T> tipObiect, String numeFisier, String separa){
        System.out.println(tipObiect.getSimpleName());
        this.tip = tipObiect;
        this.numeFis = numeFisier;
        this.separator = separa;
    }

    public String[] getInfo(int id){
        try {
            BufferedReader br = new BufferedReader(new FileReader(numeFis));
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
            FileWriter fw = new FileWriter(numeFis);
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
