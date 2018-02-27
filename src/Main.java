/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lasanga
 */
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    private static GUI mainFrame;
    private static String inputFile;
    private static String outputFile;
    private static String securityKey;
    private static Encryption e;
    private static Decryption d;
    private static String text;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        mainFrame = new GUI();
        mainFrame.setTitle("Product Cipher Ecryption");
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mainFrame.setVisible(true);
        e = new Encryption();
        d = new Decryption();
    }
    
    private static void init(){
        inputFile = mainFrame.getInputFile();
        outputFile = mainFrame.getOutputFile();
        securityKey = mainFrame.getSecurityKey();
    }

    public static void encrypt(String filename) {
        init();
        BufferedReader br = null;
        String encryptedText = "";
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            String line = br.readLine();

            while (line != null) {
                encryptedText+=(e.encrypt(line, securityKey));
                encryptedText+=(System.lineSeparator());
                line = br.readLine();
            }
           
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        write(encryptedText, outputFile);
    }
    public static void decrypt(String filename) {
        init();
        BufferedReader br = null;
        String decryptedText = "";
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            String line = br.readLine();

            while (line != null) {
                decryptedText+=(d.decrypt(line, securityKey));
                decryptedText+=(System.lineSeparator());
                line = br.readLine();
            }
           
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        write(decryptedText, outputFile);
    }

    public static void write(String s, String filename) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(filename));
            out.print(s);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
