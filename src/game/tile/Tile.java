/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tile;

import game.GameCanvas;
import graphics.Sprite;

/**
 *
 * @author Terrell
 */
public abstract class Tile {
    
    public int x, y;
    public Sprite sprite;
    
    public Tile(Sprite s) {
        sprite = s;
    }
    
    public void render(int x, int y, GameCanvas frame) {
        frame.renderPixels(x, y, sprite.getWidth(), sprite.getHeight(), sprite.getPixels());
    }
    
    public abstract boolean isSolid();
    
    public abstract boolean isSlowed();
    
    public abstract boolean isLiquid();
    
    public int getPixel(int x, int y) {
        return sprite.getPixel(x, y);
    }
    
    public int[] getPixels() {
        return sprite.getPixels(); 
    }
    
    public static Tile VOID = new VoidTile(Sprite.VOID);
    public static Tile PRAIRIE = new BackgroundTile(Sprite.PRAIRIE);
    public static Tile MUD = new BackgroundTile(Sprite.MUD);
    public static Tile ROCK = new RockTile(Sprite.ROCK);
    public static Tile LAVA = new LiquidTile(Sprite.LAVA);
    public static Tile DIRT = new BackgroundTile(Sprite.DIRT);
    public static Tile GRASS = new BackgroundTile(Sprite.GRASS);
    public static Tile WATER = new LiquidTile(Sprite.WATER);
    public static Tile SHALLOW_WATER = new LiquidTile(Sprite.SHALLOW_WATER);
    public static Tile DEEP_WATER = new LiquidTile(Sprite.DEEP_WATER);
    public static Tile WOOD = new BackgroundTile(Sprite.WOOD);
    public static Tile EVERGREEN = new ForestTile(Sprite.EVERGREEN);
    public static Tile TEMPERATE = new ForestTile(Sprite.TEMPERATE);
    public static Tile MOUNTAIN = new RockTile(Sprite.MOUNTAN);
    public static Tile SAND = new BackgroundTile(Sprite.SAND);
    public static Tile SAVANNAH = new BackgroundTile(Sprite.SAVANNAH);
}
