/**
 @file Centroid.java
 @brief Codigo de la clase Centroid

 */
package dominio.clases;

import java.util.*;
/**
 * @class Centroid
 * @brief Clase que representa un Centroide
 * @author Muhammad Haris
 */

public class Centroid {

    HashSet<Usuari>users = new HashSet<>();

    /**
     * @brief Identificador del centroide
     */
    private int centroid_number;

    /**
     * @brief Identificador del Usuario a partir del cual se ha obtenido el centroide inicial
     */
    private int centroid_user_id;

    /**
     * @brief Map para la representacion de un centroide que guarda los items del centroide y sus respectivas valoraciones promedio
     */
    private HashMap<Item, Double> centroid = new HashMap<>();

    /**
     * @brief Map que guarda la suma de todas las valoraciones de un item
     */
    private HashMap<Item,Double> valoraciones = new HashMap<>();

    /**
     * @brief Map que guarda el numero de veces que aparece el mismo item
     */
    private HashMap<Item,Integer> times = new HashMap<>();
    /**
     * @brief Getter del centroide
     * @return Estructura de datos que contiene los items y las valoraciones promedio asociadas a los mismos
     */
    public HashMap<Item, Double> getCentroid() {
        return centroid;
    }

    /**
     * @brief Getter del id del centroide
     * @return id del centroide
     */
    public int getCentroid_number() { return centroid_number;}

    /**
     * @brief Setter del id del centroide
     * @param centroid_number id del centroide
     */
    public void setCentroid_number(int centroid_number) {
        this.centroid_number = centroid_number;
    }

    /**
     * @brief Setter del map que representa el centroide
     * @param centroid El centroide
     */
    public void setCentroid(HashMap<Item, Double> centroid) {

        this.centroid = centroid;
    }

    /**
     * @brief Getter del id del usuario utilizado para crear el centroide
     * @return El id del usuario
     */
    public int getCentroid_user_id() {
        return centroid_user_id;
    }

    /**
     * @brief Setter del id del usuario utilizado para crear el centroide
     * @param centroid_user_id El id del usuario
     */
    public void setCentroid_user_id(int centroid_user_id) {
        this.centroid_user_id = centroid_user_id;
    }

    /**
     * @brief Getter de valoraciones
     * @return valoraciones
     */
    public HashMap<Item, Double> getValoraciones() {
        return valoraciones;
    }

    /**
     * @brief Setter de valoraciones
     * @param valoraciones Map valoraciones
     */
    public void setValoraciones(HashMap<Item, Double> valoraciones) {
        this.valoraciones = valoraciones;
    }

    /**
     * @brief Getter de times
     * @return times
     */
    public HashMap<Item, Integer> getTimes() {
        return times;
    }

    /**
     * @brief Setter de times
     * @param times Map times
     */
    public void setTimes(HashMap<Item, Integer> times) {
        this.times = times;
    }

    /**
     * @brief Metodo que suma la valoraciones del usuario y la del centroide para el mismo item
     * @param Usuari Usario del cual se suma la valoración
     */
    public void recolocar(Usuari Usuari) {
        for (Item key : Usuari.getRatedItems().keySet()) {
            if (valoraciones.containsKey(key)) valoraciones.put(key, valoraciones.get(key)+Usuari.getRatedItems().get(key));
            else valoraciones.put(key, Usuari.getRatedItems().get(key));
            if (!times.containsKey(key)) times.put(key,1);
            else times.put(key,times.get(key)+1);
        }
    }
    /**
     * @brief Metodo que resta las valoraciones de un usuario del centroide
     * @param Usuari Usario del cual se resta la valoración
     */
    public void eliminar (Usuari Usuari) {
        for (Item key : Usuari.getRatedItems().keySet()) {
            valoraciones.put(key, valoraciones.get(key) - Usuari.getRatedItems().get(key));
            times.put(key, times.get(key) - 1);
            if (times.get(key) == 0) valoraciones.remove(key);
        }
    }
}
