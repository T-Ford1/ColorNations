/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.tile.Tile;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ford.terrell
 */
public class Nexus extends Level {

    public Nexus(int width, int height) {
        super(width, height);
    }

    protected void init() {
        try {
            map = ImageIO.read(new File("res/levels/nexus_level_2.png"));
            tiles = new int[map.getWidth() * map.getWidth()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), tiles, 0, map.getWidth());
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] &= 255;
            }
        } catch (IOException e) {
        }
    }
    
    public Tile getTile(int x, int y) {
        int index = x + y * map.getWidth();
        if (index >= 0 && index < tiles.length && x >= 0 && x < map.getWidth() && y >= 0) {
            switch (tiles[index]) {
                case 0:
                    return Tile.LAVA;
                case 1:
                    return Tile.DIRT;
                case 2:
                    return Tile.GRASS;
                case 3:
                    return Tile.WOOD;
                case 4:
                    return Tile.ROCK;
            }
        }
        return Tile.VOID;
    }
}
