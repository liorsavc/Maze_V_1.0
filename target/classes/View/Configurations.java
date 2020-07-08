package View;

import java.io.*;
import java.util.Properties;

public  class Configurations {

    static String filePath = "resources\\config\\config.properties";


    public static String getSolveAlgorithm() {
        String algo="";
        try (InputStream input = new FileInputStream(filePath)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            algo=(prop.getProperty("solveAlgorithm"));
            // get the property value


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return algo;

    }

    public static void setConfig(String algorithm,String rows,String cols) throws FileNotFoundException {


        try (OutputStream output = new FileOutputStream(filePath)) {
            Properties prop = new Properties();


            // set the properties value

            prop.setProperty("solveAlgorithm", algorithm);
            prop.setProperty("Rows", rows);
            prop.setProperty("Cols", cols);


            // save properties to project root folder
            prop.store(output, null);

//            System.out.println(prop);//debug option

        } catch ( IOException io) {
            io.printStackTrace();
        }
    }

    public static int GetRows() {
        int rows=0;
        try (InputStream input = new FileInputStream(filePath)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            rows=Integer.parseInt(prop.getProperty("Rows"));
            // get the property value

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rows;

    }

    public static int GetCols() {
        int cols=0;
        try (InputStream input = new FileInputStream(filePath)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            cols=Integer.parseInt(prop.getProperty("Cols"));
            // get the property value

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cols;

    }
}
