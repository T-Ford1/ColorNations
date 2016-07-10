package game;

import components.gui.MessageBar;
import static game.Game.LEVEL;
import game.entity.Entity;
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

    private final ArrayList<Entity> entities;
    private final ArrayList<Player> players;
    protected ArrayList<Team> teams;
    protected Dimension size/*screensize*/, mapSize/*tiles*/, mapPixels;/*pixels*/

    protected BufferedImage map;
    protected int[] tiles;

    public Level(int width, int height) {
        entities = new ArrayList<>();
        players = new ArrayList<>();
        teams = new ArrayList<>();
        size = new Dimension(width, height);
    }
    
    public void resetClientPlayer() {
        int rX = size.width / 2 - 16;
        int rY = size.height / 2 - 16;
        //int x = 20 * SIZE;
        //int y = 40 * SIZE;
        add(new Player(getRandomNation(), rX, rY));
        if(getClientPlayer().getTeam() instanceof RedTeam) {
            teams.get(1).addMob();
            teams.get(2).addMob();
        } else if(getClientPlayer().getTeam() instanceof GreenTeam) {
            teams.get(0).addMob();
            teams.get(2).addMob();
        } else if(getClientPlayer().getTeam() instanceof BlueTeam) {
            teams.get(0).addMob();
            teams.get(1).addMob();
        }
    }
    
    public void resetEntities() {
        entities.clear();
    }

    public void resetTeams() {
        teams.clear();
        teams.add(new RedTeam(mapSize));
        teams.add(new GreenTeam(mapSize));
        teams.add(new BlueTeam(mapSize));
    }

    protected abstract void init();

    public final void add(Entity e) {
        if (e instanceof Player) {
            players.add((Player) e);
        } else {
            entities.add(e);
        }
    }

    public void update() {
        //System.out.println("entities");
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isRemoved()) {
                entities.remove(i);
                i--;
            } else {
                entities.get(i).update();
            }
        }
        
        players.stream().forEach(player -> {
            player.update();
            if(player.isRemoved()) {
                Point spawn = player.getTeam().getSpawnPoint();
                player.setPosition(spawn.x * Game.SIZE, spawn.y * Game.SIZE);
                player.setHealth(player.getMaxHealth());
                player.setRemoved(false);
                MessageBar.addMessage(1, "You Died!");
            }
        });
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).size() == 0) {
                teams.remove(i);
                i--;
            } else {
                teams.get(i).update();
            }
        }
        if(teams.size() == 1) {
            if(getClientPlayer().getTeam().equals(teams.get(0))) {
                MessageBar.addMessage(1, "You Win. :D");
                resetTeams();
            } else {
                MessageBar.addMessage(1, "You Lost. :(");
                resetTeams();
            }
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
        Point p = Game.getRenderPoint(LEVEL.getClientPlayer());
        renderBackground(p, frame);
        entities.stream().forEach((e) -> {
            e.render(frame);
        });
        players.stream().forEach((player) -> {
            player.render(frame);
        });
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

    public Player getClientPlayer() {
        return players.get(0);
    }

    public Object collision(Entity e) {
        if (e instanceof Player) {
            return null;
        }
        for (Entity entity : entities) {
            if (entity == e) {
                continue;
            }
            if (e.isCollided(entity)) {
                return entity;
            }
        }
        for (Player player : players) {
            if (e.isCollided(player)) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
