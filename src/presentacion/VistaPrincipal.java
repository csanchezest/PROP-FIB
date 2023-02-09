package presentacion;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dominio.controladores.ControladorDominio;
import persistencia.ControladorPersistencia;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;


public class VistaPrincipal {
    private ControladorPresentacion ctrlPresentacion;
    private JTextField buscarUsuarioTextField;
    private JPanel MainPanel;
    private JButton recuperarRecomendacionButton;
    private JButton generarRecomendacionButton;
    private JButton exitButton;
    private JButton logOutButton;
    private JTextField buscarItemTextField;
    private JFrame frame;
    private JButton loadDataset;
    private JLabel datasetName;
    private JButton addItem;
    private JButton guardarDatosPreprocesadosButton;
    private JFileChooser chooser;

    public VistaPrincipal(ControladorPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        guardarDatosPreprocesadosButton.addActionListener(e -> {
            int ans = JOptionPane.showConfirmDialog(null, "¿Estas seguro de que deseas guardar los datos hasta ahora procesados?", "Guardar datos", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) ctrlPresentacion.guardarDatosPreprocesados();
        });
        addItem.addActionListener(e -> {
            if (ctrlPresentacion.isDatasetCargado()) {
                frame.dispose();
                new VistaAfegirItem(ctrlPresentacion).VistaAfegirItem();
            } else JOptionPane.showMessageDialog(frame, "ERROR: no has cargado ningun dataset");
        });
        buscarItemTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (buscarItemTextField.getText().equals("Buscar Item")) {
                    buscarItemTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (buscarItemTextField.getText().equals("")) {
                    buscarItemTextField.setText("Buscar Item");
                }
            }
        });
        buscarUsuarioTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (buscarUsuarioTextField.getText().equals("Buscar Usuario")) {
                    buscarUsuarioTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (buscarUsuarioTextField.getText().equals("")) {
                    buscarUsuarioTextField.setText("Buscar Usuario");
                }
            }
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        logOutButton.addActionListener(e -> {
            frame.dispose();
            ctrlPresentacion.sincronizacionVistaPrincipalALogIn();
        });
        recuperarRecomendacionButton.addActionListener(e -> {
            setInvisible();
            new VistaRecuperarRecomendacion(ctrlPresentacion).VistaRecuperarRecomendacion();
        });
        generarRecomendacionButton.addActionListener(e -> {
            if (ctrlPresentacion.isDatasetCargado()) {
                setInvisible();
                new VistaGenerarRecomendacion(ctrlPresentacion).VistaGenerarRecomendacion();
            } else JOptionPane.showMessageDialog(frame, "ERROR: no has cargado ningun dataset");
        });
        Action searchItem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ctrlPresentacion.isDatasetCargado())
                    JOptionPane.showMessageDialog(frame, "ERROR: no hay ningun dataset cargado");
                else {
                    String item = buscarItemTextField.getText();
                    if (!ctrlPresentacion.existeItem(item))
                        JOptionPane.showMessageDialog(frame, "ERROR: el item introducido no existe");
                    else {
                        ctrlPresentacion.searchItem(item);
                        setInvisible();
                        new VistaItem(ctrlPresentacion).VistaItem();
                    }
                }
            }
        };
        Action searchUser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ctrlPresentacion.isDatasetCargado())
                    JOptionPane.showMessageDialog(frame, "ERROR: no hay ningun dataset cargado");
                else {
                    int k;
                    k = Integer.parseInt(buscarUsuarioTextField.getText());
                    HashMap<String, Double> items = ctrlPresentacion.getUsuaris(k);
                    setInvisible();
                    new VistaUser(ctrlPresentacion).VistaUser(items);

                }
            }
        };
        buscarItemTextField.addActionListener(searchItem);
        buscarUsuarioTextField.addActionListener(searchUser);
        loadDataset.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(null,
                    "¿Desea cargar datos preprocesados?",
                    "Carga datos",
                    JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena la informacion sobre los conjuntos de items");
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/persistencia/data/datosPreprocesados"));
                Frame f = new Frame();
                File items, ratings, known, unknown;
                int answer = chooser.showOpenDialog(f);
                if (answer == JFileChooser.APPROVE_OPTION) {
                    items = new File(chooser.getSelectedFile().getAbsolutePath());
                    //ctrlPresentacion.cargarDatosPreprocesados(chooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena los ratings generales");
                    chooser.setCurrentDirectory(new File(items.getAbsolutePath()));
                    f = new Frame();
                    answer = chooser.showOpenDialog(f);
                    if (answer == JFileChooser.APPROVE_OPTION) {
                        ratings = new File(chooser.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena las queries conocidas (known)");
                        chooser.setCurrentDirectory(new File(items.getAbsolutePath()));
                        f = new Frame();
                        answer = chooser.showOpenDialog(f);
                        if (answer == JFileChooser.APPROVE_OPTION) {
                            known = new File(chooser.getSelectedFile().getAbsolutePath());
                            JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena las queries desconocidas (unknown)");
                            chooser.setCurrentDirectory(new File(items.getAbsolutePath()));
                            f = new Frame();
                            answer = chooser.showOpenDialog(f);
                            if (answer == JFileChooser.APPROVE_OPTION) {
                                unknown = new File(chooser.getSelectedFile().getAbsolutePath());
                                ctrlPresentacion.cargarDatosPreprocesados(items.getAbsolutePath(), ratings.getAbsolutePath(), known.getAbsolutePath(), unknown.getAbsolutePath());
                                datasetName.setText("Dataset cargado correctamente");
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena la informacion sobre los tipos de datos del dataset de items");
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Datasets"));
                Frame f = new Frame();
                int answer = chooser.showOpenDialog(f);
                String path = "";
                if (answer == JFileChooser.APPROVE_OPTION) {
                    File aux = new File(chooser.getSelectedFile().getAbsolutePath());
                    path = aux.getAbsolutePath();
                    JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena los items a tratar");
                    chooser.setCurrentDirectory(new File(path));
                    answer = chooser.showOpenDialog(frame);
                    if (answer == JFileChooser.APPROVE_OPTION) {
                        File items = new File(chooser.getSelectedFile().getAbsolutePath());
                        path = items.getAbsolutePath();
                        JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena las valoraciones generales");
                        chooser.setCurrentDirectory(new File(path));
                        answer = chooser.showOpenDialog(frame);
                        if (answer == JFileChooser.APPROVE_OPTION) {
                            File ratings = new File(chooser.getSelectedFile().getAbsolutePath());
                            JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena las valoraciones \"conocidas\" (known)");
                            chooser.setCurrentDirectory(new File(path));
                            answer = chooser.showOpenDialog(frame);
                            if (answer == JFileChooser.APPROVE_OPTION) {
                                File known = new File(chooser.getSelectedFile().getAbsolutePath());
                                JOptionPane.showMessageDialog(frame, "Porfavor, selecciona el archivo que almacena las valoraciones \"desconocidas\" (unknown)");
                                chooser.setCurrentDirectory(new File(path));
                                answer = chooser.showOpenDialog(frame);
                                if (answer == JFileChooser.APPROVE_OPTION) {
                                    String[] list = {"--Seleccione un idioma--", "Catalan", "Espanol", "Ingles", "Portugues", "Frances",
                                            "Italiano", "Aleman", "Otros"};
                                    JComboBox jcb = new JComboBox(list);
                                    jcb.setEditable(true);
                                    JOptionPane.showMessageDialog(null, jcb, "Elija el idioma del Dataset", JOptionPane.QUESTION_MESSAGE);
                                    String language = jcb.getSelectedItem().toString();
                                    while (language.equals("--Seleccione un idioma--")) {
                                        JOptionPane.showMessageDialog(null, "Por favor, elija algun idioma");
                                        JOptionPane.showMessageDialog(null, jcb, "Elija el idioma del Dataset", JOptionPane.QUESTION_MESSAGE);
                                        language = jcb.getSelectedItem().toString();
                                    }
                                    if (language.equals("Otros"))
                                        JOptionPane.showMessageDialog(null, "No tenemos registrado el idioma del dataset, no se podra computar distancia entre atributos de texto libre");
                                    if (answer == JFileChooser.APPROVE_OPTION) {
                                        String type = JOptionPane.showInputDialog(null, "Porfavor, indique un identificador para el tipo de item introducido");
                                        datasetName.setText("");
                                        ctrlPresentacion.guardarTipoDataset(type);
                                        File unknown = new File(chooser.getSelectedFile().getAbsolutePath());
                                        ctrlPresentacion.guardarItems(items);
                                        ctrlPresentacion.guardarAuxiliares(aux);
                                        ctrlPresentacion.guardarRatings(ratings);
                                        ctrlPresentacion.guardarKnown(known);
                                        ctrlPresentacion.guardarUnknown(unknown);
                                        ctrlPresentacion.guardarLanguage(language);
                                        ctrlPresentacion.loadDataset();
                                        ctrlPresentacion.resetUpdatedFile();
                                        datasetName.setText("Dataset cargado correctamente");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        if (ctrlPresentacion.isDatasetCargado()) datasetName.setText("Dataset cargado correctamente");
    }

    public void desactivar() {
        frame.dispose();
    }

    public void activar() {
        frame = new JFrame("Menu Principal");
        frame.setContentPane(this.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900, 300);
    }

    public void setVisible() {
        frame.setVisible(true);
    }

    public void setInvisible() {
        frame.setVisible(false);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayoutManager(8, 9, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        MainPanel.add(spacer1, new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buscarItemTextField = new JTextField();
        buscarItemTextField.setText("Buscar Item");
        MainPanel.add(buscarItemTextField, new GridConstraints(4, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, -1), null, 0, false));
        buscarUsuarioTextField = new JTextField();
        buscarUsuarioTextField.setText("Buscar Usuario");
        MainPanel.add(buscarUsuarioTextField, new GridConstraints(4, 2, 2, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loadDataset = new JButton();
        loadDataset.setText("Cargar dataset");
        MainPanel.add(loadDataset, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        MainPanel.add(spacer2, new GridConstraints(7, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        recuperarRecomendacionButton = new JButton();
        recuperarRecomendacionButton.setText("Recuperar Recomendacion");
        MainPanel.add(recuperarRecomendacionButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generarRecomendacionButton = new JButton();
        generarRecomendacionButton.setText("Generar Recomendacion");
        MainPanel.add(generarRecomendacionButton, new GridConstraints(6, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addItem = new JButton();
        addItem.setText("Añadir item");
        MainPanel.add(addItem, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        MainPanel.add(spacer3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        MainPanel.add(spacer4, new GridConstraints(6, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        logOutButton = new JButton();
        logOutButton.setText("Log Out");
        MainPanel.add(logOutButton, new GridConstraints(5, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        MainPanel.add(spacer5, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        MainPanel.add(exitButton, new GridConstraints(6, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("DialogInput", Font.BOLD, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Menu Principal");
        MainPanel.add(label1, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        MainPanel.add(spacer6, new GridConstraints(7, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        MainPanel.add(spacer7, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        MainPanel.add(spacer8, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        datasetName = new JLabel();
        datasetName.setText("");
        MainPanel.add(datasetName, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        guardarDatosPreprocesadosButton = new JButton();
        guardarDatosPreprocesadosButton.setText("Guardar datos preprocesados");
        MainPanel.add(guardarDatosPreprocesadosButton, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}
