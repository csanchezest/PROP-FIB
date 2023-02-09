package presentacion;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class VistaAfegirItem {
    private ControladorPresentacion ctrlPresentacion;
    private JPanel windowPanel;
    private JLabel title;
    private JComboBox itemAttributes;
    private JTextField itemValues;
    private JButton confirmarValorButton;
    private JButton back;
    private JButton exit;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JFrame frame;
    private HashMap<String, String> item;
    private int n = 0, itemAtrCount = 0;

    public VistaAfegirItem(ControladorPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        frame = new JFrame("VistaAfegirItem");
        itemAtrCount = 0;
        item = new HashMap<>();
        loadInfo();
        itemValues.addActionListener(e -> {
            if (itemValues.getText().equals(""))
                JOptionPane.showMessageDialog(frame, "ERROR: no has introducido ningun valor");
            else {
                String header = (String) itemAttributes.getSelectedItem();
                String type = ctrlPresentacion.getHeaderType(header);
                //afegir tractament que fa l'Houda a cjt_items de tal forma que s'identifiqui si el tipus d'informacio
                // introduida per l'usuari correspon a la que es requereix per la columna indicada
                if (true) JOptionPane.showMessageDialog(frame, "Has introducido toda la " +
                        "informacion necesaria para introducir un item nuevo en el dataset");
            }
        });
        confirmarValorButton.addActionListener(e -> {
            if (itemValues.getText().equals(""))
                JOptionPane.showMessageDialog(frame, "ERROR: no has introducido ningun valor");
            else {
                String header = (String) itemAttributes.getSelectedItem();
                String type = ctrlPresentacion.getHeaderType(header);
                //afegir tractament que fa l'Houda a cjt_items de tal forma que s'identifiqui si el tipus d'informacio
                // introduida per l'usuari correspon a la que es requereix per la columna indicada
                itemAtrCount++;
                if (itemAtrCount == n) JOptionPane.showMessageDialog(frame, "Has introducido toda la " +
                        "informacion necesaria para introducir un item nuevo en el dataset");
            }
        });
        back.addActionListener(e -> {
            frame.dispose();
            ctrlPresentacion.activarVistaPrincipal();
        });
        exit.addActionListener(e -> {
            System.exit(0);
        });
    }

    public void VistaAfegirItem() {
        frame.setContentPane(this.windowPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 300);
        frame.setVisible(true);
    }

    private void loadInfo() {
        List<String> cols = ctrlPresentacion.getItemInfo();
        n = cols.size();
        for (String s : cols) itemAttributes.addItem(s);
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
        windowPanel = new JPanel();
        windowPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        windowPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        title = new JLabel();
        Font titleFont = this.$$$getFont$$$(null, Font.BOLD, 22, title.getFont());
        if (titleFont != null) title.setFont(titleFont);
        title.setText("Addicion de item");
        panel1.add(title, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(inputPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        itemAttributes = new JComboBox();
        inputPanel.add(itemAttributes, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(234, 30), null, 0, false));
        itemValues = new JTextField();
        inputPanel.add(itemValues, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        confirmarValorButton = new JButton();
        confirmarValorButton.setText("Confirmar valor");
        panel1.add(confirmarValorButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(buttonPanel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        back = new JButton();
        back.setText("Atras");
        buttonPanel.add(back, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exit = new JButton();
        exit.setText("Salir");
        buttonPanel.add(exit, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        buttonPanel.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        return windowPanel;
    }
}
