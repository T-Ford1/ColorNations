/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.tile.BackgroundTile;
import game.tile.Tile;
import graphics.Sprite;
import java.awt.Color;

/**
 *
 * @author ford.terrell
 */
public class VisibleChunks {

    private static Sprite[][] visTiles;
    private static Sprite voidSprite;

    protected VisibleChunks() {
        int width = Game.LEVEL.map.getWidth();
        int height = Game.LEVEL.map.getHeight();
        visTiles = new Sprite[height][width];
        for (int y = 0; y < visTiles.length; y++) {
            for (int x = 0; x < visTiles[y].length; x++) {
                visTiles[y][x] = new Sprite("./res/tiles/" + y + "/" + x + ".png");
            }
        }
        voidSprite = new Sprite(Color.black.getRGB(), 32 * 64, 32 * 64);
    }

    protected Tile getChunk(int x, int y) {
        try {
            return new BackgroundTile(visTiles[y][x]);
        } catch (Exception ex) {
        }
        return new BackgroundTile(voidSprite);
    }
}
