/**
 * @file DriverSlopeOne.java
 * @brief Codigo de la clase DriverSlopeOne
 */
package dominio.controladores.drivers;

import dominio.clases.*;

import java.util.*;

/**
 * @author Cristian Sanchez Estape
 * @class DriverSlopeOne
 * @brief Driver representativo de un ejemplo de ejecucion del algoritmo de prediccion SlopeOne
 */

public class DriverSlopeOne {

    /**
     * @brief Metodo main
     */

    public static void main(String[] args) {
        test1();
        System.out.println();
        test2();
        System.out.println();
        test3();
        System.out.println("----- Fin de la ejecucion -----");
    }

    /**
     * @brief Metodo local para hacer el test 1
     */

    private static void test1() {
        HashMap<Item, Double> valoracions = new HashMap<>();
        List<Usuari> users = new LinkedList<>();
        System.out.println("----- Test 1 -----");
        valoracions.put(new Item("232"), 2.);
        valoracions.put(new Item("233"), 3.);
        Usuari u = new Usuari(1);
        u.setRatedItems(new HashMap<>(valoracions));
        users.add(u);
        u = new Usuari(2);
        valoracions.clear();
        valoracions.put(new Item("232"), 2.);
        users.add(u);
        u.setRatedItems(new HashMap<>(valoracions));
        SlopeOne sp = new SlopeOne(u);
        testSlopeOne(sp, users);
        HashMap<Item, Double> preds = testGetPredictions(sp);
        Algorisme.print(preds);
        valoracions.clear();
        users.clear();
    }

    /**
     * @brief Metodo local para hacer el test 2
     */

    private static void test2() {
        HashMap<Item, Double> valoracions = new HashMap<>();
        List<Usuari> users;
        System.out.println("----- Test 2 -----");
        Item a = new Item("1"), b = new Item("2"), c = new Item("3");
        Usuari f = new Usuari(1), g = new Usuari(2), h = new Usuari(3);
        System.out.println("Tabla con los datos para un conjunto pequeño de usuarios (donde X = item no valorado):");
        System.out.println("Usuario\tItem 1\tItem 2\tItem 3");
        System.out.println("1\t\t5.0\t\t3.0\t\t2.0");
        System.out.println("2\t\t3.0\t\t4.0\t\tX");
        System.out.println("3\t\tX\t\t2.0\t\t5.0");
        valoracions.put(a, 5.);
        valoracions.put(b, 3.);
        valoracions.put(c, 2.);
        f.setRatedItems(new HashMap<>(valoracions));
        valoracions.clear();
        valoracions.put(a, 3.);
        valoracions.put(b, 4.);
        g.setRatedItems(new HashMap<>(valoracions));
        valoracions.clear();
        valoracions.put(b, 2.);
        valoracions.put(c, 5.);
        h.setRatedItems(new HashMap<>(valoracions));
        valoracions.clear();
        users = Arrays.asList(f, g, h);
        SlopeOne sp = new SlopeOne(g);
        System.out.println();
        sp.slopeOne(users, 5., 10);
        HashMap<Item,Double> preds = sp.getPredictions();
        preds.remove(a);
        preds.remove(b);
        System.out.println("Para el usuario " + g.getUserId());
        Algorisme.print(preds);
        sp = new SlopeOne(h);
        System.out.println("--");
        sp.slopeOne(users, 5., 10);
        preds = sp.getPredictions();
        preds.remove(c);
        preds.remove(b);
        System.out.println("Para el usuario " + h.getUserId());
        Algorisme.print(preds);
    }

    /**
     * @brief Metodo para hacer el test 3
     */

    private static void test3() {
        System.out.println("----- Test 3 -----");
        String path = System.getProperty("user.dir") + "/Datasets/Movielens/250/";
        Cjt_items dataset = new Cjt_items(path + "items.csv", System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv");
        Datos ratings = new Datos(path + "ratings.db.csv", dataset);
        Datos ratingsKnown = new Datos(path + "ratings.test.known.csv", dataset);
        List<Usuari> users = ratings.getUsers();
        SlopeOne sp;
        for (Usuari us : ratingsKnown.getUsers()) {
            sp = new SlopeOne(us);
            System.out.println("Los ítems que recomendamos para el usuario " + us.getUserId() + " son: ");
            sp.slopeOne(users, 5., 10);
            Algorisme.print(sp.getPredictions());
        }
    }

    /**
     * @brief Metodo para testear la llamada al algoritmo de SlopeOne
     * @param sp Instancia de la clase SlopeOne (necesaria para conocer el usuario sobre el cual se generan recomendaciones)
     * @param users Lista de usuarios a partir de los cuales hay que generar las predicciones
     */

    private static void testSlopeOne(SlopeOne sp, List<Usuari> users) {
        System.out.println("Se realiza la llamada a SlopeOne para una lista de usuarios determinada y con el objetivo de" +
                " generar una prediccion para un usuario determinado");
        sp.slopeOne(users, 5., 10);

    }

    /**
     * @brief Metodo para testear la llamada al metodo get de las predicciones generadas por SlopeOne
     * @param sp Instancia de la clase SlopeOne (necesaria para conocer el usuario sobre el cual se generan recomendaciones)
     * @return Mapa con las predicciones sobre un conjunto determinado de items
     */

    private static HashMap<Item,Double> testGetPredictions(SlopeOne sp) {
        System.out.println("Se realiza una consulta a las predicciones generadas por SlopeOne (a posteriori de su llamada)");
        return sp.getPredictions();
    }

}
