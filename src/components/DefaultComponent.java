package components;

import static frame.Frame.*;
import graphics.Renderable;
import graphics.SpriteSheet;

public class DefaultComponent extends GraphicsComponent {

    public DefaultComponent(int x, int y, int w, int h, Renderable... s) {
        super(x, y, w, h, s);
    }

    public DefaultComponent(int x, int y, Renderable... s) {
        super(x, y, s);
    }

    public DefaultComponent(int x, int y, int index) {
        super(x, y, SpriteSheet.COMPONENTS.getSprite(index, 0), SpriteSheet.COMPONENTS.getSprite(index, 1), SpriteSheet.COMPONENTS.getSprite(index, 2));
    }

    public void update() {
        super.update();
        render = render | hovered | pressed;
        if (pressed) {
            onClick();
        }
        setHover(isInside(mouse.getPoint()));
        setPressed(hovered && mouse.isPressed());
    }

    public void render() {
        renderSprite();
        render = false;
    }

    protected void onClick() {
    }
}
