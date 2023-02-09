/**
 @file DriverCjt_items.java
 @brief Codigo de la clase DriverCjt_items

 */

package dominio.controladores.drivers;

import dominio.clases.*;
import persistencia.ControladorPersistencia;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

/**
 @class DriverCjt_items
 @brief Driver representativo de un ejemeplo de ejecucion del funcionamiento de la clase Cjt_items
 @author Muhammad Haris and Cristian Sanchez Estape

 */
public class DriverCjt_items {

    /**
     * @brief Metodo para comprobar la correcta asignacion de un conjunto de items a un Cjt_items
     * @param c Objeto de Cjt_items a tratar
     * @param items Lista de items a añadir en el Cjt_items
     */

//    public static void testSetItems(Cjt_items c, ArrayList<Item>items) {
//        System.out.println("Test setItems");
//        items.add(new Item("223"));
//        c.setItems(items);
//        System.out.println("\tItemId: 223");
//    }

    /**
     * @brief Metodo para comprobar la correcta consulta del conjunto de items de un Cjt_items
     * @param c Objeto de Cjt_items a consultar
     */

    public static void testGetItems(Cjt_items c) {
        System.out.println("Test getItems:");
        for(Item i: c.getItems().values()) System.out.println("\t" + i.getId());
        //Falta hacer un printeo de lo que hace
    }

    /**
     * @brief Metodo que comprueba si un string es una fecha
     * @param c Objeto de Cjt_items con el que tratar
     * @param column Cadena de caracteres candidata a ser fecha
     */

    public static void testIsDate (Cjt_items c, String column) {
        System.out.println("Test date");
        Method isDate = null;
        try {
            isDate = c.getClass().getDeclaredMethod("isDate", String.class);
            isDate.setAccessible(true);
            System.out.println(isDate.invoke(c,column));
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo que comprueba si un string es un entero
     * @param c Objeto de Cjt_items con el que tratar
     * @param column Cadena de caracteres candidata a ser entero
     */

    public static void testIsInteger (Cjt_items c, String column) {
        System.out.println("Test isInteger");
        Method isInteger = null;
        try {
            isInteger = c.getClass().getDeclaredMethod("isInteger", String.class);
            isInteger.setAccessible(true);
            System.out.println(isInteger.invoke(c,column));
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo que comprueba si un string es un double
     * @param c Objeto de Cjt_items con el que tratar
     * @param column Cadena de caracteres candidata a ser double
     */

    public static void testIsDouble (Cjt_items c, String column) {
        System.out.println("Test isDouble");
        Method isDouble = null;
        try {
            isDouble = c.getClass().getDeclaredMethod("isDouble", String.class);
            isDouble.setAccessible(true);
            System.out.println(isDouble.invoke(c,column));
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo que comprueba si un string es un atributo de tipo categorico
     * @param c Objeto de Cjt_items con el que tratar
     * @param column Cadena de caracteres candidata a ser categorica
     */
    /*
    public static void testIsCategorical (Cjt_items c, String column) {
        System.out.println("Test isCategorical");
        Method isCategorical = null;
        try {
            isCategorical = c.getClass().getDeclaredMethod("isCategorical", String.class);
            isCategorical.setAccessible(true);
            System.out.println(isCategorical.invoke(c,column));
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }*/

    /**
     * @brief Metodo que comprueba si un string es un conjunto de atributos de un mismo tipo
     * @param c Objeto de Cjt_items con el que tratar
     * @param column Cadena de caracteres candidata a ser un conjunto de datos del mismo tipo
     */

    public static void testParseToArray(Cjt_items c, String column) {
        System.out.println("Test parseToArray");
        Method parseToArray = null;
        try {
            parseToArray = c.getClass().getDeclaredMethod("parseToArray", String.class);
            parseToArray.setAccessible(true);
            parseToArray.invoke(c,column);
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo que comprueba si un string es un booleano
     * @param c Objeto de Cjt_items con el que tratar
     * @param column Cadena de caracteres candidata a ser booleano
     */

    public static void testIsBoolean (Cjt_items c, String column) {
        System.out.println("Test isBoolean");
        Method isBoolean = null;
        try {
            isBoolean = c.getClass().getDeclaredMethod("isBoolean", String.class);
            isBoolean.setAccessible(true);
            System.out.println(isBoolean.invoke(c,column));
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @brief Metodo dedicado a la impresion de los objetos contenidos en un Cjt_items
     */

    public static void testPrintItems() {
        System.out.println("Test printItems");
        List<Item> items = new ArrayList<>();
        items.add(new Item("223"));
        Cjt_items.printItems((HashMap<String, Item>) items);
    }

    /**
     * @brief Metodo de consulta de los valores enteros maximos de las columnas del conjunto de items
     * @param c Instancia de consulta de la clase Cjt_items
     */

    public static void testGetMaxValIntegers(Cjt_items c) {
        System.out.println("testGetMaxValIntegers:");
        LinkedHashMap<Integer, Long> data = c.getMaxValIntegers();
        for(int i: data.keySet()){
            System.out.println(i + " -> " + data.get(i));
        }
    }

    /**
     * @brief Metodo de consulta de los valores enteros minimos de las columnas del conjunto de items
     * @param c Instancia de consulta de la clase Cjt_items
     */

    public static void testGetMinValIntegers(Cjt_items c) {
        System.out.println("testGetMinValIntegers:");
        LinkedHashMap<Integer, Long> data = c.getMinValIntegers();
        for(int i: data.keySet()){
            System.out.println(i + " -> " + data.get(i));
        }
    }

    /**
     * @brief Metodo de consulta de los valores doubles maximos de las columnas del conjunto de items
     * @param c Instancia de consulta de la clase Cjt_items
     */

    public static void testGetMaxValDoubles(Cjt_items c) {
        System.out.println("testGetMaxValDoubles:");
        HashMap<Integer,Double> data = c.getMaxValDoubles();
        for(int i: data.keySet()){
            System.out.println(i + " -> " + data.get(i));
        }
    }

    /**
     * @brief Metodo de consulta de los valores doubles minimos de las columnas del conjunto de items
     * @param c Instancia de consulta de la clase Cjt_items
     */

    public static void testGetMinValDoubles(Cjt_items c) {
        System.out.println("testGetMinValDoubles:");
        HashMap<Integer,Double> data = c.getMinValDoubles();
        for(int i: data.keySet()){
            System.out.println(i + " -> " + data.get(i));
        }
    }

    /**
     * @brief Metodo de consulta de los valores date maximos de las columnas del conjunto de items
     * @param c Instancia de consulta de la clase Cjt_items
     */

    public static void testGetMaxDates(Cjt_items c) {
        System.out.println("testGetMaxDates:");
        HashMap<Integer, LocalDate> data = c.getMaxDates();
        for(int i: data.keySet()){
            System.out.println(i + " -> " + data.get(i));
        }
    }

    /**
     * @brief Metodo de consulta de los valores date minimos de las columnas del conjunto de items
     * @param c Instancia de consulta de la clase Cjt_items
     */

    public static void testGetMinDates(Cjt_items c) {
        System.out.println("testGetMinDates:");
        HashMap<Integer,LocalDate> data = c.getMinDates();
        for(int i: data.keySet()){
            System.out.println(i + " -> " + data.get(i));
        }
    }

    public static void testGetColMedians(Cjt_items c, String pathToDataset, String pathToAttrData_types) {
        System.out.println("testColMedians:");
        ControladorPersistencia CtrlPers = ControladorPersistencia.getInstance();
        String[] readData = new String[]{};
        try {
            readData = CtrlPers.getDataTypes(pathToAttrData_types);
        } catch (Exception e){}
        HashMap<Integer, Long> IntColMedian = new HashMap<>();
        HashMap<Integer, Double> DoubleColMedian = new HashMap<>();
        HashMap<Integer, Integer> DateColMedian = new HashMap<>();
        int x = 0;
        try {
           x =  c.getColMedians(IntColMedian, DoubleColMedian, DateColMedian, pathToDataset, readData);
           System.out.println("INT: ");
           for (Integer key : IntColMedian.keySet()) {
               System.out.println("Columna: " + key + " Mediana: " + IntColMedian.get(key));
           }
            System.out.println("DOUBLE: ");
            for (Integer key : DoubleColMedian.keySet()) {
                System.out.println("Columna: " + key + " Mediana: " + DoubleColMedian.get(key));
            }
            System.out.println("DATE: ");
            for (Integer key : DateColMedian.keySet()) {
                System.out.println("Columna: " + key + " Mediana: " + DateColMedian.get(key));
            }

        } catch (Exception e){}


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
                String pathToCsv = System.getProperty("user.dir") + "/src/persistencia/data/DriversData/Cjt_itemsTestSets/";
                Cjt_items dataset = new Cjt_items(pathToCsv + "smallTestItems.csv", pathToCsv+ "data_types_smallTest.csv");
                testGetItems(dataset);
                System.out.println("------------");
                testGetMaxDates(dataset);
                System.out.println("------------");
                testGetMaxValDoubles(dataset);
                System.out.println("------------");
                testGetMaxValIntegers(dataset);
                System.out.println("------------");
                testGetMinValDoubles(dataset);
                System.out.println("------------");
                testGetMinDates(dataset);
                System.out.println("------------");
                testGetMinValIntegers(dataset);
                System.out.println("------------");
                testGetColMedians(dataset, pathToCsv + "smallTestItems.csv", pathToCsv + "data_types_smallTest.csv");
                System.out.println("--------------------------------------");
                System.out.println("Impresión de Items y sus atributos:");
                System.out.println("");
                for (Item i : dataset.getItems().values()) {
                    System.out.println("ItemId " + i.getId() + "-> " + "IntAttributes: " + i.getIntAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "CategoryPscBits: " + i.getCategoryPscBits());
                    System.out.println("ItemId " + i.getId() + "-> " + "StringAttributes: " + i.getStringAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "DoubleAttributes: " + i.getDoubleAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "DateAttributes: " + i.getDateAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "BooleanAttributes: " + i.getBooleanAttributes());
                    System.out.println("------------");

                }
                break;
            case 1:
                String pathToCsv2 = System.getProperty("user.dir") + "/src/persistencia/data/DriversData/Cjt_itemsTestSets/";
                String pathToAuxCsv = System.getProperty("user.dir") + "/Datasets/Movielens/";
                Cjt_items dataset2 = new Cjt_items(pathToCsv2 + "items.csv", pathToAuxCsv + "data_types_movies.csv");
                testGetItems(dataset2);
                System.out.println("------------");
                testGetMaxDates(dataset2);
                System.out.println("------------");
                testGetMaxValDoubles(dataset2);
                System.out.println("------------");
                testGetMaxValIntegers(dataset2);
                System.out.println("------------");
                testGetMinValDoubles(dataset2);
                System.out.println("------------");
                testGetMinDates(dataset2);
                System.out.println("------------");
                testGetMinValIntegers(dataset2);
                System.out.println("------------");
                testGetColMedians(dataset2, pathToCsv2 + "items.csv", pathToAuxCsv + "data_types_movies.csv");
                System.out.println("--------------------------------------");
                System.out.println("Impresión de Items y sus atributos:");
                System.out.println("");
                for (Item i : dataset2.getItems().values()) {
                    System.out.println("ItemId " + i.getId() + "-> " + "IntAttributes: " + i.getIntAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "CategoryPscBits: " + i.getCategoryPscBits());
                    System.out.println("ItemId " + i.getId() + "-> " + "StringAttributes: " + i.getStringAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "DoubleAttributes: " + i.getDoubleAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "DateAttributes: " + i.getDateAttributes());
                    System.out.println("ItemId " + i.getId() + "-> " + "BooleanAttributes: " + i.getBooleanAttributes());
                    System.out.println("------------");


                }
        }
    }

}

