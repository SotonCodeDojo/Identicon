import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Iconiser {

    public BufferedImage getIcon(String input, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        int[] codes = getInputAsCodes(input);
        for (int i = codes.length; i != 0; i--) {
            addCharCodeToImage(codes[i -1], g, width, height);
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

    private void addCharCodeToImage(int i, Graphics2D g, int width, int height) {
        g.setColor(new Color(Color.HSBtoRGB(i / 32f, 0.5f, 1f)));
        Random r = new Random(i);
        g.fillPolygon(new int[]{
                r.nextInt(width),
                r.nextInt(width),
                r.nextInt(width)
        }, new int[]{
                r.nextInt(height),
                r.nextInt(height),
                r.nextInt(height)
        }, 3);
    }

}
