package graphics;


/**
 *
 * @author ford.terrell
 */
public class BGSprite extends ColorSprite {

    private boolean renderedLastFrame;
    private final int updates;
    private int x, y, index;

    public BGSprite(Renderable r, int u, int xPos, int yPos) {
        super(r);
        updates = u - 1;
        index = 0;
        x = xPos;
        y = yPos;
        animated = true;
    }
    
    public void update() {
        super.update();
        index = ++index > updates ? updates : index;
    }
    
    public void updateRender() {
        renderedLastFrame = index == updates;
    }
    
    public boolean renderedLast() {
        return renderedLastFrame;
    }
    
    public final int getX() {
        return x;
    }
    
    public final int getY() {
        return y;
    }
    
    public BGSprite copyOf() {
    	return new BGSprite(this, updates, x, y);
    }
    
    public BGSprite setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public int getPixel(int x, int y) {
        return schemes.get(pixels[y * WIDTH + x], index);
    }
}