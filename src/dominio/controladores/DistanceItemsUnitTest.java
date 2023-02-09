/**
 @file DistanceItemsUnitTest.java
 @brief Codigo de la clase DistanceItemsUnitTest

 */
package dominio.controladores;

import dominio.clases.DistanceItems;
import dominio.clases.Item;
import org.junit.Test;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @class DistanceItemsUnitTest
 * @brief JUnit para testear la funcionalidad de distancia entre atributos de texto libre implementada en la clase DistanceItemsUnitTest
 * @author Jordi Elgueta Serra
 */

public class DistanceItemsUnitTest {

    /**
     * @brief Metodo que testea la funcion de calculo de la distancia entre dos variables de tipo texto libre
     */

    @Test
    public void distBetweenTextTest() {
        DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        decimalSymbols.setDecimalSeparator('.');
        Item a = new Item("123");
        Item b = new Item("456");
        HashMap<String, Integer> m1 = new HashMap<>();
        HashMap<String, Integer> m2 = new HashMap<>();
        HashMap<String, Integer> m3 = new HashMap<>();
        HashMap<String, Integer> m4 = new HashMap<>();
        m1.put("love", 1);
        m1.put("jacket", 1);
        m1.put("blue", 1);
        m2.put("walking", 1);
        m2.put("road", 1);
        m2.put("saw", 1);
        m2.put("lion", 1);
        m2.put("amazed", 1);
        m2.put("beauty", 1);
        m3.put("love", 1);
        m3.put("Paris", 1);
        m4.put("movie", 2);
        m4.put("lion", 1);
        m4.put("pride", 1);
        m4.put("boss", 1);
        m4.put("dream", 1);
        m4.put("saw", 1);
        m4.put("future", 1);
        m4.put("animals", 1);
        m4.put("sad", 1);
        LinkedHashMap<Integer, HashMap<String, Integer>> aux1 = new LinkedHashMap<>();
        aux1.put(1, m1);
        aux1.put(2, m2);
        LinkedHashMap<Integer, HashMap<String, Integer>> aux2 = new LinkedHashMap<>();
        aux2.put(1, m3);
        aux2.put(2, m4);
        a.setWordFrequencies(aux1);
        b.setWordFrequencies(aux2);
        DistanceItems di = new DistanceItems(a, b, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>(List.of(true,true)));
        Method TextDist;
        try {
            TextDist = di.getClass().getDeclaredMethod("distBetweenString");
            TextDist.setAccessible(true);
            assertEquals("0.683", new DecimalFormat("0.000", decimalSymbols).format(TextDist.invoke(di)));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}