/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.particle;

import graphics.SpriteSet;
import game.entity.Entity;

/**
 *
 * @author Terrell
 */
public abstract class Particle extends Entity {
    
    protected Entity source;
    protected int life, range;
    private final double speed;
    private double zStep, xStep, yStep;
    
    public Particle(Entity src, int xSpwn, int ySpwn, int l, double s) {
        super(xSpwn, ySpwn);
        life = l;
        speed = s;
        source = src;
        animation = SpriteSet.PARTICLE_SET;
        xStep = random.nextGaussian() * speed;
        yStep = random.nextGaussian() * speed;
    }
    
    public void update() {
        if (update >= life) {
            removed = true;
        }
        zStep += .15;
        move(xStep, yStep + zStep, false);
        super.update();
    }
}