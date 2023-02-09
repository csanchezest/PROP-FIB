/**
 @file DistanceUsersTest.java
 @brief Codigo de la clase DistanceUsersTest

 */
package dominio.controladores;
import dominio.clases.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @class DistanceUsersTest
 * @brief JUnit para testear la clase distancia entre usuarios
 * @author Muhammad Haris
 */

public class DistanceUsersTest {
    /**
     * @brief Metodo que testea la funcion de calculo de la distania entre dos bools
     */
    @Test
    public void calculateEuclideanDistanceTest() {
        DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        decimalSymbols.setDecimalSeparator('.');
        DistanceUsers d = new DistanceUsers();
        Item a = new Item("9");
        Item b = new Item ("4");
        Map<Item, Double> f1 = new HashMap<>();
        Map<Item, Double> f2 = new HashMap<>();
        Map<Item, Double> f3 = new HashMap<>();
        f1.put(a,3.0);
        f1.put(b,1.0);
        f2.put(a,1.0);
        f2.put(b,1.0);
        f3.put(a,2.5);
        f3.put(b,3.5);
        assertEquals("2.0000",new DecimalFormat("0.0000", decimalSymbols).format(d.calculateEuclideanDistance(f1,f2)));
        assertEquals("2.9155",new DecimalFormat("0.0000", decimalSymbols).format(d.calculateEuclideanDistance(f2,f3)));
    }
}