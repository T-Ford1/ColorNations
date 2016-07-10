package components.gui;

import components.DefaultComponent;
import java.awt.Dimension;

import graphics.Sprite;

public class ToolBar extends DefaultComponent {

    public ToolBar(Dimension screen) {
        super(screen.width - Sprite.TOOLBAR.getWidth(), screen.height - Sprite.TOOLBAR.getHeight(), Sprite.TOOLBAR);
        new DefaultComponent(getX() + 5, getY() + 4, 4);
        new DefaultComponent(getX() + 73, getY() + 4, 5);
        new DefaultComponent(getX() + 142, getY() + 4, 6);
        new DefaultComponent(getX() + 211, getY() + 4, 7);
        new DefaultComponent(getX() + 280, getY() + 4, 8);
        render = false;
    }

    public void update() {
    }
}
