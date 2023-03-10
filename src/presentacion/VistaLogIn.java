package presentacion;


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class VistaLogIn extends Component {
    private ControladorPresentacion ctrlPresentacion;
    private JLabel title;
    private JComboBox usernameSelection;
    private JLabel username;
    private JButton exit;
    private JButton search;
    private JPanel generalPanel;
    private JPanel titlePanel;
    private JPanel usernamePanel;
    private JPanel functionsPanel;
    private JTextField userId;
    private JLabel introText;
    private JLabel introLabel2;
    private JFrame frame;

    public VistaLogIn(ControladorPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        exit.addActionListener(e -> System.exit(0));
        search.addActionListener(e -> {
            //Aqui s'ha d'incloure que es guardi l'id d'usuari a algun lloc...
            if (userId.getText().equals(""))
                JOptionPane.showMessageDialog(frame, "ERROR: has intentado entrar en el sistema sin introducir tu id de usuario");
            else {
                try {
                    if (Integer.parseInt(userId.getText()) <= 0) throw new Exception();
                    ctrlPresentacion.setUser(Integer.parseInt(userId.getText()));
                    frame.dispose();
                    ctrlPresentacion.sincronizacionLogInAVistaPrincipal();
                    //new VistaPrincipal().VistaPrincipal();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, "ERROR: has intentado entrar en el sistema con un id erroneo");
                }
            }
            userId.setText("");
        });
        userId.addActionListener(e -> {
            if (userId.getText().equals(""))
                JOptionPane.showMessageDialog(frame, "ERROR: has intentado entrar en el sistema sin introducir tu id de usuario");
            else {
                try {
                    if (Integer.parseInt(userId.getText()) <= 0) throw new Exception();
                    ctrlPresentacion.setUser(Integer.parseInt(userId.getText()));
                    frame.dispose();
                    ctrlPresentacion.sincronizacionLogInAVistaPrincipal();
                    //new VistaPrincipal().VistaPrincipal();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, "ERROR: has intentado entrar en el sistema con un id erroneo");
                }
            }
            userId.setText("");
        });
    }

    public void activar() {
        frame = new JFrame("Menu de inicio de sesion");
        frame.setContentPane(this.generalPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 400);
    }

    public void desactivar() {
        frame.dispose();
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
        generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        generalPanel.add(titlePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        title = new JLabel();
        Font titleFont = this.$$$getFont$$$(null, Font.BOLD | Font.ITALIC, 18, title.getFont());
        if (titleFont != null) title.setFont(titleFont);
        title.setText("Sistema Recomendador");
        titlePanel.add(title, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        generalPanel.add(usernamePanel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        username = new JLabel();
        username.setText("Indica tu id de usuario");
        usernamePanel.add(username, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        search = new JButton();
        search.setText("Entrar");
        usernamePanel.add(search, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userId = new JTextField();
        usernamePanel.add(userId, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        functionsPanel = new JPanel();
        functionsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        generalPanel.add(functionsPanel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        exit = new JButton();
        exit.setText("Exit");
        functionsPanel.add(exit, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        introText = new JLabel();
        Font introTextFont = this.$$$getFont$$$(null, Font.BOLD, 16, introText.getFont());
        if (introTextFont != null) introText.setFont(introTextFont);
        introText.setText("Bienvenido al sistema recomendador.  ");
        generalPanel.add(introText, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        introLabel2 = new JLabel();
        Font introLabel2Font = this.$$$getFont$$$(null, Font.BOLD, 16, introLabel2.getFont());
        if (introLabel2Font != null) introLabel2.setFont(introLabel2Font);
        introLabel2.setText("Porfavor, introduzca su identificador de usuario");
        generalPanel.add(introLabel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return generalPanel;
    }
}
