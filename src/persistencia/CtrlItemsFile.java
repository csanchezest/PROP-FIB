/**
 @file Cjt_items.java
 @brief Codigo de la clase CtrlItemsFile

 */

package persistencia;

import dominio.clases.AttributeData;
import dominio.clases.Cjt_items;
import dominio.clases.Datos;
import dominio.clases.Item;
import dominio.controladores.ControladorDominio;
import presentacion.ControladorPresentacion;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @class CtrlItemsFile
 * @brief Clase dedicada al preprocesado de un dataset de items con numero y tipo de atributos uniforme y la posterior asignacion del conjunto de items resultante a la instancia de Cjt_items
 * @author Houda El Fezzak Bekkouri
 */

public class CtrlItemsFile {


    private static CtrlItemsFile singelton;

    private BufferedReader br;
    private BufferedWriter bw;
    /**
     * @brief contiene el path al dataset que contiene los items
     * */
    private String pathToDataset;
    private String pathToUpdatedFile;
    private int nAttributes;
    private File datasetPreprocesado;

    private static final String ExceptionHandling_1 = "El fichero ya se ha abierto para escritura.";
    private static final String ExceptionHandling_2 = "El fichero ya se ha abierto para lectura.";
    private static final String ExceptionHandling_3 = "No se ha abierto ningún fichero para lectura.";
    private static final String ExceptionHandling_4 = "No se ha abierto ningún fichero para escritura.";
    private static final String ExceptionHandling_5 = "Ningún fichero abierto.";

    private CtrlItemsFile() {
        br = null;
        bw = null;
        pathToDataset = "";
        pathToUpdatedFile = "";
        nAttributes = 0;
    }

    public static CtrlItemsFile getInstance() {
        if (singelton == null) singelton = new CtrlItemsFile();
        return singelton;
    }

    public File getDatasetPreprocesado() {
        return datasetPreprocesado;
    }

    public String getPathToUpdatedFile() {
        return pathToUpdatedFile;
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

    public void initialize(String pathToFile) throws Exception {
        pathToDataset = pathToFile;
        openFileForReading(pathToDataset);
    }

    public boolean hasMoreItems() throws IOException {
        String line = "";
        boolean hasMore = false;
        if (br != null) {
            br.mark(1000000);
            hasMore = (line = br.readLine()) != null;
            br.reset();
        }
        return hasMore;
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

    public String[] getDataTypes(String pathToFile) throws Exception {
        BufferedReader bRead = null;
        File f = new File(pathToFile);
        try {
            bRead = new BufferedReader(new FileReader(f.getAbsolutePath()));
        } catch (Exception e) {
        }
        String data_types = bRead.readLine();
        String[] readData = data_types.split(",", -1);
        bRead.close();
        bRead = null;
        return readData;
    }

    public ArrayList<String> getCatalanStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/Catalan.txt";
        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }

    public ArrayList<String> getEnglishStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/English.txt";
        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }

    public ArrayList<String> getFrenchStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/French.txt";
        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }

    public ArrayList<String> getGermanStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/German.txt";
        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }

    public ArrayList<String> getItalianStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/Italian.txt";
        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }

    public ArrayList<String> getPortugueseStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/Portuguese.txt";
        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }

    public ArrayList<String> getSpanishStepWords() {
        String pathToFile = System.getProperty("user.dir") + "/src/persistencia/data/StopWords/Spanish.txt";

        ArrayList<String> stepWords = new ArrayList<>();
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToFile));
        } catch (Exception e) {
        }
        String word = "";
        try {
            while ((word = bRead.readLine()) != null) {
                stepWords.add(word);
            }
        } catch (Exception e) {
        }
        return stepWords;
    }


    public int getNumOfAttributes() {
        return nAttributes;
    }

    public ArrayList<String> getAttributeHeaders() throws Exception {

        if (br == null) throw new Exception(ExceptionHandling_3);
        String line = "";
        ArrayList<String> AttributeHeaders = new ArrayList<>();
        if ((line = br.readLine()) != null) {
            String[] readCols = line.split(",", -1);
            AttributeHeaders = new ArrayList<>(Arrays.asList(readCols));

        }
        nAttributes = AttributeHeaders.size();
        return AttributeHeaders;
    }

    public ArrayList<String> getItemAttributes() throws Exception {
        if (br == null) throw new Exception(ExceptionHandling_3);
        String line = "";
        ArrayList<String> st = new ArrayList<>();

        if ((line = br.readLine()) != null) {
            String[] readCols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);

            ArrayList<String> myCols = new ArrayList<>(Arrays.asList(readCols));
            st = myCols;
            int curAttr = st.size();

            // Resuelve las lineas del dataset que se dividen erroneamente por newlines contenidas en algun campo de los atributos
            while (curAttr < nAttributes) {
                for (int k = 1; k < myCols.size(); ++k) myCols.set(0, myCols.get(0) + myCols.get(k));

                if (myCols.size() > 1) myCols.subList(1, st.size()).clear();

                String remaining = br.readLine();
                //myCols.set(0, myCols.get(0).replaceAll("\n", ""));
                remaining = myCols.get(0) + remaining;


                String[] Attrs1 = remaining.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
                st = new ArrayList<>(Arrays.asList(Attrs1));

                curAttr = st.size();
            }
        }
        return st;
    }

    public boolean UpdfileNotCreated() {
        return pathToUpdatedFile.equals("");
    }

    private void createUpdatedFile(String pathToDataset) {
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        String timestampStr = timestamp2.toString();
        timestampStr = timestampStr.replaceAll("\\s+", "__");
        timestampStr = timestampStr.replaceAll(":", "_");
        String newFileName = "items" + timestampStr + ".csv";
        int dirIndex = pathToDataset.lastIndexOf("\\");
        String pathToDir = "";
        try {
            pathToDir = pathToDataset.substring(0, dirIndex);
            pathToUpdatedFile = pathToDir + "\\" + newFileName;
        } catch (Exception e) {
            dirIndex = pathToDataset.lastIndexOf("/");
            pathToDir = pathToDataset.substring(0, dirIndex);
            pathToUpdatedFile = pathToDir + "/" + newFileName;
        }
        try {
            File csvFile = new File(pathToUpdatedFile);
            csvFile.createNewFile(); // *** idk if this works as I believe it does... ***
        } catch (Exception e) {
        }
    }

    public void firstUpdateCSVFile(int row, int col, String newValue, String pathToCsv) throws Exception {

        createUpdatedFile(pathToDataset);

        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToCsv));
        } catch (Exception e) {
        }
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines.add(bRead.readLine());
        } catch (Exception e) {
        }
        try {
            while ((line = bRead.readLine()) != null) {
                String remaining = line;
                String[] readCols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
                ArrayList<String> myCols = new ArrayList<>(Arrays.asList(readCols));
                ArrayList<String> st = myCols;
                int curAttr = st.size();

                // Resuelve las lineas del dataset que se dividen erroneamente por newlines contenidas en algun campo de los atributos
                while (curAttr < nAttributes) {
                    for (int k = 1; k < myCols.size(); ++k) myCols.set(0, myCols.get(0) + myCols.get(k));

                    if (myCols.size() > 1) myCols.subList(1, st.size()).clear();

                    remaining = bRead.readLine();
                    //myCols.set(0, myCols.get(0).replaceAll("\n", ""));
                    remaining = myCols.get(0) + remaining;


                    String[] Attrs1 = remaining.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
                    st = new ArrayList<>(Arrays.asList(Attrs1));

                    curAttr = st.size();
                }
                lines.add(remaining);
            }
        } catch (Exception e) {
        }

        bRead.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter(pathToUpdatedFile, false));
        bw2.write(lines.get(0));
        int l = 1;
        String[] colData;
        try {
            while (l < row) {

                bw2.newLine();
                bw2.write(lines.get(l));
                ++l;
            }
            colData = lines.get(l).split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
            colData[col] = newValue;
            int k = 0;
            String upd_line = "";
            while (k < nAttributes - 1) {
                upd_line += colData[k] + ",";
                ++k;
            }
            upd_line += colData[k];
            bw2.newLine();
            bw2.write(upd_line);
            bw2.newLine();
        } catch (Exception e) {
        }
        try {
            ++l;
            while (l < lines.size()) {
                bw2.write(lines.get(l));
                bw2.newLine();
                ++l;
            }
        } catch (Exception e) {
        }
        bw2.close();
    }

    public void UpdateCSVFile(int row, int col, String newValue, String pathToCsv) throws Exception {

        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(pathToUpdatedFile));
        } catch (Exception e) {
        }

        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines.add(bRead.readLine());
        } catch (Exception e) {
        }
        try {
            while ((line = bRead.readLine()) != null) {
                String remaining = line;
                String[] readCols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
                ArrayList<String> myCols = new ArrayList<>(Arrays.asList(readCols));
                ArrayList<String> st = myCols;
                int curAttr = st.size();

                // Resuelve las lineas del dataset que se dividen erroneamente por newlines contenidas en algun campo de los atributos
                while (curAttr < nAttributes) {
                    for (int k = 1; k < myCols.size(); ++k) myCols.set(0, myCols.get(0) + myCols.get(k));

                    if (myCols.size() > 1) myCols.subList(1, st.size()).clear();

                    remaining = bRead.readLine();
                    //myCols.set(0, myCols.get(0).replaceAll("\n", ""));
                    remaining = myCols.get(0) + remaining;


                    String[] Attrs1 = remaining.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
                    st = new ArrayList<>(Arrays.asList(Attrs1));

                    curAttr = st.size();
                }
                lines.add(remaining);
            }
        } catch (Exception e) {
        }

        bRead.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter(pathToUpdatedFile, false));
        bw2.write(lines.get(0));
        int l = 1;
        String[] colData;
        try {
            while (l < row) {
                bw2.newLine();
                bw2.write(lines.get(l));
                ++l;
            }
            colData = lines.get(l).split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
            colData[col] = newValue;
            int k = 0;
            String upd_line = "";
            while (k < nAttributes - 1) {
                upd_line += colData[k] + ",";
                ++k;
            }
            bw2.newLine();
            upd_line += colData[k];
            bw2.write(upd_line);
            bw2.newLine();
        } catch (Exception e) {
        }
        try {
            ++l;
            while (l < lines.size()) {
                bw2.write(lines.get(l));
                bw2.newLine();
                ++l;
            }
        } catch (Exception e) {
        }
        bw2.close();
    }

    public void guardarDatosPreprocesados(Cjt_items dataset, String generalPath, String datasetType) {
        String file = generalPath + datasetType;
        datasetPreprocesado = new File(file + ".txt");
        try {
            datasetPreprocesado.getParentFile().mkdirs();
            datasetPreprocesado.createNewFile();
            FileWriter fw = new FileWriter(datasetPreprocesado);
            //escritura del hashmap de items de cjt_items
            fw.write("" + dataset.getItems().size());
            fw.write("\n");
            fw.write(printItemMap(dataset.getItems()));
            fw.write(writeList(new ArrayList<>(dataset.getHeaders())));
            fw.write("\n");
            fw.write(writeSimpleMap(new LinkedHashMap<>(dataset.getMaxValIntegers())));
            fw.write("\n");
            fw.write(writeSimpleMap(new LinkedHashMap<>(dataset.getMinValIntegers())));
            fw.write("\n");
            fw.write(writeSimpleMap(new LinkedHashMap<>(dataset.getMaxValDoubles())));
            fw.write("\n");
            fw.write(writeSimpleMap(new LinkedHashMap<>(dataset.getMinValDoubles())));
            fw.write("\n");
            fw.write(writeSimpleMap(new LinkedHashMap<>(dataset.getMaxDates())));
            fw.write("\n");
            fw.write(writeSimpleMap(new LinkedHashMap<>(dataset.getMinDates())));
            fw.write("\n");
//            System.out.println(items.getMaxValIntegers().size() + "\t" + items.getMinValIntegers().size() + "\t" + items.getMaxValDoubles().size() + "\t"
//            + items.getMinValDoubles().size() + "\t" + items.getMaxDates().size() + "\t" + items.getMinDates().size());
            fw.write(writeSimpleMap(new HashMap<>(dataset.getIntAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getDoubleAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getDateAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getBooleanAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getCategoricalAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getStringAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getOtherAttrIndexes())));
            fw.write("\n");
            fw.write(writeSimpleMap(new HashMap<>(dataset.getHeaderIndexes())));
            fw.write("\n");
//            System.out.println(items.getIntAttrIndexes().size() + "\t" + items.getDoubleAttrIndexes().size() + "\t" + items.getDateAttrIndexes().size() + "\t" +
//                    items.getBooleanAttrIndexes().size() + "\t" + items.getCategoricalAttrIndexes().size() + "\t" +
//                    items.getStringAttrIndexes().size() + "\t" + items.getOtherAttrIndexes().size() + "\t" +
//                    items.getHeaderIndexes().size());
            try {
                fw.write(dataset.getLanguage());
                fw.write("\n");
            } catch (Exception e) {

            }
            fw.close();
        } catch (Exception e) {

        }
    }

    public Cjt_items cargarDatosPreprocesados(String absolutePath) {
        Cjt_items items = new Cjt_items();
        try {
            Scanner scan = new Scanner(new File(absolutePath));
            String data = "";
            String[] strings = {}, auxiliar = {}, auxiliar2 = {}, auxiliar3 = {}, auxiliar4 = {};
            int size = Integer.parseInt(scan.nextLine());
            HashMap<String, Item> newItems = new HashMap<>();
            for (int i = 0; i < size; i++) {
                data = scan.nextLine();
                strings = data.split(Pattern.quote("--------"));
                strings = strings[1].split(Pattern.quote("....*"));
                while (strings.length != 9) {
                    data += scan.nextLine();
                    data = data.replaceAll(System.getProperty("line.separator"), "").replaceAll("\n", "");
                    strings = data.split(Pattern.quote("....*"));
                }
                //Creamos el nuevo item
                Item item = new Item(strings[0]);
                //Recogemos los intAttrs
                auxiliar = strings[1].split(Pattern.quote("////"));
                ArrayList<Long> longs = new ArrayList<>();
                for (String datos : auxiliar) longs.add(Long.parseLong(datos));
                item.setIntAtt(longs);
                //Recogemos los doubleAttrs
                auxiliar = strings[2].split(Pattern.quote("////"));
                ArrayList<Double> doubles = new ArrayList<>();
                for (String datos : auxiliar) doubles.add(Double.parseDouble(datos));
                item.setDoubleAtt(doubles);
                //Recogemos los stringAttrs
                auxiliar = strings[3].split(Pattern.quote("////"));
                ArrayList<String> strs = new ArrayList<>();
                for (String datos : auxiliar) strs.add(datos);
                item.setStringAtt(strs);
                //Recogemos los bitsets
                auxiliar = strings[4].split(Pattern.quote("////"));
                ArrayList<BitSet> bits = new ArrayList<>();
                for (String elements : auxiliar) {
                    auxiliar2 = elements.split(Pattern.quote("-"));
                    BitSet bitset = new BitSet(Integer.parseInt(auxiliar2[0]));
                    String[] string = auxiliar2[1].replaceAll("\\{", "")
                            .replaceAll("}", "").replaceAll(" ", "")
                            .split(",");
                    for (int l = 0; l < string.length && string.length > 1; l++)
                        bitset.set(Integer.parseInt(string[l]));
                    bits.add(bitset);
                }
                item.setCategoricalAtt_Psc_bits(bits);
                //Recogemos los categoricos
                auxiliar = strings[5].split(Pattern.quote("****"));
                HashMap<Integer, ArrayList<String>> categoricals = new HashMap<>();
                for (String elements : auxiliar) {
                    auxiliar2 = elements.split(Pattern.quote("||||"));
                    ArrayList<String> categoricalValues = new ArrayList<>();
                    try {
                        auxiliar3 = auxiliar2[1].split(Pattern.quote("////"));
                        for (String datos : auxiliar3) categoricalValues.add(datos);
                        categoricals.put(Integer.parseInt(auxiliar2[0]), new ArrayList<>(categoricalValues));
                    } catch (Exception e) {
                        categoricals.put(Integer.parseInt(auxiliar2[0]), new ArrayList<>());
                    }
                }
                item.setCatAttr(categoricals);
                //Recogemos las fechas
                auxiliar = strings[6].split(Pattern.quote("////"));
                ArrayList<LocalDate> dates = new ArrayList<>();
                for (String datos : auxiliar) dates.add(LocalDate.parse(datos));
                item.setDateAtt(dates);
                //Recogemos los booleans
                auxiliar = strings[7].split(Pattern.quote("////"));
                ArrayList<String> booleans = new ArrayList<>();
                for (String datos : auxiliar) booleans.add(datos);
                item.setBooleanAtt(booleans);
                LinkedHashMap<Integer, HashMap<String, Integer>> freqs = new LinkedHashMap<>();
                //Recogemos las frecuencias de las palabras
                auxiliar = strings[8].split(Pattern.quote("::::"));
                for (String elements : auxiliar) {
                    auxiliar2 = elements.split(Pattern.quote(";;;;"));
                    HashMap<String, Integer> map = new HashMap<>();
                    try {
                        auxiliar3 = auxiliar2[1].split(Pattern.quote("????"));
                        for (String subelement : auxiliar3) {
                            auxiliar4 = subelement.split(Pattern.quote("----"));
                            map.put(auxiliar4[0], Integer.parseInt(auxiliar4[1]));
                        }
                        freqs.put(Integer.parseInt(auxiliar2[0]), new HashMap<>(map));
                    } catch (Exception e) {
                        freqs.put(Integer.parseInt(auxiliar2[0]), new HashMap<>());
                    }
                }
                item.setWordFrequencies(freqs);
                newItems.put(strings[0], item);
            }
//            System.out.println(newItems.size());
            //hasta aqui lee todos los items
            //Ahora recogemos las cabeceras del dataset
            items.setItems(newItems);
            data = scan.nextLine();
            strings = data.split(Pattern.quote("////"));
            ArrayList<String> attrHeaders = new ArrayList<>();
            for (String datos : strings) attrHeaders.add(datos);
            items.setHeaders(attrHeaders);
            //Recogemos los maxIntAttrs del dataset
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            LinkedHashMap<Integer, Long> intAttrs = new LinkedHashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                intAttrs.put(Integer.parseInt(auxiliar[0]), Long.parseLong(auxiliar[1]));
            }
            items.setMaxValIntegers(intAttrs);
            //Recogemos los minIntAttrs del dataset
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            intAttrs = new LinkedHashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                intAttrs.put(Integer.parseInt(auxiliar[0]), Long.parseLong(auxiliar[1]));
            }
            items.setMinValIntegers(intAttrs);
            //Recogemos los maxValDoubles del dataset
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            LinkedHashMap<Integer, Double> doubleAttrs = new LinkedHashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                doubleAttrs.put(Integer.parseInt(auxiliar[0]), Double.parseDouble(auxiliar[1]));
            }
            items.setMaxValDoubles(doubleAttrs);
            //Recogemos los minValDoubles del dataset
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            doubleAttrs = new LinkedHashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                doubleAttrs.put(Integer.parseInt(auxiliar[0]), Double.parseDouble(auxiliar[1]));
            }
            items.setMinValDoubles(doubleAttrs);
            //Recogemos los maxDates del dataset
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            LinkedHashMap<Integer, LocalDate> dateAttrs = new LinkedHashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                dateAttrs.put(Integer.parseInt(auxiliar[0]), LocalDate.parse(auxiliar[1]));
            }
            items.setMaxDates(dateAttrs);
            //Recogemos los minDates del dataset
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            dateAttrs = new LinkedHashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                dateAttrs.put(Integer.parseInt(auxiliar[0]), LocalDate.parse(auxiliar[1]));
            }
            items.setMinDates(dateAttrs);
            //Hasta aqui carga todos los LinkedHashMaps
//            System.out.println(items.getMaxValIntegers().size() + "\t" + items.getMinValIntegers().size() + "\t" + items.getMaxValDoubles().size() + "\t"
//                    + items.getMinValDoubles().size() + "\t" + items.getMaxDates().size() + "\t" + items.getMinDates().size());
            for (int i = 0; i < 7; i++) {
                data = scan.nextLine();
                strings = data.split(Pattern.quote("????"));
                HashMap<Integer, Integer> attrIndexs = new HashMap<>();
                for (String element : strings) {
                    auxiliar = element.split(Pattern.quote("----"));
                    if (auxiliar.length == 2)
                        attrIndexs.put(Integer.parseInt(auxiliar[0]), Integer.parseInt(auxiliar[1]));
                }
                switch (i) {
                    case 0:
                        items.setIntAttrIndexes(attrIndexs);
                        break;
                    case 1:
                        items.setDoubleAttrIndexes(attrIndexs);
                        break;
                    case 2:
                        items.setDateAttrIndexes(attrIndexs);
                        break;
                    case 3:
                        items.setBooleanAttrIndexes(attrIndexs);
                        break;
                    case 4:
                        items.setCategoricalAttrIndexes(attrIndexs);
                        break;
                    case 5:
                        items.setStringAttrIndexes(attrIndexs);
                        break;
                    case 6:
                        items.setOtherAttrIndexes(attrIndexs);
                        break;
                }
            }
            data = scan.nextLine();
            strings = data.split(Pattern.quote("????"));
            HashMap<String, Integer> attrIndexs = new HashMap<>();
            for (String element : strings) {
                auxiliar = element.split(Pattern.quote("----"));
                attrIndexs.put(auxiliar[0], Integer.parseInt(auxiliar[1]));
            }
            items.setHeaderIndexes(attrIndexs);
//            System.out.println(items.getIntAttrIndexes().size() + "\t" + items.getDoubleAttrIndexes().size() + "\t" + items.getDateAttrIndexes().size() + "\t" +
//                    items.getBooleanAttrIndexes().size() + "\t" + items.getCategoricalAttrIndexes().size() + "\t" +
//                    items.getStringAttrIndexes().size() + "\t" + items.getOtherAttrIndexes().size() + "\t" +
//                    items.getHeaderIndexes().size());
            if (scan.hasNext()) {
                data = scan.nextLine();
                items.setLanguage(data);
            }
        } catch (Exception e) {
            System.out.println("Algo ha fallado en la lectura de los datos preprocesados, en CtrlItemsFile");
        }
        System.out.println("Dataset cargado correctamente");
        return items;
    }

    public void resetUpdatedFile() {
        pathToUpdatedFile = "";
    }

    private static String printItemMap(HashMap<String, Item> map) {
        String s = "";
        for (String key : map.keySet()) {
            s += key + "--------" + printItem(map.get(key));
            s += "\n";
        }
        return s;
    }

    private static String printItem(Item item) {
        String s = item.getId() + "....*";
        s += writeList(new ArrayList<>(item.getIntAttributes())) + "....*";
        s += writeList(new ArrayList<>(item.getDoubleAttributes())) + "....*";
        s += writeList(new ArrayList<>(item.getStringAttributes())) + "....*";
        s += writeList(new ArrayList<>(item.getCategoryPscBits())) + "....*";
        s += writeListMap(new HashMap<>(item.getCategoricalAttributes())) + "....*";
        s += writeList(new ArrayList<>(item.getDateAttributes())) + "....*";
        s += writeList(new ArrayList<>(item.getBooleanAttributes())) + "....*";
        s += writeMap(new LinkedHashMap<>(item.getWordFrequencies()));
        return s.replaceAll(System.getProperty("line.separator"), "").replaceAll("\\\\n|\\r", "");
    }

    private static String writeSimpleMap(HashMap<Object, Object> map) {
        String s = "";
        for (Object key : map.keySet()) s += key.toString() + "----" + map.get(key) + "????";
        return s;
    }

    private static String writeMap(HashMap<Integer, HashMap<String, Integer>> map) {
        String s = "";
        for (Integer key : map.keySet())
            s += key.toString() + ";;;;" + writeSimpleMap(new HashMap<>(map.get(key))) + "::::";
        return s;
    }

    private static String writeListMap(HashMap<Integer, List<String>> map) {
        String s = "";
        for (Integer key : map.keySet())
            s += key.toString() + "||||" + writeList(new ArrayList<>(map.get(key))) + "****";
        return s;
    }

    private static String writeList(List<Object> list) {
        String s = "";
        if (list.size() > 0 && list.get(0) instanceof BitSet) {
            for (Object o : list) s += ((BitSet) o).size() + "-" + o.toString() + "////";
        } else {
            for (Object o : list)
                s += o.toString().replaceAll(System.getProperty("line.separator"), "").replaceAll("\n", "") + "////";
        }
        return s.replaceAll("\n", "");
    }

    public void guardarRating(int userId, String itemId, String rating, String path) {
        File f = new File(path);
        try {
            Writer output = new BufferedWriter(new FileWriter(f, true));
            output.write("" + userId + "," + itemId + "," + rating);//PROVISIONAL
        } catch (IOException e) {
            System.out.println("Salta error en el metodo de escritura de una nueva valoracion de un usuario, en CtrlItemsFile");
        }
    }

    public void guardarDatosPreprocesados(String path, Datos datos) {
        HashMap<Integer, HashMap<Item, Double>> info = datos.getUserRatings();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (Integer key : info.keySet()) {
                HashMap<Item, Double> itemData = info.get(key);
                for (Item secondKey : itemData.keySet()) {
                    bw.write(key + ";" + secondKey.getId() + ";" + itemData.get(secondKey));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, HashMap<Item, Double>> readMap(String path) {
        BufferedReader br = null;
        HashMap<Integer, HashMap<Item, Double>> data = new HashMap<>();
        try {
            br = new BufferedReader(new FileReader(path));
            String intermediate;
            HashMap<Item, Double> media;
            while ((intermediate = br.readLine()) != null) {
                String[] strings = intermediate.split(Pattern.quote(";"));
                int key = Integer.parseInt(strings[0]);
                Item i = new Item(strings[1]);
                Double rating = Double.parseDouble(strings[2]);
                if (data.containsKey(key)) media = data.get(key);
                else media = new HashMap<>();
                media.put(i, rating);
                data.put(key, media);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

