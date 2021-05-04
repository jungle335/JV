package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Biblioteca {
    private final Connection con;

    public Biblioteca() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?user=root&password=Password1");
    }


    void addCont(Cititor c){
        String [] data = {c.getNume(), c.getPrenume(), c.getParola(), String.valueOf(c.getValabPermisCititor())};
        try{
            String sql = "insert into conturi (nume, prenume, parola, valabPermis) values (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            for(int i = 1; i <= 4; i++){
                st.setString(i, data[i - 1]);
            }
            try{
                st.executeUpdate();
            }catch (Exception e){
                System.out.println("Cititorul nu a putut fi adaugat!");
            } finally {
              st.close();
              con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUserId(String nume, String prenume){
        try{
            ResultSet rez = con.prepareStatement("select * from conturi").executeQuery();

            while(rez.next()){
                if (Objects.equals(nume, rez.getString(2)) && Objects.equals(prenume, rez.getString(3))) {
                    return rez.getInt(1);
                }
            }
        } catch (Exception e){
            return -1;
        }
        return -1;
    }

    public Map<Boolean, Cititor> verificaCont(String utilizator, String parola) { ;
        try {
            Map<Boolean, Cititor> ans = new HashMap<>() {};
            ResultSet rez = con.prepareStatement("select * from conturi").executeQuery();

            while(rez.next()){
                String nume = rez.getString(2), prenume = rez.getString(3);
                String parol = rez.getString(4), valab = rez.getString(5);
                String user = nume + prenume;
                if (Objects.equals(utilizator, user)) {
                    ans.put(Objects.equals(parola, parol), new Cititor(nume, prenume, parol, LocalDate.parse(valab)));
                    return ans;
                }
            }
            return new HashMap<>(){{put(false, new Cititor());}};
        } catch (Exception e) {
            return new HashMap<>(){{put(false, new Cititor());}};
        }
    }

    void addAutor(Autor a){
        try{
            String[] data = {a.getNume(), a.getPrenume(), a.getTara()};
            String sql = "insert into autori (nume, prenume, tara) values (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            for(int i = 1; i <= 3; i++){
                st.setString(i, data[i - 1]);
            }
            try{
                st.executeUpdate();
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Autorul nu a putut fi adaugat!");
            } finally {
                st.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    void addCarte(Carte c){
        try{
            String [] data = {c.getTitlu(), String.valueOf(c.getIdAutor()),
                        String.valueOf(c.getIdEditura()), c.getCategorie(), String.valueOf(c.getAnPublicare()),
                        String.valueOf(c.getNrBucati())};
            String sql = "insert into carti (titlu, idAutor, idEditura, categorie, anPublicare, nrBucati) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            for(int i = 1; i <= 6; i++){
                st.setString(i, data[i - 1]);
            }
            try{
                st.executeUpdate();
            }catch (Exception e){
                System.out.println("Cartea nu a putut fi adaugata!");
            } finally {
                st.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }

    public void addEdituri(Editura ed){
        try{
            String sql = "insert into edituri(denumire) values (?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ed.getDenumire());
            try{
                st.executeUpdate();
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Editura nu a putut fi adaugata!");
            } finally {
                st.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String [][] getAutori() {
        try {
            ResultSet rez = con.prepareStatement("select count(*) from autori").executeQuery();
            String[][] autori;

            try {
                rez.next();
                int nr_autori = rez.getInt(1), i = 0;
                autori = new String[nr_autori][nr_autori];

                rez = con.prepareStatement("select * from autori").executeQuery();
                while (rez.next()) {
                    String[] obj = {rez.getString(2), rez.getString(3), rez.getString(4)};
                    autori[i++] = obj;
                }
                return autori;
            }
            catch(Exception e){
                    return new String[][]{{"", "", ""}};
            }
        } catch (Exception e) {
            return new String[][]{{"", "", ""}};
        }
    }

    public Autor getInfoAutor(int id_autor){
        try{
            String sql = String.format("%s = %d", "select * from autori where id", id_autor);
            ResultSet rez = con.prepareStatement(sql).executeQuery();
            rez.next();
            return new Autor(rez.getString(2), rez.getString(3), rez.getString(4));
        } catch (Exception e){
            return new Autor("", "", "");
        }
    }

    public String getTitluCarte(int id_carte){
        try{
            String sql = String.format("%s = %d", "select * from carti where id", id_carte);
            ResultSet rez = con.prepareStatement(sql).executeQuery();
            rez.next();
            return rez.getString(2);
        } catch (Exception e){
            return "";
        }
    }

    public String [][] getCarti(){
        try{
            ResultSet rez = con.prepareStatement("select count(*) from carti").executeQuery();
            String[][] carti;
            try {
                rez.next();
                int nr_carti = rez.getInt(1), i = 0;
                carti = new String[nr_carti][nr_carti];

                rez = con.prepareStatement("select * from carti").executeQuery();
                while (rez.next()) {
                    Autor l = getInfoAutor(rez.getInt(3));
                    String[] obj = {String.valueOf(rez.getInt(1)), rez.getString(2), l.getNume(), l.getPrenume(), rez.getString(5)};
                    carti[i++] = obj;
                }
                return carti;
            }
            catch(Exception e){
                return new String[][]{{"", "", ""}};
            }
        } catch (Exception e){
            return new String[][]{{"", "", ""}};
        }
    }
}
