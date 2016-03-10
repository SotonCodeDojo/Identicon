import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Iconiser {

    public BufferedImage getIcon(String input, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        for (int i : getInputAsCodes(input)) {
            addCharCodeToImage(i, g, width);
        }
        return image;
    }

    private int[] getInputAsCodes(String input) {
        char[] inputCharArray = input.trim().toCharArray();
        int[] charCodes = new int[inputCharArray.length];
        int i = 0;
        for (char c : inputCharArray) {
            charCodes[i] = ((int) c) - 33;
            i++;
        }
        return charCodes;
    }

    private void addCharCodeToImage(int i, Graphics2D g, int width) {
        g.setColor(new Color(Color.HSBtoRGB((new Random()).nextFloat(), 0.5f, 1f)));
        g.drawPolygon(new int[]{
                (new Random()).nextInt(width),
                (new Random()).nextInt(width),
                (new Random()).nextInt(width)
        }, new int[]{
                (new Random()).nextInt(width),
                (new Random()).nextInt(width),
                (new Random()).nextInt(width)
        }, 3);
    }

}
