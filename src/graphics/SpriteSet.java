/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import static graphics.SpriteSheet.*;
import java.awt.Color;

/**
 *
 * @author Terrell
 */
public class SpriteSet {

    private final int width, height;
    private final Sprite[] sprites;
    

    public SpriteSet(int x, int y, int width, int height, SpriteSheet sheet) {
        this.width = width;
        this.height = height;
        sprites = new Sprite[width * height];
        for (int y1 = 0; y1 < height; y1++) {
            int yOff = y1 + y;
            for (int x1 = x; x1 < x + width; x1++) {
                int xOff = x1 + x;
                sprites[y1 * width + x1] = sheet.getSprite(xOff, yOff);
            }
        }
    }
    
    private SpriteSet(int width, int height, int length) {
        this.width = width;
        this.height = height;
        sprites = new Sprite[length];
    }

    public Sprite getSprite(int x, int y) {
        return sprites[y * width + x];
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public static SpriteSet getRedPlayer() {
        return PLAYER_SET;
    }

    public static SpriteSet getGreenPlayer() {
        SpriteSet anim = new SpriteSet(PLAYER_SET.width, PLAYER_SET.height, PLAYER_SET.sprites.length);
        for (int s = 0; s < PLAYER_SET.sprites.length; s++) {
            Sprite sprite = PLAYER_SET.sprites[s];
            anim.sprites[s] = new Sprite(0xFF_FF_00_FF, sprite.getWidth(), sprite.getHeight());
            for(int i = 0; i < sprite.getPixels().length; i++) {
                int rgb = sprite.getPixels()[i];
                if(rgb == 0xFF_FF_00_FF) {
                    continue;
                }
                Color c = new Color(rgb);
                Color newColor = new Color(c.getGreen(), c.getRed(), c.getBlue());
                anim.sprites[s].pixels[i] = newColor.getRGB();
            }
        }
        return anim;
    }

    public static SpriteSet getBluePlayer() {
        SpriteSet anim = new SpriteSet(PLAYER_SET.width, PLAYER_SET.height, PLAYER_SET.sprites.length);
        for (int s = 0; s < PLAYER_SET.sprites.length; s++) {
            Sprite sprite = PLAYER_SET.sprites[s];
            anim.sprites[s] = new Sprite(0xFF_FF_00_FF, sprite.getWidth(), sprite.getHeight());
            for(int i = 0; i < sprite.getPixels().length; i++) {
                int rgb = sprite.getPixels()[i];
                if(rgb == 0xFF_FF_00_FF) {
                    continue;
                }
                Color c = new Color(rgb);
                Color newColor = new Color(c.getBlue(), c.getGreen(), c.getRed());
                anim.sprites[s].pixels[i] = newColor.getRGB();
            }
        }
        return anim;
    }
    
    public static final SpriteSet PLAYER_SET = new SpriteSet(0, 0, 4, 3, PLAYER);
    public static final SpriteSet PARTICLE_SET = new SpriteSet(0, 0, 1, 1, PARTICLE);
    public static final SpriteSet MOB_SET = new SpriteSet(0, 0, 4, 3, MOB);
}
