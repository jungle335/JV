package com.company;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Loguri {
    private final static Logger log = Logger.getLogger(Loguri.class.getSimpleName());
    private static FileHandler fh;

    public static void init(String numFis) throws IOException {
        fh = new FileHandler(numFis);
    }

    public static void catchLogs(String type, String mes){
        try{
            log.setUseParentHandlers(false);
            log.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            switch (type) {
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
        } catch (Exception ignored){ }
    }
}
