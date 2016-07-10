package game;

import components.gui.MessageBar;
import static game.Game.LEVEL;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.team.BlueTeam;
import game.team.GreenTeam;
import game.team.RedTeam;
import game.team.Team;
import game.tile.Tile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Terrell
 */
public abstract class Level {

    private final ArrayList<Mob> mobs;
    private Player player;
    protected ArrayList<Team> teams;
    protected Dimension size/*screensize*/, mapSize/*tiles*/, mapPixels;/*pixels*/

    protected BufferedImage map;
    protected int[] tiles;

    public Level(int width, int height) {
        mobs = new ArrayList<>();
        teams = new ArrayList<>();
        size = new Dimension(width, height);
    }

    public void resetClientPlayer() {
        int rX = size.width / 2 - 16;
        int rY = size.height / 2 - 16;
        //int x = 20 * SIZE;
        //int y = 40 * SIZE;
        player = new Player(teams.get(2), rX, rY);
        teams.get(0).addMob();
        teams.get(1).addMob();
    }

    public void resetAll() {
        resetEntities();
        resetTeams();
        resetClientPlayer();
    }

    public void resetEntities() {
        mobs.clear();
    }

    public void resetTeams() {
        teams.clear();
        teams.add(new RedTeam(mapSize));
        teams.add(new GreenTeam(mapSize));
        teams.add(new BlueTeam(mapSize));
    }

    protected abstract void init();

    public final void add(Mob e) {
        mobs.add(e);
    }

    public void update() {
        for (int i = 0; i < mobs.size(); i++) {
            if (mobs.get(i).isRemoved()) {
                mobs.remove(i);
                i--;
            } else {
                mobs.get(i).update();
            }
        }
        player.update();
        if (player.isRemoved()) {
            Point spawn = player.getTeam().getSpawnPoint();
            player.setPosition(spawn.x * Game.SIZE, spawn.y * Game.SIZE);
            player.setHealth(player.getMaxHealth());
            player.setRemoved(false);
            MessageBar.addMessage(1, "You Lost");
            Game.reset();
        }
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).update();
            System.out.println(teams.get(i) + ", " + teams.get(i).size()); 
        }
        if(teams.get(0).size() == 0 && teams.get(1).size() == 0) {
            MessageBar.addMessage(1, "You Win");
            Game.reset();
        }
    }

    private void renderBackground(Point p, GameCanvas frame) {
        int step = Game.SIZE * 32;
        int xLeft = p.x / step;
        if (p.x < 0) {
            xLeft--;
        }
        int xRight = (p.x + frame.getWidth()) / step;
        if (p.x + frame.getWidth() >= mapPixels.width) {
            xRight++;
        }
        int yTop = p.y / step;
        if (p.y < 0) {
            yTop--;
        }
        int yBottom = (p.y + frame.getHeight()) / step;
        if (p.y + frame.getHeight() >= mapPixels.height) {
            yBottom++;
        }
        //choosing the first and last tiles to render
        for (int y = yTop; y <= yBottom; y++) {
            for (int x = xLeft; x <= xRight; x++) {
                Tile t = Game.CHUNKS.getChunk(x, y);
                t.render(x * step, y * step, frame);
            }
        }
        step = Game.SIZE;
        xLeft = p.x / step;
        xRight = (p.x + frame.getWidth()) / step;
        yTop = p.y / step;
        yBottom = (p.y + frame.getHeight()) / step;
        if (xLeft < 0) {
            xLeft = 0;
        }
        if (yTop < 0) {
            yTop = 0;
        }
        if (xRight >= mapSize.width) {
            xRight = mapSize.width - 1;
        }
        if (yBottom >= mapSize.height) {
            yBottom = mapSize.height - 1;
        }
        //choosing the first and last tiles to render
        for (int y = yTop; y <= yBottom; y++) {
            for (int x = xLeft; x <= xRight; x++) {
                Team n = getNation(x, y);
                if (n == null) {
                    continue;
                }
                Color filter = n.getFilter();
                frame.layerPixels(x * step, y * step, Game.SIZE, Game.SIZE, filter);
            }
        }
    }

    public void render(GameCanvas frame) {
        Point p = Game.getRenderPoint(LEVEL.getPlayer());
        renderBackground(p, frame);
        mobs.stream().forEach((e) -> {
            e.render(frame);
        });
        player.render(frame);
    }

    public void capture(Team cap, int x, int y) {
        teams.stream().forEach((n) -> {
            if (n == cap) {
                n.capture(x, y);
            } else {
                n.lose(x, y);
            }
        });
    }

    public Team getNation(int x, int y) {
        for (Team n : teams) {
            if (n.controlsTile(x, y)) {
                return n;
            }
        }
        return null;
    }

    private Team getRandomNation() {
        if (teams.isEmpty()) {
            return null;
        }
        return teams.get((int) (Math.random() * teams.size()));
    }

    public abstract Tile getTile(int x, int y);

    public Tile getTilePixels(int x, int y) {
        if (x >= 0 && y >= 0) {
            return getTile(x / Game.SIZE, y / Game.SIZE);
        }
        return Tile.VOID;
    }

    public Dimension getMapSize() {
        return mapSize;
    }

    public Dimension getMapPixels() {
        return mapPixels;
    }

    public Player getPlayer() {
        return player;
    }

    public Object collision(Entity e) {
        if (e instanceof Player) {
            return null;
        }
        for (Entity entity : mobs) {
            if (entity == e) {
                continue;
            }
            if (e.isCollided(entity)) {
                return entity;
            }
        }
        if (e.isCollided(player)) {
            return player;
        }
        return null;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Mob> getEntities() {
        return mobs;
    }
}
