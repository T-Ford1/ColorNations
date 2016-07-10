package components.gui;

import components.DefaultComponent;
import graphics.Renderable;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Tab extends DefaultComponent {

    private Renderable message;
    private int updates;

    public Tab(int xPos, int yPos, int w, int h) {
        super(xPos, yPos, w, h, Sprite.TAB, Sprite.TAB2, Sprite.TAB3);
        int width = getWidth() - renderable[0].getWidth() - renderable[2].getWidth();
        if (width != 0) {
            renderable[1] = Sprite.scaleSprite(renderable[1], width, renderable[1].getHeight());
        }
    }
    
    public final void addMessage(String m) {
        message = getMessage(m, 81);
        updates = 360;
    }
    
    

    public void update() {
        if (updates > 0) {
            updates--;
        }
        if (updates == 0 && message != null) {
            message = null;
        }
    }

    public void render() {
        renderSprite(0, 0, renderable[0]);
        renderSprite(renderable[0].getWidth(), 0, renderable[1]);
        renderSprite(getWidth() - renderable[2].getWidth(), 0, renderable[2]);
        if(message != null) {
            int x = (getWidth() - message.getWidth()) / 2;
            int y = (getHeight() - message.getHeight()) / 2;
            renderSprite(x, y, message);//or y = 6
        }
        render = false;
    }
    
    private static Renderable getMessage(String m, int size) {
        Sprite s = new Sprite(0xFF_FF_00_FF, size * m.length(), size);
        for (int i = 0, xOff = 0; i < m.length(); i++, xOff += size) {
            int index = SpriteSheet.ALPHA.indexOf(m.charAt(i) + "");
            if(index < 0) {
                //unparsable char
                continue;
            }
            Sprite let = Sprite.scaleSprite(SpriteSheet.ALPHABET.getSprites()[index], size, size);
            for (int y = 0; y < let.getHeight(); y++) {
                for (int x = 0; x < let.getWidth(); x++) {
                    s.setPixel(x + xOff, y, let.getPixel(x, y));
                }
            }
        }
        return s;
    }
}
