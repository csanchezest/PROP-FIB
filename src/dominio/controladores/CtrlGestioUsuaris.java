package dominio.controladores;

import dominio.clases.Datos;
import dominio.clases.Item;
import dominio.clases.Usuari;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @class CtrlGestioUsuaris
 * @brief Controlador que gestiona toda la parte de gestion de usuarios
 */

public class CtrlGestioUsuaris {


    /**
     * @brief String que almacena el identificador del usuario logeado en el sistema
     */
    private String user;

    /**
     * @brief Lista con todos los usuarios presentes en el dataset cargado en el sistema
     */

    private List<Usuari> users;

    /**
     * @brief Objeto que almacena la informacion asociada al usuario logeado en el sistema
     */
    private Usuari u;

    /**
     * @brief Entero que almacena el identificador asociado al usuario logeado en el sistema
     */

    private int userId;

    /**
     * @brief Metodo constructor del controlador de gestion de usuarios
     */

    public CtrlGestioUsuaris() {
        user = "";
        userId = -1;
        u = null;
    }

    public void setUsers(List<Usuari> users) {
        this.users = new ArrayList<>(users);
    }

    public HashMap<String, Double> getRatedItems(int id) {
        Usuari aux = new Usuari(id);
        for (Usuari us : users) {
            if (us.getUserId() == id) aux = us;
        }
        HashMap<String, Double> u = new HashMap<>();
        HashMap<Item,Double> rated = aux.getRatedItems();
        for (Item i: rated.keySet()) u.put(i.getId(),rated.get(i));
        return u;
    }

    public void setUser(Usuari u) { this.u=u; }

    public void setUserId(int id) { userId=id; }

    public Usuari getUsuari() { return u; }

    public int getUserId() { return userId; }

    public void searchUser(String text) { user = text; }

    /**
     * @brief Metodo que comprueba si existe un usuario en el dataset cargado
     * @param id Entero con el identificador del usuario de interes
     * @return Booleano cierto o falso segun la condicion aqui descrita
     */

    public boolean existeUsuario(int id) {
        return users.contains(new Usuari(id));
    }
}
