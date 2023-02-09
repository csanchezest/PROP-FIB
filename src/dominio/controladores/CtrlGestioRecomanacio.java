package dominio.controladores;

import dominio.clases.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @class CtrlGestioRecomanacio
 * @brief Controlador que se encarga de todas las funcionalidades de gestion de las recomendaciones
 * @author Jordi Elgueta Serra
 */
public class CtrlGestioRecomanacio {

    /**
     * @brief Numero de items que se recomendaran
     */
    private int k;
    /**
     * @brief Map con las valoraciones hechas por el usuario
     */
    private HashMap<Item, Double> valoraciones;

    /**
     * @brief Contructora de la clase
     */
    public CtrlGestioRecomanacio() {
        valoraciones = new LinkedHashMap<>();
        k = 0;
    }

    /**
     * @brief Metodo para hacer una recomendacion con cualquiera de los tres algortimos
     * @param n Parametro que nos indica el algortimo a usar, 0 para el Collaborative, 1 para el ContentBased y 2 para el Hybrid
     * @param u Usuario que pide la recomendacion
     * @param dataset Dataset sobre el que se hará la recomendacin
     * @param vals Ratings sobre los que se hara la recomendacion
     * @param intAt Lista de booleanos que indica cuales atributos de tipo enteros que hay tener en cuenta en la recomendacion
     * @param doubAt Lista de booleanos que indica cuales atributos de tipo double que hay tener en cuenta en la recomendacion
     * @param dateAt Lista de booleanos que indica cuales atributos de tipo date que hay tener en cuenta en la recomendacion
     * @param bAt Lista de booleanos que indica cuales atributos de tipo booleano que hay tener en cuenta en la recomendacion
     * @param catAt Lista de booleanos que indica cuales atributos de tipo categorico que hay tener en cuenta en la recomendacion
     * @param ftAt Lista de booleanos que indica cuales atributos de tipo texto libre que hay tener en cuenta en la recomendacion
     * @return Mapa con la recomendacion, con los identificadores de los items como key y las predicciones de valoracion como values
     */
    public HashMap<String, Double> makeRecomendation(int n, Usuari u, Cjt_items dataset, Datos vals, List<Boolean> intAt,
                                                     List<Boolean> doubAt,List<Boolean> dateAt,List<Boolean> bAt,
                                                     List<Boolean> catAt,List<Boolean> ftAt) {
        HashMap<String, Double> recommendation = new LinkedHashMap<>();
        if (n == 0)
            valoraciones = new Recomendacion(u, dataset, vals.getRatings(), vals.getMaxRating(), vals.getUsers(), k, intAt,
                    doubAt,dateAt,bAt,catAt,ftAt).Collaborative();
        else if (n == 1)
            valoraciones = new Recomendacion(u, dataset, vals.getRatings(), vals.getMaxRating(), vals.getUsers(), k, intAt,
                    doubAt,dateAt,bAt,catAt,ftAt).Content();
        else
            valoraciones = new Recomendacion(u, dataset, vals.getRatings(), vals.getMaxRating(), vals.getUsers(), k, intAt,
                    doubAt,dateAt,bAt,catAt,ftAt).Hybrid();

        for (Item i : valoraciones.keySet()) recommendation.put(i.getId(), valoraciones.get(i));

        return recommendation;
    }

    /**
     * @brief Metodo que devuelve en formato string una recomendacion para poder printearla
     * @param dataset Dataset sobre el que se hace la recomendacion
     * @param unknown Archivo que contiene el ratings unknown
     * @param u usuario que pide la recomendacion
     * @return Devuelve el string que contiene la recomendacion
     */
    public String getRecomendacion(Cjt_items dataset, File unknown, Usuari u) {
        Datos unknownVals = new Datos(unknown.getAbsolutePath(), dataset);
        if (!unknownVals.getUsers().contains(u))
            return "No hay datos suficientes para evaluar la calidad de la prediccion realizada";
        String s = "El valor de la recomendacion realizada es: ";
        List<Usuari> users = unknownVals.getUsers();
        Usuari us = users.get(users.indexOf(u));
        s += Avaluacio.DCG(us, u);
        return s;
    }

    public void setK(int n) {
        k = n;
    }
}
