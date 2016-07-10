/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.Game;
import game.entity.mob.AIMob;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.team.Team;
import game.tile.Tile;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Terrell
 */
public class CaptureAI extends AIBase {

    public static final CaptureAI r = new CaptureAI();
    public static final CaptureAI g = new CaptureAI();
    public static final CaptureAI b = new CaptureAI();

    private final ArrayList<Point> targets;

    public CaptureAI() {
        targets = new ArrayList<>();
    }

    public Point nextPoint(AIMob m) {
        ArrayList<Point> points = new ArrayList<>();
        Team n = m.getTeam();
        Dimension d = game.Game.LEVEL.getMapSize();
        Point orig = new Point(m.getTileX(), m.getTileY());
        int dist = Integer.MAX_VALUE;
        int rad = 10;
        int y = orig.y - rad < 0 ? 0 : orig.y - rad;
        int x = orig.x - rad < 0 ? 0 : orig.x - rad;
        for (int yPos = y; yPos < d.height && yPos < orig.y + rad; yPos++) {
            for (int xPos = x; xPos < d.width && xPos < orig.x + rad; xPos++) {
                if (n.controlsTile(xPos, yPos)) {
                    continue;
                }
                if (Game.LEVEL.getTile(xPos, yPos) == Tile.VOID) {
                    continue;
                }
                if (containsTarget(xPos, yPos)) {
                    continue;
                }
                Point p = new Point(xPos, yPos);
                int dist2 = (int) p.distanceSq(orig);
                if (dist2 < dist) {
                    dist = dist2;
                    points.clear();
                    points.add(p);
                } else if (dist2 == dist) {
                    points.add(p);
                }
            }
        }
        Point target = null;
        if (points.isEmpty()) {
            m.getTeam().setAIBase(m.getTeam().a, m);
        } else {
            target = points.get((int) (Math.random() * points.size()));
        }
        if (target != null) {
            targets.add(target);
        }
        return target;
    }

    public void reachedTarget(Point target) {
        targets.remove(target);
    }

    private boolean containsTarget(int x, int y) {
        for (Point t : targets) {
            if (t.x == x && t.y == y) {
                return true;
            }
        }
        return false;
    }
    
    public Mob nextTarget(AIMob m) {
        Team t1 = null;
        Team t2 = null;
        ArrayList<Team> teams = Game.LEVEL.getTeams();
        for (Team t : teams) {
            if (t1 != null) {
                if (!t.equals(m.getTeam())) {
                    t2 = t;
                }
            } else {
                if (!t.equals(m.getTeam())) {
                    t1 = t;
                }
            }
        }
        if (t1 != null && t2 != null) {
            return selectEnemy(t1, t2, m);
        }
        return null;
    }

    private Mob selectEnemy(Team other1, Team other2, AIMob me) {
        double dist = Double.MAX_VALUE;
        Mob target = null;
        ArrayList<AIMob> a1 = other1.getMobs();
        ArrayList<AIMob> a2 = other2.getMobs();
        for (AIMob m : a1) {
            double d = me.getTilePos().distance(m.getTilePos());//finding the closest mob to the spawn point
            if (d < dist) {
                dist = d;
                target = m;
            }
        }
        for (AIMob m : a2) {
            double d = me.getTilePos().distance(m.getTilePos());
            if (d < dist) {
                dist = d;
                target = m;
            }
        }
        if (target == null) {
            return null;
        }
        Player p = Game.LEVEL.getClientPlayer();
        double distToPlayer = p.getTilePos().distance(me.getTilePos());
        if (p.getTeam() != me.getTeam() && distToPlayer <= dist) {
            return Game.LEVEL.getClientPlayer();
        }
        return target;
    }
}
