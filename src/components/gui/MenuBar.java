package components.gui;

import components.DefaultComponent;
import java.awt.Dimension;
import graphics.Sprite;

public class MenuBar extends DefaultComponent {

    public MenuBar(Dimension screen) {
        super(0, 0, Sprite.MENUBAR);
        new Settings(5, 4, 0);
        new DefaultComponent(74, 4, 1);
        new DefaultComponent(143, 4, 2);
        new DefaultComponent(212, 4, 3);
        int height = 93;
        int cX = 281;
        int cL = Math.max(174, screen.width / 3 - 278);
        new Tab(cX, 0, cL, height);
        int mX = cX + cL;
        int mL = Math.max(174, (screen.width - 278) / 2);
        new Tab(mX, 0, mL, height);
        int tX = mX + mL;
        new Tab(tX, 0, Math.max(174, screen.width - tX), height);
    }

    public void update() {
    }
}
