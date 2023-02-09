package presentacion;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class VistaGenerarRecomendacion {
    private ControladorPresentacion ctrlPresentacion;
    private JRadioButton collaborativeFilteringRadioButton;
    private JRadioButton contentBasedRadioButton;
    private JTextField kElements;
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private JPanel DoRecomendation;
    private JButton atrasButton;
    private JButton exitButton;
    private JButton generarRecomendacionButton;
    private JRadioButton hybridRadioButton;
    private JComboBox atrSelection;
    private JButton escogerDeNuevoButton;
    private JTextPane listAttr;
    private JFrame frame = new JFrame();
    private List<String> headers;
    private List<Boolean> chosenAtr;

    public VistaGenerarRecomendacion(ControladorPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        frame = new JFrame("generarRecomenacio");
        atrasButton.addActionListener(e -> {
            frame.dispose();
            ctrlPresentacion.activarVistaPrincipal();
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        generarRecomendacionButton.addActionListener(actionEvent -> {
            if (collaborativeFilteringRadioButton.isSelected() || contentBasedRadioButton.isSelected() || hybridRadioButton.isSelected()) {
                int k;
                try {
                    k = Integer.parseInt(kElements.getText());
                    ctrlPresentacion.setK(k);
                    HashMap<String, Double> recom;
                    if (siRadioButton.isSelected() || noRadioButton.isSelected()) {
                        if (ctrlPresentacion.userHasRatings()) {
                            if (!listAttr.getText().equals("Atributos elegidos:") || collaborativeFilteringRadioButton.isSelected()) {
                                if (collaborativeFilteringRadioButton.isSelected())
                                    recom = ctrlPresentacion.makeRecomendation(0, chosenAtr);
                                else if (contentBasedRadioButton.isSelected())
                                    recom = ctrlPresentacion.makeRecomendation(1, chosenAtr);
                                else
                                    recom = ctrlPresentacion.makeRecomendation(2, chosenAtr);
                                NumberFormat formatter = new DecimalFormat("#0.000");
                                String s = "";
                                for (String i : recom.keySet())
                                    s += (" " + i + " --> " + formatter.format(recom.get(i).doubleValue())) + "\n";
                                if (siRadioButton.isSelected()) s += ctrlPresentacion.getRecomendacion();
                                new VistaRecomendacion(ctrlPresentacion).VistaRecomendacion(s);
                            } else
                                JOptionPane.showMessageDialog(frame, "Error: Seleccione los items con los que se hara la valoracion");
                        } else
                            JOptionPane.showMessageDialog(frame, "Error: No hay ninguna valoracion hecha por el usuario actual. Para pedir una recomendacion debes valorar por lo menos un item");
                    } else
                        JOptionPane.showMessageDialog(frame, "Error: Seleccione si quiere que se evalue la recomendacion");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: Introduce un numero de items correcto");
                }
            } else
                JOptionPane.showMessageDialog(frame, "Error: Seleccione el algoritmo que quiere usar");
        });
        atrSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s = listAttr.getText();
                if (!atrSelection.getSelectedItem().toString().equals("--Seleccione los atributos--")) {
                    if (atrSelection.getSelectedItem().toString().equals("Todos")) {
                        listAttr.setText("Atributos elegidos:" + atrSelection.getSelectedItem().toString() + ";");
                        Collections.fill(chosenAtr, Boolean.TRUE);
                    } else {
                        if (s.equals("Atributos elegidos:Todos;")) {
                            Collections.fill(chosenAtr, Boolean.FALSE);
                            s = "Atributos elegidos:";
                        }
                        for (int i = 0; i < headers.size(); ++i) {
                            if (headers.get(i).equals(atrSelection.getSelectedItem().toString())) {
                                if (!chosenAtr.get(i)) {
                                    listAttr.setText(s + atrSelection.getSelectedItem().toString() + ";");
                                    chosenAtr.set(i, true);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });
        escogerDeNuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listAttr.setText("Atributos elegidos:");
                Collections.fill(chosenAtr, Boolean.FALSE);
            }
        });
    }

    public void VistaGenerarRecomendacion() {
        frame.setContentPane(this.DoRecomendation);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        loadInfo();
        frame.setVisible(true);
        frame.setSize(1400, 500);
    }

    private void loadInfo() {
        headers = ctrlPresentacion.getHeaders();
        chosenAtr = new ArrayList<>(Arrays.asList(new Boolean[headers.size()]));
        Collections.fill(chosenAtr, Boolean.FALSE);
        atrSelection.addItem("--Seleccione los atributos--");
        atrSelection.addItem("Todos");
        for (int i = 0; i < headers.size(); ++i) {
            if (i == ctrlPresentacion.getIdxId()) continue;
            if (!ctrlPresentacion.getHeaderInfo(headers.get(i)).equals("other"))
                atrSelection.addItem(headers.get(i));
        }

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
        DoRecomendation = new JPanel();
        DoRecomendation.setLayout(new GridLayoutManager(11, 8, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setEnabled(true);
        Font label1Font = this.$$$getFont$$$("DialogInput", Font.BOLD, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Por favor, elija los siguientes parametros");
        DoRecomendation.add(label1, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        DoRecomendation.add(spacer1, new GridConstraints(2, 6, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Indique el algoritmo");
        DoRecomendation.add(label2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Indique el numero de items a recomendar");
        DoRecomendation.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        DoRecomendation.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        DoRecomendation.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        kElements = new JTextField();
        DoRecomendation.add(kElements, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        DoRecomendation.add(spacer4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        DoRecomendation.add(spacer5, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Desea que se imprima la evaluacion de la recomendacion?");
        DoRecomendation.add(label4, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        siRadioButton = new JRadioButton();
        siRadioButton.setText("Si");
        DoRecomendation.add(siRadioButton, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noRadioButton = new JRadioButton();
        noRadioButton.setText("No");
        DoRecomendation.add(noRadioButton, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        collaborativeFilteringRadioButton = new JRadioButton();
        collaborativeFilteringRadioButton.setText("Collaborative Filtering");
        DoRecomendation.add(collaborativeFilteringRadioButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contentBasedRadioButton = new JRadioButton();
        contentBasedRadioButton.setText("Content Based");
        DoRecomendation.add(contentBasedRadioButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        atrasButton = new JButton();
        atrasButton.setText("Back");
        DoRecomendation.add(atrasButton, new GridConstraints(9, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        DoRecomendation.add(exitButton, new GridConstraints(9, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        DoRecomendation.add(spacer6, new GridConstraints(9, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        DoRecomendation.add(spacer7, new GridConstraints(10, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        hybridRadioButton = new JRadioButton();
        hybridRadioButton.setText("Hybrid");
        DoRecomendation.add(hybridRadioButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generarRecomendacionButton = new JButton();
        generarRecomendacionButton.setText("Generar Recomendacion");
        DoRecomendation.add(generarRecomendacionButton, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        atrSelection = new JComboBox();
        DoRecomendation.add(atrSelection, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        DoRecomendation.add(spacer8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        escogerDeNuevoButton = new JButton();
        escogerDeNuevoButton.setText("Escoger de Nuevo");
        DoRecomendation.add(escogerDeNuevoButton, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        DoRecomendation.add(scrollPane1, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listAttr = new JTextPane();
        listAttr.setText("Atributos elegidos:");
        scrollPane1.setViewportView(listAttr);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(siRadioButton);
        buttonGroup.add(noRadioButton);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(collaborativeFilteringRadioButton);
        buttonGroup.add(contentBasedRadioButton);
        buttonGroup.add(hybridRadioButton);
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
        return DoRecomendation;
    }

}
