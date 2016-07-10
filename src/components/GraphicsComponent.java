package components;

import java.awt.Rectangle;
import static frame.Frame.*;
import frame.Render;
import graphics.Renderable;
import graphics.Sprite;
import java.awt.Point;
import java.util.Random;

public abstract class GraphicsComponent {

    protected final Rectangle bounds;
    protected Renderable[] renderable;
    protected boolean pressed, hovered, render, alwaysRender;
    protected static final Random random = new Random();

    public GraphicsComponent(int x, int y, int width, int height, Renderable... r) {
        addComponent(this);
        render = true;
        alwaysRender = false;
        bounds = new Rectangle(x, y, width, height);
        renderable = new Renderable[r.length];
        for (int i = 0; i < r.length; i++) {
            renderable[i] = new Sprite(r[i]);
        }
        pressed = false;
        hovered = false;
    }
    
    public GraphicsComponent(int x, int y, Renderable... r) {
        this(x, y, r[0].getWidth(), r[0].getHeight(), r);
    }

    public void update() {
        for (Renderable r : renderable) {
            r.update();
        }
    }

    public abstract void render();

    protected final void renderPixel(int x, int y, int rX, int rY) {
        renderPixel(x, y, getRenderable().getPixel(rX, rY));
    }
    
    protected void renderPixel(int x, int y, int rgb) {
        if (rgb == 0xFF_FF_00_FF) {
            return;
        }
        Render.renderPixel(bounds.x + x, bounds.y + y, rgb);
    }
    
    protected void renderSprite(int xOff, int yOff, Renderable r) {
       Render.renderArray(new Rectangle(xOff + bounds.x, yOff + bounds.y, r.getWidth(), r.getHeight()), r.getPixels());
    }

    protected void renderSprite() {
        renderSprite(0, 0, getRenderable());
    }
    
    public void setAlwaysRender() {
    	alwaysRender = true;
    }
    
    public boolean isAlwaysRender() {
    	return alwaysRender;
    }
    
    public boolean isRender() {
    	return render;
    }
    
    protected Renderable getRenderable() {
        if(renderable.length > 2) {
            return !hovered ? renderable[0] : !pressed ? renderable[1] : renderable[2];
        } else {
            return renderable[0];
        }
    }

    public int getX() {
        return bounds.x;
    }

    public int getY() {
        return bounds.y;
    }
    
    public void setX(int x) {
        bounds.x = x;
    }
    
    public void setY(int y) {
        bounds.y = y;
    }
    
    public void setPos(int x, int y) {
        setX(x);
        setY(y);
    }

    protected int getWidth() {
        return bounds.width;
    }

    protected int getHeight() {
        return bounds.height;
    }

    public boolean isInside(Point mouse) {
        return bounds.contains(mouse);
    }

    protected final void setHover(boolean h) {
        hovered = h;
    }

    protected final void setPressed(boolean p) {
        pressed = p;
    }
    
    public void setRender(boolean render) {
        render = true;
    }
    
    public int getPixel(int x,  int y) {
        return Render.getPixel(x + bounds.x, y + bounds.y);
    }
}
