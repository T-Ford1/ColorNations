/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tile;

import graphics.Sprite;

/**
 *
 * @author Terrell
 */
public class RockTile extends Tile {

    public RockTile(Sprite s) {
        super(s);
    }

    public boolean isSolid() {
        return false;
    }
    
    public boolean isSlowed() {
        return true;
    }
    
    public boolean isLiquid() {
        return false;
    }
}
