package dominio.controladores;

import dominio.clases.*;

import java.io.File;
import java.util.*;
/**
 * @class CtrlGestioItems
 * @brief Controlador que se encarga de todas las funcionalidades de gestion de items
 * @author Cristian Sanchez Estape
 */
public class CtrlGestioItems {

    /**
     * @brief Instancia del controlador de dominio
     */
    private ControladorDominio padre;

    /**
     * @brief Item sobre el cual, en un determinado momento, se esta consultando informacion o se esta valorando
     */
    private String item;
    /**
     * @brief Instancia de Cjt_items con el dataset actual
     */
    private Cjt_items dataset;
    /**
     * @brief Instancias de Datos con los known ratings del dataset actual
     */
    private Datos knownVals;
    /**
     * @brief Instancias de Datos con los ratings del dataset actual
     */
    private Datos vals;
    /**
     * @brief Instancias de Datos con los unknown ratings del dataset actual
     */
    private Datos unknownVals;
    /**
     * @brief Archivo donde se almacenan los items del dataset
     */
    private File items;
    /**
     * @brief Archivo donde se almacenan los tipos de atributos de los items del dataset
     */
    private File aux;
    /**
     * @brief Archivo donde se alamacenan los ratings del dataset
     */
    private File ratings;
    /**
     * @brief Archivo donde se alamacenan los unkown ratings del dataset
     */
    private File unknown;
    /**
     * @brief Archivo donde se alamacenan los kown ratings del dataset
     */
    private File known;

    /**
     * @brief Idioma del dataset actual
     */
    private String language;

    /**
     * @brief Creadora de la clase CtrlGestioItems
     * @param padre Instancia del ControladorDominio
     */
    public CtrlGestioItems(ControladorDominio padre) {
        this.padre=padre;
        item = "";
        dataset = null;
        knownVals = vals = unknownVals = null;
        items = aux = ratings = unknown = known = null;
    }

    /**
     * @brief Metodo para cargar un dataset
     * @param userId id del usuario que manda a cargar el dataset
     */
    public void loadDataset(int userId) {
        dataset = new Cjt_items(items.getAbsolutePath(), aux.getAbsolutePath(), padre, language);
        vals = new Datos(ratings.getAbsolutePath(), dataset);
        knownVals = new Datos(known.getAbsolutePath(), dataset);
        unknownVals = new Datos(unknown.getAbsolutePath(),dataset);
        List<Usuari> u1 = vals.getUsers();
        List<Usuari> u2 = knownVals.getUsers();
        Usuari placeholder = new Usuari(userId);
        int index = u1.indexOf(placeholder);
        if(index==-1) {
            index = u2.indexOf(placeholder);
            if(index==-1) padre.setUser(placeholder);
            else padre.setUser(u2.get(index));
        } else padre.setUser(u1.get(index));
        List<Usuari> users = new ArrayList<>(vals.getUsers());
        users.addAll(knownVals.getUsers());
        padre.setUsers(users);
    }

    public String getColumnaActual(String current) throws Exception{
        AttributeData data = dataset.getTypeIndex(current);
        switch (data.getType()) {
            //integer,float,categorical,boolean,date,freetext,(other)
            case "integer":
                return "" + dataset.getItem(item).getIntAttributes().get(data.getIndex());
            case "float":
                return "" + dataset.getItem(item).getDoubleAttributes().get(data.getIndex());
            case "categorical":
                return "" + dataset.getItem(item).getCategoricalAttributes().get(data.getIndex());
            case "boolean":
                return "" + dataset.getItem(item).getBooleanAttributes().get(data.getIndex());
            case "date":
                return "" + dataset.getItem(item).getDateAttributes().get(data.getIndex());
            case "freetext":
                return "" + dataset.getItem(item).getStringAttributes().get(data.getIndex());
            case "other":
                return "No disponible";
        }
        return "";
    }

    public void searchItem(String text) {
        item = text;
    }

    public String getItem() { return item;}

    public Datos getVals() { return vals; }

    public File getUnknown() { return unknown; }

    public File getKnown() { return known; }

    public File getRatings() { return ratings; }

    public Cjt_items getDataset() { return dataset; }

    public String getItemNames() { return items.getName(); }

    public void guardarItems(File file) { items = file; }

    public void guardarAuxiliares(File file) { aux=file; }

    public void guardarRatings(File file) {
        ratings = file;
    }

    public void guardarUnknown(File file) {
        unknown = file;
    }

    public void guardarKnown(File file) {
        known = file;
    }

    public void guardarLanguage(String l) {language = l;}

    /**
     * @brief Metodo que indica si hay un dataset cargado
     * @return Un booleano a true si el dataset esta cargado altramente a false
     */
    public boolean isDatasetCargado() {
        return items != null;
    }

    public Datos getUnknownVals() { return unknownVals; }

    public Datos getKnownVals() { return knownVals; }

    /**
     * @brief Metodo que nos indica si existe un item en el dataset acutal
     * @param item id del item que se busca
     * @return booleano indicando si se ha encontrado el item en el dataset
     */
    public boolean existeItem(String item) {
        try {
            dataset.getItem(item);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public List<String> getHeaders() {return dataset.getHeaders();}

    /**
     * @brief Metodo que busca si existe un usuario en el dataset cargado, tanto en el fichero ratings como en el known
     * @param userId id del usuario que se busca
     * @return booleano indicando si se ha encontrado el usario en el datset
     */
    public boolean usuarioPerteneceARatings(int userId) {
        Datos valoraciones = new Datos(ratings.getAbsolutePath(),dataset);
        return valoraciones.getUsers().contains(new Usuari(userId));
    }

    /**
     * @brief Metodo que carga, de manera efectiva, la informacion preprocesada (ya maquetada en el objeto Cjt_items)
     * @param datosPreprocesados Objeto con la informacion preprocesada y de interes
     */
    public void cargarDataset(Cjt_items datosPreprocesados) {
        dataset = datosPreprocesados;
    }

    /**
     * @brief Metodo que actualiza la informacion del sistema, una vez realizada la carga de los datos preprocesados
     * @param ratedItems Mapa con la informacion perteneciente a los datos preprocesados de los ratings generales del dataset
     *                   (Tenemos, para cada usuario, representado con un entero, su mapa de valoraciones donde, para cada item se sabe que puntuacion le ha dado)
     * @param knownQueries Mapa con la informacion perteneciente a los datos preprocesados de las queries known del dataset
     * @param unknownQueries Mapa con la informacion perteneciente a los datos preprocesados de las queries unknown del dataset
     * @param itemsPath Enrutamiento hasta el fichero que almacena la informacion preprocesada de los items del dataset
     * @param rating Enrutamiento hasta el fichero que almacena la informacion preprocesada de los ratings generales del dataset
     * @param Known Enrutamiento hasta el fichero que almacena la informacion preprocesada de las queries known del dataset
     * @param Unknown Enrutamiento hasta el fichero que almacena la informacion preprocesada de las queries unknown del dataset
     */

    public void setNewData(HashMap<Integer,HashMap<Item,Double>> ratedItems,
                           HashMap<Integer,HashMap<Item,Double>> knownQueries,
                           HashMap<Integer,HashMap<Item,Double>> unknownQueries,
                           String itemsPath, String rating, String Known, String Unknown) {
        vals = new Datos();
        vals.setUserRatings(ratedItems);
        ArrayList<Usuari> users = new ArrayList<>();
        for (Integer i: ratedItems.keySet()) users.add(new Usuari(i));
        vals.setUsers(users);
        knownVals = new Datos();
        knownVals.setUserRatings(knownQueries);
        users = new ArrayList<>();
        for (Integer i: knownQueries.keySet()) users.add(new Usuari(i));
        unknownVals = new Datos();
        unknownVals.setUserRatings(unknownQueries);
        users = new ArrayList<>();
        for (Integer i: unknownQueries.keySet()) users.add(new Usuari(i));
        items = new File(itemsPath);
        ratings = new File(rating);
        known=new File(Known);
        unknown= new File(Unknown);
    }

    public int getidxId() {return dataset.getItemId_index();}
}
