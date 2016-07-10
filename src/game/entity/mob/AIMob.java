/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.mob;

import game.Game;
import game.ai.*;
import game.entity.projectile.Projectile;
import game.team.Team;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Terrell
 */
public class AIMob extends Mob {

    private AIBase ai;
    private ArrayList<Point> target;
    public Mob enemy;
    private int lastScan;

    public AIMob(Team n, int x, int y) {
        super(n, x, y);
        lastScan = 600;
    }

    public void update() {
        if(++lastScan > 600) {
            lastScan = 600;
            enemy = ai.nextTarget(this);
            target = AStarPath.getPath(getTilePos(), ai.nextPoint(this));
        }
        if (enemy == null) {
            enemy = ai.nextTarget(this);
        }
        if (enemy != null && getDistanceSqr(enemy) < Projectile.rangeSqr) {
            shoot(enemy);
        }
        if (target == null || target.isEmpty()) {
            target = AStarPath.getPath(getTilePos(), ai.nextPoint(this));
        }
        if (target != null && !target.isEmpty()) {
            moveToTarget(target.get(target.size() - 1));
        }
        super.update();
    }

    private void moveToTarget(Point p) {
        Point tPix = new Point(p.x * Game.SIZE + Game.SIZE / 2, p.y * Game.SIZE + Game.SIZE / 2);
        int x = 0, y = 0;
        if (getX() < tPix.x) {
            x = move;
        } else if (getX() > tPix.x) {
            x = -move;
        }
        if (getY() < tPix.y) {
            y = move;
        } else if (getY() > tPix.y) {
            y = -move;
        }
        super.move(x, y, false);
        if (getTilePos().equals(p)) {
            target.remove(p);
        }
    }

    public void setAIBase(AIBase b) {
        ai = b;
    }

    public AIBase getAIBase() {
        return ai;
    }

    public Team getTeam() {
        return team;
    }

    public String toString() {
        return "aimob " + ai.getClass().getSimpleName() + ", team " + team.getClass().getSimpleName();
    }
}
