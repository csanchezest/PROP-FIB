/**
 @file Collaborative.java
 @brief Codigo de la clase Collaborative

 */
package dominio.clases;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @class Collaborative
 * @brief Clase dedicada a la implementacion del algoritmo K-means
 * @author Muhammad Haris
 */


public class Collaborative extends Algorisme{

    public Collaborative(){
        super();
    }

    /**
     * @brief Instancia de Random()
     */

    private static final Random random = new Random();

    /**
     * @brief Metodo que ejecuta el algoritmo k-means
     * @param Usuaris Lista de usuarios del dataset
     * @param k Numero de clusters
     * @param distance Instancia de la clase DistanceUsers
     * @param n Numero maximo de iteraciones
     * @return K clusters
     */
    public ArrayList<Cluster> recommendations (List<Usuari> Usuaris, int k, DistanceUsers distance, int n) {
        List<Centroid> centroids = applyCentroids(Usuaris,k);
        ArrayList<Cluster> clusters = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            boolean isLastIteration = i == n - 1;
            int terminar = 0;
            for (Usuari Usuari : Usuaris) {
                if (i == 0) {
                    Centroid centroid = nearestCentroid(Usuari, centroids, distance, k);
                    if (Usuari.getUserId() != centroid.getCentroid_user_id()) {
                       centroid.recolocar(Usuari);
                    }
                    Usuari.setCentroid(centroid);
                    assignToCluster(clusters, Usuari, centroid);
                }
                else {
                    double dis = distance.calculateEuclideanDistance(Usuari.getRatedItems(), Usuari.getCentroid().getCentroid());
                    if (dis <= Usuari.getNearest_dist()) {
                        if (Usuari.getNearest_dist() == Double.MAX_VALUE) Usuari.setNearest_dist(dis);
                        ++terminar;
                    }
                    else {
                        Centroid centroid = nearestCentroid(Usuari, centroids, distance, k);
                        if (centroid != Usuari.getCentroid()) {
                            Usuari.getCentroid().eliminar(Usuari);
                            centroid.recolocar(Usuari);
                            eliminateFromCluster(Usuari, clusters);
                            Usuari.setCentroid(centroid);
                            assignToCluster(clusters, Usuari, centroid);
                        }
                    }
                }
            }
            boolean shouldTerminate = isLastIteration || terminar == Usuaris.size();
            if (shouldTerminate) {
                break;
            }
            centroids = relocateCentroids(clusters);
        }
        return clusters;
    }

    /**
     * @brief Metodo que elimina un usuario de su cluster
     * @param u El usuario a eliminar
     * @param clusters Lista de todos los clusters
     */
    public static void eliminateFromCluster(Usuari u, List<Cluster> clusters) {
        for (Cluster c: clusters) {
            if (c.getCentroid() == u.getCentroid()) {
                HashSet<Usuari> usuaris = new HashSet<>();
                usuaris = c.getUsuaris();
                usuaris.remove(u);
                c.setUsuaris(usuaris);
            }
        }
    }

    /**
     * @brief Metodo que genera y devuelve k centroides
     * @param Usuaris Lista de usuarios del dataset
     * @param k Numero de clusters
     * @return Lista de los centroides generados
     */

    public static List<Centroid> applyCentroids(List<Usuari> Usuaris, int k) {
        List<Centroid> centroids = new ArrayList<>();
        for (int j = 0; j < k; ++j) {
            int aux = 0;
            Usuari u = Usuaris.get(random.nextInt(Usuaris.size()));
            for (Centroid ce : centroids) {
                if (u.getUserId() != ce.getCentroid_user_id()) {
                    ++aux;
                }
            }
            if (aux == centroids.size()) {
                Centroid c = new Centroid();
                c.setCentroid_number(j + 1);
                u.getRatedItems().forEach((key, value) -> {
                    c.getCentroid().put(key, value);
                    c.getValoraciones().put(key, value);
                    c.getTimes().put(key, 1);
                });
                c.setCentroid_user_id(u.getUserId());
                centroids.add(c);
            } else --j;
        }
        return centroids;

    }

    /**
     * @brief Metodo que recoloca el centroide de todos los clusters respecto al promedio de los usuarios asignados
     * @param clusters Los clusters actuales
     * @return Lista de los centroides recolocados
     */

    public static List<Centroid> relocateCentroids(ArrayList<Cluster> clusters) {
        List<Centroid> centroids = new ArrayList<>();
        for (Cluster c : clusters) {
            Centroid ce = average(c.getCentroid());
            centroids.add(ce);
        }
        return centroids;
    }

    /**
     * @brief Metodo que mueve el centroide a su posicion promedio. Se suman las valoraciones de los items en comun de todos los usuarios pertenecientes al cluster y se divide entre el numero de veces que aparece el item.
     * @param centroid El centroide
     * @return El centroide promedio
     */

    public static Centroid average(Centroid centroid) {
        HashMap<Item, Double> average = new HashMap<>();
        for (Item key : centroid.getValoraciones().keySet()) {
            average.put(key, centroid.getValoraciones().get(key)/centroid.getTimes().get(key));
        }
        centroid.setCentroid(average);
        return centroid ;
    }

    /**
     * @brief Metodo que devuelve los items en comun entre un usuario y un centroide
     * @param f1 Map que guarda los items del usuario y sus respectivas valoraciones
     * @param f2 Map que guarda los items del centroide y sus respectivas valoraciones
     * @return Numero de items en comun
     */

    public static int calculaItemsComun(Map<Item, Double> f1, Map<Item, Double> f2) {
        int i = 0;
        for (Item key : f1.keySet()) {
            if (f2.containsKey(key) == true) {
                ++i;
            }
        }

        return i;
    }

    /**
     * @brief Metodo que devuelve el centroide mas cercano a un usuario
     * @param Usuari El usuario
     * @param centroids La lista de todos los centroides
     * @param distance Instancia de la clase DistanceUsers
     * @return El centroide mas cercano al usuario
     */

    public static Centroid nearestCentroid(Usuari Usuari, List<Centroid> centroids, DistanceUsers distance, int k) {
        double minimumDistance = Double.MAX_VALUE;
        int maxComun = -1;
        Centroid nearest = new Centroid();
        for (Centroid centroid : centroids) {
            double currentDistance =  distance.calculateEuclideanDistance(Usuari.getRatedItems(), centroid.getCentroid());
            int currentComun = calculaItemsComun(Usuari.getRatedItems(), centroid.getCentroid());
            if (currentDistance < minimumDistance && currentDistance != -1 || currentDistance == minimumDistance && currentDistance != -1 && currentComun > maxComun) {
                minimumDistance = currentDistance;
                maxComun = currentComun;
                nearest = centroid;

            }

        }
        if (nearest.getCentroid().size() == 0) {
            nearest = centroids.get(random.nextInt(k));
        }
        Usuari.setNearest_dist(minimumDistance);
        return nearest;
    }

    /**
     * @brief Metodo que asigna el usuario a su cluster correspondiente
     * @param clusters La lista de todos los clusters
     * @param Usuari El usuario a asignar
     * @param centroid El centroide al que pertenece el usuario
     */

    public static void assignToCluster(ArrayList<Cluster> clusters, Usuari Usuari, Centroid centroid) {
        boolean exist = false;
        for (Cluster c : clusters) {
            if (c.getCentroid() == centroid) {
                exist = true;
                c.addUsuari(Usuari);
            }
        }

        if (!exist) {
            Cluster cl = new Cluster();
            cl.setCentroid(centroid);
            HashSet<Usuari> u = new HashSet<>();
            u.add(Usuari);
            cl.setUsuaris(u);
            clusters.add(cl);
        }

    }

    /**
     * @brief Metodo que encuentra la distancia del usuario más lejano a su centroide de entre todos los clusters
     * @param clusters La lista de todos los clusters
     * @param distance  Instancia de la clase DistanceUsers
     * @return La distancia del usuario más lejano
     */
    public static double maxDistanceUserCentroid(ArrayList<Cluster> clusters, DistanceUsers distance) {
        Double maxDistance = Double.MIN_VALUE;
        for (Cluster c: clusters) {
            HashSet<Usuari>  users = c.getUsuaris();
            Iterator<Usuari> it = users.iterator();
            while (it.hasNext()) {
                double d = distance.calculateEuclideanDistance(it.next().getRatedItems(), c.getCentroid().getCentroid());
                if (d > maxDistance) maxDistance = d;
            }
        }
        return maxDistance;
    }

    /**
     * @brief Metodo que encuentra la k optima y ejecuta el algoritmo k-means al completo
     * @param users Lista de usuarios del dataset
     * @return K clusters
     */

    public static ArrayList<Cluster> ejecuta_k_means(List<Usuari> users) {
        ArrayList<ArrayList<Cluster>> clustersDef = new ArrayList<>();
        List<Double> maxDistances1 = new ArrayList<>();
        double mindis = Double.MAX_VALUE;
        int k_definitiva = 0;
        int k_alternativa = 0;
        boolean acabat = false;
        for (int k1 = 3; k1 <= 12; ++k1) {
            ArrayList<Cluster> clusters12 = new Collaborative().recommendations(users,k1,new DistanceUsers(),100);
            clustersDef.add(clusters12);
            double mdistance1 = maxDistanceUserCentroid(clusters12, new DistanceUsers());
            maxDistances1.add(mdistance1);
            if (mdistance1 < mindis) {
                mindis = mdistance1;
                k_alternativa = k1;
            }
            for (int i = 0; i < maxDistances1.size(); ++i) {
                if (i != maxDistances1.size() - 1) {
                    if (maxDistances1.get(i) >= maxDistances1.get(i+1)) {
                        if (maxDistances1.get(i) - maxDistances1.get(i+1) < 1) {
                            k_definitiva = i + 3;
                            acabat = true;
                            break;
                        }
                    }
                    else {
                        if (maxDistances1.get(i+1) - maxDistances1.get(i) > 0.2) {
                            k_definitiva = i + 3;
                            acabat = true;
                            break;
                        }
                    }
                }
            }
            if (acabat) break;
        }
        if (k_definitiva != 0) return clustersDef.get(k_definitiva-3);
        else {
            ArrayList<Cluster> aux;
            k_definitiva = k_alternativa;
            aux = new Collaborative().recommendations(users,k_definitiva,new DistanceUsers(),100);
            return aux;
        }
    }

}
