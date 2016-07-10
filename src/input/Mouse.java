package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author ford.terrell
 */
public class Mouse implements MouseMotionListener, MouseListener {

    private int x, y, b;
    private boolean pressed, moved, clicked;

    public Mouse() {
        x = -1;
        y = -1;
        b = -1;
    }

    public void mouseDragged(MouseEvent me) {
        moved = true;
        x = me.getX();
        y = me.getY();
    }

    public void mouseMoved(MouseEvent me) {
        moved = true;
        x = me.getX();
        y = me.getY();
    }

    public void mouseClicked(MouseEvent me) {
        b = me.getButton();
        clicked = true;
    }

    public void mousePressed(MouseEvent me) {
        b = me.getButton();
        pressed = true;
    }

    public void mouseReleased(MouseEvent me) {
        pressed = false;
        b = -1;
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {

    }

    public void update() {
        moved = false;
        clicked = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public int getButton() {
        return b;
    }

    public boolean isPressed() {
        return pressed;
    }
    
    public boolean isClicked() {
        return clicked;
    }

    public boolean isMoved() {
        return moved;
    }
}
