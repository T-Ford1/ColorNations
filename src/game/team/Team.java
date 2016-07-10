package game.team;

import components.gui.MessageBar;
import game.Game;
import game.ai.*;
import game.entity.mob.AIMob;
import game.tile.Tile;
import graphics.SpriteSet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author ford.terrell
 */
public class Team {

    private int controlled, spawn;
    protected final int toSpawn;
    private final boolean[][] territories;
    private final boolean player;
    private final ArrayList<AIMob> mobs;
    //private final ArrayList<Player> players;
    private Point spawnPoint;

    public CaptureAI c;
    public DefendAI d;
    public AttackAI a;

    protected Color filter;
    protected SpriteSet slug;

    public Team(Dimension mapSize, boolean p) {
        territories = new boolean[mapSize.height][mapSize.width];
        mobs = new ArrayList<>();
        //0 = capture
        //1 = defend
        //2 = attack
        //3 = balanced
        player = p;
        controlled = 0;
        spawn = 0;
        toSpawn = 200;
        setSpawn();
    }

    private void setSpawn() {
        Dimension mapSize = new Dimension(territories[0].length, territories.length);
        Point temp = null;
        ArrayList<Team> teams = game.Game.LEVEL.getTeams();
        for (int y = 1; y < mapSize.height - 1; y++) {
            if (temp != null) {
                break;
            }
            for (int x = 1; x < mapSize.width - 1; x++) {
                if (isGoodSpawn(x, y) && !isTooClose(teams, mapSize, x, y)) {
                    temp = new Point(x, y);
                    break;
                }
            }
        }
        spawnPoint = temp;
    }

    private boolean isGoodSpawn(int x, int y) {
        for (int yPos = y - 2; yPos <= y + 2; yPos++) {
            for (int xPos = x - 2; xPos <= x + 2; xPos++) {
                Tile pos = Game.LEVEL.getTile(xPos, yPos);
                if (pos == Tile.VOID) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTooClose(ArrayList<Team> team, Dimension size, int x, int y) {
        double length = (size.width + size.height) / (team.size() + 1.);
        for (Team t : team) {
            Point p = t.spawnPoint;
            int len = Math.abs(p.x - x) + Math.abs(p.y - y);
            if (len <= length) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<AIMob> getMobs() {
        return mobs;
    }

    public void update() {
        if (spawn > toSpawn) {//spawns a new minion every 200 tile captures
            spawn -= toSpawn;
            addMob();
        }
        for (int i = 0; i < mobs.size(); i++) {
            if (mobs.get(i).isRemoved()) {
                removeTeam(mobs.get(i));
            }
        }
        if (player) {
            MessageBar.addMessage(0, toSpawn - spawn + "");
            MessageBar.addMessage(2, controlled + "");
        }
    }

    public void addMob() {
        if (spawnPoint == null) {
            return;
        }
        int x = spawnPoint.x * Game.SIZE;
        int y = spawnPoint.y * Game.SIZE;
        AIMob m = new AIMob(this, x, y);
        addAIMob(m);
    }

    private void addAIMob(AIMob m) {
        Game.LEVEL.add(m);
        mobs.add(m);
        setAI(m);
    }

    public void removeTeam(AIMob m) {
        mobs.remove(m);
    }

    private void setCapture(AIMob m) {
        m.setAIBase(c);
    }

    private void setDefend(AIMob m) {
        m.setAIBase(d);
    }

    private void setAttack(AIMob m) {
        m.setAIBase(a);
    }

    private void setAI(AIMob m) {
        if (controlled < 500) {
            m.setAIBase(c);
        } else if (Math.random() < .5) {
            m.setAIBase(a);
        } else {
            m.setAIBase(d);
        }
    }

    public SpriteSet getPlayerAnimation() {
        return slug;
    }

    public Color getFilter() {
        return filter;
    }

    public void capture(int x, int y) {
        if (territories[y][x]) {
            return;
        }
        spawn++;
        controlled++;
        territories[y][x] = true;
    }

    public void lose(int x, int y) {
        if (!territories[y][x]) {
            return;
        }
        controlled--;
        spawn--;
        territories[y][x] = false;
    }

    public boolean controlsTile(int x, int y) {
        return territories[y][x];
    }

    public void setCapture() {
        for (AIMob m : mobs) {
            setCapture(m);
        }
    }

    public void setDefend() {
        for (AIMob m : mobs) {
            setDefend(m);
        }
    }

    public void setAttack() {
        for (AIMob m : mobs) {
            setAttack(m);
        }
    }

    public void setBalanced() {
        for (AIMob m : mobs) {
            setAI(m);
        }
    }

    public int size() {
        if(player) {
            return 1 + mobs.size();
        }
        return mobs.size();
    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }
}
