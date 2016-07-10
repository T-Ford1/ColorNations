/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import components.GraphicsComponent;
import graphics.Sprite;
import java.awt.Dimension;
import game.entity.mob.Player;
import java.awt.Point;

/**
 *
 * @author ford.terrell
 */
public class Game extends GraphicsComponent {

    private static final long serialVersionUID = 1L;

    public static final int SIZE = 64;
    
    private final GameCanvas canvas;
    public static VisibleChunks CHUNKS;
    public static Level LEVEL;

    public Game(Dimension size) {
        super(0, 72, size.width, size.height - 72, Sprite.DEFAULT);
        renderable = null;
        canvas = new GameCanvas(getWidth(), getHeight());
        LEVEL = new RPG(getWidth(), getHeight());
        LEVEL.init();
        LEVEL.resetTeams();
        LEVEL.resetClientPlayer();
        CHUNKS = new VisibleChunks();
    }
    
    public static void reset() {
        LEVEL.init();
        LEVEL.resetAll();
    }

    public void update() {
        LEVEL.update();
    }

    public void render() {
        canvas.render();
        LEVEL.render(canvas);
        super.renderSprite(0, 0, canvas);
    }
    
    public static Point getRenderPoint(Player p) {
        int xR = p.getX() - p.getRenderX();
        int yR = p.getY() - p.getRenderY();
        return new Point(xR, yR);
    }
}