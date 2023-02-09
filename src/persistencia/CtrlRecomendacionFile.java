package persistencia;

import dominio.clases.Item;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class CtrlRecomendacionFile {


    /**
     * @brief Instancia singelton del controlador de recomendacion
     */
    private static CtrlRecomendacionFile singelton;

    private BufferedReader br;
    private BufferedWriter bw;
    private File recomendacionFile;
    private static final String ExceptionHandling_1 = "El fichero ya se ha abierto para escritura.";
    private static final String ExceptionHandling_2 = "El fichero ya se ha abierto para lectura.";
    private static final String ExceptionHandling_3 = "No se ha abierto ningún fichero para lectura.";
    private static final String ExceptionHandling_4 = "No se ha abierto ningún fichero para escritura.";
    private static final String ExceptionHandling_5 = "Ningún fichero abierto.";

    /**
     * @brief Constructura privada de la clase
     */

    private CtrlRecomendacionFile() {

    }


    public static CtrlRecomendacionFile getInstance() {
        if (singelton == null) singelton = new CtrlRecomendacionFile();
        return singelton;
    }


    private void openFileForReading(String pathToFile) throws Exception {
        if (br != null) throw new Exception(ExceptionHandling_2);
        if (bw != null) throw new Exception(ExceptionHandling_1);

        try {
            br = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }

    }

    private void openFileForWriting(String pathToFile) throws Exception {
        if (br != null) throw new Exception(ExceptionHandling_2);
        if (bw != null) throw new Exception(ExceptionHandling_1);

        try {
            bw = new BufferedWriter(new FileWriter(pathToFile, true));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void closeFile() throws Exception {
        if (br == null && bw == null) throw new Exception(ExceptionHandling_5);
        else if (br != null) {
            br.close();
            br = null;
        } else {
            bw.close();
            bw = null;
        }
    }

    private void createFile() {
        //carpeta Recomendaciones - path: si no existeix crear carpeta
        String dirName = "Recomendaciones";
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String fileName = "recomendacion" + timestamp;

        File dir = new File(dirName);
        recomendacionFile = new File(dir, fileName);
    }

    public void initializeWrite() {
        createFile();
    }

    public void writeRecomendacion(String id_usuari, HashMap<Item, Double> recommendedItems) throws Exception {
        //idUser idItem rating

        openFileForWriting(recomendacionFile.getAbsolutePath());
        bw.write("Identificador de l'usuari: " + id_usuari);
        bw.newLine();

        Iterator<Map.Entry<Item, Double>> it = recommendedItems.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Item, Double> predItems = it.next();
            bw.write(predItems.getKey() + ",");
            bw.write("" + predItems.getValue());
            bw.newLine();
        }
    }

    public HashMap<Item, Double> recuperarRecomendacion(String pathToFile) {
        //falta implementar
        return null;
    }

    public void guardarRecomendacion(int userId, String datasetType, String recomendation) {
        String filename = System.getProperty("user.dir").replaceAll("\\\\","/") + "/src/persistencia/data/users/" + userId + "/" + datasetType + "_";
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        String timestampStr = timestamp2.toString();
        timestampStr = timestampStr.replaceAll("\\s+", "_");
        filename += timestampStr.replaceAll(":","_") + ".txt";
        BufferedWriter writer = null;
        File f = new File(filename);
        try {
            f.getParentFile().mkdirs();
            f.createNewFile();
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(recomendation);
            writer.close();
        } catch (IOException e) {

        }
        System.out.println(filename);
    }

    public List<String> getUserRecomendations(int userId) {
        List<String> results = new ArrayList<>();
        File[] files = new File(System.getProperty("user.dir") + "/src/persistencia/data/users/" + userId + "/").listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    results.add(file.getName());
                } else {
                    files = new File(System.getProperty("user.dir") + "\\src\\persistencia\\data\\users\\" + userId + "\\").listFiles();
                    for (File file1 : files) {
                        if (file1.isFile()) results.add(file1.getName());
                    }
                    break;
                }
            }
        }
        return results;
    }

    public String getTextRecomendacion(String file, int userId) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/persistencia/data/users/" + userId + "/" + file));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
        }
        return sb.toString();
    }
}


    




