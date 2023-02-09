/**
 @file DriverKMeans.java
 @brief Codigo de la clase DriverKMeans


 */
package dominio.controladores.drivers;
import dominio.clases.*;

import java.util.*;

/**
 * @class DriverKMeans
 * @brief Driver representativo de un ejemplo de ejecucion del algoritmo K-means
 * @author Muhammad Haris
 */


public class DriverKMeans {

    /**
     * @brief Metodo de testeo de la aplicacion total del algoritmo K-means (incluye el calculo del parametro k)
     * @param a Instancia de la clase Collaborative
     * @param usuaris Lista de los usuarios a tratar
     */
    public static int testEjecuta_K_Means (Collaborative a, List<Usuari> usuaris) {
        System.out.println("Método que ejecuta el algoritmo k-means. Devuelve k clusters con sus centroides y los usuarios correspondientes");
        ArrayList<Cluster> clusters = a.ejecuta_k_means(usuaris);
        for (Cluster c : clusters) {
            System.out.println("-------------------------- CLUSTER " + c.getCentroid().getCentroid_number() + " ----------------------------");
            System.out.println("Items:");
            System.out.println("");
            for(Item i: c.getCentroid().getCentroid().keySet()) System.out.println(i.getId() + "\t" + c.getCentroid().getCentroid().get(i));
            System.out.println("");
            System.out.println("Users:");
            System.out.println("");
            for (Usuari Usuari : c.getUsuaris()) {
                System.out.println(Usuari.getUserId());
            }
            System.out.println();
            System.out.println();
        }
        return clusters.size();

    }

    /**
     * @brief Metodo de testeo del metodo applyCentroids
     * @param a Instancia de la clase Collaborative
     * @param usuaris Lista de los usuarios a tratar
     * @param k Numero de clusters
     */
    public static List<Centroid> testApplyCentroids (Collaborative a, List<Usuari> usuaris, int k) {
        System.out.println("Método que genera y devuelve k centroides");
        List<Centroid> l = a.applyCentroids(usuaris, k);
        int j = 1;
        for(Centroid centroid: l) {
            System.out.println("Centroid " + j + ":");
            for(Item i: centroid.getCentroid().keySet()) System.out.println(i.getId() + "\t" + centroid.getCentroid().get(i));
            System.out.println("--------");
            ++j;
        }
        return l;
    }

    /**
     * @brief Metodo de testeo del metodo relocateCentroids
     * @param a Instancia de la clase Collaborative
     * @param clusters Los clusters actuales
     */
    public static void testRelocateCentroids(Collaborative a, ArrayList<Cluster> clusters) {
        System.out.println("Método que recoloca el centroide de todos los clusters respecto al promedio de los usuarios asignados");
        List<Centroid> l = a.relocateCentroids(clusters);
        for(Centroid centroid: l) {
            for(Item i: centroid.getCentroid().keySet()) System.out.println(i.getId() + "\t" + centroid.getCentroid().get(i));
            System.out.println("--------");
        }
    }

    /**
     * @brief Metodo de testeo del metodo calculaItemsComun
     * @param a Instancia de la clase Collaborative
     * @param f1 Map que guarda los items del usuario y sus respectivas valoraciones
     * @param f2 Map que guarda los items del centroide y sus respectivas valoraciones
     */
    public static void testCalculaItemsComun(Collaborative a, Map<Item, Double> f1, Map<Item, Double> f2) {
        System.out.println("Método que devuelve los items en común entre un usuario y un centroide");
        int c = a.calculaItemsComun(f1,f2);
        System.out.println(c);
    }

    /**
     * @brief Metodo de testeo del metodo nearestCentroid
     * @param a Instancia de la clase Collaborative
     * @param Usuari El usuario
     * @param centroids La lista de todos los centroides
     * @param distance Instancia de la clase DistanceUsers
     */

    public static Centroid testNearestCentroid(Collaborative a, Usuari Usuari, List<Centroid> centroids, DistanceUsers distance, int k) {
        System.out.println("Método que devuelve el centroide más cercano a un usuario");
        Centroid c = a.nearestCentroid (Usuari, centroids, distance,k);
        System.out.println("Usuari " + Usuari.getUserId());
        System.out.println("Centroid " + c.getCentroid_number() + ":");
        for(Item i: c.getCentroid().keySet()) System.out.println(i.getId() + "\t" + c.getCentroid().get(i));
        return c;
    }

    /**
     * @brief Metodo de testeo del metodo assignToCluster
     * @param a Instancia de la clase Collaborative
     * @param clusters Clusters
     * @param Usuari El usuario
     * @param centroid El centroide al que pertenece el usuario
     */
    public static void testAssignToCluster(Collaborative a, ArrayList<Cluster> clusters, Usuari Usuari, Centroid centroid) {
        System.out.println(" Método que asigna el usuario a su centroide correspondiente");
        a.assignToCluster(clusters, Usuari, centroid);
    }

    public static void main (String args[]) {
        Scanner sc = new Scanner(System.in);
        int ds;
        System.out.print("Indique si quiere realizar el test con datos inventados o del dataset (0 - Inventados; 1 - Dataset): ");
        ds = sc.nextInt();
        while (ds != 0 && ds != 1) {
            System.out.print("Número incorrecto, inténtalo de nuevo:");
            ds = sc.nextInt();
        }
        switch (ds) {
            case 0:
                System.out.println("Test con datos inventados:");
                List<Usuari> Usuaris = new ArrayList<>();
                String pathToCsv = System.getProperty("user.dir") + "/src/persistencia/data/DriversData/KmeansTestSets/SmallSizeTest/";
                String pathnuevo = System.getProperty("user.dir") + "/src/persistencia/data/DriversData/KmeansTestSets/SmallSizeTest/";
                Cjt_items dataset = new Cjt_items(pathToCsv + "smallTestItemsKmeans.csv", pathnuevo + "data_types_smallTest.csv");
                Datos ratings = new Datos(pathToCsv + "smallTestRatingsKmeans.csv", dataset);
                for (Usuari us : ratings.getUsers()) {
                    Usuaris.add(us);
                }
                Collaborative c = new Collaborative();
                ArrayList<Cluster> clusters = new ArrayList<>();
                int k = 0;
                k = testEjecuta_K_Means(c,Usuaris);
                System.out.println("K ÓPTIMA = " + k);
                List<Centroid> centroids = testApplyCentroids(c,Usuaris,k);
                Centroid ce = testNearestCentroid(c,Usuaris.get(8),centroids,new DistanceUsers(),k);
                for (int i = 0; i < k; ++i) {
                    testAssignToCluster(c, clusters, Usuaris.get(i), centroids.get(i));
                }
                testAssignToCluster(c, clusters, Usuaris.get(8), ce);
                break;
            case 1:
                System.out.println("Test con el dataset Movielens 250:");
                List<Usuari> Usuaris2 = new ArrayList<>();
                pathnuevo = System.getProperty("user.dir") + "/Datasets/Movielens/";
                String pathToCsv2 = System.getProperty("user.dir") + "/Datasets/Movielens/250/";
                String pathnuevo2 = System.getProperty("user.dir") + "/Datasets/Movielens/";
                Cjt_items dataset2 = new Cjt_items(pathToCsv2 + "items.csv", pathnuevo2 + "data_types_movies.csv");
                Datos ratings2 = new Datos(pathToCsv2 + "ratings.db.csv", dataset2);
                for (Usuari us : ratings2.getUsers()) {
                    Usuaris2.add(us);
                }
                Collaborative c2 = new Collaborative();
                ArrayList<Cluster> clusters2 = new ArrayList<>();
                int k2 = 0;
                k2 = testEjecuta_K_Means(c2,Usuaris2);
                System.out.println("K ÓPTIMA = " + k2);
                List<Centroid> centroids2 = testApplyCentroids(c2,Usuaris2,k2);
                Centroid ce2 = testNearestCentroid(c2,Usuaris2.get(8),centroids2,new DistanceUsers(),k2);
                for (int i = 0; i < k2; ++i) {
                    testAssignToCluster(c2, clusters2, Usuaris2.get(i), centroids2.get(i));
                }
                testAssignToCluster(c2, clusters2, Usuaris2.get(8), ce2);
        }
    }
}

