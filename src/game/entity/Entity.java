/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity;

import game.Game;
import static game.Game.LEVEL;
import game.GameCanvas;
import game.tile.Tile;
import graphics.SpriteSet;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Terrell
 */
public abstract class Entity {

    protected Random random;
    protected SpriteSet animation;
    protected boolean moving, removed, entityCollide;
    protected int update, move, dir;
    protected final int spawnX, spawnY;
    protected Rectangle bounds;
    private double x, y, xRemain, yRemain;

    public Entity(int xSpwn, int ySpwn) {
        entityCollide = false;
        x = spawnX = xSpwn;
        y = spawnY = ySpwn;
        random = new Random();
    }

    public void update() {
        moving = false;
        update++;
    }

    public abstract void render(GameCanvas frame);

    /**
     * returns an int[] of length 4 where array at index 0 and 1 are x bounds
     * and index 2 and 3 are y bounds index 0 is less than index 1 and index 2
     * is less than index 3
     *
     * @return
     */
    protected abstract Rectangle getBounds();

    protected Rectangle getTileBounds() {
        Rectangle bds = getBounds();
        int x = bds.x / Game.SIZE;
        int y = bds.y / Game.SIZE;
        int xMax = (bds.x + bds.width) / Game.SIZE;
        int yMax = (bds.y + bds.height) / Game.SIZE;
        return new Rectangle(x, y, xMax - x + 1, yMax - y + 1);
    }

    protected abstract void onCollision(Object collide);

    protected void move(double x, double y, boolean slow) {
        moving = x != 0 | y != 0;
        setDir(x, y);
        if (slow) {
            x /= 4.0;
            y /= 4.0;
        }
        move(x, y);
        if (collision() != null) {
            move(-x, -y);
            if (x != 0) {
                setDir(x, 0);
                xStep(x);
            }
            if (y != 0) {
                setDir(0, y);
                yStep(y);
            }
        }
    }

    private void xStep(double x) {
        int step = x < 0 ? -1 : 1;
        int xDist = (int) (xRemain + x);
        xRemain += x - xDist;
        for (int i = 0; i != xDist;) {
            move(step, 0);
            Object c = collision();
            if (c != null) {
                move(-step, 0);
                onCollision(c);
                break;
            }
            i += dir == 1 ? 1 : -1;
        }
    }

    private void yStep(double y) {
        int step = y < 0 ? -1 : 1;
        int yDist = (int) (yRemain + y);
        yRemain += y - yDist;
        for (int i = 0; i != yDist;) {
            move(0, step);
            Object c = collision();
            if (c != null) {
                move(0, -step);
                onCollision(c);
                break;
            }
            i += dir == 3 ? 1 : -1;
        }
    }

    private void move(double xChange, double yChange) {
        x += xChange;
        y += yChange;
    }

    private void setDir(double x, double y) {
        if (dir == 1 | dir == 0) {
            if (x != 0) {
                dir = x > 0 ? 1 : 0;
            } else {
                dir = y > 0 ? 3 : 2;
            }
        } else {
            if (y != 0) {
                dir = y > 0 ? 3 : 2;
            } else {
                dir = x > 0 ? 1 : 0;
            }
        }
    }

    protected Tile[] getOccupiedTiles() {
        Rectangle b = getTileBounds();
        Tile[] tiles = new Tile[b.width * b.height];
        int index = 0;
        for (int yPos = b.y; yPos < b.getMaxY(); yPos++) {
            for (int xPos = b.x; xPos < b.getMaxX(); xPos++) {
                tiles[index++] = Game.LEVEL.getTile(xPos, yPos);
            }
        }
        return tiles;
    }

    private Object collision() {
        Tile[] tiles = getOccupiedTiles();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].isSolid()) {
                return i;
            }
        }
        if (entityCollide) {
            return LEVEL.collision(this);
        }
        return null;
    }

    public boolean isCollided(Entity other) {
        return getBounds().intersects(other.getBounds());
    }

    protected final boolean isInside(Tile t) {
        Tile[] occupied = getOccupiedTiles();
        for (Tile o : occupied) {
            if (o.equals(t)) {
                return true;
            }
        }
        return false;
    }

    protected final boolean isSlowed() {
        Tile[] occupied = getOccupiedTiles();
        for (int i = 0; i < occupied.length; i++) {
            if (occupied[i].isSlowed()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMoving() {
        return moving;
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setPosition(double xPos, double yPos) {
        x = xPos;
        y = yPos;
    }
    
    public Point getPos() {
        return new Point(getX(), getY());
    }
    
    public Point getTilePos() {
        return new Point(getTileX(), getTileY());
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getTileX() {
        return getX() / Game.SIZE;
    }

    public int getTileY() {
        return getY() / Game.SIZE;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }
    
    public void setRemoved(boolean rm) {
        removed = rm;
    }
    
    protected double getDistance(Entity other) {
        return other.getPos().distance(getPos());
    }

    protected double getDistanceSqr(Entity other) {
        return other.getPos().distanceSq(getPos());
    }
    
    protected double getDistanceSqr(double x, double y) {
        return x * x + y * y;
    }
    
    protected double getDistance(double x, double y) {
        return Math.sqrt(getDistanceSqr(x, y));
    }

    public String toString() {
        return "abstract entity " + hashCode();
    }
}
