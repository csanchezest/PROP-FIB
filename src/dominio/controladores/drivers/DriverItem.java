/**
 @file DriverItem.java
 @brief Codigo de la clase DriverItem

 */
package dominio.controladores.drivers;
import dominio.clases.*;

import java.time.LocalDate;
import java.util.*;

/**
 * @class DriverItem
 * @brief Driver para testear la clase Item
 * @author Muhammad Haris
 */

public class DriverItem {

    /**
     * @brief Asigna a una instancia de item el identificador id
     * @param i Objeto de la clase Item
     * @param id indica el identificador que se asignara al item
     */

    public static void testSetId (Item i, String id) {
        System.out.println("Test setId");
        new Item(id);
        i.setId(id);
        System.out.println("ItemId: " + id);
    }

    /**
     * @brief Obtiene el identificador asociado al item
     * @param i Objeto de la clase Item
     */

    public static void testGetId (Item i) {
        System.out.println("Test getId");
        System.out.println("ItemId: " + i.getId());
    }

    /**
     * @brief Asigna a una instancia de item los n atributos no nulos dados en los parametros
     * @param i Objeto de la clase Item
     * @param attrI Lista de parametros de tipo Integer a asignar al item
     * @param attrD Lista de parametros de tipo Double a asignar al item
     * @param attrStr Lista de parametros de tipo String a asignar al item
     * @param attrCatBits Lista de parametros de tipo BitSet a asignar al item
     * @param attrDate Lista de parametros de tipo LocalDate a asignar al item
     * @param attrBool Lista de parametros de tipo attrBool a asignar al item
     */
    public static void testSetAttributes (Item i, ArrayList<Long> attrI, ArrayList<Double> attrD, ArrayList<String> attrStr, ArrayList<BitSet> attrCatBits, ArrayList<LocalDate> attrDate, ArrayList<String> attrBool, HashMap<Integer, ArrayList<String>> CatAttr) {
        System.out.println("Test setAttributes");
        i.setAttributes(attrI, attrD, attrStr, attrCatBits, attrDate, attrBool,null);
    }

    /**
     * @brief Metodo que devuelve el conjunto de atributos enteros del item
     * @param i Objeto de la clase Item
     */
    public static void testGetIntAttributes (Item i) {
        System.out.println("Test getIntAttributes");
        System.out.println("IntAttributes: " + i.getIntAttributes());
    }

    /**
     * @brief Metodo que devuelve el conjunto de atributos reales del item
     * @param i Objeto de la clase Item
     */
    public static void testGetDoubleAttributes (Item i) {
        System.out.println("Test getDoubleAttributes");
        System.out.println("DoubleAttributes: " + i.getDoubleAttributes());
    }

    /**
     * @brief Metodo que devuelve el conjunto de atributos de tipo string del item
     * @param i Objeto de la clase Item
     */
    public static void testGetStringAttributes (Item i) {
        System.out.println("Test getStringAttributes");
        System.out.println("StringAttributes: " + i.getStringAttributes());
    }

    /**
     * @brief Metodo que devuelve el conjunto de atributos categoricos del item
     * @param i Objeto de la clase Item
     */
    public static void testGetCategoryPscBits (Item i) {
        System.out.println("Test getCategoryPscBits");
        System.out.println("CategoryPscBits: " + i.getCategoryPscBits());
    }
    
    /**
     * @brief Metodo que devuelve el conjunto de atributos categoricos del item
     * @param i Objeto de la clase Item
     */
    public static void testGetCategoricalAttributes (Item i) {
        System.out.println("Test getCategoricalAttributes");
        System.out.println("Categories: " + i.getCategoricalAttributes());
    }

    /**
     * @brief Metodo que devuelve el conjunto de atributos fecha del item
     * @param i Objeto de la clase Item
     */
    public static void testGetDateAttributes (Item i) {
        System.out.println("Test getDateAttributes");
        System.out.println("DateAttributes: " + i.getDateAttributes());
    }

    /**
     * @brief Metodo que devuelve el conjunto de atributos booleanos del item
     * @param i Objeto de la clase Item
     */
    public static void testGetBooleanAttributes (Item i) {
        System.out.println("Test getBooleanAttributes");
        System.out.println("BooleanAttributes: " + i.getBooleanAttributes());
    }

    /**
     * @brief Metodo que devuelve la totalidad de atributos del item en un contenedor de objetos genericos
     * @param i Objeto de la clase Item
     */
    public static void testGetItemAttributes (Item i) {
        System.out.println("Test getItemAttributes");
        System.out.println("ItemAttributes: " + i.getItemAttributes());
    }

    public static void testSetWordFrequencies(Item i, LinkedHashMap<Integer,HashMap<String,Integer>> wordFrequencies) {
        System.out.println("Test setWordFrequencies");
        i.setWordFrequencies(wordFrequencies);
    }

    public static void testGetWordFrequencies(Item i) {
        System.out.println("Test getWordFrequencies: ");
        LinkedHashMap<Integer,HashMap<String,Integer>> wordFreq = i.getWordFrequencies();
        for (Integer key : wordFreq.keySet()) {
            for (String key2: wordFreq.get(key).keySet()) {
                System.out.println("Columna: " + key + " La palabra " + key2 + " aparece " + wordFreq.get(key).get(key2) + " veces");
            }
        }
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
                Item i = new Item();
                String id = "21";
                ArrayList<Long> attrI = new ArrayList<>();
                attrI.add(10000000L);
                attrI.add(5000400L);
                ArrayList<Double> attrD = new ArrayList<>();
                attrD.add(2.0);
                ArrayList<String> attrStr = new ArrayList<>();
                attrStr.add("Esto es un test");
                ArrayList<BitSet> attrCatBits = new ArrayList<>();
                BitSet e = new BitSet();
                e.set(10);
                attrCatBits.add(e);
                HashMap<Integer, ArrayList<String>> CatAttr = new HashMap<>();
                CatAttr.put(0, new ArrayList<>(List.of("horror", "mystery")));
                CatAttr.put(1, new ArrayList<>(List.of("comedy", "family")));
                ArrayList<LocalDate> attrDate = new ArrayList<>();
                LocalDate d = LocalDate.parse("2019-01-04");
                attrDate.add(d);
                ArrayList<String> attrBool = new ArrayList<>();
                String b = "False";
                attrBool.add(b);
                testSetId(i, id);
                LinkedHashMap<Integer, HashMap<String, Integer>> wordFrequencies = new LinkedHashMap<>();
                HashMap<String,Integer> interno = new HashMap<>();
                interno.put("hola", 9);
                wordFrequencies.put(1, interno);
                System.out.println("----------------------");
                testGetId(i);
                System.out.println("----------------------");
                testSetAttributes(i, attrI, attrD, attrStr, attrCatBits, attrDate, attrBool, CatAttr);
                System.out.println("----------------------");
                testGetIntAttributes(i);
                System.out.println("----------------------");
                testGetDoubleAttributes(i);
                System.out.println("----------------------");
                testGetStringAttributes(i);
                System.out.println("----------------------");
                testGetCategoryPscBits(i);
                System.out.println("----------------------");
                testGetDateAttributes(i);
                System.out.println("----------------------");
                testGetBooleanAttributes(i);
                System.out.println("----------------------");
                testSetWordFrequencies(i, wordFrequencies);
                System.out.println("----------------------");
                testGetWordFrequencies(i);
                System.out.println("----------------------------------------------------");
                testGetItemAttributes(i);
                break;
            case 1:
                String pathToCsv2 = System.getProperty("user.dir") + "/Datasets/Movielens/250/";
                String pathnuevo2 = System.getProperty("user.dir") + "/Datasets/Movielens/";
                Cjt_items dataset2 = new Cjt_items(pathToCsv2 + "items.csv", pathnuevo2 + "data_types_movies.csv");
                HashMap<String,Item> Items = dataset2.getItems();
                for (String key: Items.keySet()) {
                    System.out.println("Item id: " + Items.get(key).getId());
                    testGetWordFrequencies(Items.get(key));
                }
        }

    }


}