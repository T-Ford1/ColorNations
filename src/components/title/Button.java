
package components.title;

import components.DefaultComponent;
import graphics.*;
import graphics.Sprite;
import graphics.SpriteSheet;

/**
 *
 * @author ford.terrell
 */
public class Button extends DefaultComponent {
    
    public Button(String name, int x, int y) {
        super(x, y, SpriteSheet.TITLEBAR.getSprites());
        int width = (bounds.width - 50) / name.length();
        int size = Math.min(width, bounds.height - 20);
        int xOff = (bounds.width - size * name.length()) >> 1;
        int yOff = (bounds.height - size) >> 1;
        for (int i = 0; i < name.length(); i++) {
            int index = SpriteSheet.ALPHA.indexOf(name.charAt(i) + "");
            Sprite r = Sprite.scaleSprite(SpriteSheet.ALPHABET.getSprites()[index], size, size);
            int xPos = xOff + i * size;
            renderable[0] = combineSprites(renderable[0], r, xPos, yOff);
            renderable[1] = combineSprites(renderable[1], r, xPos, yOff);
            renderable[2] = combineSprites(renderable[2], r, xPos, yOff);
        }
    }
    
    public void render() {
        super.render();
    }
    
    private Renderable combineSprites(Renderable orig, Renderable overlay, int xOff, int yOff) {
        Renderable toRet = new Sprite(orig);
        for (int y = 0; y < overlay.getHeight(); y++) {
            int yPos = y + yOff;
            for (int x = 0; x < overlay.getWidth(); x++) {
                int xPos = x + xOff;
                toRet.setPixel(xPos, yPos, overlay.getPixel(x, y));
            }
        }
        return toRet;
    }
    
    protected void onClick() {
    }
}
