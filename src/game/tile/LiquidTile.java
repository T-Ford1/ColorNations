/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tile;

import graphics.Sprite;

/**
 *
 * @author ford.terrell
 */
public class LiquidTile extends BackgroundTile {

    public LiquidTile(Sprite s) {
        super(s);
    }
    
    public boolean isLiquid() {
        return false;
    }
    
    public boolean isSlowed() {
        return true;
    }
}
