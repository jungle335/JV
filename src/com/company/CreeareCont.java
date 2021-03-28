package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CreeareCont implements ActionListener {
    private static final CreeareCont app = new CreeareCont();
    Biblioteca b = Biblioteca.openB();
    private JFrame frame = new JFrame("BibleotecaApp");
    private JLabel nume = new JLabel("Nume: "), prenume = new JLabel("Prenume: "), abon = new JLabel();
    private JLabel parola = new JLabel("Parola: ");
    private JTextField numeUtilizator = new JTextField(), prenumeUtilizator = new JTextField(), searchBar = new JTextField();
    private JPasswordField parolaUtilizator = new JPasswordField();
    private JButton register = new JButton("Creare cont "), afis = new JButton("Autori");
    private JButton wishList = new JButton("Carti"), contNou = new JButton("Creeaza");
    private final Object[] coloane = { "Nume", "Prenume"};
    DefaultTableModel model = new DefaultTableModel(umpleJtable(b.getAutori().values()), coloane);
    JTable tabel = new JTable(model);
    JScrollPane sp = new JScrollPane(tabel);
    private boolean flag = false;

    private CreeareCont(){
        parolaUtilizator.setEchoChar('*');
        register.setBounds(250, 90, 115, 30);
        afis.setBounds(100, 90, 115, 30);
        wishList.setBounds(400, 90, 115, 30);
        contNou.setBounds(400, 450, 115, 30);
        abon.setBounds(30, 20, 200, 50);
        register.addActionListener(this);
        afis.addActionListener(this);
        wishList.addActionListener(this);
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
        frame.add(contNou);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,540);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public static CreeareCont openApp() { return app; }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == register) {
            nume.setVisible(true);
            prenume.setVisible(true);
            parola.setVisible(true);
            numeUtilizator.setVisible(true);
            prenumeUtilizator.setVisible(true);
            parolaUtilizator.setVisible(true);

            nume.setBounds(125, 170, 85, 30);
            prenume.setBounds(125, 215, 85, 30);
            parola.setBounds(125, 260, 85, 30);
            numeUtilizator.setBounds(250, 170, 170, 30);
            prenumeUtilizator.setBounds(250, 215, 170, 30);
            parolaUtilizator.setBounds(250, 260, 170, 30);
        }
        if(e.getSource() == contNou){
            nume.setVisible(false);
            prenume.setVisible(false);
            parola.setVisible(false);
            numeUtilizator.setVisible(false);
            prenumeUtilizator.setVisible(false);
            parolaUtilizator.setVisible(false);
            String nume = numeUtilizator.getText();
            String prenume = prenumeUtilizator.getText();
            String parola = new String(parolaUtilizator.getPassword());
            if (nume.length() > 0 && prenume.length() > 0 && parola.length() > 0) {
                Cititor cititor = new Cititor(nume, prenume, parola);
                String generareUtilizator = nume + prenume + cititor.getIdCititor();
                JOptionPane.showMessageDialog(frame, "Contul tau a fost creat cu succes", "Notificare", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(frame, "Numele tau de utilizator este: " + generareUtilizator, "Notificare", JOptionPane.INFORMATION_MESSAGE);
                b.addCont(cititor);

                JFrame loginFrame = new JFrame("BibleotecaApp");
                JLabel u = new JLabel("Utilizator: "), parolaU = new JLabel("Parola: ");
                JTextField uLogin = new JTextField();
                JPasswordField parolaLogin = new JPasswordField();
                JButton login = new JButton("Intra in cont");

                u.setBounds(50, 70, 100, 30);
                parolaU.setBounds(50, 115, 100, 30);
                uLogin.setBounds(150, 70, 140, 30);
                parolaLogin.setBounds(150, 115, 140, 30);
                login.setBounds(200, 300, 115, 30);
                login.addActionListener(e12 -> {
                String utilizator = uLogin.getText(), parola1 = new String(parolaLogin.getPassword());
                Boolean bool = b.verificaCont(utilizator, parola1).keySet().iterator().next();
                Cititor cit  = b.verificaCont(utilizator, parola1).get(bool);
                if (bool){
                    JOptionPane.showMessageDialog(loginFrame, "Autentificare reusita", "Notificare", JOptionPane.INFORMATION_MESSAGE);
                    flag = true;
                    LocalDate date = LocalDate.now().plusMonths(cit.getValabilitatePermisCititor());
                    abon.setText("Valabilitate abonament: " +
                            DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format(date));
                    abon.setVisible(true);
                    loginFrame.dispatchEvent(new WindowEvent(loginFrame, WindowEvent.WINDOW_CLOSING));
                }
                else{
                    JOptionPane.showMessageDialog(loginFrame, "Autentificare nereusita", "Notificare", JOptionPane.ERROR_MESSAGE);
                }});
                loginFrame.add(u);
                loginFrame.add(uLogin);
                loginFrame.add(parolaLogin);
                loginFrame.add(parolaU);
                loginFrame.add(login);
                loginFrame.setResizable(false);
                loginFrame.setSize(400,400);
                loginFrame.setLayout(null);
                loginFrame.setVisible(true);
            }
        }
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
            if(flag){
                JFrame utilizatorFrame = new JFrame("BibleotecaApp");
                Object[] coloanee = { "Id", "Titlu", "Nume", "Prenume", "Categorie"};
                DefaultTableModel model = new DefaultTableModel(umpleJtableC(b.getCarti().values()), coloanee);
                JTable tabel = new JTable(model);
                JScrollPane sp = new JScrollPane(tabel);

                JLabel jl = new JLabel("Sorteaza dupa: "), j2 = new JLabel("Ce as vrea sa citesc...");
                JTextField jtf = new JTextField();
                JButton btn = new JButton("Adauga"), btn1 = new JButton("Sterge"), btn2 = new JButton("Programare");
                JRadioButton jr = new JRadioButton("Titlu"), jr1 = new JRadioButton("Categorie");
                jtf.setBounds(50, 330, 150,30);
                btn.setBounds(50, 390, 100, 30);
                btn1.setBounds(200, 390, 100, 30);
                btn2.setBounds(350, 390, 100, 30);
                jr.addActionListener(e1 -> {
                    jr1.setSelected(false);
                    model.setRowCount(0);
                    String [][] infos = umpleJtableC(b.getCarti().values());
                    Arrays.sort(infos, (s1, s2) -> {
                        String titlu1 = s1[1];
                        String titlu2 = s2[1];
                        return titlu1.compareToIgnoreCase(titlu2);
                    });
                    for(String [] s : infos){
                        model.addRow(s);
                    }
                });

                jr1.addActionListener(e13 -> {
                    jr.setSelected(false);
                    model.setRowCount(0);
                    String[][] infos = umpleJtableC(b.getCarti().values());
                    Arrays.sort(infos, (s1, s2) -> {
                        String titlu1 = s1[4];
                        String titlu2 = s2[4];
                        return titlu1.compareToIgnoreCase(titlu2);
                    });
                    for(String[] s : infos){
                        model.addRow(s);
                    }
                });

                DefaultListModel modell = new DefaultListModel();
                JList favorite = new JList(modell);
                btn.addActionListener(e14 -> {
                    String elem = b.getCarti().get(Integer.parseInt(new String(jtf.getText()))).getTitlu();
                    if (!modell.contains(elem)){
                        modell.addElement(elem);
                    }
                    else{
                        JOptionPane.showMessageDialog(utilizatorFrame, "Elementul este deja in lista!", "Notificare",
                                JOptionPane.WARNING_MESSAGE);
                    }
                });

                btn1.addActionListener(e15 -> modell.removeElement(b.getCarti().get(Integer.parseInt(new String(jtf.getText()))).getTitlu()));
                btn2.addActionListener(e16 -> {
                    JFrame progFrame = new JFrame("BibleotecaApp");
                    JPanel form = new JPanel(new GridLayout());
                    JLabel lb = new JLabel("FORMULAR");
                    JLabel lb1 = new JLabel("Alege cartea");
                    JLabel lb2 = new JLabel("Data");
                    Object[] col = {"Titlu", "Categorie", "Numar bucati"};
                    DefaultTableModel mod = new DefaultTableModel(umpleJtableC(b.getCarti().values()), col);
                    JTable tabela = new JTable(mod);
                    JScrollPane panou = new JScrollPane(tabela);

                    lb.setBounds(140, 10, 100, 50);
                    lb1.setBounds(40, 50, 100, 50);
                    panou.setBounds(40,90, 270, 110);
                    lb2.setBounds(40, 200, 100, 50);
                    tabela.getColumnModel().getColumn(0).setPreferredWidth(20);
                    form.setLayout(null);
                    form.setBackground(Color.WHITE);
                    form.setBounds(200,70,350,450);
                    form.add(panou);
                    form.add(lb);
                    form.add(lb1);
                    form.add(lb2);
                    progFrame.add(form);
                    progFrame.getContentPane().setBackground(Color.BLUE);
                    progFrame.setResizable(false);
                    progFrame.setSize(750,540);
                    progFrame.setLayout(null);
                    progFrame.setVisible(true);
                });
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
            else{
                JOptionPane.showMessageDialog(frame, "Trebuie sa fii logat!", "Notificare", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public Object[][] umpleJtable(Collection<Autor> autori){
        Object [][] info = new Object[autori.size()][autori.size()];
        int i = 0;
        for(Autor a : autori){
            Object [] obj = {a.getNume(), a.getPrenume()};
            info[i++] = obj;
        }
        return info;
    }

    public String[][] umpleJtableC(Collection<Carte> carti){
        String [][] info = new String[carti.size()][carti.size()];
        int i = 0;
        for(Carte c : carti){
            String num = b.getAutori().get(c.getIdAutor()).getNume();
            String prenum = b.getAutori().get(c.getIdAutor()).getPrenume();
            String categorie = c.getCategorie();
            String [] obj = {String.valueOf(c.getIdCarte()), c.getTitlu(), num, prenum, categorie};
            info[i++] = obj;
        }
        return info;
    }
}
