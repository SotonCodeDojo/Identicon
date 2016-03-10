import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Identicon Class
 *
 * @author Huw Jones
 * @since 10/03/2016
 */
public class Identifier extends JFrame {

    private JLabel label_username;
    private JTextField text_username;

    private JButton btn_identiconify;

    private JPanel panel_controls;
    private ImagePanel panel_image;

    Iconiser iconiser;

    public Identifier () {
        super("Identifier");
        this.setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        iconiser = new Iconiser();
        panel_image = new ImagePanel();
        this.add(panel_image, BorderLayout.CENTER);

        panel_controls = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panel_controls_inner = new JPanel(new SpringLayout());
        label_username = new JLabel("Username:", JLabel.TRAILING);
        panel_controls_inner.add(label_username);

        text_username = new JTextField();
        panel_controls_inner.add(text_username);

        panel_controls_inner.add(new JPanel());

        btn_identiconify = new JButton("Identiconify Me!");
        btn_identiconify.setMnemonic(KeyEvent.VK_I);
        btn_identiconify.addActionListener(e -> {
            BufferedImage image = iconiser.getIcon(text_username.getText(), panel_image.getWidth(), panel_image.getWidth());
            panel_image.setImage(image, true);
        });
        panel_controls_inner.add(btn_identiconify);
        this.panel_controls.add(panel_controls_inner, c);
        c.weighty = 1;
        c.gridy = 1;
        this.panel_controls.add(new JPanel(), c);
        SpringUtilities.makeCompactGrid(panel_controls_inner, 2, 2, 6, 6, 6, 6);
        this.add(this.panel_controls, BorderLayout.LINE_START);
        this.setMinimumSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            Identifier identifier = new Identifier();
        });
    }
}
