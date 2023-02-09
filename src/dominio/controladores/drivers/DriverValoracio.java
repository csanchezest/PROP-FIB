/**
 @file DriverValoracio.java
 @brief Codigo de la clase DriverValoracio

 */
package dominio.controladores.drivers;

import dominio.clases.*;

import java.util.*;

/**
 * @class DriverValoracio
 * @brief Driver de la clase Valoracio
 * @author Houda El Fezzak Bekkouri
 */

public class DriverValoracio {

    public DriverValoracio(){
    }

    /**
     * @brief Metodo de testeo de la constructora de la clase
     * @param filepath Path hasta el dataset de valoraciones a leer
     * @param data Path hasta el conjunto de items para el que se han obtenido las valoraciones
     * @return Devuelve una instancia de la clase Valoracio
     */
    static public Datos testValoracio(String filepath, String data, String pathToAuxCsv) {
        System.out.println("Se ha creado una instancia de la clase Valoracio.");
        Cjt_items dataset = new Cjt_items(data, pathToAuxCsv);
        return new Datos(filepath,dataset);
    }

    /** @brief Metodo de testeo del getter del conjunto de valoraciones asociadas a cada uno de los items
     * @param v Instancia de la clase Valoracio, necesaria para las llamadas a los metodos a testear
     * */
    public static void testGetRatings(Datos v) {
        System.out.println("Test getRatings");
        HashMap<Item,ArrayList<Double>> it = v.getRatings();

        for(Item i: it.keySet()) {
            Iterator itR = it.get(i).iterator();
            System.out.print("\t" + i.getId() + ":  ");
            while (itR.hasNext()) System.out.print(itR.next() + " ");
            System.out.println();
        }

    }

    /** @brief Metodo de testeo del getter del conjunto de valoraciones hechas por cada usuario
     * @param v Instancia de la clase Valoracio, necesaria para las llamadas a los metodos a testear
     * */
    public static void testGetUserRatings(Datos v) {
        System.out.println("Test getUserRatings");
        HashMap<Integer, HashMap<Item, Double>> m = v.getUserRatings();

        for (Map.Entry<Integer, HashMap<Item, Double>> entry : m.entrySet()) {
            System.out.println("User: "+ entry.getKey());
            for (Map.Entry<Item, Double> innerEntry : entry.getValue().entrySet()) {
                Item i = innerEntry.getKey();
                System.out.println(i.getId() + ":  " + innerEntry.getValue());
            }
        }
    }

    /** @brief Metodo de testeo del getter del conjunto de usuarios que tinen alguna valoracion asociada en el dataset
     * @param v Instancia de la clase Valoracio, necesaria para las llamadas a los metodos a testear
     * */
    public static void testGetUsers(Datos v) {
        System.out.println("Test getUsers");
        Iterator it = v.getUsers().listIterator();

        while(it.hasNext()) {
            Usuari u = (Usuari) it.next();
            System.out.println(u.getUserId());
        }

    }

    /** @brief Metodo de testeo del getter de la valoracion maxima del conjunto de valoraciones
     *  @param v Instancia de la clase Valoracio, necesaria para las llamadas a los metodos a testear
     * */
    public static void testGetMaxRating(Datos v) {
        System.out.println("Test getMaxRating");
        System.out.println("MaxRating: " + v.getMaxRating());
    }

    public static void main(String[] args) {
        String pathToAuxCsv = "/persistencia/data/datasets/Movielens/";
        Datos v = testValoracio("/persistencia/data/datasets/Movielens/250/ratings.test.unknown.csv",
                "/persistencia/data/datasets/Movielens/250/items.csv", pathToAuxCsv+"data_types_movies");

        testGetUsers(v);
        System.out.println("----------------------");
        testGetUserRatings(v);
        System.out.println("----------------------");
        testGetRatings(v);
        System.out.println("----------------------");
        testGetMaxRating(v);

    }
}
