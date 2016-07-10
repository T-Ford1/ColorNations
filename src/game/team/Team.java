package game.team;

import components.gui.MessageBar;
import game.Game;
import game.ai.AIBase;
import game.ai.*;
import game.entity.mob.AIMob;
import game.entity.mob.Mob;
import game.entity.mob.Player;
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

    private int controlled, captures;
    private int attack, defend, capture, players;
    private byte objective;
    private final boolean[][] territories;
    private final ArrayList<AIMob> mobs;
    //private final ArrayList<Player> players;
    private Point spawn;

    public CaptureAI c;
    public DefendAI d;
    public AttackAI a;

    protected Color filter;
    protected SpriteSet slug;

    public Team(Dimension mapSize) {
        territories = new boolean[mapSize.height][mapSize.width];
        objective = 0;
        mobs = new ArrayList<>();
        attack = 0;
        defend = 0;
        capture = 0;
        players = 0;
        //0 = capture
        //1 = defend
        //2 = attack
        //3 = balanced
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
        //System.out.println(temp);
        spawn = temp;
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
            Point p = t.spawn;
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
        //updates 60 times per sec
        if (captures > 200) {//spawns a new minion every 50 tile captures
            captures -= 200;
            addMob();
        }
        MessageBar.addMessage(0, 200 - captures + "");
        MessageBar.addMessage(2, controlled + "");
    }

    public void addMob() {
        if (spawn == null) {
            return;
        }
        if(size() != 0 && !controlsTile(spawn.x, spawn.y)) {
            return;//if your team doesn't control your spawn, you can't respawn.
        }
        int x = spawn.x * Game.SIZE;
        int y = spawn.y * Game.SIZE;
        
        AIMob m = new AIMob(this, x, y);
        addAIMob(m);
    }

    private void addAIMob(AIMob m) {
        Game.LEVEL.add(m);
        mobs.add(m);
        if (isCapture()) {
            setCapture(m);
        } else if (isDefend()) {
            setDefend(m);
        } else if (isAttack()) {
            setAttack(m);
        } else {
            setBalanced(m);
        }
    }

    public void addPlayer() {
        players++;
    }

    public void removePlayer() {
        players--;
    }

    public void removeTeam(Mob m) {
        if (m instanceof AIMob) {
            removeAIMob((AIMob) m);
        } else if (m instanceof Player) {
            removePlayer();
        }
    }

    private void removeAIMob(AIMob m) {
        AIBase b = m.getAIBase();
        if (b instanceof CaptureAI) {
            capture--;
        } else if (b instanceof DefendAI) {
            defend--;
        } else {
            attack--;
        }
    }
    
    public void setAIBase(AIBase b, AIMob m) {
        m.setAIBase(b);
    }

    private void setCapture(AIMob m) {
        m.setAIBase(c);
        capture++;
    }

    private void setDefend(AIMob m) {
        m.setAIBase(d);
        defend++;
    }

    private void setAttack(AIMob m) {
        m.setAIBase(a);
        attack++;
    }

    private void setBalanced(AIMob m) {
        if (attack < defend) {
            setAttack(m);
        } else if (defend < capture) {
            setDefend(m);
        } else {
            setCapture(m);
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
        System.out.println(this + ", captured " + x + ", " + y);
        captures++;
        controlled++;
        territories[y][x] = true;
    }

    public void lose(int x, int y) {
        if (!territories[y][x]) {
            return;
        }
        controlled--;
        captures--;
        territories[y][x] = false;
    }

    public boolean controlsTile(int x, int y) {
        return territories[y][x];
    }

    public void setCapture() {
        defend = 0;
        attack = 0;
        capture = 0;
        for (AIMob m : mobs) {
            setCapture(m);
        }
        objective = 0;
    }

    public void setDefend() {
        defend = 0;
        attack = 0;
        capture = 0;
        for (AIMob m : mobs) {
            setDefend(m);
        }
        objective = 1;
    }

    public void setAttack() {
        defend = 0;
        attack = 0;
        capture = 0;
        for (AIMob m : mobs) {
            setAttack(m);
        }
        objective = 2;
    }

    public void setBalanced() {
        defend = 0;
        attack = 0;
        capture = 0;
        for (AIMob m : mobs) {
            setBalanced(m);
        }
        objective = 3;
    }

    public int size() {
        return defend + attack + capture + players;
    }

    public Point getSpawnPoint() {
        return spawn;
    }

    public boolean isCapture() {
        return objective == 0;
    }

    public boolean isDefend() {
        return objective == 1;
    }

    public boolean isAttack() {
        return objective == 2;
    }

    public boolean isBalanced() {
        return objective == 3;
    }
}
