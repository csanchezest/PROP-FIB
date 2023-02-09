package dominio.clases;
import java.util.*;

/**
 * @class Cluster
 * @brief Clase que representa un Cluster
 * @author Muhammad Haris
 */

public class Cluster {

    /**
     * @brief Centroide del cluster
     */
    private Centroid centroid;

    /**
     * @brief Set de todos los usuarios que pertenecen al cluster
     */
    private HashSet<Usuari> usuaris = new HashSet<>();

    public Centroid getCentroid() {
        return centroid;
    }

    public HashSet<Usuari> getUsuaris() {
        return usuaris;
    }

    public void setCentroid(Centroid centroid) {
        this.centroid = centroid;
    }

    public void setUsuaris(HashSet<Usuari> usuaris) {
        this.usuaris = usuaris;
    }

    /**
     * @brief Metodo que añade el usuario u al set de usuarios
     * @param u El usuario a añadir
     */
    public void addUsuari(Usuari u) {
        usuaris.add(u);
    }

}

