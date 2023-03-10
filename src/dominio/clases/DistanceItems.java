/**
 @file DistanceItems.java
 @brief Codigo de la clase DistanceItems

 */
package dominio.clases;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @class DistanceItems
 * @brief Clase que calcula la distancia entre un par de items. Para ello se calcula la distancia entre cada par de atributos
 *    de los items en funcion del tipo que sean, y luego se usa la distancia euclidiana para obtener la distancia total.
 *    Se hace una ponderacion de forma que se da mas peso a los atributos tipo categorico, luego los de tipo int,
 *    double y date por igual y finalmente los de tipo boolean, al hacer el computo de la distancia.
 * @author Jordi Elgueta Serra
 */
public class DistanceItems {

    /**
     * @brief Primer item en el computo de la distancia
     */
    private final Item a;
    /**
     * @brief Segundo item en el computo de la distancia
     */
    private final Item b;
    /**
     * @brief ArrayList con los valores maximos que tienen los atributos de tipo integer, en el mismo orden que aparecen a las
     * columnas del dataset
     */
    private final ArrayList<Long> MaxValIntegers;
    /**
     * @brief ArrayList con los valores minimos que tienen los atributos de tipo integer, en el mismo orden que aparecen a las
     * columnas del dataset
     */
    private final ArrayList<Long> MinValIntegers;
    /**
     * @brief ArrayList con los valores maximos que tienen los atributos de tipo double, en el mismo orden que aparecen a las
     * columnas del dataset
     */
    private final ArrayList<Double> MaxValDoubles;
    /**
     * @brief ArrayList con los valores minimos que tienen los atributos de tipo double, en el mismo orden que aparecen a las
     * columnas del dataset
     */
    private final ArrayList<Double> MinValDoubles;
    /**
     * @brief ArrayList con los valores maximos que tienen los atributos de tipo date, en el mismo orden que aparecen a las
     * columnas del dataset
     */
    private final ArrayList<LocalDate> MaxDates;
    /**
     * @brief ArrayList con los valores minimos que tienen los atributos de tipo date, en el mismo orden que aparecen a las
     * columnas del dataset
     */
    private final ArrayList<LocalDate> MinDates;
    private List<Boolean> intAt;
    private List<Boolean> doubAt;
    private List<Boolean> dateAt;
    private List<Boolean> bAt;
    private List<Boolean> catAt;
    private List<Boolean> ftAt;


    /**
     * @brief Constructora de la clase DistanceItems
     * @param a Primer item en el computo de la distancia
     * @param b Segundo item en el computo de la distancia
     * @param MaxValIntegers Lista con los valores maximos que tienen los atributos de tipo integer, en el mismo orden que aparecen
     *      *                      a las columnas del dataset
     * @param MinValIntegers Lista con los valores minimos que tienen los atributos de tipo integer, en el mismo orden que aparecen
     *      *                      a las columnas del dataset
     * @param MaxValDoubles Lista con los valores minimos que tienen los atributos de tipo double, en el mismo orden que aparecen
     *      *                      a las columnas del dataset
     * @param MinValDoubles Lista con los valores minimos que tienen los atributos de tipo double, en el mismo orden que aparecen
     *                      a las columnas del dataset
     * @param MaxValDate Lista con los valores maximos que tienen los atributos de tipo date, en el mismo orden que aparecen
     *      *            a las columnas del dataset
     * @param MinValDate Lista con los valores minimos que tienen los atributos de tipo date, en el mismo orden que aparecen
     *      *            a las columnas del dataset
     */
    public DistanceItems(Item a, Item b, Collection MaxValIntegers, Collection MinValIntegers, Collection MaxValDoubles,
                         Collection MinValDoubles, Collection MaxValDate, Collection MinValDate, List<Boolean> intAt,
                         List<Boolean> doubAt,List<Boolean> dateAt,List<Boolean> bAt, List<Boolean> catAt,List<Boolean> ftAt) {
        this.a = a;
        this.b = b;
        this.MaxValIntegers = new ArrayList<>(MaxValIntegers);
        this.MinValIntegers = new ArrayList<>(MinValIntegers);
        this.MaxValDoubles = new ArrayList<>(MaxValDoubles);
        this.MinValDoubles = new ArrayList<>(MinValDoubles);
        this.MaxDates = new ArrayList<>(MaxValDate);
        this.MinDates = new ArrayList<>(MinValDate);
        this.dateAt = dateAt;
        this.bAt = bAt;
        this.catAt = catAt;
        this.ftAt = ftAt;
        this.intAt = intAt;
        this.doubAt = doubAt;
    }

    /**
     * @brief Metodo que devuelve la distancia entre los items a y b. La distancia siempre  sera un numero entre 0 y 1
     * donde 0 significa que son ??tems exacatamente iguales y 1 significa que son completamente opuestos.
     * @return Devuelve el valor de la distancia, un double entre 0 y 1
     */
    public double get_distance() {
        double distInt = distBetweenInt();
        double distBool = distBetweenBool();
        double distDate = distBetweenDate();
        double distDouble = distBetweenDouble();
        double distString = distBetweenString();
        double distBS = distBetweenCategory();
        return 0.15*distInt + 0.15*distDouble + 0.15*distDate + 0.05*distBool + 0.3*distBS + 0.2*distString;
    }

    /**
     * @brief Metodo que calcula la similitud entre dos atributos de tipo categorico. Entre cada par de atributos categoricos la similitud se obtiene haciendo el cardinal
     * de la interseccion entre el cardinal de la union. Para la similitud global entre los atributos categoricos de los dos items se calcula la distancia euclidiana
     * normalizada para que el resultado sea entre 0 y 1.
     * @return Devuelve la distancia entre los dos items teniendo solo en cuenta los atributos categoricos
     */
    private double distBetweenCategory() {
        ArrayList<BitSet> c1 = a.getCategoryPscBits();
        ArrayList<BitSet> c2 = b.getCategoryPscBits();
        int n = c1.size();
        if (n==0) return 0.0;
        double dist = 0.0;
        int n1 = 0;
        for (int i = 0; i < n; ++i) {
            if (c1.get(i) != null) {
                if (catAt.get(i)) {
                    ++n1;
                    BitSet unio = (BitSet) c1.get(i).clone();
                    BitSet interseccio = (BitSet) c1.get(i).clone();
                    unio.or(c2.get(i));
                    interseccio.and(c2.get(i));
                    if (unio.cardinality() != 0)
                        dist += Math.pow(1 - (double) interseccio.cardinality() / unio.cardinality(), 2);
                }
            }
        }
        if(n1 != 0)
            return Math.sqrt(dist/n1);
        else return 0;
    }

    /**
     * @brief Metodo que calcula la similitud entre dos atributos de tipo date. Entre cada par de datas la similitud se obtiene haciendo la diferencia
     * entre los a??os dividido por la m??xima diferencia que puede haber. Para la similitud global entre las datas de los dos items se calcula
     * la distancia euclidiana normalizada para que el resultado sea entre 0 y 1.
     * @return Devuelve la distancia entre los dos items teniendo solo en cuenta los atributos de tipo date
     */
    private double distBetweenDate() {
        ArrayList<LocalDate> ld1 = a.getDateAttributes();
        ArrayList<LocalDate> ld2 = b.getDateAttributes();
        int n = ld1.size();
        if (n == 0) return 0.0;
        double dist = 0.0;
        int n1 = 0;
        for (int i = 0; i < n; ++i) {
            if(dateAt.get(i)) {
                ++n1;
                int maxDif = MaxDates.get(i).getYear() - MinDates.get(i).getYear();
                if (maxDif != 0)
                    dist += Math.pow((double) (ld1.get(i).getYear() - ld2.get(i).getYear()) / maxDif, 2);
            }
        }
        if(n1 != 0)
            return Math.sqrt(dist/n1);
        else return 0;
    }

    /**
     * @brief Metodo que calcula la similitud entre dos atributos de tipo boolean. Entre cada par de bools la similitud puede ser 1 si son diferentes
     * o 0 si son iguales. Para la similitud global entre los bools de los dos items se calcula
     * la distancia euclidiana normalizada para que el resultado sea entre 0 y 1.
     * @return Devuelve la distancia entre los dos items teniendo solo en cuenta los atributos de tipo boolean
     */
    private double distBetweenBool() {
        ArrayList<String> b1 = a.getBooleanAttributes();
        ArrayList<String> b2 = b.getBooleanAttributes();

        int n = b1.size();
        if (n==0) return 0.0;
        double dist = 0.0;
        int n1 = 0;
        for (int i = 0; i < n; ++i) {
            if (bAt.get(i)) {
                n1++;
                dist += (b1.get(i).equals(b2.get(i))) ? 0 : 1;
            }
        }
        if(n1 != 0)
            return Math.sqrt(dist/n1);
        else return 0;
    }

    /**
     * @brief Metodo que calcula la similitud entre dos atributos de tipo double. Entre cada par de doubles la similitud se obtiene haciendo la diferencia
     * dividido por la maxima diferencia que puede haber. Para la similitud global entre los doubles de los dos items se calcula la distancia euclidiana
     * normalizada para que el resultado sea entre 0 y 1.
     * @return Devuelve la distancia entre los dos items teniendo solo en cuenta los atributos de tipo double.
     */
    private double distBetweenDouble() {
        ArrayList<Double> d1 = a.getDoubleAttributes();
        ArrayList<Double> d2 = b.getDoubleAttributes();
        int n = d1.size();
        if (n==0) return 0.0;
        double dist = 0.0;
        int n1 = 0;
        for (int i = 0; i < n; ++i) {
            if(doubAt.get(i)) {
                n1++;
                double maxDif = MaxValDoubles.get(i) - MinValDoubles.get(i);
                if (maxDif != 0)
                    dist += Math.pow((d1.get(i) - d2.get(i)) / maxDif, 2);
            }
        }
        if(n1 != 0)
            return Math.sqrt(dist/n1);
        else return 0;
    }

    /**
     * @brief Metodo que calcula la similitud entre dos atributos de tipo integer. Entre cada par de integers la similitud se obtiene haciendo la diferencia
     * dividido por la maxima diferencia que puede haber. Para la similitud global entre los integers de los dos items se calcula la distancia euclidiana
     * normalizada para que el resultado sea entre 0 y 1.
     * @return Devuelve la distancia entre los dos items teniendo solo en cuenta los atributos de tipo integer.
     */
    private double distBetweenInt() {

        ArrayList<Long> i1 = a.getIntAttributes();
        ArrayList<Long> i2 = b.getIntAttributes();
        int n = i1.size();
        if (n==0) return 0.0;
        int n1 = 0;
        double dist = 0.0;
        for (int i = 0; i < n && intAt.get(i); ++i) {
            ++n1;
            Long maxDif = MaxValIntegers.get(i) - MinValIntegers.get(i);
            if (maxDif != 0)
                dist += Math.pow((double) (i1.get(i) - i2.get(i)) / maxDif, 2);
        }
        if(n1 != 0)
            return Math.sqrt(dist/n1);
        else return 0;
    }

    /**
     * @brief Metodo que calcula la similitud entre dos atributos de tipo texto libre. Los textos se reciben ya mapeados con la frecuencia por palabra y sin stopwords.
     * Dados dos maps primero se hace la union entre ellos con ello se obtienen las palabras distintas entre los dos textos. Se obtiene por cada texto un vector
     * de frecuencias de las palabras distintas y se computa la distancia coseno entre los dos vectors obtenidos. Para la similitud global entre los textos de los
     * dos items se calcula la distancia euclidiana normalizada para que el resultado sea entre 0 y 1.
     * @return Devuelve la distancia entre los dos items teniendo solo en cuenta los atributos de tipo texto.
     */

    private double distBetweenString() {
        LinkedHashMap<Integer, HashMap<String,Integer>> s1 = a.getWordFrequencies();
        LinkedHashMap<Integer, HashMap<String,Integer>> s2 = b.getWordFrequencies();
        int n = s1.size();
        if (n == 0) return 0.0;
        double dist = 0.0;
        int aux = 0;
        int n1 = 0;
        for (int i : s1.keySet()) {
            if (ftAt.get(aux)) {
                ++n1;
                LinkedHashMap<String, Integer> difWords = new LinkedHashMap<>();
                for (String s : s1.get(i).keySet())
                    difWords.put(s, 0);
                for (String s : s2.get(i).keySet())
                    difWords.put(s, 0);
                ArrayList<Integer> v1 = new ArrayList<>();
                ArrayList<Integer> v2 = new ArrayList<>();
                for (String w : difWords.keySet()) {
                    if (s1.get(i).containsKey(w)) {
                        v1.add(s1.get(i).get(w));
                    } else v1.add(0);
                    if (s2.get(i).containsKey(w)) {
                        v2.add(s2.get(i).get(w));
                    } else v2.add(0);
                }
                double sumProduct = 0;
                double sum1Sq = 0;
                double sum2Sq = 0;
                for (int j = 0; j < v1.size(); j++) {
                    sumProduct += v1.get(j) * v2.get(j);
                    sum1Sq += v1.get(j) * v1.get(j);
                    sum2Sq += v2.get(j) * v2.get(j);
                }
                if (sum1Sq != 0 || sum2Sq != 0) {
                    dist += Math.pow(1 - (sumProduct / (Math.sqrt(sum1Sq) * Math.sqrt(sum2Sq))), 2);
                } else dist += 1;
            }
            ++aux;
        }
        if(n1 != 0)
            return Math.sqrt(dist/n1);
        else return 0;
    }
}

