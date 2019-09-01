import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Identicon Class
 *
 * @author Huw Jones
 * @since 10/03/2016
 */
public class Identiconify extends JFrame {

    private JLabel label_username;
    private JTextField text_username;

    private JButton btn_identiconify;
    private JButton btn_saveimage;

    private JPanel panel_controls;
    private ImagePanel panel_image;

    private JLabel label_iconiser;
    private JComboBox<String> combo_iconiser;

    protected BufferedImage identicon;

    IIconiser hexIconiser;
    IIconiser randomIconiser;

    public Identiconify() {
        super("Identiconify");
        this.setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        randomIconiser = new RandomIconiser();
        hexIconiser = new HexIconiser();
        panel_image = new ImagePanel();
        this.add(panel_image, BorderLayout.CENTER);

        panel_controls = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panel_controls_inner = new JPanel(new SpringLayout());
        label_username = new JLabel("Username:", JLabel.TRAILING);
        panel_controls_inner.add(label_username);
        text_username = new JTextField();
        panel_controls_inner.add(text_username);

        label_iconiser = new JLabel("Iconiser:", JLabel.TRAILING);
        panel_controls_inner.add(label_iconiser);
        combo_iconiser = new JComboBox<String>();
        combo_iconiser.addItem("Hexagonal");
        combo_iconiser.addItem("Random Triangles");
        panel_controls_inner.add(combo_iconiser);

        panel_controls_inner.add(new JPanel());

        btn_identiconify = new JButton("Identiconify Me!");
        btn_identiconify.setMnemonic(KeyEvent.VK_I);
        btn_identiconify.addActionListener(e -> {
            IIconiser iconiser;
            switch(combo_iconiser.getSelectedIndex()) {
                case 0:
                    iconiser = hexIconiser;
                    break;
                case 1:
                    iconiser = randomIconiser;
                    break;
                default:
                    return;
            }
            identicon = iconiser.getIcon(text_username.getText(), 1024, 1024);
            double scale = HexIconiser.getSquareDimensions(panel_image.getWidth(), panel_image.getHeight()) / 1024f;
            BufferedImage image = new BufferedImage(identicon.getWidth(), identicon.getHeight(), identicon.getType());
            AffineTransform at = new AffineTransform();
            at.scale(scale, scale);
            image = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR).filter(identicon, image);
            panel_image.setImage(image, true);
        });
        panel_controls_inner.add(btn_identiconify);

        panel_controls_inner.add(new JPanel());

        btn_saveimage = new JButton("Save Image");
        btn_saveimage.setMnemonic(KeyEvent.VK_S);
        btn_saveimage.addActionListener(e -> {
            JFileChooser saveChooser = new JFileChooser();
            saveChooser.setFileFilter(new FileNameExtensionFilter("PNG Files", "png"));
            saveChooser.setSelectedFile(new File(text_username.getText() + ".png"));
            if (saveChooser.showSaveDialog(btn_saveimage) == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(identicon, "png", saveChooser.getSelectedFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel_controls_inner.add(btn_saveimage);


        this.panel_controls.add(panel_controls_inner, c);
        c.weighty = 1;
        c.gridy = 1;
        this.panel_controls.add(new JPanel(), c);
        SpringUtilities.makeCompactGrid(panel_controls_inner, 4, 2, 6, 6, 6, 6);
        this.add(this.panel_controls, BorderLayout.LINE_START);
        this.setMinimumSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            Identiconify identiconify = new Identiconify();
        });
    }
}
