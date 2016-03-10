import java.awt.image.BufferedImage;

public class Iconiser {
    private String input;

    public Iconiser(String input) {
        this.input = input;
    }

    public BufferedImage getIcon() {

        return null;
    }

    private int[] getInputAsCodes() {
        char[] inputCharArray = input.toCharArray();
        int[] charCodes = new int[inputCharArray.length];
        int i = 0;
        for (char c : inputCharArray) {
            charCodes[i] = (int) c;
            i++;
        }
        return charCodes;
    }

}
