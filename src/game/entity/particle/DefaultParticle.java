/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.particle;

import game.GameCanvas;
import game.entity.Entity;
import graphics.Sprite;
import java.awt.Rectangle;

/**
 *
 * @author ford.terrell
 */
public class DefaultParticle extends Particle {

    public DefaultParticle(Entity src, int xSpwn, int ySpwn, int l, double s) {
        super(src, xSpwn, ySpwn, l, s);
    }
    
    protected Rectangle getBounds() {
        return new Rectangle(getX(), getY(), 7, 7);
    }
    
    public void render(GameCanvas frame) {
        Sprite s = animation.getSprite(0, 0);
        frame.renderPixels(getX(), getY(), s.getWidth(), s.getHeight(), s.getPixels());
    }
    
    protected void onCollision(Object collision) {
        removed = true;
    }
}
