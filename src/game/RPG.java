/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.tile.Tile;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ford.terrell
 */
public class RPG extends Level {

    public RPG(int width, int height) {
        super(width, height);
    }

    protected void init() {
        try {
            map = ImageIO.read(new File("res/levels/color.png"));
            mapSize = new Dimension(map.getWidth(), map.getHeight());
            mapPixels = new Dimension(map.getWidth() * Game.SIZE, map.getHeight() * Game.SIZE);
            tiles = map.getRGB(0, 0, map.getWidth(), map.getHeight(), null, 0, map.getWidth());
            for (int i = 0; i < tiles.length; i++) {
                switch (tiles[i]) {
                    case 0xFF_13_12_89://new Color(19, 18, 137)
                        tiles[i] = 0;//deep water
                        break;
                    case 0xFF_7F_7F_7F://new Color(127, 127, 127)
                        tiles[i] = 1;//mountan
                        break;
                    case 0xFF_29_2E_9E://new Color(41, 46, 158)
                        tiles[i] = 2;//shallow water
                        break;
                    case 0xFF_50_4F_1E://new Color(80, 79, 30)
                        tiles[i] = 3;//savannah
                        break;
                    case 0xFF_B0_A9_81://new Color(176, 169, 129)
                        tiles[i] = 4;//sand
                        break;
                    case 0xFF_32_50_1F://new Color(50, 80, 31)
                        tiles[i] = 5;//plains
                        break;
                    case 0xFF_1E_43_11://new Color(30, 67, 17)
                        tiles[i] = 6;//forest
                        break;
                    case 0xFF_BF_C9_C9://new Color(191, 201, 201)
                        tiles[i] = 7;//snow mountain
                        break;
                    case 0xFF_5D_5A_59://new Color(93, 90, 89)
                        tiles[i] = 8;//clay
                        break;
                    case 0xFF_2C_4D_3B://new Color(44, 77, 59)
                        tiles[i] = 9;//evergreen
                        break;
                    case 0xFF_00_00_00://black
                        tiles[i] = 10;//void
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void update() {
        super.update();
    }

    public Tile getTile(int x, int y) {
        int index = x + y * map.getWidth();
        if (index >= 0 && index < tiles.length && x >= 0 && x < map.getWidth() && y >= 0) {
            switch (tiles[index]) {
                case 0:
                    return Tile.DEEP_WATER;
                case 1:
                    return Tile.MOUNTAIN;
                case 2:
                    return Tile.SHALLOW_WATER;
                case 3:
                    return Tile.SAVANNAH;
                case 4:
                    return Tile.SAND;
                case 5:
                    return Tile.PRAIRIE;
                case 6:
                    return Tile.TEMPERATE;
                case 7:
                    return Tile.MOUNTAIN;
                case 8:
                    return Tile.MUD;
                case 9:
                    return Tile.EVERGREEN;
                case 10:
                    return Tile.VOID;
            }
        }
        return Tile.VOID;
    }
}
