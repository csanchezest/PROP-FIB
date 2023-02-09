package persistencia;

import dominio.clases.Cjt_items;
import dominio.clases.Datos;
import dominio.clases.Item;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author
 * @class ControladorPersistencia
 * @brief Clase dedicada a la implementacion de las funciones de las cuales es responsable la capa de persistencia
 */

public class ControladorPersistencia {

    /**
     * @brief Instancia de CtrlItemsFile
     */
    private CtrlItemsFile controladorItemsDades;

    /**
     * @brief Instancia de CtrlDatosFile
     */
    private CtrlDatosFile controladorRatingsDades;

    /**
     * @brief Instancia de CtrlRecomendacionFile
     */
    private CtrlRecomendacionFile controladorRecomendacionDades;

    /**
     * @brief Tipo de dataset
     */
    private String datasetType;

    /**
     * @brief Instancia de ControladorPersistencia
     */
    private static ControladorPersistencia singelton;

    /**
     * @brief Creadora de la clase ControladorPersistencia
     */
    private ControladorPersistencia() {
        controladorItemsDades = CtrlItemsFile.getInstance();
        controladorRatingsDades = CtrlDatosFile.getInstance();
        controladorRecomendacionDades = CtrlRecomendacionFile.getInstance();
    }

    /**
     * @brief Método que devuelve una instancia de la clase
     * @return Instancia de ControldorPersistencia
     */
    public static ControladorPersistencia getInstance() {
        if (singelton==null) singelton = new ControladorPersistencia();
        return singelton;
    }

    /**
     * @brief ??
     */
    public void initializeCtrlItems(String pathToFile) throws Exception {
        controladorItemsDades.initialize(pathToFile);
    }

    /**
     * @brief Método que comprueba que hay más
     */
    public boolean hasMoreItems() throws IOException {
        return controladorItemsDades.hasMoreItems();
    }

    public void reset() throws IOException {
        controladorItemsDades.reset();
    }

    public String[] getDataTypes(String pathToFile) throws Exception {
        return controladorItemsDades.getDataTypes(pathToFile);
    }
    
    public ArrayList<String> getCatalanStepWords() throws Exception {
        return controladorItemsDades.getCatalanStepWords();
    }
    
    public ArrayList<String> getEnglishStepWords() throws Exception {
        return controladorItemsDades.getEnglishStepWords();
    }
    
    public ArrayList<String> getFrenchStepWords() throws Exception {
        return controladorItemsDades.getFrenchStepWords();
    }
    
    public ArrayList<String> getGermanStepWords() throws Exception {
        return controladorItemsDades.getGermanStepWords();
    }
    
    public ArrayList<String> getItalianStepWords() throws Exception {
        return controladorItemsDades.getItalianStepWords();
    }

    public ArrayList<String> getPortugueseStepWords() throws Exception {
        return controladorItemsDades.getPortugueseStepWords();
    }
    
    public ArrayList<String> getSpanishStepWords() throws Exception {
        return controladorItemsDades.getSpanishStepWords();
    }
    
    public int getNumOfAttributes(){
        return controladorItemsDades.getNumOfAttributes();
    }

    public ArrayList<String> getAttributeHeaders() throws Exception {
        return controladorItemsDades.getAttributeHeaders();
    }

    public ArrayList<String> getItemAttributes() throws Exception {
        return controladorItemsDades.getItemAttributes();
    }

    public boolean UpdfileNotCreated(){
        return controladorItemsDades.UpdfileNotCreated();
    }

    public void firstUpdateCSVFile(int row, int col, String newValue, String pathToCsv) throws Exception {
        controladorItemsDades.firstUpdateCSVFile(row,col,newValue,pathToCsv);
    }

    public void UpdateCSVFile(int row, int col, String newValue, String pathToCsv) throws Exception {
        controladorItemsDades.UpdateCSVFile(row,col,newValue,pathToCsv);
    }

    public String getPathToUpdatedFile() { return controladorItemsDades.getPathToUpdatedFile(); }

    /* CtrlDatos methods */

    public void initializeCtrlDatos(String pathToFile) throws Exception {
        controladorRatingsDades.initialize(pathToFile);
    }

    public void readRating() throws Exception {
        controladorRatingsDades.readRating();
    }

    public double getMaxRating() {
        return controladorRatingsDades.getMaxRating();
    }

    public boolean hasMoreRatings() throws Exception{
        return controladorRatingsDades.hasMoreRatings();
    }

    public String getItemId() {
        return controladorRatingsDades.getItemId();
    }

    public int getUserId() {
        return controladorRatingsDades.getUserId();
    }

    public double getRating() {
        return controladorRatingsDades.getRating();
    }

    public void resetDatos() throws IOException {
        controladorRatingsDades.reset();
    }


    /* CtrlRecomendacion methods */

    public void initializeWrite(){
        controladorRecomendacionDades.initializeWrite();
    }

    public void writeRecomendacion(String id_usuari, HashMap<Item,Double> recommendedItems) throws Exception {
        controladorRecomendacionDades.writeRecomendacion(id_usuari,recommendedItems);
    }

    public HashMap<Item,Double> recuperarRecomendacion(String pathToFile){
        return controladorRecomendacionDades.recuperarRecomendacion(pathToFile);
    }


    public void guardarRecomendacion(int userId, String recomendation) {
        controladorRecomendacionDades.guardarRecomendacion(userId,datasetType,recomendation);
    }

    public void guardarTipoDataset(String type) { datasetType=type; }

    public List<String> getUserRecomendations(int userId) {
        return controladorRecomendacionDades.getUserRecomendations(userId);
    }

    public String getTextRecomendacion(String file, int userId) {
        return controladorRecomendacionDades.getTextRecomendacion(file, userId);
    }

    public void guardarDatosPreprocesados(Cjt_items dataset, Datos vals, Datos knownVals, Datos unknownVals) {
        String generalPath = System.getProperty("user.dir") + "/src/persistencia/data/datosPreprocesados/";
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        String timestampStr = timestamp2.toString();
        timestampStr = timestampStr.replaceAll("\\s+", "__");
        timestampStr = timestampStr.replaceAll(":", "_");
        generalPath += datasetType + "_" + timestampStr + "/";
        controladorItemsDades.guardarDatosPreprocesados(dataset, generalPath,datasetType);
        controladorItemsDades.guardarDatosPreprocesados(generalPath + "ratings.txt",vals);
        controladorItemsDades.guardarDatosPreprocesados(generalPath + "known.txt",knownVals);
        controladorItemsDades.guardarDatosPreprocesados(generalPath + "unknown.txt",unknownVals);
    }

    public void resetUpdatedFile() { controladorItemsDades.resetUpdatedFile(); }

    public Cjt_items cargarDatosPreprocesados(String absolutePath) {
        return controladorItemsDades.cargarDatosPreprocesados(absolutePath);
    }

    public void guardarRating(int userId, String itemId, String rating, String path) {
        controladorItemsDades.guardarRating(userId,itemId,rating,path);
    }

    public String getDatasetPreprocesado() {
        return controladorItemsDades.getDatasetPreprocesado().getAbsolutePath();
    }

    public HashMap<Integer, HashMap<Item, Double>> loadDatos(String pathToRatings) {
        return controladorItemsDades.readMap(pathToRatings);
    }
}

