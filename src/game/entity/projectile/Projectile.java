/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.projectile;

import static game.Game.LEVEL;
import game.GameCanvas;
import game.entity.Entity;
import game.entity.mob.Mob;
import graphics.SpriteSet;
import java.awt.Rectangle;

/**
 *
 * @author Terrell
 */
public class Projectile extends Entity {

    public static final int damage = 200, rateOfFire = 20;
    public static final double speed = 16, range = 450, rangeSqr = range * range;
    public final Mob source;
    
    private final int size;
    private final double xStep, yStep, theta;

    public Projectile(Mob s, int spwnX, int spwnY, double t) {
        super(spwnX, spwnY);
        entityCollide = true;
        animation = SpriteSet.PARTICLE_SET;
        size = animation.getSprite(0, 0).getWidth();
        theta = t;
        source = s;
        xStep = Math.cos(theta) * speed;
        yStep = Math.sin(theta) * speed;
        move((Math.cos(theta) * 50), (Math.sin(theta) * 50), false);
    }
    
    public void render(GameCanvas c) {
        if (!removed) {
            c.renderPixels(getX(), getY(), size, size, animation.getSprite(0, 0).getPixels());
        }
    }
    
    protected Rectangle getBounds() {
        return new Rectangle(getX() + 4, getY() + 4, 20, 20);
    }
    
    protected void onCollision(Object collision) {
        removed = true;
        for (int i = 0; i < 20; i++) {
            //LEVEL.add(new DefaultParticle(this, getX() + 15, getY() + 15, 10 + random.nextInt(25), speed / 7.));
        }
        if(collision instanceof Mob) {
            Mob m = (Mob) collision;
            if(m.getTeam().equals(source.getTeam())) return;
            m.doDamage(damage);
        }
    }

    public void update() {
        if (rangeSqr < getDistanceSqr(getX() - getSpawnX(), getY() - getSpawnY())) {
            removed = true;
        }
        if (!removed) {
            move(xStep, yStep, false);
            super.update();
        }
    }

    public int getFireRate() {
        return rateOfFire;
    }
}
