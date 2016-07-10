package game.entity.mob;

import game.Game;
import game.GameCanvas;
import graphics.Sprite;
import java.util.ArrayList;
import game.entity.Entity;
import game.tile.Tile;
import game.entity.projectile.Projectile;
import game.team.Team;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Terrell
 */
public abstract class Mob extends Entity {

    protected final ArrayList<Projectile> projectiles;
    protected final Team team;
    protected int anim, fire, healthMax, health;

    public Mob(Team n, int x, int y) {
        super(x, y);
        healthMax = 500;
        health = healthMax;
        animation = n.getPlayerAnimation();
        projectiles = new ArrayList<>();
        move = 8;
        team = n;
    }

    protected void shoot(Mob target) {
        shoot(target.getPos());
    }

    public int getMaxHealth() {
        return healthMax;
    }

    public void setHealth(int hp) {
        if (hp > healthMax) {
            health = healthMax;
        } else {
            health = hp;
        }
    }

    protected void shoot(Point target) {
        if (fire > 0) {
            return;
        }
        Point start = getPos();
        int xTravel = target.x - start.x;
        int yTravel = target.y - start.y;
        double theta = Math.atan2(yTravel, xTravel);
        Projectile proj = new Projectile(this, getX(), getY() + 8, theta);
        projectiles.add(proj);
        fire = proj.getFireRate();
    }

    public void render(GameCanvas screen) {
        Sprite s = animation.getSprite(dir, anim);
        if (dir == 3 || dir == 2) {
            screen.renderPixels(getX() - 14, getY() - 16, s.getWidth(), s.getHeight(), s.getPixels());
        } else {
            screen.renderPixels(getX() - 14, getY(), s.getWidth(), s.getHeight(), s.getPixels());
        }
        projectiles.stream().forEach((p) -> {
            p.render(screen);
        });
    }

    public void doDamage(int hp) {
        health -= hp;
    }

    public void update() {
        if (fire > 0) {
            fire--;
        }
        if (health <= 0) {
            removed = true;
        }
        if (health < healthMax) {
            health++;
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved()) {
                projectiles.remove(i);
            } else {
                projectiles.get(i).update();
            }
        }
        if (update % 10 == 0 && isMoving()) {
            anim = (anim + 1) % animation.getHeight();
        } else if (!isMoving()) {
            dir = 3;
            anim = 0;
        }
        Rectangle b = getTileBounds();
        for (int yPos = b.y; yPos < b.getMaxY(); yPos++) {
            for (int xPos = b.x; xPos < b.getMaxX(); xPos++) {
                Game.LEVEL.capture(team, xPos, yPos);
            }
        }
        super.update();
    }

    protected void onCollision(Object collide) {
    }

    public Team getTeam() {
        return team;
    }

    /**
     * returns an array with values: [0] = leftX [1] = rightX [2] = topY [3] =
     * bottomY bottomY
     *
     * @return
     */
    protected Rectangle getBounds() {
        return new Rectangle(getX(), getY(), 24, 24);
    }

    //protected abstract void onCollisionTile(Tile collide);
    //protected abstract void onCollisionEntity(Entity collide);
    public String toString() {
        return "mob " + hashCode();
    }
}
