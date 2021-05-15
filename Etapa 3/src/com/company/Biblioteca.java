package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Biblioteca {
    private static Connection con = null;
    private static String[] tabele;
    private static DatabaseMetaData query;
    private final Map<String, String> at = new HashMap<>(){{
        put("conturi", "create table conturi " +
                        "(id int not null auto_increment, " +
                        "utilizator varchar(50) not null, " +
                        "nume varchar(10) not null, " +
                        "prenume varchar(35) not null, " +
                        "parola varchar(50) not null, " +
                        "valabPermis varchar(15) not null, " +
                        "primary key (id))");
        put("edituri", "create table edituri " +
                        "(id int not null auto_increment, " +
                        "denumire varchar(15) not null, " +
                        "primary key (id))");
        put("autori", "create table autori " +
                        "(id int not null auto_increment, " +
                        "nume varchar(10) not null, " +
                        "prenume varchar(35) not null, " +
                        "pseudonim varchar(50), " +
                        "tara varchar(50) not null, " +
                        "primary key (id))");
        put("carti", "create table carti " +
                        "(id int not null auto_increment, " +
                        "titlu varchar(30) not null, " +
                        "idAutor int not null, " +
                        "idEditura int not null, " +
                        "categorie varchar(15) not null, " +
                        "anPublicare int(4) not null, " +
                        "nrBucati int not null, " +
                        "primary key (id), " +
                        "foreign key (idAutor) references autori(id), " +
                        "foreign key (idEditura) references edituri(id))");
        put("prefUtil", "create table prefUtil " +
                            "(id int not null, " +
                            "idCarte int not null, " +
                            "primary key (id, idCarte), " +
                            "foreign key (id) references conturi(id), " +
                            "foreign key (idCarte) references carti(id))");
    }};

    static {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?user=root&password=Password1");
            tabele = new String[]{"conturi", "edituri", "autori", "carti", "prefUtil"};
            query = con.getMetaData();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Biblioteca() throws SQLException {
        for (String tabel : tabele){
            ResultSet rs = query.getTables(null, null, tabel, null);
            if (!rs.next()){
                Statement stmt = con.createStatement();
                stmt.executeUpdate(at.get(tabel));
            }
        }
    }

    int verificaUtilizator(String util){
        try{
            ResultSet rez = con.prepareStatement("select * from conturi").executeQuery();

            while (rez.next()) {
                if (Objects.equals(util, rez.getString(2))) {
                    return 1;
                }
            }
            return 0;
        }catch (SQLException e){
            return -1;
        }
    }

    void addCont(Cititor c){
        String [] data = {c.getPseudonim(), c.getNume(), c.getPrenume(), c.getParola(), String.valueOf(c.getValabPermisCititor())};
        try{
            String sql = "insert into conturi (utilizator, nume, prenume, parola, valabPermis) values (?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            for(int i = 1; i <= 5; i++){
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

    int modificareParola(String idUtil, String parolaNoua){
        try{
            PreparedStatement st = con.prepareStatement("update conturi set parola = ? where utilizator = ?");
            st.setString(1, parolaNoua);
            st.setString(2, idUtil);
            if (st.executeUpdate() > 0)
                return 1;
            return -1;
        }catch (SQLException e){
            return -1;
        }
    }

    int deleteUtilizator(String util, String parola){
        try{
            PreparedStatement du = con.prepareStatement("select * from conturi where utilizator = ?");
            du.setString(1, util);
            ResultSet rez = du.executeQuery();

            if (rez.next()) {
                if (Objects.equals(parola, rez.getString(5))) {
                    PreparedStatement duu = con.prepareStatement("delete from conturi where utilizator = ?");
                    duu.setString(1, util);
                    return duu.executeUpdate();
                }
            }
            return -1;
        }catch (Exception e){
            return -1;
        }
    }

    public int getUserId(String util){
        try{
            ResultSet rez = con.prepareStatement("select * from conturi").executeQuery();

            while(rez.next()){
                if (Objects.equals(util, rez.getString(2))) {
                    return rez.getInt(1);
                }
            }
            return 0;
        } catch (Exception e){
            return -1;
        }
    }

    public Map<Boolean, Cititor> verificaCont(String utilizator, String parola) {
        try {
            Map<Boolean, Cititor> ans = new HashMap<>() {};
            ResultSet rez = con.prepareStatement("select * from conturi").executeQuery();

            while(rez.next()){
                String nickname = rez.getString(2), nume = rez.getString(3), prenume = rez.getString(4);
                String parol = rez.getString(5), valab = rez.getString(6);
                if (Objects.equals(nickname, utilizator)) {
                    ans.put(Objects.equals(parola, parol), new Cititor(nickname, nume, prenume, parol, LocalDate.parse(valab)));
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void modificaAutor(int id_autor, String flag, String ... nou){
        try{
            PreparedStatement st = con.prepareStatement("update autori set " + flag + "= ? where id = ?");
            st.setString(1, nou[0]);
            st.setInt(2, id_autor);
            st.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void deleteAutor(int id_autor){
        try{
            Statement st = con.createStatement();
            String sql = String.format("%s = %d", "delete from autori where id ", id_autor);
            st.executeUpdate(sql);
        }catch (Exception ignored){ }
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }

    void modificaCarte(int id_carte, String flag, String ... nou){
        try{
            PreparedStatement st = con.prepareStatement("update carti set " + flag + " = ? where id = ?");
            st.setString(1, nou[0]);
            st.setInt(2, id_carte);
            st.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void deleteCarte(int id_carte){
        try{
            Statement st = con.createStatement();
            String sql = String.format("%s = %d", "delete from carti where id ", id_carte);
            st.executeUpdate(sql);
        }catch (Exception ignored){ }
    }

    public void addEdituri(Editura ed){
        try{
            String sql = "insert into edituri(denumire) values (?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ed.getDenumire());
            try{
                st.executeUpdate();
            }catch (Exception e){
                System.out.println("Editura nu a putut fi adaugata!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void modificaEditura(int id_editura, String ... nou){
        try{
            PreparedStatement st = con.prepareStatement("update edituri set denumire = ? where id = ?");
            st.setString(1, nou[0]);
            st.setInt(2, id_editura);
            st.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void deleteEditura(int id_editura){
        try{
            Statement st = con.createStatement();
            String sql = String.format("%s = %d", "delete from edituri where id", id_editura);
            st.executeUpdate(sql);
        }catch (Exception e){
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

    public List<String> getEdituri(){
        List<String> edituri = new ArrayList<>();
        try{
            ResultSet rez = con.prepareStatement("select * from edituri").executeQuery();
            while (rez.next()) {
               edituri.add(rez.getString(2));
            }
            return edituri;
        }catch (Exception ignored){}
        return edituri;
    }
}
