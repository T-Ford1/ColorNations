/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Terrell
 */
public class Keyboard implements KeyListener {

    private final boolean[] keys;
    public boolean up, down, left, right;

    public Keyboard() {
        keys = new boolean[120];
    }

    public void update() {
        up = keys[KeyEvent.VK_UP] | keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] | keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] | keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] | keys[KeyEvent.VK_D];
    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() < 120) {
            keys[ke.getKeyCode()] = true;
        }
    }

    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() < 120) {
            keys[ke.getKeyCode()] = false;
        }
    }

    public void keyTyped(KeyEvent ke) {
    }

    public boolean isPressed(int keyCode) {
        return keyCode < keys.length && keyCode > -1 && keys[keyCode];
    }
}
