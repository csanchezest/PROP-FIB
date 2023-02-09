package persistencia;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CtrlDatosFile {

    /**
     * @brief Instancia singelton del controlador de datos
     */

    private static CtrlDatosFile singelton;

    /**
     * @brief Lector de datos
     */

    private BufferedReader br;

    /**
     * @brief Escritor de datos
     */

    private BufferedWriter bw;

    /**
     * @brief String que almacena el enrutamiento hasta el fichero de ratings generales del dataset
     */

    private String pathToRatings;

    /**
     * @brief Entero que identifica el usuario logeado en el sistema
     */

    private int userId;

    /**
     * @brief String que identifica (en un momento determinado) un item del dataset
     */

    private String itemId;

    /**
     * @brief Double que identifica (en un momento determinado) una valoracion del usuario
     */

    private double rating;

    /**
     * @brief Double global que actua como cota superior de las valoraciones posiblemente realizables
     */

    private static double maxRating = 0.0;

    /**
     * @brief Lista con los
     */

    private ArrayList<Integer> indices;

    private static final String ExceptionHandling_1 = "El fichero ya se ha abierto para escritura.";
    private static final String ExceptionHandling_2 = "El fichero ya se ha abierto para lectura.";
    private static final String ExceptionHandling_3 = "No se ha abierto ningún fichero para lectura.";
    private static final String ExceptionHandling_4 = "No se ha abierto ningún fichero para escritura.";
    private static final String ExceptionHandling_5 = "Ningún fichero abierto.";

    /**
     * @brief Constructura privada de la clase
     */

    private CtrlDatosFile() {
        br = null;
        bw = null;
        String pathToFile = "";
        indices = new ArrayList<>();
    }
    
    public static CtrlDatosFile getInstance() {
        if (singelton==null) singelton = new CtrlDatosFile();
        return singelton;
    }

    /**
     * @brief Metodo dedicado a la apertura de un fichero para su lectura
     * @param pathToFile Ruta hasta el fichero sobre el que se quiere leer
     */

    private void openFileForReading(String pathToFile) throws Exception {
        if (br != null) throw new Exception(ExceptionHandling_2);
        if (bw != null) throw new Exception(ExceptionHandling_1);
        
        try {
            br = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        
    }
    
     public void reset() throws IOException {
        if (br != null) {
            br.close();
            br = null;
        }
        if (bw != null) {
            bw.close();
            bw = null;
        }
    }

    
    private void setIndices() throws Exception {
        String line = "";
        try { line = br.readLine(); }
        catch (IOException e){
            System.out.println(e);
        }
        String[] colNames = line.split(",", -1);
        if (colNames[0].toLowerCase().contains("id")) {
            if (colNames[0].toLowerCase().contains("item")) indices.add(0,0);
            else if (colNames[0].toLowerCase().contains("user")) indices.add(0,1);
        }
        else indices.add(0,2);
        if (colNames[1].toLowerCase().contains("id")) {
            if (colNames[1].toLowerCase().contains("item")) indices.add(1,0);
            else if (colNames[1].toLowerCase().contains("user")) indices.add(1,1);
        }
        else indices.add(1,2);
        if (colNames[2].toLowerCase().contains("id")) {
            if (colNames[2].toLowerCase().contains("item")) indices.add(2,0);
            else if (colNames[2].toLowerCase().contains("user")) indices.add(2,1);
        }
        else indices.add(2,2);
    }
    
    public void readRating() throws Exception {
        String line = "";
        try{
            line = br.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        String[] readCols = line.split(",", -1);
        ArrayList<String> myCols = new ArrayList<>(Arrays.asList(readCols));
        if (!(myCols.get(0) == null) && !myCols.get(0).isEmpty() && !(myCols.get(1) == null) && !myCols.get(1).isEmpty() && !(myCols.get(2) == null) && !myCols.get(2).isEmpty()) {
            for (int i = 0; i < myCols.size(); ++i){
                if (indices.get(i) == 0) itemId = myCols.get(i) ;
                else if (indices.get(i) == 1) userId = Integer.parseInt(myCols.get(i));
                else{
                    rating = Double.parseDouble(myCols.get(i));
                    if (maxRating < rating) maxRating = rating;
                }
            }

        }
    }
    
    public void initialize(String pathToFile) throws Exception {
        pathToRatings = pathToFile;
        openFileForReading(pathToRatings);
        setIndices();
    }
    public double getMaxRating(){
        return maxRating;
    }
    
    public boolean hasMoreRatings() throws Exception{

        String line = "";
        br.mark(1000000);
        boolean hasMore = ((line=br.readLine()) != null);
        br.reset();
        
        return hasMore;
    }
    
    public String getItemId() { return itemId; }
    public int getUserId() { return userId; }
    public double getRating() { return rating; }
}