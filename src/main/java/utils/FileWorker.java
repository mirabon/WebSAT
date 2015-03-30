package utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWorker {

    

    public FileWorker() {

    }

    public static void write(String text, String fileName) {
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
                PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                out.print("0");
                out.close();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        exists(fileName);

        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
                PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                out.print("0");
                out.close();
        }
    }

}
