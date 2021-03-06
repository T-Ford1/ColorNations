package game.entity.mob;

import static frame.Frame.*;
import game.Game;
import game.GameCanvas;
import game.entity.projectile.Projectile;
import game.team.Team;
import java.awt.Point;

/**
 *
 * @author Terrell
 */
public class Player extends Mob {

    private int renderX, renderY;

    public Player(Team n, int xR, int yR) {
        super(n, n.getSpawnPoint().x * Game.SIZE, n.getSpawnPoint().y * Game.SIZE);
        renderX = xR;
        renderY = yR;
    }

    protected void move() {
        int xMove = keys.left ? (keys.right ? 0 : -move) : (keys.right ? move : 0);
        int yMove = keys.up ? (keys.down ? 0 : -move) : (keys.down ? move : 0);
        super.move(xMove, yMove, false);
    }

    public void render(GameCanvas screen) {
        super.render(screen);
    }

    protected void shoot(Point target) {
        if (fire > 0) {
            return;
        }
        double theta = Math.atan2(target.y, target.x);
        Projectile proj = new Projectile(this, getX() - 8, getY(), theta);
        projectiles.add(proj);
        fire = proj.getFireRate();
    }

    public void remove() {
        super.remove();
    }

    public void update() {
        move();
        if (mouse.isPressed()) {
            shoot(new Point(mouse.getX() - getRenderX() - 8, mouse.getY() - getRenderY() - 90));
        }
        super.update();
    }

    public int getMouseX() {
        return mouse.getX();
    }

    public int getMouseY() {
        return mouse.getY();
    }

    public int getMouseButton() {
        return mouse.getButton();
    }

    protected void onCollision(Object collided) {
    }

    public void setRenderPosition(int xS, int yS) {
        renderX = xS;
        renderY = yS;
    }

    public int getRenderX() {
        return (int) renderX;
    }

    public int getRenderY() {
        return (int) renderY;
    }

    public String toString() {
        return "player";
    }
}
