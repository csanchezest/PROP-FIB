/**
 * @file DriverFuncionalidades.java
 * @brief Codigo de la clase DriverFuncionalidades
 */
package dominio.controladores.drivers;

import dominio.clases.Cjt_items;
import dominio.clases.Item;
import dominio.controladores.ControladorDominio;
import presentacion.ControladorPresentacion;

import javax.swing.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
/**
 * @author Cristian Sanchez Estape
 * @class DriverFuncionalidades
 * @brief Driver representativo de las principales funcionalidades presentes en el proyecto
 */
public class DriverFuncionalidades {

    /**
     * @brief Strings usados para almacenar las predicciones generadas por los algoritmos principales del proyecto
     */

    private static String recomendacion1, recomendacion2, recomendacion3;

    /**
     * @brief Metodo main
     */

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Bienvenido al driver de funcionalidades. " +
                "A continuacion, se iran repasando las funcionalidades implementadas.");
        testLecturaYCargaDataset();
        testLecturaYCargaDatasetAnomalo();
        testAlmacenarDatosPreprocesados();
        testCargarDatosPreprocesados();
        testGenerarPrediccion();
        testGuardarPrediccion();
        testBusquedaItem();
        testBusquedaUsuario();
        testEleccionSubconjuntoColumnas();
    }

    /**
     * @brief Test dedicado a la lectura y carga de un dataset cableado (en este caso Movielens-250)
     */

    private static void testLecturaYCargaDataset() {
        JOptionPane.showMessageDialog(null, "Test de carga del dataset: cargamos un dataset " +
                "de prueba (ubicado en X directorio)");
        ControladorDominio ctrl = new ControladorDominio(null);
        JOptionPane.showMessageDialog(null, "Cableamos un usuario con id 44868");
        ctrl.setUser(44868); //ID provisional
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.loadDataset();
        JOptionPane.showMessageDialog(null, "Dataset cargado correctamente");
    }

    /**
     * @brief Test dedicado a la lectura y carga de un dataset cableado y anomalo, es decir, que contiene atributos erroneos (en este caso Movielens-750)
     */

    private static void testLecturaYCargaDatasetAnomalo() {
        String n = "";
        JOptionPane.showMessageDialog(null, "Test de carga del dataset anomalo: cargamos un dataset " +
                "de prueba con algun atributo erroneo (ubicado en X directorio)");
        if(n==null) System.exit(0);
        ControladorPresentacion ctrl = new ControladorPresentacion();
        ctrl.setUser(44868);
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/750/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/750/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/750/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.loadDataset();
        Cjt_items ogDataset = new Cjt_items(System.getProperty("user.dir") + "/Datasets/Movielens/750/items.csv",
                System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv");
        Cjt_items newDataset = null;
        try {
            newDataset = new Cjt_items(ctrl.getPathToUpdatedFile(), System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Has seleccionado la opcion de automatizacion o la de " +
                    "ignorar, con lo cual no podemos comprobar que diferencia habia con el original");
            return;
        }
        //Comparar newDataset amb ogDataset
        List<String> headers = ogDataset.getHeaders();
        for (String header : headers) System.out.print(header + " || ");
        System.out.println();
        HashMap<String, Item> olditems = ogDataset.getItems();
        HashMap<String, Item> newitems = newDataset.getItems();
        System.out.println("----- Dataset viejo -----");
        for (String key : olditems.keySet()) {
            for (int i = 0; i < headers.size(); i++) {
                ctrl.searchItem(key);
                try {
                    System.out.print(ctrl.getColumnaActual(headers.get(i)) + " || ");
                } catch (Exception e) {
                    System.out.println("Error en el driver de funcionalidades, en el metodo de test de lectura y escritura" +
                            "de un dataset anomalo");
                }
            }
            System.out.println();
        }
        System.out.println("----- Dataset nuevo -----");
        for (String key : newitems.keySet()) {
            for (int i = 0; i < headers.size(); i++) {
                ctrl.searchItem(key);
                try {
                    System.out.print(ctrl.getColumnaActual(headers.get(i)) + " || ");
                } catch (Exception e) {
                    System.out.println("Error en el driver de funcionalidades, en el metodo de test de lectura y escritura" +
                            "de un dataset anomalo");
                }
            }
            System.out.println();
        }
        JOptionPane.showMessageDialog(null, "Dataset cargado correctamente");
    }

    /**
     * @brief Test dedicado al almacenamiento de datos preprocesados (en este caso, aquellos pertenecientes al dataset)
     */

    private static void testAlmacenarDatosPreprocesados() {
        JOptionPane.showMessageDialog(null, "Test de almacenamiento de la informacion contenida" +
                " por el dataset");
        ControladorDominio ctrl = new ControladorDominio(null);
        JOptionPane.showMessageDialog(null, "Cableamos un usuario con id 44868");
        ctrl.setUser(44868); //ID provisional
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.guardarLanguage("Ingles");
        ctrl.loadDataset();
        ctrl.guardarTipoDataset("Peliculas");
        ctrl.guardarDatosPreprocesados();
        JOptionPane.showMessageDialog(null, "Datos preprocesados almacenados correctamente");
    }

    /**
     * @brief Test dedicado a la comprobacion de la lectura de los datos preprocesados
     */

    public static void testCargarDatosPreprocesados() {
        JOptionPane.showMessageDialog(null, "Test de lectura de la informacion preprocesada");
        ControladorDominio ctrl = new ControladorDominio(null);
        JOptionPane.showMessageDialog(null, "Cableamos un usuario con id 44868");
        ctrl.setUser(44868); //ID provisional
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.guardarLanguage("Ingles");
        ctrl.loadDataset();
        ctrl.guardarTipoDataset("Peliculas");
        ctrl.cargarDatosPreprocesados(ctrl.getDatasetPreprocesado());
        JOptionPane.showMessageDialog(null, "Datos preprocesados almacenados correctamente");
    }

    /**
     * @brief Test dedicado a la comprobacion de la correcta generacion de predicciones por parte de los algoritmos del proyecto
     */

    private static void testGenerarPrediccion() {
        JOptionPane.showMessageDialog(null, "Test de generacion de predicciones (y recomendaciones) para un determinado usuario.");
        ControladorPresentacion ctrl = new ControladorPresentacion();
        JOptionPane.showMessageDialog(null, "Cableamos un usuario con id 44868");
        ctrl.setUser(44868); //ID provisional
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.guardarUnknown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.unknown.csv"));
        ctrl.guardarLanguage("Ingles");
        ctrl.setK(10);
        ctrl.loadDataset();
        JOptionPane.showMessageDialog(null, "Realizaremos 3 pruebas, una para cada algoritmo presente en el proyecto. Ademas, fijaremos el numero de items recomendados a 10");
        JOptionPane.showMessageDialog(null, "Prueba para el Collaborative:");
        HashMap<String,Double> ratings = ctrl.makeRecomendation(0, new ArrayList<>());
        String recommendation = ctrl.getRecomendacion();
        NumberFormat formatter = new DecimalFormat("#0.000");
        recomendacion1 = "";
        for (String i : ratings.keySet())
            recomendacion1 += (" " + i + " --> " + formatter.format(ratings.get(i).doubleValue())) + "\n";
        recomendacion1 += recommendation;
        System.out.println(recomendacion1);
        JOptionPane.showMessageDialog(null,"Recomendacion generada. Compruebe el output en la consola.");
        JOptionPane.showMessageDialog(null, "Prueba para el Content-based:");
        ArrayList<Boolean> list = new ArrayList<>(Arrays.asList(new Boolean[ctrl.getHeaders().size()]));
        Collections.fill(list, Boolean.FALSE);
        ratings = ctrl.makeRecomendation(1,list);
        recommendation = ctrl.getRecomendacion();
        recomendacion2 = "";
        for (String i : ratings.keySet())
            recomendacion2 += (" " + i + " --> " + formatter.format(ratings.get(i).doubleValue())) + "\n";
        recomendacion2 += recommendation;
        System.out.println(recomendacion2);
        JOptionPane.showMessageDialog(null,"Recomendacion generada. Compruebe el output en la consola.");
        JOptionPane.showMessageDialog(null, "Prueba para el Hybrid:");
        list = new ArrayList<>(Arrays.asList(new Boolean[ctrl.getHeaders().size()]));
        Collections.fill(list, Boolean.FALSE);
        ratings = ctrl.makeRecomendation(2,list);
        recommendation = ctrl.getRecomendacion();
        recomendacion3 = "";
        for (String i : ratings.keySet())
            recomendacion3 += (" " + i + " --> " + formatter.format(ratings.get(i).doubleValue())) + "\n";
        recomendacion3 += recommendation;
        System.out.println(recomendacion3);
        JOptionPane.showMessageDialog(null,"Recomendacion generada. Compruebe el output en la consola.");
    }

    /**
     * @brief Test dedicado al almacenamiento de las predicciones previamente generadas
     */

    private static void testGuardarPrediccion() {
        JOptionPane.showMessageDialog(null, "Test de guardado de las predicciones (y recomendaciones) generadas por los algoritmos aplicados");
        ControladorPresentacion ctrl = new ControladorPresentacion();
        JOptionPane.showMessageDialog(null, "Cableamos un usuario con id 44868");
        ctrl.setUser(44868); //ID provisional
        ctrl.guardarRecomendacion(recomendacion1);
        ctrl.guardarRecomendacion(recomendacion2);
        ctrl.guardarRecomendacion(recomendacion3);
        JOptionPane.showMessageDialog(null, "Ficheros creados exitosamente");
    }

    /**
     * @brief Test dedicado a la busqueda de un item dentro de un dataset dado; como precondicion, dicho dataset debe haber sido cargado
     */

    private static void testBusquedaItem() {
        JOptionPane.showMessageDialog(null,"Test de búsqueda de un item dentro de un dataset cableado.");
        ControladorPresentacion ctrl = new ControladorPresentacion();
        ctrl.setUser(44868);
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.loadDataset();
        String ans = JOptionPane.showInputDialog(null,"Para el dataset Movielens 250, " +
                "introduzca el ID de un item deseado.","Pedir item",
                JOptionPane.QUESTION_MESSAGE);
        while(true) {
            try {
                if(ans == null) break;
                if(ctrl.existeItem(ans)) JOptionPane.showMessageDialog(null,"El item " + ans + " se encuentra dentro del dataset aqui tratado");
                else JOptionPane.showMessageDialog(null,"El item " + ans + " no se encuentra en el dataset.");
                ans = JOptionPane.showInputDialog(null,"Indica de nuevo otro ID de item o bien \"n\" para parar la prueba");
            } catch (Exception e) {
                ans = JOptionPane.showInputDialog(null,"Has introducido un valor erroneo. Vuelve a intentarlo.");
            }
        }
    }

    /**
     * @brief Test dedicado a la busqueda de un usuario dentro de un dataset dado
     */

    private static void testBusquedaUsuario() {
        JOptionPane.showMessageDialog(null,"Test de búsqueda de un usuario para un juego de pruebas cableado.");
        String ans = JOptionPane.showInputDialog(null,"Para el dataset Movielens 250, " +
                "introduzca el ID de un usuario deseado o, alternativamente, una n para terminar la prueba.",JOptionPane.YES_OPTION);
        ControladorPresentacion ctrl = new ControladorPresentacion();
        ctrl.setUser(44868);
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.loadDataset();
        while(true) {
            try {
                if(ans == null) break;
                if(ans.equals("n") || ans.equals("")) break;
                int i = Integer.parseInt(ans);
                if(i<0) throw new Exception();
                if(ctrl.existeUsuario(i)) JOptionPane.showMessageDialog(null,"El item " + ans + " se encuentra dentro del dataset aqui tratado");
                else JOptionPane.showMessageDialog(null,"El item " + ans + " no se encuentra en el dataset aqui tratado.");
                ans = JOptionPane.showInputDialog(null,"Indica de nuevo otro ID de item o bien \"n\" para parar la prueba");
            } catch (Exception e) {
                ans = JOptionPane.showInputDialog(null,"Has introducido un valor erroneo. Vuelve a intentarlo.");
            }
        }

    }

    /**
     * @brief Test dedicado a la ejemplificacion del proceso de seleccion de un subconjunto de atributos de un determinado dataset,
     * proceso que particularmente afecta al algoritmo Content-based y Hybrid.
     */

    private static void testEleccionSubconjuntoColumnas() {
        JOptionPane.showConfirmDialog(null, "Test particular para la generacion de predicciones con los algoritmos Content-Based e Hybrid. " +
                "Se trata de la eleccion de subconjuntos de atributos de un conjunto de items dado (y una k fija a 10).");
        ControladorPresentacion ctrl = new ControladorPresentacion();
        ctrl.setUser(44868);
        ctrl.guardarItems(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/items.csv")); //Paths provisionals
        ctrl.guardarRatings(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.db.csv"));
        ctrl.guardarKnown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.known.csv"));
        ctrl.guardarAuxiliares(new File(System.getProperty("user.dir") + "/Datasets/Movielens/data_types_movies.csv"));
        ctrl.guardarUnknown(new File(System.getProperty("user.dir") + "/Datasets/Movielens/250/ratings.test.unknown.csv"));
        ctrl.guardarLanguage("Ingles");
        ctrl.setK(10);
        ctrl.loadDataset();
        String s = JOptionPane.showInputDialog(null, "Inserta el numero de columnas que quieres elegir");
        int j;
        while (true) {
            try {
                j = Integer.parseInt(s);
                if(j<0) throw new Exception();
                break;
            } catch (Exception e) {
                s = JOptionPane.showInputDialog(null, "Vuelve a introducir el numero de " +
                        "columnas que quieres elegir");
            }
        }
        List<String> headers = ctrl.getHeaders();
        Object[] items = headers.toArray();
        JComboBox comboBox = new JComboBox(items);
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < j; i++) {
            JOptionPane.showMessageDialog(null, comboBox, "Selecciona un atributo", JOptionPane.QUESTION_MESSAGE);
            columns.add((String) comboBox.getSelectedItem());
        }
        List<Boolean> res = new ArrayList<>();
        for(int i=0;i<headers.size();i++) {
            if(columns.contains(headers.get(i))) res.add(true);
            else res.add(false);
        }
        HashMap<String,Double> ratings = ctrl.makeRecomendation(1, res);
        String recommendation = ctrl.getRecomendacion();
        NumberFormat formatter = new DecimalFormat("#0.000");
        String recomendacion = "";
        for (String i : ratings.keySet())
            recomendacion += (" " + i + " --> " + formatter.format(ratings.get(i).doubleValue())) + "\n";
        recomendacion += recommendation;
        System.out.println(recomendacion);
        JOptionPane.showMessageDialog(null,"Recomendacion generada. Compruebe el output en la consola.");
    }

}
