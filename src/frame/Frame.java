package frame;

import components.GraphicsComponent;
import game.Game;
import components.gui.MenuBar;
import components.title.Title;
import components.gui.ToolBar;
import input.Keyboard;
import input.Mouse;
import static java.awt.Toolkit.getDefaultToolkit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import net.Network;

/**
 *
 * @author ford.terrell
 */
public class Frame extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;

    private static BufferStrategy bs;
    private static Render render;
    private static boolean running;
    private static Network io;
    public static Keyboard keys;
    public static Mouse mouse;
    
    private static ArrayList<GraphicsComponent> components;
    private static final double ns = 1_000_000_000.0 / 60.0;
    private static long ticks, renders;

    public Frame() {
        Dimension screen = getDefaultToolkit().getScreenSize();
        setAutoRequestFocus(true);
        setPreferredSize(screen);
        setIgnoreRepaint(true);
        setResizable(false);
        setUndecorated(true);
        add(render = new Render());
        render.addKeyListener(keys = new Keyboard());
        render.addMouseListener(mouse = new Mouse());
        render.addMouseMotionListener(mouse);
        io = new Network();
        pack();
        render.init();
        render.createBufferStrategy(3);
        bs = render.getBufferStrategy();
        components = new ArrayList<>();
        runTitle();
    }

    public void start() {
        io.start();
        running = true;
        setVisible(true);
        run();
    }

    public void run() {
        long startTime = System.nanoTime();
        //conversion from nanoseconds to 1/60 of a second
        while (running) {
            //do game updates (60 per second)
            //if scheduled for one or multiple
            //updates, do game tick
            while ((System.nanoTime() - startTime) / ns > ticks) {
                update();
                //update the game
                ticks++;
                //one more tick recorded
            }
            //if has updated game, render image
            renders++;
            render();
        }
    }

    public void render() {
        Graphics g = bs.getDrawGraphics();
        components.stream().forEach((gc) -> {
            gc.render();
        });
        g.drawImage(Render.getImage(), 0, 0, null);
        bs.show();
        g.dispose();
    }
    
    public static ArrayList<GraphicsComponent> getFrameComponents() {
        return components;
    }
    
    public static void addComponent(GraphicsComponent g) {
        components.add(g);
    }

    public static void removeComponent(GraphicsComponent g) {
        components.remove(g);
    }
    
    public static void removeAllComponents() {
        for (int i = 0; i < components.size(); i++) {
            components.remove(i--);
        }
    }
    
    public void update() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update();
        }
        keys.update();
        mouse.update();
    }
    
    public static void runTitle() {
        removeAllComponents();
        new Title(render.getSize());
    }
    
    public static void runGame() {
        removeAllComponents();
        //new Background(panel.getSize(), components.gui.Type.SHIFTING);
        new Game(render.getSize());
        new MenuBar(render.getSize());
        //new ToolBar(render.getSize());
    }
    
    public static void close() {
        double ratio = (double) renders / (double) ticks;
        //System.out.println("Average FPS: " + (int) (60 * ratio));
        Network.close();
        running = false;
        System.exit(0);
    }
}
