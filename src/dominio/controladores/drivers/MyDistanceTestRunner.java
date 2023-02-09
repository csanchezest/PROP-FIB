/**
 @file MyDistanceTestRunner.java
 @brief Codigo de la clase MyDistanceTestRunner

 */
package dominio.controladores.drivers;
import dominio.clases.DistanceItems;
import dominio.clases.DistanceUsers;
import dominio.controladores.DistanceItemsUnitTest;
import dominio.controladores.DistanceUsersTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @class MyDistanceTestRunner
 * @brief Clase para ejecutar todos los tests de jUnit
 * @author Cristian Sanchez Estape y Jordi Elgueta Serra
 */

public class MyDistanceTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DistanceItemsUnitTest.class);
        if(result.getFailures().size()>0) {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        } else System.out.println("All tests succesful");
        result = JUnitCore.runClasses(DistanceUsersTest.class);
        if(result.getFailures().size()>0) {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        } else System.out.println("All tests succesful");
    }
}
