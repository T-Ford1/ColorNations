package components.gui;

import components.DefaultComponent;
import frame.Frame;
import java.awt.Color;

import graphics.ColorFader;
import graphics.ColorSprite;
import graphics.Sprite;

public class Settings extends DefaultComponent {

    public Settings(int x, int y, int index) {
        super(x, y, index);
        ColorSprite s = new ColorSprite(renderable[0]);
        s.addColorScheme(Color.black.getRGB(), new ColorFader(Sprite.RAINBOW));
        s.animated = true;
        renderable[0] = s;
    }
    
    protected void onClick() {
        Frame.runTitle();
    }
}
