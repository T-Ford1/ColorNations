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
public class ForestTile extends BackgroundTile {

    public ForestTile(Sprite s) {
        super(s);
    }
    
    public boolean isSlowed() {
        return true;
    }
}
