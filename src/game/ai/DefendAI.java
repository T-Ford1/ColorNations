package game.ai;

import game.Game;
import game.entity.mob.AIMob;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.team.BlueTeam;
import game.team.Team;
import game.tile.Tile;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Terrell
 */
public class DefendAI extends AIBase {

    public static final DefendAI r = new DefendAI();
    public static final DefendAI g = new DefendAI();
    public static final DefendAI b = new DefendAI();

    public Point nextPoint(AIMob m) {
        Point p = m.getTeam().getSpawnPoint();
        Point next = null;
        while (next == null) {
            int x = p.x - 5 + (int) (Math.random() * 10);
            int y = p.y - 5 + (int) (Math.random() * 10);
            Tile t = Game.LEVEL.getTile(x, y);
            if (!t.isSolid()) {
                next = new Point(x, y);
            }
        }
        return next;
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
            double d = me.getTeam().getSpawnPoint().distance(m.getTilePos());//finding the closest mob to the spawn point
            if (d < dist) {
                dist = d;
                target = m;
            }
        }
        for (AIMob m : a2) {
            double d = me.getTeam().getSpawnPoint().distance(m.getTilePos());
            if (d < dist) {
                dist = d;
                target = m;
            }
        }
        if (target == null) {
            return null;
        }
        Player p = Game.LEVEL.getPlayer();
        double distToPlayer = p.getTilePos().distance(me.getTilePos());
        if (p.getTeam() != me.getTeam() && distToPlayer <= dist) {
            return Game.LEVEL.getPlayer();
        }
        return target;
    }
}
