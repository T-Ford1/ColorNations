package game;

import graphics.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author ford.terrell
 */
public class GameCanvas extends Sprite {

    private int renderX, renderY;
    
    public GameCanvas(int w, int h) {
        super(0xFF_FF_00_FF, w, h);
    }

    public void renderString(Graphics g, String s) {
        g.setColor(Color.black);
        g.drawString(s, 10, 25);
    }
    
    public void render() {
        Point p = Game.getRenderPoint(Game.LEVEL.getPlayer());
        renderX = p.x;
        renderY = p.y;
    }

    public void renderPixels(int xPos, int yPos, int width, int height, int[] pixels) {
        xPos -= renderX;
        yPos -= renderY;
        if(!isOnScreen(xPos, yPos, width, height)) {
            return;
        }
        for (int yOff = 0; yOff < height; yOff++) {
            int y = yOff + yPos;
            int index1 = y * getWidth() + xPos;
            int index2 = yOff * width;
            if (y < 0 || y >= getHeight()) {
                continue;
            }
            for (int xOff = 0; xOff < width; xOff++, index1 ++, index2++) {
                int x = xOff + xPos;
                if (x < 0 || x >= getWidth()) {
                    continue;
                }
                renderPixel(index1, pixels[index2]);
            }
        }
    }
    
    public void layerPixels(int xPos, int yPos, int width, int height, Color c) {
        xPos -= renderX;
        yPos -= renderY;
        if(!isOnScreen(xPos, yPos, width, height)) {
            return;
        }
        for (int yOff = 0; yOff < height; yOff++) {
            int y = yOff + yPos;
            int index1 = y * getWidth() + xPos;
            int index2 = yOff * width;
            if (y < 0 || y >= getHeight()) {
                continue;
            }
            for (int xOff = 0; xOff < width; xOff++, index1 ++, index2++) {
                int x = xOff + xPos;
                if (x < 0 || x >= getWidth()) {
                    continue;
                }
                layerPixel(index1, c);
            }
        }
    }

    private void renderPixel(int index, int color) {
        if (color == 0xFF_FF_00_FF) {
            return;
        }
        pixels[index] = color;
    }
    
    private void layerPixel(int index, Color c) {
        int rgb = pixels[index];
        byte rO = (byte) (rgb >> 16);
        byte gO = (byte) (rgb >> 8);
        byte bO = (byte) rgb;
        int r = (rO + c.getRed()) & 255;
        int g = (gO + c.getGreen()) & 255;
        int b = (bO + c.getBlue()) & 255;
        pixels[index] = new Color(r, g, b).getRGB();
    }

    public boolean isOnScreen(int x, int y, int width, int height) {
        return x + width >= 0 && x < getWidth() && y + height >= 0 && y < getHeight();
    }
}
