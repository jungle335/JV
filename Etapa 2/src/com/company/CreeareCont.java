package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class CreeareCont implements ActionListener {
    private JFrame frame = new JFrame("BibleotecaApp");
    private List<Cititor> logged = new ArrayList<>();
    private JLabel nume = new JLabel("Nume: "), prenume = new JLabel("Prenume: "), abon = new JLabel();
    private JLabel parola = new JLabel("Parola: ");
    private JTextField numeUtilizator = new JTextField(), prenumeUtilizator = new JTextField(), searchBar = new JTextField();
    private JPasswordField parolaUtilizator = new JPasswordField();
    private JButton register = new JButton("Creare cont "), afis = new JButton("Autori");
    private JButton wishList = new JButton("Carti"), contNou = new JButton("Creeaza");
    private JButton cont = new JButton("Acces cont");
    private final Object[] coloane = { "Nume", "Prenume"}, col = {"Id", "Titlu", "Autor"}, coloanee = { "Id", "Titlu", "Nume", "Prenume", "Categorie"};
    private Biblioteca b = new Biblioteca();
    private Loguri log = new Loguri();
    DefaultTableModel model = new DefaultTableModel(umpleJtable(b.getAutori()), coloane);
    JTable tabel = new JTable(model);
    JScrollPane sp = new JScrollPane(tabel);
    private boolean flag = false;

    public CreeareCont(){
        parolaUtilizator.setEchoChar('*');
        register.setBounds(250, 90, 115, 30);
        afis.setBounds(100, 90, 115, 30);
        wishList.setBounds(400, 90, 115, 30);
        contNou.setBounds(400, 450, 115, 30);
        cont.setBounds(100, 450, 115, 30);
        abon.setBounds(30, 20, 200, 50);
        register.addActionListener(this);
        afis.addActionListener(this);
        wishList.addActionListener(this);
        cont.addActionListener(this);
        contNou.addActionListener(this);
        tabel.setEnabled(false);
        frame.add(abon);
        frame.add(nume);
        frame.add(prenume);
        frame.add(parola);
        frame.add(searchBar);
        frame.add(numeUtilizator);
        frame.add(prenumeUtilizator);
        frame.add(parolaUtilizator);
        frame.add(register);
        frame.add(afis);
        frame.add(wishList);
        frame.add(cont);
        frame.add(contNou);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,540);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == register) {
            nume.setBounds(125, 170, 85, 30);
            prenume.setBounds(125, 215, 85, 30);
            parola.setBounds(125, 260, 85, 30);
            numeUtilizator.setBounds(250, 170, 170, 30);
            prenumeUtilizator.setBounds(250, 215, 170, 30);
            parolaUtilizator.setBounds(250, 260, 170, 30);
        }

        if(e.getSource() == contNou) {
            nume.setVisible(false);
            prenume.setVisible(false);
            abon.setVisible(false);
            parola.setVisible(false);
            numeUtilizator.setVisible(false);
            prenumeUtilizator.setVisible(false);
            parolaUtilizator.setVisible(false);

            String nume = numeUtilizator.getText(), prenume = prenumeUtilizator.getText();
            String parola = new String(parolaUtilizator.getPassword());
            if (nume.length() > 0 && prenume.length() > 0 && parola.length() > 0) {
                Cititor cititor = new Cititor(nume, prenume, parola, LocalDate.now().plusMonths(3));
                JOptionPane.showMessageDialog(frame, "Contul tau a fost creat.", "Notificare", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(frame, "Numele de utilizator este: " + nume + prenume, "Notificare", JOptionPane.INFORMATION_MESSAGE);
                b.addCont(cititor);
                log.catchLogs("I", "Cont creat");
            }
        }

        if (e.getSource() == cont) { openLogWindow();}

        if(e.getSource() == afis) {
            if(flag){
                if(!searchBar.isVisible()) {
                    searchBar.setBounds(215, 140, 185, 25);
                    sp.setBounds(100, 180, 415,250);
                    searchBar.setVisible(true);
                    sp.setVisible(true);
                }
                else{
                    searchBar.setVisible(false);
                    sp.setVisible(false);
                }
            }
            else {
                JOptionPane.showMessageDialog(frame, "Trebuie sa fii logat!", "Notificare", JOptionPane.WARNING_MESSAGE);
                log.catchLogs("W", "Conectare necesara");
            }
        }
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String s = searchBar.getText();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                tabel.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(s));
            }
        });

        if(e.getSource() == wishList){
            if(flag) {
                utilizatorWindow();
            }else{
                JOptionPane.showMessageDialog(frame, "Trebuie sa fii logat!", "Notificare", JOptionPane.WARNING_MESSAGE);
                log.catchLogs("W", "Conectare necesara");
            }
        }
    }

    public String[][] umpleJtable(String[][] str){
        int i = 0, dim = str.length;
        String [][] info = new String[dim][dim];

        for(String [] a : str){
            int j = 0;
            String [] obj = new String[dim];
            for(String c : a){
                obj[j++] = c;
            }
            info[i++] = obj;
        }
        return info;
    }

    public void sortare(JRadioButton elem, DefaultTableModel m, int poz){
        elem.setSelected(false);
        m.setRowCount(0);
        String [][] infos = umpleJtable(b.getCarti());
        Arrays.sort(infos, (s1, s2) -> {
            String titlu1 = s1[poz];
            String titlu2 = s2[poz];
            return titlu1.compareToIgnoreCase(titlu2);
        });
        for(String[] s : infos){
            m.addRow(s);
        }
    }

    public void openLogWindow(){
        JFrame loginFrame = new JFrame("BibleotecaApp");
        JLabel u = new JLabel("Utilizator: "), parolaU = new JLabel("Parola: ");
        JTextField uLogin = new JTextField();
        JPasswordField parLog = new JPasswordField();
        JButton login = new JButton("Intra in cont");

        u.setBounds(50, 70, 100, 30);
        parolaU.setBounds(50, 115, 100, 30);
        uLogin.setBounds(150, 70, 140, 30);
        parLog.setBounds(150, 115, 140, 30);
        login.setBounds(200, 300, 115, 30);
        login.addActionListener(e12 -> {String utilizator = uLogin.getText(), parola1 = new String(parLog.getPassword());

        Map.Entry<Boolean, Cititor> user_info = b.verificaCont(utilizator, parola1).entrySet().iterator().next();
        if (user_info.getKey()) {
            JOptionPane.showMessageDialog(loginFrame, "Autentificare reusita", "Notificare", JOptionPane.INFORMATION_MESSAGE);
            log.catchLogs("I", "Conectare reusita");
            flag = true;
            logged.add(user_info.getValue());
            LocalDate valab = user_info.getValue().getValabPermisCititor();
            abon.setText("Valabilitate abonament: " +
                    DateTimeFormatter.ofPattern("dd-MM-yyyy").format(valab));
            abon.setVisible(true);
            cont.setVisible(false);
            loginFrame.dispatchEvent(new WindowEvent(loginFrame, WindowEvent.WINDOW_CLOSING));
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Autentificare nereusita", "Notificare", JOptionPane.ERROR_MESSAGE);
            log.catchLogs("E", "Conectare esuata");
        }});
        loginFrame.add(u);
        loginFrame.add(uLogin);
        loginFrame.add(parLog);
        loginFrame.add(parolaU);
        loginFrame.add(login);
        loginFrame.setSize(400, 400);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
        loginFrame.setResizable(false);
    }

    public void utilizatorWindow(){
        JFrame utilizatorFrame = new JFrame("BibleotecaApp");
        DefaultTableModel model = new DefaultTableModel(umpleJtable(b.getCarti()), coloanee);
        JTable tabel = new JTable(model);
        JScrollPane sp = new JScrollPane(tabel);

        JLabel jl = new JLabel("Sorteaza dupa: "), j2 = new JLabel("Ce as vrea sa citesc...");
        JTextField jtf = new JTextField("Introduceti ID-ul", 50);
        JButton btn = new JButton("Adauga"), btn1 = new JButton("Sterge"), btn2 = new JButton("Programare");
        JRadioButton jr = new JRadioButton("Titlu"), jr1 = new JRadioButton("Categorie");
        jtf.setBounds(50, 330, 150,30);
        btn.setBounds(50, 390, 100, 30);
        btn1.setBounds(200, 390, 100, 30);
        btn2.setBounds(350, 390, 100, 30);
        jr.addActionListener(e1 -> {
            log.catchLogs("I", "Sortare carti dupa titlu");
            sortare(jr1, model, 1);
        });

        jr1.addActionListener(e13 -> {
            log.catchLogs("I", "Sortare carti dupa categorie");
            sortare(jr, model, 4);
        });

        DefaultListModel modell = new DefaultListModel();
        try{
            BufferedReader bf = new BufferedReader(new FileReader("prefUtil.csv"));
            String row;
            while ((row = bf.readLine()) != null){
                String [] data = row.split(",");
                if (Objects.equals(data[0], logged.get(0).getNume()) && Objects.equals(data[1], logged.get(0).getPrenume())){
                    modell.addElement(data[2]);
                }
            }
            bf.close();
        }catch (Exception e){
            log.catchLogs("E", "Eroare neprevazuta...");
        }

        JList favorite = new JList(modell);
        btn.addActionListener(e14 -> {
            try {
                FisierIN in = new FisierIN("carti.csv", ",");
                String elem = in.getInfo(Integer.parseInt(jtf.getText()))[1];
                if (elem.equals("")){
                    throw new Exception();
                }
                if (!modell.contains(elem)) {
                        modell.addElement(elem);
                        log.catchLogs("I", "Adaugare in wishlist");
                        try{
                            FisierIN out = new FisierIN("prefUtil.csv", ",");
                            List<String> data = new ArrayList<>(){{
                                add(logged.get(0).getNume() + "," + logged.get(0).getPrenume() + "," + elem);
                            }};
                            out.writeInFile(data);
                        }catch (Exception e){
                            JOptionPane.showMessageDialog(utilizatorFrame, "Eroare neprevazuta...", "Notificare",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                } else {
                    JOptionPane.showMessageDialog(utilizatorFrame, "Elementul se afla in lista!", "Notificare",
                                JOptionPane.WARNING_MESSAGE);
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(utilizatorFrame, "Numar invalid!", "Notificare",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btn1.addActionListener(e15 -> {
                FisierIN in = new FisierIN( "carti.csv", ",");
                String [] elem = in.getInfo(Integer.parseInt(jtf.getText()));
                try {
                    if (Objects.equals(elem[0], "")) {
                        throw new Exception("Id-ul este invalid");
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(utilizatorFrame, e.getMessage(), "Notificare", JOptionPane.WARNING_MESSAGE);
                }
                modell.removeElement(elem[1]);
                try{
                    BufferedReader bf = new BufferedReader(new FileReader("prefUtil.csv"));
                    String row;
                    List <String> loc = new ArrayList<>();
                    while ((row = bf.readLine()) != null){
                        if (row.equals("")){
                            continue;
                        }
                        String [] data = row.split(",");
                        if (!Objects.equals(data[2], elem[1])){
                            loc.add(data[0] + "," + data[1] + "," + data[2]);
                        }
                    }
                    try{
                        FileWriter fw = new FileWriter("prefUtil.csv");
                        for(String s : loc){
                            fw.append(String.join(",", s));
                            fw.append("\n");
                        }
                        fw.close();
                    } catch (Exception e){
                        System.out.println("Eroare neprevazuta...");
                    };
                    log.catchLogs("I", "Stergere din wishlist");
                }catch (Exception e){
                    JOptionPane.showMessageDialog(utilizatorFrame, "Eroare neprevazuta...", "Notificare",
                            JOptionPane.WARNING_MESSAGE);
                }});
        btn2.addActionListener(e16 -> openWindow());
        j2.setBounds(500, 40, 150, 30);
        favorite.setBounds(500, 70, 150,220);
        jl.setBounds(50,40,130,30);
        jr.setBounds(50,70, 100, 30);
        jr1.setBounds(50, 100, 100, 30);
        sp.setBounds(50, 150, 350, 140);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabel.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabel.setEnabled(false);
        utilizatorFrame.add(sp);
        utilizatorFrame.add(jl);
        utilizatorFrame.add(j2);
        utilizatorFrame.add(jr);
        utilizatorFrame.add(jr1);
        utilizatorFrame.add(jtf);
        utilizatorFrame.add(btn);
        utilizatorFrame.add(btn1);
        utilizatorFrame.add(btn2);
        utilizatorFrame.add(favorite);
        utilizatorFrame.setResizable(false);
        utilizatorFrame.setSize(750,540);
        utilizatorFrame.setLayout(null);
        utilizatorFrame.setVisible(true);
    }

    public void openWindow(){
        JFrame progFrame = new JFrame("BibleotecaApp");
        JPanel form = new JPanel(new GridLayout());
        JLabel lb = new JLabel("FORMULAR");
        JLabel lb1 = new JLabel("Nume"), lb2 = new JLabel("Prenume");
        JLabel lb3 = new JLabel("Alege cartea"), lb4 = new JLabel("Data");
        JTextField data = new JTextField(), num = new JTextField(), prenum = new JTextField();
        DefaultTableModel mod = new DefaultTableModel(umpleJtable(b.getCarti()), col);
        JTable tabela = new JTable(mod);
        JScrollPane panou = new JScrollPane(tabela);
        JButton bt = new JButton("Trimite");

        lb.setBounds(140, 10, 100, 50);
        lb1.setBounds(40, 50, 100, 50);
        num.setBounds(40, 90, 170, 20);
        panou.setBounds(40,190, 270, 110);
        lb2.setBounds(40, 100, 100, 50);
        prenum.setBounds(40, 140, 170, 20);
        lb3.setBounds(40, 150, 100, 50);
        lb4.setBounds(40, 300, 100, 50);
        data.setBounds(40, 340, 170, 20);
        bt.setBounds(130, 385, 100, 30);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(20);
        tabela.setEnabled(false);
        form.setLayout(null);
        form.setBackground(Color.WHITE);
        form.setBounds(200,70,350,450);
        form.add(panou);
        form.add(lb);
        form.add(lb1);
        form.add(num);
        form.add(lb2);
        form.add(prenum);
        form.add(lb3);
        form.add(lb4);
        form.add(data);
        form.add(bt);
        progFrame.add(form);
        progFrame.getContentPane().setBackground(Color.lightGray);
        progFrame.setResizable(false);
        progFrame.setSize(750,540);
        progFrame.setLayout(null);
        progFrame.setVisible(true);
    }
}