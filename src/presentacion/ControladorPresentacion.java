package presentacion;

import dominio.controladores.ControladorDominio;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControladorPresentacion {

    private VistaLogIn vistaLogIn;
    private VistaPrincipal vistaPrincipal;
    private ControladorDominio ctrlDominio;

    public ControladorPresentacion() {
        vistaLogIn = new VistaLogIn(this);
        ctrlDominio = new ControladorDominio(this);
        vistaPrincipal = new VistaPrincipal(this);
    }

    public List<String> getItemInfo() { return ctrlDominio.getItemInfo(); }

    public boolean existeItem(String item) { return ctrlDominio.existeItem(item); }

    public void inicializarPresentacion() {
        vistaLogIn.activar();
    }

    /** Métodos de sincronizacion entre vistas **/

    public void activarVistaPrincipal() {
        vistaPrincipal.setVisible();
    }

    public void sincronizacionLogInAVistaPrincipal() {
        vistaLogIn.desactivar();
        vistaPrincipal.activar();
    }

    public void sincronizacionVistaPrincipalALogIn() {
        vistaPrincipal.desactivar();
        vistaLogIn.activar();
    }

    public void guardarItems(File file) { ctrlDominio.guardarItems(file); }

    public void guardarAuxiliares(File file) { ctrlDominio.guardarAuxiliares(file); }

    public void guardarRatings(File file) { ctrlDominio.guardarRatings(file); }

    public void guardarKnown(File file) {ctrlDominio.guardarKnown((file));}

    public void guardarUnknown(File file) { ctrlDominio.guardarUnknown(file); }

    public void guardarLanguage(String language) {ctrlDominio.guardarLanguage(language);}

    public boolean isDatasetCargado() { return ctrlDominio.isDatasetCargado(); }

    public void almacenarRating(double rating) throws Exception { ctrlDominio.almacenarRating(rating); }

    public String getColumnaActual(String current) throws Exception { return ctrlDominio.getColumnaActual(current); }

    public void setK(int k) {ctrlDominio.setK(k);}

    public HashMap<String,Double> makeRecomendation(int n, List<Boolean> l) {return ctrlDominio.makeRecomendation(n,l);}

    public void setUser(int id){ctrlDominio.setUser(id);}

    public void loadDataset() {ctrlDominio.loadDataset();}

    public String getRecomendacion() { return ctrlDominio.getRecomendacion(); }

    public HashMap<String, Double> getUsuaris (int id) { return ctrlDominio.getUsuaris(id); }

    public String getPathToUpdatedFile() { return ctrlDominio.getPathToUpdatedFile(); }

    //public static String pedirInput(int i) { return VistaFixAttributes.askInput(i); }
    public ArrayList<Object> pedirInput(int i, String attrHeader, String idItem) {
        Object[] options = {"Arreglar","Automatizar","Ignorar"};
        int opt = JOptionPane.showOptionDialog(null,
                "El valor para el atributo " + attrHeader + " en el item " + idItem + " es de tipo incorrecto. Indica que tratamiento quieres hacerle:",
                "Arreglar atributo",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        String val = "";
        int ret = -1;
        ArrayList<Object> userResponse = new ArrayList<>();
        switch(opt) {
            case JOptionPane.YES_OPTION:
                ret = 1;
                switch (i) {
                    case 1:
                        val = JOptionPane.showInputDialog(null, "Introduce el nuevo valor entero");
                        break;
                    case 2:
                        val = JOptionPane.showInputDialog(null, "Introduce el nuevo valor decimal");
                        break;
                    case 3:
                        val = JOptionPane.showInputDialog(null, "Introduce el nuevo valor booleano");
                        break;
                    case 4:
                        val = JOptionPane.showInputDialog(null, "Introduce la nueva fecha en formato ISO yyyy-mm-dd ");
                        break;
                    case 5:
                        val = JOptionPane.showInputDialog(null, "Introduce las categorias (con separador distinto al espaciado sencillo ' ')");
                        break;
                    case 6:
                        val = JOptionPane.showInputDialog(null, "Introduce el texto libre");
                        break;
                }
                break;
            case JOptionPane.NO_OPTION:
                ret = 2;
                break;
            case JOptionPane.CANCEL_OPTION:
                ret = 3;
                break;
        }
        userResponse.add(ret);
        userResponse.add(val);
        return userResponse;
    }

    public boolean userHasRatings() {return ctrlDominio.userHasRatings();}

    public String getHeaderType(String header) { return ctrlDominio.getHeaderInfo(header); }

    public void searchItem(String item) { ctrlDominio.searchItem(item); }

    public void guardarRecomendacion(String recomendation) { ctrlDominio.guardarRecomendacion(recomendation); }

    public void guardarTipoDataset(String type) {ctrlDominio.guardarTipoDataset(type);}

    public List<String> getUserRecomendations() {return ctrlDominio.getUserRecomendations();}

    public String getTextRecomendacion(String file) {return ctrlDominio.getTextRecomendacion(file);}

    public List<String> getHeaders() {return ctrlDominio.getHeaders();}

    public void guardarDatosPreprocesados() { ctrlDominio.guardarDatosPreprocesados(); }

    public void resetUpdatedFile() { ctrlDominio.resetUpdatedFile(); }

    public void cargarDatosPreprocesados(String absolutePath) { ctrlDominio.cargarDatosPreprocesados(absolutePath); }

    public void cargarDatosPreprocesados(String items, String ratings, String known, String unknown) {
        ctrlDominio.cargarDatosPreprocesados(items,ratings,known,unknown);
    }

    public boolean existeUsuario(int id) { return ctrlDominio.existeUsuario(id); }

    public Object getHeaderInfo(String s) {return ctrlDominio.getHeaderInfo(s);}

    public int getIdxId() {return ctrlDominio.getIdxId();}
}
