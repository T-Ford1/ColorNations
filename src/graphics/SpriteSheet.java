package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ford.terrell
 */
public class SpriteSheet {

    public static final SpriteSheet GAME = new SpriteSheet("res/containers/game.png", 72, 128);
    public static final SpriteSheet COMPONENTS = new SpriteSheet("res/components/components.png", 64);
    public static final SpriteSheet TITLEBAR = new SpriteSheet("res/containers/titlebar.png", 201, 61);
    public static final SpriteSheet DIGIT_SHIFT = new SpriteSheet("res/animations/digitshift.png", 72, 128);
    public static final SpriteSheet TITLE = new SpriteSheet("res/animations/title.png", 64, 120);
    public static final SpriteSheet ALPHABET = new SpriteSheet("res/animations/alpha.png", 32, 32);
    public static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?()-=+[]%' .";

    public final int COLUMNS, ROWS;
    private final Sprite[] sprites;
    
    public SpriteSheet(String p, int w, int h) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(p));
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        } catch (IOException ex) {
            image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        }
        COLUMNS = image.getWidth() / w;
        ROWS = image.getHeight() / h;
        sprites = new Sprite[COLUMNS * ROWS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                sprites[y * COLUMNS + x] = new ColorSprite(x * w, y * h, w, h, image);
            }
        }
    }

    public SpriteSheet(String p, int s) {
        this(p, s, s);
    }

    public Sprite getSprite(int x, int y) {
        return sprites[y * COLUMNS + x];
    }
    
    public Sprite[] getSprites() {
        return sprites;
    }
    
    public void setSprite(Sprite s, int x, int y) {
        sprites[y * COLUMNS + x] = s;
    }
    
    public void setSprites(Sprite[] s) {
        for (int i = 0; i < sprites.length & i < s.length; i++) {
            sprites[i] = s[i];
        }
    }
    
    public static final SpriteSheet PIXEL_16_SHEET = new SpriteSheet("./res/textures/sprites_16pix.png", 16);
    public static final SpriteSheet PIXEL_32_SHEET = new SpriteSheet("./res/textures/sprites_32pix.png", 32);
    public static final SpriteSheet PIXEL_64_SHEET = new SpriteSheet("./res/textures/sprites_64pix.png", 64);

    //public static final SpriteSheet PLAYER = new SpriteSheet("./res/textures/sprites_32pix_player.png", 32);
    public static final SpriteSheet PLAYER = new SpriteSheet("./res/textures/slug.png", 86, 62);
    public static final SpriteSheet PARTICLE = new SpriteSheet("./res/projectiles/particle.png", 36);
    public static final SpriteSheet MOB = new SpriteSheet("./res/textures/sprites_32pix_mob.png", 32);
}
