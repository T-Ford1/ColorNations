package frame;

import game.Game;
import java.awt.Canvas;
import java.awt.image.BufferedImage;

import java.awt.Rectangle;
import java.awt.image.DataBufferInt;

/**
 *
 * @author ford.terrell
 */
public class Render extends Canvas {

    private static final long serialVersionUID = 1L;

    private static BufferedImage image;
    private static int[] pixels;

    public void init() {
        image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) (image.getRaster().getDataBuffer())).getData();
    }

    public static void renderPixel(int x, int y, int rgb) {
        renderPixel(x + y * image.getWidth(), rgb);
    }

    public static void renderPixel(int index, int rgb) {
        if (rgb == 0xFF_FF_00_FF) {
            return;
        }
        pixels[index] = rgb;
    }

    public static void renderArray(Rectangle img, int[] p) {
        int xStart = img.x < 0 ? -img.x : 0;
        //int xStart = (img.x < 0 ? -img.x : 0) + (img.x < bounds.x ? bounds.x - img.x : 0);
        int yStart = img.y < 0 ? -img.y : 0;
        int xMax = img.x + img.width >= image.getWidth() ? image.getWidth() -img.x : img.width;
        int yMax = img.y + img.height >= image.getHeight() ? image.getHeight() -img.y : img.height;
        int xSkip = xStart + img.width - xMax;
        int wSkip = image.getWidth() - img.width + xSkip;
        int index1 = (yStart + img.y) * image.getWidth() + (xStart + img.x), index2 = yStart * img.width + xStart;
        for (int y = yStart; y < yMax; y++, index1 += wSkip, index2 += xSkip) {
            for (int x = xStart; x < xMax; x++, index1++, index2++) {
                renderPixel(index1, p[index2]);
            }
        }
    }

    public static int getPixel(int x, int y) {
        return pixels[x + y * image.getWidth()];
    }
    
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xFF_00_00_00;
        }
    }

    protected static BufferedImage getImage() {
        return image;
    }
}
