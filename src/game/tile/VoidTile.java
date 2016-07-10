/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tile;

import graphics.Sprite;

/**
 *
 * @author Rance and Lori
 */
public class VoidTile extends Tile {

    public VoidTile(Sprite s) {
        super(s);
    }

    public boolean isSolid() {
        return true;
    }
    
    public boolean isSlowed() {
        return true;
    }
    
    public boolean isLiquid() {
        return false;
    }
}
