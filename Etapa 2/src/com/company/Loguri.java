package com.company;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Loguri  {
    private final static Logger log = Logger.getLogger("Log");
    private static FileHandler fh;

    public static void init(String numFis) throws IOException {
        fh = new FileHandler(numFis);
        fh.setFormatter(new MyForm());
        log.setUseParentHandlers(false);
        log.addHandler(fh);
    }

    public void catchLogs(String type, String mes){
        switch (type){
            case "I":
                log.info(mes);
                break;

            case "E":
                log.severe(mes);
                break;

            case "W":
                log.warning(mes);
                break;
        }
    }
}

class MyForm extends Formatter{
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss:a");

    public String format(LogRecord record) {
        String s = "";
        s += "[" + df.format(new Date()) + "] ";
        s += "[" + record.getLevel() + "]  ";
        s += formatMessage(record);
        s += "\n";
        return s;
    }
}
