import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HexIconiser implements IIconiser {

    protected int dimension;
    protected int mid_point;
    protected int side_length;

    protected final int color_allocation_multiplier = 5;
    protected final int[] hex_map = new int[]{0, 1, 2, 3, 4, 5, 6, 18, 12, 7, 19, 13, 8, 20, 14, 9, 21, 15, 10, 22, 16, 11, 23, 17};
    public final double cos_60 = 0.5;
    public final double sin_60 = 0.86602540378;

    public BufferedImage getIcon(String input, int width, int height) {
        dimension = getSquareDimensions(width, height);
        mid_point = Math.floorDiv(dimension, 2);
        side_length = Math.floorDiv(dimension, 4);
        BufferedImage image = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        try {
            byte[] hashedInput = hashIdentifier(input);
            Color[] colors = convertHashToColours(hashedInput);
            Graphics2D g = image.createGraphics();
            g.setComposite(AlphaComposite.Clear);
            g.fillRect(0, 0, width, height);
            g.setComposite(AlphaComposite.Src);

            for (int i = 0; i < 24; i++) {
                Polygon triangle = createTriangle(i);
                g.setColor(colors[i]);
                g.fillPolygon(triangle);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static int getSquareDimensions(int width, int height) {
        int size = width;
        if (height < width) size = height;
        int modulo = size % 4;
        if (modulo == 0) {
            return size;
        }
        size -= modulo;
        return size;
    }

    private byte[] hashIdentifier(String identifier) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-384");
        return digest.digest(identifier.getBytes());
    }

    private Color[] convertHashToColours(byte[] hash) {
        int[] hue = new int[hash.length / 2];
        int[] sat = new int[hash.length / 2];
        Color[] output = new Color[hue.length];
        for (int i = 0; i < hue.length; i++) {
            hue[i] = hash[2 * i] & 0xFF;
            sat[i] = (hash[2 * i + 1] & 0xFF) % 8 + 5;
        }

        for (int i = 0; i < 24; i++) {
            output[hex_map[i]] = new Color(Color.HSBtoRGB(hue[i] / 64f, sat[i] / 16f, 1f));
        }
        return output;
    }

    private Color[] allocateColors(Color[] input) {
        Color[] output = new Color[input.length];
        for (int i = 0; i < 24; i++) {
            int out_index = (i % color_allocation_multiplier) * color_allocation_multiplier + Math.floorDiv(i, color_allocation_multiplier);
            if (out_index > 23) {
                out_index = (out_index % 24);
            }
            output[out_index] = input[i];
        }
        return output;
    }

    private Polygon createTriangle(int number) {
        Polygon p = new Polygon();
        int x1 = mid_point;
        int y1 = mid_point;
        if (number >= 12) {
            x1 = calc_triangle_x(x1, number + 1);
            y1 = calc_triangle_y(y1, number + 1);
        } else if (number >= 6) {
            x1 = calc_triangle_x(x1, number);
            y1 = calc_triangle_y(y1, number);
        }

        p.addPoint(x1, y1);

        int x2 = 0, y2 = 0;
        if (number >= 18) {
            x2 = calc_triangle_x(x1, number - 1);
            y2 = calc_triangle_y(y1, number - 1);
        } else {
            x2 = calc_triangle_x(x1, number);
            y2 = calc_triangle_y(y1, number);
        }
        p.addPoint(x2, y2);

        int x3 = 0, y3 = 0;
        if (number >= 18) {
            x3 = calc_triangle_x(x1, number);
            y3 = calc_triangle_y(y1, number);
        } else {
            x3 = calc_triangle_x(x1, number + 1);
            y3 = calc_triangle_y(y1, number + 1);
        }
        p.addPoint(x3, y3);

        return p;
    }

    private int calc_triangle_x(int x, int number) {
        return calc_triangle_x(x, number, side_length);
    }

    private int calc_triangle_x(int x, int number, int side_length) {
        return (int) Math.round(x + side_length * Math.cos(Math.toRadians(number * 60)));
    }

    private int calc_triangle_y(int y, int number) {
        return calc_triangle_y(y, number, side_length);
    }

    private int calc_triangle_y(int y, int number, int side_length) {
        return (int) Math.round(y + side_length * Math.sin(Math.toRadians(number * 60)));
    }
}
