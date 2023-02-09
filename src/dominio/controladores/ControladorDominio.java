package dominio.controladores;

import dominio.clases.Item;
import dominio.clases.Usuari;
import persistencia.ControladorPersistencia;
import presentacion.ControladorPresentacion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

/**
 * @class ControladorDominio
 * @brief Clase que implementa el controlador de dominio del sistema
 * @author Cristian Sanchez Estape y Jordi Elgueta Serra
 */

public class ControladorDominio {

    /**
     * @brief Instancia del controlador del administrador de recomendaciones
     */

    private CtrlGestioRecomanacio ctrlGestioRecomanacio;

    /**
     * @brief Instancia del controlador del administrador de items
     */

    private CtrlGestioItems ctrlGestioItems;

    /**
     * @brief Instancia del controlador del administrador de usuarios
     */

    private CtrlGestioUsuaris ctrlUsuaris;

    /**
     * @brief Instancia del controlador principal de presentacion
     */

    private ControladorPresentacion padre;

    /**
     * @brief Constructora de ControladorDominio
     * @param ctrlPresentacion Instancia (que sera unica) del controlador de presentacion con el cual debera comunicarse siempre
     */

    public ControladorDominio(ControladorPresentacion ctrlPresentacion){
        padre = ctrlPresentacion;
        ctrlGestioRecomanacio = new CtrlGestioRecomanacio();
        ctrlGestioItems = new CtrlGestioItems(this);
        ctrlUsuaris = new CtrlGestioUsuaris();
    }

    /**
     * @brief Metodo dedicado a la comprobacion de la existencia de un item determinado dentro de un dataset
     * @param item Item sobre el que queremos determinar si existe o no
     * @return Valor cierto o falso en funcion de si el dataset reconoce la pertenencia de dicho item
     */

    public boolean existeItem(String item) { return ctrlGestioItems.existeItem(item); }

    /**
     * @brief Metodo dedicado a la consulta del valor asociado a una columna del dataset
     * @param header El nombre de la cabecera de una columna determinada
     * @return El tipo de valor asociado a la columna de la entrada
     */

    public String getHeaderInfo(String header) { return ctrlGestioItems.getDataset().getTypeIndex(header).getType(); }

    /**
     * @brief Metodo que configura el numero maximo de items que se van a tener en consideracion durante una recomendacion
     * @param n Numero de items (deseable) y sobre los cuales deberemos generar las predicciones y recomendaciones
     */

    public void setK(int n) {
        ctrlGestioRecomanacio.setK(n);
    }

    /**
     * @brief Metodo que fija el usuario sobre el cual se trabaja durante una sesion determinada
     * @param id El identificador del usuario sobre el cual se trabaja
     */

    public void setUser(int id) {
        ctrlUsuaris.setUserId(id);
    }

    /**
     * @brief Metodo que fija el usuario sobre el cual se trabaja durante una sesion dada
     * @param u Objeto del usuario sobre el cual se trabaja
     */

    public void setUser(Usuari u) {
        ctrlUsuaris.setUser(u);
    }

    /**
     * @brief Metodo dedicado al almacenamiento del archivo donde se ubican los items del dataset
     * @param file Archivo donde se almacena la informacion de los items
     */

    public void guardarItems(File file) { ctrlGestioItems.guardarItems(file); }

    /**
     * @brief Metodo dedicado al almacenamiento del archivo donde se ubica la informacion auxiliar necesaria para
     * conocer los tipos de dato de cada columna del dataset
     * @param file Archivo donde se almacena la informacion sobre los tipos de dato de cada columna del dataset
     */

    public void guardarAuxiliares(File file) {
        ctrlGestioItems.guardarAuxiliares(file);
    }

    /**
     * @brief Metodo dedicado al almacenamiento del archivo que alberga la informacion de los ratings generales del dataset
     * @param file Archivo donde se almacena la informacion sobre los ratings generales
     */

    public void guardarRatings(File file) {
        ctrlGestioItems.guardarRatings(file);
    }

    /**
     * @brief Metodo dedicado al almacenamiento del archivo que alberga la informacion sobre las queries desconocidas (o unknown) del dataset
     * @param file Archivo donde se almacena la informacion de las queries unknown
     */

    public void guardarUnknown(File file) {
        ctrlGestioItems.guardarUnknown(file);
    }

    /**
     * @brief Metodo dedicado al almacenamiento del archivo que alberga la informacion sobre las queries conocidas (o known) del dataset
     * @param file Archivo donde se almacena la informacion de las queries known
     */

    public void guardarKnown(File file) {
        ctrlGestioItems.guardarKnown(file);
    }

    /**
     * @brief Metodo dedicado al almacenamiento del lenguaje del dataset
     * @param language String que indica (en castellano) sobre qué lenguaje trabaja el dataset
     */

    public void guardarLanguage(String language) {ctrlGestioItems.guardarLanguage(language);}

    /**
     * @brief Metodo dedicado a la comprovacion de si el sistema tiene algun dataset cargado
     * @return Un valor cierto o falso en funcion de si se encuentra cargado
     */

    public boolean isDatasetCargado() {
        return ctrlGestioItems.isDatasetCargado();
    }

    /**
     * @brief Metodo que almacena el rating realizado por el usuario logeado en el sistema
     * @param rating Valor numerico de la valoracion del usuario sobre un item dado
     */

    public void almacenarRating(double rating) throws Exception {
        ctrlUsuaris.getUsuari().addRatedItems(ctrlGestioItems.getDataset().getItem(ctrlGestioItems.getItem()), rating);
        if(ctrlGestioItems.usuarioPerteneceARatings(ctrlUsuaris.getUserId()))
            ControladorPersistencia.getInstance().guardarRating(ctrlUsuaris.getUserId(), ctrlGestioItems.getItem(),
                    ""+rating,ctrlGestioItems.getRatings().getAbsolutePath());
        else ControladorPersistencia.getInstance().guardarRating(ctrlUsuaris.getUserId(), ctrlGestioItems.getItem(),
                ""+rating,ctrlGestioItems.getKnown().getAbsolutePath());
    }

    /**
     * @brief Metodo dedicado a la consulta del valor asociado a una columna determinada del dataset
     * @param current La columna de la cual necesitamos saber su tipo
     * @return El tipo de valor de la columna indicada en formato String
     */

    public String getColumnaActual(String current) throws Exception { return ctrlGestioItems.getColumnaActual(current); }

    /**
     * @brief Metodo dedicado a la generacion de una recomendacion sobre un conjunto de predicciones
     * @param n Entero identificador del algoritmo que se va a usar (0 = Collaborative; 1 = Content-Based; 2 = Hybrid)
     * @param l Lista de booleanos donde, para cada atributo, si se encuentra en estado true, significa que forma parte del subconjunto de atributos a tener en cuenta para generar una recomendacion
     * @return Un mapa donde, para cada clave, que corresponde a un identificador de item, tenemos la valoracion predecida para el usuario del sistema
     */

    public HashMap<String, Double> makeRecomendation(int n, List<Boolean> l) {
        List<Boolean> intAt = new ArrayList<>();
        List<Boolean> doubAt = new ArrayList<>();
        List<Boolean> dateAt = new ArrayList<>();
        List<Boolean> bAt = new ArrayList<>();
        List<Boolean> catAt = new ArrayList<>();
        List<Boolean> ftAt = new ArrayList<>();
        for (int i = 0; i < l.size(); ++i) {
            switch (getHeaderInfo(getHeaders().get(i))) {
                case "integer":
                    intAt.add(l.get(i));
                    break;
                case "float":
                    doubAt.add(l.get(i));
                    break;
                case "date":
                    dateAt.add(l.get(i));
                    break;
                case "boolean":
                    bAt.add(l.get(i));
                    break;
                case "categorical":
                    catAt.add(l.get(i));
                    break;
                case "freetext":
                    ftAt.add(l.get(i));
                    break;
                case "other":
                    break;
            }
        }
        return ctrlGestioRecomanacio.makeRecomendation(n, ctrlUsuaris.getUsuari(), ctrlGestioItems.getDataset(), ctrlGestioItems.getVals(),
                intAt, doubAt, dateAt, bAt, catAt, ftAt);
    }

    /**
     * @brief Metodo dedicado a la carga del dataset
     */

    public void loadDataset() {
        ctrlGestioItems.loadDataset(ctrlUsuaris.getUserId());
    }

    /**
     * @brief Metodo dedicado a la consulta del path hasta una copia nueva del dataset; dicha copia se producira en caso de que haya errores y el usuario decida corregirlos
     * @return String almacenando el enrutamiento hacia dicho archivo
     */

    public String getPathToUpdatedFile() { return ControladorPersistencia.getInstance().getPathToUpdatedFile(); }

    /**
     * @brief Metodo dedicado a la comprobacion de si un determinado item pertenece al dataset cargado
     * @param text String con el identificador del item a buscar
     */

    public void searchItem(String text) { ctrlGestioItems.searchItem(text); }

    /**
     * @brief Metodo dedicado a la busqueda de la pertenencia de un determinado usuario al dataset cargado en el sistema
     * @param text String con el identificador del usuario en cuestion
     */

    public void searchUser(String text) {
        ctrlUsuaris.searchUser(text);
    }

    public List<String> getItemInfo() { return ctrlGestioItems.getDataset().getHeaders(); }

    public String getItems() {
        return ctrlGestioItems.getItemNames();
    }

    public String getRecomendacion() {
        return ctrlGestioRecomanacio.getRecomendacion(ctrlGestioItems.getDataset(),ctrlGestioItems.getUnknown(), ctrlUsuaris.getUsuari());
    }

    public HashMap<String, Double> getUsuaris(int id) { return ctrlUsuaris.getRatedItems(id); }

    /**
     * @brief Metodo dedicado a la comunicacion entre Cjt_items y el usuario con el objetivo de tratar los errores detectados en el dataset
     * @param i Entero que indica de qué tipo de valor se trata {entero, booleano, ...}
     * @param attrHeader String que indica la cabecera de la columna afectada
     * @param idItem String que contiene el identificador del item afectado
     * @return Una lista con todos los posibles objetos que se pueden almacenar en el campo del item afectado
     */

    public ArrayList<Object> pedirInput(int i, String attrHeader, String idItem) { return padre.pedirInput(i, attrHeader, idItem); }

    /**
     * @brief Metodo que comprueba si un usuario tiene valoraciones
     * @return Valor booleano cierto o falso segun la condicion comprobada
     */

    public boolean userHasRatings() { return (ctrlUsuaris.getUsuari().getRatedItems().size() > 0); }

    /**
     * @brief Metodo que almacena una recomendacion generada por alguno de los algoritmos predictores
     * @param recomendation String que contiene, ya formatada, la informacion respecto la recomendacion realizada
     */

    public void guardarRecomendacion(String recomendation) {
        ControladorPersistencia.getInstance().guardarRecomendacion(ctrlUsuaris.getUserId(),recomendation);
    }

    public int getUserId() { return ctrlUsuaris.getUserId(); }

    public List<String> getUserRecomendations() {
        return ControladorPersistencia.getInstance().getUserRecomendations(getUserId());
    }

    public String getTextRecomendacion(String file) {
        return ControladorPersistencia.getInstance().getTextRecomendacion(file,getUserId());
    }

    /**
     * @brief Metodo que almacena el tipo de dataset indicado por el usuario
     * @param type String que contiene el valor indicado por el usuario para el dataset tratado
     */

    public void guardarTipoDataset(String type) {
        ControladorPersistencia.getInstance().guardarTipoDataset(type);
    }

    public List<String> getHeaders() {return ctrlGestioItems.getHeaders();}

    /**
     * @brief Metodo dedicado a la instruccion de almacenar la informacion actualmente cargada en el sistema, y de manera preprocesada
     */

    public void guardarDatosPreprocesados() { ControladorPersistencia.getInstance().guardarDatosPreprocesados(ctrlGestioItems.getDataset(),ctrlGestioItems.getVals(),ctrlGestioItems.getKnownVals(),ctrlGestioItems.getUnknownVals()); }

    /**
     * @brief Metodo que reinicia el archivo sobre el cual se realizan modificaciones durante la deteccion de errores en el dataset
     */

    public void resetUpdatedFile() { ControladorPersistencia.getInstance().resetUpdatedFile(); }

    /**
     * @brief Metodo dedicado al ordenamiento de cargar datos ya preprocesados y almacenados
     * @param absolutePath Enrutamiento en el cual se encuentra el archivo preprocesado de interes
     */

    public void cargarDatosPreprocesados(String absolutePath) {
        ctrlGestioItems.cargarDataset(ControladorPersistencia.getInstance().cargarDatosPreprocesados(absolutePath));
    }

    /**
     * @brief Metodo de ordenamiento y almacenamiento de datos preprocesados. En dicho metodo se carga el dataset preprocesado, juntamente con los ratings (generales/known/unknown)
     * @param items Path hasta el archivo que almacena la informacion preprocesada de los items del dataset
     * @param ratings Path hasta el archivo que almacena la informacion preprocesada de los ratings generales del dataset
     * @param known Path hasta el archivo que almacena la informacion preprocesada de las queries known del dataset
     * @param unknown Path hasta el archivo que almacena la informacion preprocesada de las queries unknown del dataset
     */

    public void cargarDatosPreprocesados(String items,String ratings,String known, String unknown) {
        cargarDatosPreprocesados(items);
        HashMap<Integer,HashMap<Item,Double>> ratedItems = ControladorPersistencia.getInstance().loadDatos(ratings);
        HashMap<Integer,HashMap<Item,Double>> knownQueries = ControladorPersistencia.getInstance().loadDatos(known);
        HashMap<Integer,HashMap<Item,Double>> unknownQueries = ControladorPersistencia.getInstance().loadDatos(unknown);
        ctrlGestioItems.setNewData(ratedItems,knownQueries,unknownQueries,items,ratings,known,unknown);
    }

    public String getDatasetPreprocesado() { return ControladorPersistencia.getInstance().getDatasetPreprocesado();
    }

    /**
     * @brief Metodo dedicado a la consulta de si un usuario determinado pertenece al dataset cargado en el sistema
     * @param id Entero con el numero identificador del usuario de interes
     * @return Valor booleano cierto o falso en funcion de la condicion tratada
     */

    public boolean existeUsuario(int id) { return ctrlUsuaris.existeUsuario(id); }

    public void setUsers(List<Usuari> list) {
        ctrlUsuaris.setUsers(list);
    }

    public int getIdxId() {return ctrlGestioItems.getidxId();}
}
