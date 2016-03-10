import java.awt.*;
import java.awt.image.BufferedImage;

public class Iconiser {
    private String input;

    public Iconiser(String input) {
        this.input = input;
    }

    public BufferedImage getIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        for (int i : getInputAsCodes()) {
            addCharCodeToImage(i, g);
        }
        return image;
    }

    private int[] getInputAsCodes() {
        char[] inputCharArray = input.trim().toCharArray();
        int[] charCodes = new int[inputCharArray.length];
        int i = 0;
        for (char c : inputCharArray) {
            charCodes[i] = (int) c;
            i++;
        }
        return charCodes;
    }

    private void addCharCodeToImage(int i, Graphics2D g) {

    }

}
