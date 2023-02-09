/**
 * @file DriverDCG.java
 * @brief Codigo de la clase DriverDCG
 */
package dominio.controladores.drivers;

import dominio.clases.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @class DriverDCG
 * @brief Driver representativo de un ejemplo de ejecucion del algoritmo de evaluacion de recomendaciones DCG
 * @author Cristian Sanchez Estape
 */

public class DriverDCG {

    /**
     * @brief Metodo de testeo del DCG
     * @param lt Usuario con las valoraciones ideales para un conjunto de items
     * @param lr Usuario con las valoraciones predecidas para un conjunto de items
     */

    private static void testDCG(Usuari lt, Usuari lr) {
        System.out.println("Metodo de computo del DCG para un conjunto de valoraciones realizadas por un" +
                " mismo usuario");
        System.out.println(Avaluacio.DCG(lt, lr));
    }

    /**
     * @brief Metodo de testeo del computo de DCG ideal
     * @param vals Mapa con las valoraciones ideales de un conjunto dado de items
     */

    private static void testComputeIdealDCG(HashMap<Item, Double> vals) {
        System.out.println("Metodo que computa el DCG ideal de un conjunto de items");
        Avaluacio instance = new Avaluacio();
        Method computeIDCG;
        try {
            computeIDCG = instance.getClass().getDeclaredMethod("computeIdealDCG", HashMap.class);
            computeIDCG.setAccessible(true);
            System.out.println("" + computeIDCG.invoke(instance, vals));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo de ordenacion de las valoraciones de un item segun su valor
     * @param vals Valoracion asociada a un item
     */

    private static void testSortByValue(HashMap<Item, Double> vals) {
        System.out.println("Metodo que ordena las valoraciones (contenido) de un item segun su valor");
        HashMap<Item, Double> res;
        Avaluacio instance = new Avaluacio();
        Method sortByValue;
        try {
            sortByValue = instance.getClass().getDeclaredMethod("sortByValue", HashMap.class);
            sortByValue.setAccessible(true);
            res = (HashMap<Item, Double>) sortByValue.invoke(instance, vals);
            System.out.println("-----Vector ordenado:");
            for (Item i : res.keySet()) System.out.println("\t" + i.getId() + " -> " + vals.get(i));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo de testeo de la funcion logaritmo
     */

    private static void testLog() {
        System.out.println("Metodo que prueba la funcion de logaritmo");
        int i = 32, b = 2;
        Avaluacio instance = new Avaluacio();
        Method log;
        try {
            log = instance.getClass().getDeclaredMethod("log", int.class, int.class);
            log.setAccessible(true);
            System.out.println(log.invoke(instance, i, b));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

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
     * @brief Método local que implementa el test 1
     */

    private static void test1() {
        System.out.println("----- Test 1 -----");
        System.out.println("Test previo de los metodos usados por DCG ");
        System.out.print("---Computo del log_2(32): ");
        testLog();
        System.out.println("---Comprobacion de la ordenacion de un determinado vector de valoraciones segun el valor de las mismas: ");
        System.out.println("-----Vector original:");
        HashMap<Item, Double> vals = new HashMap<>();
        vals.put(new Item("1"), 12.);
        vals.put(new Item("5"), -5.);
        vals.put(new Item("-13"), 24.);
        vals.put(new Item("245"), 200.);
        vals.put(new Item("34"), 1.);
        for (Item i : vals.keySet()) System.out.println("\t" + i.getId() + " -> " + vals.get(i));
        testSortByValue(vals);
        System.out.println("-- Previsualizacion de los datos cableados para las siguientes pruebas --");
        System.out.println("\tValoracion predecida\tValoracion ideal");
        System.out.println("\t\t\t  3.\t\t\t\t\t3.");
        System.out.println("\t\t\t  1.\t\t\t\t\t3.");
        System.out.println("\t\t\t  2.\t\t\t\t\t2.");
        System.out.println("\t\t\t  3.\t\t\t\t\t2.");
        System.out.println("\t\t\t  2.\t\t\t\t\t1.");
        System.out.println("\t\t\t  0.\t\t\t\t\t0.");
        System.out.println("---Computo del DCG ideal para el conjunto de items cableado:");
        Usuari lr = new Usuari(1);
        Usuari lt = new Usuari(1);
        HashMap<Item, Double> ideal = new LinkedHashMap<>(), real = new LinkedHashMap<>();
        ideal.put(new Item("Doc1"), 3.);
        ideal.put(new Item("Doc2"), 3.);
        ideal.put(new Item("Doc3"), 2.);
        ideal.put(new Item("Doc4"), 2.);
        ideal.put(new Item("Doc5"), 1.);
        ideal.put(new Item("Doc6"), 0.);
        real.put(new Item("Doc1"), 3.);
        real.put(new Item("Doc2"), 1.);
        real.put(new Item("Doc3"), 2.);
        real.put(new Item("Doc4"), 3.);
        real.put(new Item("Doc5"), 2.);
        real.put(new Item("Doc6"), 0.);
        lr.setRatedItems(ideal);
        lt.setRatedItems(real);
        testComputeIdealDCG(ideal);
        System.out.println("---Computo del DCG normalizado para el conjunto de items cableado:");
        testDCG(lt, lr);
    }

    /**
     * @brief Metodo local que implementa el test 2
     */

    private static void test2() {
        System.out.println("----- Test 2 -----");
        System.out.println("Test de aplicacion del nDCG sobre un conjunto pequeño de datos");
        String path = System.getProperty("user.dir") + "/src/persistencia/data/DriversData/DCGTestSets/";
        Cjt_items c = new Cjt_items(path + "itemsSmallTest.csv", path + "data_types_1.csv");
        Datos ratingsKnown =  new Datos(path + "smallTestKnown.csv", c);
        Datos ratingsUnknown =  new Datos(path + "smallTestUnknown.csv", c);
        HashMap<Integer, HashMap<Item,Double>> LR = ratingsKnown.getUserRatings();
        HashMap<Integer, HashMap<Item,Double>> LT = ratingsUnknown.getUserRatings();
        for(Integer i: LR.keySet()) {
            Usuari lr = new Usuari(i), lt = new Usuari(i);
            lr.setRatedItems(LR.get(i));
            lt.setRatedItems(LT.get(i));
            System.out.println("-Usuario " + i + " --> " + Avaluacio.DCG(lr,lt));
        }
    }

    /**
     * @brief Metodo loca que implementa el test 3
     */

    private static void test3() {
        System.out.println("----- Test 3 -----");
        System.out.println("Test de aplicacion del nDCG sobre un conjunto grande de datos");
        String path = System.getProperty("user.dir") + "/src/persistencia/data/DriversData/DCGTestSets/";
        Cjt_items c = new Cjt_items(path + "itemsBigTest.csv", path + "data_types_2.csv");
        Datos ratingsKnown =  new Datos(path + "bigTestKnown.csv", c);
        Datos ratingsUnknown =  new Datos(path + "bigTestUnknown.csv", c);
        HashMap<Integer, HashMap<Item,Double>> LR = ratingsKnown.getUserRatings();
        HashMap<Integer, HashMap<Item,Double>> LT = ratingsUnknown.getUserRatings();
        for(Integer i: LR.keySet()) {
            Usuari lr = new Usuari(i), lt = new Usuari(i);
            lr.setRatedItems(LR.get(i));
            lt.setRatedItems(LT.get(i));
            System.out.println("-Usuario " + i + " --> " + Avaluacio.DCG(lr,lt));
        }
    }

}
