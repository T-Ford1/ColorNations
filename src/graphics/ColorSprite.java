package graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ColorSprite extends Sprite {

    protected final ColorScheme schemes;
    public boolean animated;

    protected ColorSprite(int x, int y, int w, int h, BufferedImage img) {
        super(x, y, w, h, img);
        schemes = new ColorScheme();
        animated = false;
    }

    public ColorSprite(String p) {
        super(p);
        schemes = new ColorScheme();
        animated = false;
    }

    public ColorSprite(Renderable r) {
        super(r);
        schemes = new ColorScheme();
        animated = false;
        if (r instanceof ColorSprite) {
            ColorSprite sprite = (ColorSprite) r;
            HashMap<Integer, ColorFader> map = sprite.schemes.getSchemes();
            map.keySet().stream().forEach((c) -> {
                addColorScheme(c, new ColorFader(map.get(c)));
            });
        }
    }

    public ColorSprite(int rgb, int w, int h) {
        super(rgb, w, h);
        schemes = new ColorScheme();
        animated = false;
    }

    public int getPixel(int x, int y) {
        return getPixel(super.getPixel(x, y));
    }

    private int getPixel(int rgb) {
        return schemes.get(rgb);
    }

    public int[] getPixels() {
        if (animated) {
            int[] p = new int[pixels.length];
            for (int i = 0; i < p.length; i++) {
                p[i] = getPixel(pixels[i]);
            }
            return p;
        }
        return super.getPixels();
    }

    public void update() {
        schemes.update();
    }

    public final void addColorScheme(int color, ColorFader colors) {
        schemes.getSchemes().put(color, colors);
    }

    public ColorSprite copyOf() {
        return new ColorSprite(this);
    }
}
