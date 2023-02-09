package dominio.controladores.drivers;

import dominio.clases.Hybrid;
import dominio.clases.Item;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;


    /**
     * @class DriverHybrid
     * @brief Driver de la clase Hybrid
     * @author Jordi Elgueta Serra
     */
public class DriverHybrid {

        /**
         *  @brief Metodo de testeo de la constructora de la clase
         * @param collaborative Map con los items recomendados por el metodo Collaborative como key y sus predicciones como value
         * @param content Map con los items recomendados por el metodo ContentBased como key y sus predicciones como value
         * @return Devuelve una instancia de la clase Hybrid
         */
        static public Hybrid testHybrid(HashMap<Item, Double> collaborative, HashMap<Item, Double> content) {
            System.out.println("Se ha creado una instancia de la clase Hybrid.");
            return new Hybrid(collaborative, content);
        }
        /**
         * @brief Metodo de testeo de la funcion de recomendacion de items. Escribe por pantalla los items recomendados, junto con
         * la prediccion de su valoracion.
         * @param h Instancia de la clase Hybrid, necesaria para las llamadas a los metodos de la clase
         */
        public static void testRecommended_items(Hybrid h) {
            System.out.println("Los items recomendados por el sistema Hybrid son: ");
            HashMap<Item,Double> recommendations = h.recommended_items();
            NumberFormat formatter = new DecimalFormat("#0.000");
            for (Item i : recommendations.keySet()) {
                System.out.println(" " + i.getId() + " --> " + formatter.format(recommendations.get(i).doubleValue()));
            }
        }

        /**
         * @brief Metodo main
         */
        public static void main(String[]args) {
            System.out.println("Resultado del testeo 1 pequeño con 5 items recomendados:");
            test1();
            System.out.println("Resultado del testeo grande con 20 items recomendados:");
            test2();
        }

        /**
         * @brief Metodo que implementa el testeo pequeño con 5 items a recomendar
         */
        private static void test1() {
            HashMap<Item, Double> collaborative = new HashMap<>();
            HashMap<Item, Double> content = new HashMap<>();
            collaborative.put(new Item("1"),4.0);
            collaborative.put(new Item("2"),4.5);
            collaborative.put(new Item("3"),3.0);
            collaborative.put(new Item("4"),3.5);
            collaborative.put(new Item("5"),5.0);
            content.put(new Item("1"),5.0);
            content.put(new Item("6"),3.5);
            content.put(new Item("7"),4.5);
            content.put(new Item("2"),3.8);
            content.put(new Item("8"),4.0);
            Hybrid h = testHybrid(collaborative, content);
            testRecommended_items(h);
            System.out.println("-----");
        }

        /**
         * @brief Metodo que implementa el testeo grande con 20 items a recomendar
         */
        private static void test2() {
            HashMap<Item, Double> collaborative = new HashMap<>();
            HashMap<Item, Double> content = new HashMap<>();
            collaborative.put(new Item("1"),4.0);
            collaborative.put(new Item("2"),4.5);
            collaborative.put(new Item("3"),3.0);
            collaborative.put(new Item("4"),3.5);
            collaborative.put(new Item("5"),5.0);
            collaborative.put(new Item("143"),3.5);
            collaborative.put(new Item("123"),3.0);
            collaborative.put(new Item("164"),4.0);
            collaborative.put(new Item("14"),4.7);
            collaborative.put(new Item("175"),4.2);
            collaborative.put(new Item("231"),3.3);
            collaborative.put(new Item("561"),5.0);
            collaborative.put(new Item("81"),3.4);
            collaborative.put(new Item("176"),4.7);
            collaborative.put(new Item("541"),5.0);
            collaborative.put(new Item("91"),4.4);
            collaborative.put(new Item("51"),4.2);
            collaborative.put(new Item("71"),3.2);
            collaborative.put(new Item("4151"),4.0);
            collaborative.put(new Item("153"),2.9);
            content.put(new Item("1"),5.0);
            content.put(new Item("6"),3.5);
            content.put(new Item("7"),4.5);
            content.put(new Item("2"),3.8);
            content.put(new Item("8"),4.0);
            content.put(new Item("14"),4.9);
            content.put(new Item("176"),3.9);
            content.put(new Item("71"),5.0);
            content.put(new Item("91"),4.2);
            content.put(new Item("81"),4.7);
            content.put(new Item("651"),5.0);
            content.put(new Item("1348"),5.0);
            content.put(new Item("1745"),4.8);
            content.put(new Item("2341"),5.0);
            content.put(new Item("6441"),3.5);
            content.put(new Item("1456"),4.5);
            content.put(new Item("1123"),4.0);
            content.put(new Item("1534"),3.6);
            content.put(new Item("1795"),5.0);
            content.put(new Item("1865"),5.0);
            Hybrid h = testHybrid(collaborative, content);
            testRecommended_items(h);
            System.out.println("-----");
        }
}
