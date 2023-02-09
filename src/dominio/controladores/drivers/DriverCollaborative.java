/**
 @file DriverCollaborative.java
 @brief Codigo de la clase DriverCollaborative

 */

package dominio.controladores.drivers;
import dominio.clases.*;

import java.util.*;

/**
 * @class DriverCollaborative
 * @brief Driver representativo de dos ejemplos de ejecución del algoritmo de Collaborative Filtering
 * @author Cristian Sanchez Estape and Muhammad Haris
 */

public class DriverCollaborative {

    /**
     * @brief Nº de iteraciones a realizar hasta encontrar la distribucion adecuada de usuarios en clusters
     */

    public static int n = Integer.MAX_VALUE;

    /**
     * @brief Nº de clusters a crear
     */

    public static int k = 3;

    /**
     * @brief Metodo de ejemplo de ejecucion del algoritmo KMeans sobre una muestra de usuarios concreta
     *
     * @param usuaris Lista de usuarios sobre los que aplicar KMeans
     * @return Distribucion de los usuarios en centroides; el nº de centroides viene dado como atributo de clase
     */

    public static Map<Centroid, List<Usuari>> testKMeans(List<Usuari> usuaris) {
        System.out.println("Método que ejecuta el algoritmo k-means. Devuelve k clusters con sus centroides y los usuarios correspondientes");
        Collaborative a = new Collaborative();
        Map<Centroid, List<Usuari>> clusters = (Map<Centroid, List<Usuari>>) a.recommendations(usuaris, k, new DistanceUsers(), n);
        clusters.forEach((key, value) -> {
            System.out.println("-------------------------- CLUSTER " + key.getCentroid_number() + " ----------------------------");
            System.out.println("Items:");
            System.out.println("");
            for (Item i : key.getCentroid().keySet()) System.out.println(i.getId() + "\t" + key.getCentroid().get(i));
            System.out.println("");
            System.out.println("Users:");
            System.out.println("");
            for (Usuari Usuari : value) {
                System.out.println(Usuari.getUserId());
            }
            System.out.println();
            System.out.println();
        });
        return clusters;
    }

    /**
     * @brief Metodo de ejemplo de ejecucion del algoritmo SlopeOne sobre una muestra de usuarios e items concreta
     *
     * @param users Lista de usuarios sobre los que aplicar el algoritmo
     * @param items Lista de items sobre la que aplicar el algoritmo
     */

    public static void testSlopeOne(List<Usuari> users, List<Item> items) {
        System.out.println("Metodo que ejecuta el SlopeOne para cada cluster encontrado en KMeans");
        //SlopeOne sp = new SlopeOne();
        //sp.slopeOne(users, items, 5.);
        //Algorisme.printData(sp.getOutputData());
    }

    public static void main(String[] args) {
        System.out.println("\tTest con datos inventados:");
        List<Integer> usuaris = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Item> items = Arrays.asList(new Item("11"), new Item("12"), new Item("13"), new Item("14"), new Item("15"), new Item("16"),
                new Item("17"), new Item("18"), new Item("19"), new Item("110"));
        List<Usuari> valoracions = new LinkedList<>();
        initializeValoracions(valoracions, usuaris, items);
        Map<Centroid, List<Usuari>> centroids = testKMeans(valoracions);
        for (Centroid c : centroids.keySet()) testSlopeOne(centroids.get(c), items);
        System.out.println("----------");
        System.out.println("Testeo sobre datos de Series:");
        Cjt_items c = new Cjt_items("/persistencia/data/datasets/Series/250/items.csv", "/persistencia/data/datasets/Series/data_types_series.csv" );
        Datos v = new Datos("/persistencia/data/datasets/Series/250/ratings.db.csv", c);
        List<Usuari> users = v.getUsers();
        //items = c.getItems();
        centroids = testKMeans(users);
        for (Centroid ct : centroids.keySet()) testSlopeOne(centroids.get(ct), items);
        System.out.println("----------");
    }

    /**
     * @brief Metodo local para inicializar los usuarios, items y valoraciones de los mismos de manera manual
     *
     * @param valoracions Lista de usuarios que vamos a tratar
     * @param usuaris     Lista de los IDs de los usuarios con los que vamos a tratar
     * @param items       Lista de items con los que vamos a tratar
     */

    private static void initializeValoracions(List<Usuari> valoracions,
                                              List<Integer> usuaris, List<Item> items) {
        Random rand = new Random();
        Usuari u = new Usuari(usuaris.get(0));
        u.addRatedItems(items.get(0), rand.nextDouble() * 5);
        u.addRatedItems(items.get(1), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(1));
        u.addRatedItems(items.get(1), rand.nextDouble() * 5);
        u.addRatedItems(items.get(2), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(2));
        u.addRatedItems(items.get(2), rand.nextDouble() * 5);
        u.addRatedItems(items.get(3), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(3));
        u.addRatedItems(items.get(3), rand.nextDouble() * 5);
        u.addRatedItems(items.get(4), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(4));
        u.addRatedItems(items.get(4), rand.nextDouble() * 5);
        u.addRatedItems(items.get(5), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(5));
        u.addRatedItems(items.get(5), rand.nextDouble() * 5);
        u.addRatedItems(items.get(6), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(6));
        u.addRatedItems(items.get(6), rand.nextDouble() * 5);
        u.addRatedItems(items.get(7), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(7));
        u.addRatedItems(items.get(7), rand.nextDouble() * 5);
        u.addRatedItems(items.get(8), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(8));
        u.addRatedItems(items.get(8), rand.nextDouble() * 5);
        u.addRatedItems(items.get(9), rand.nextDouble() * 5);
        valoracions.add(u);
        u = new Usuari(usuaris.get(9));
        u.addRatedItems(items.get(9), rand.nextDouble() * 5);
        u.addRatedItems(items.get(0), rand.nextDouble() * 5);
        valoracions.add(u);
    }

}
