/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.title;

import components.GraphicsComponent;
import frame.Frame;
import graphics.Renderable;
import graphics.Sprite;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author ford.terrell
 */
public class TNumber extends GraphicsComponent {
    
    private static Rectangle screen;
    private double xPos, yPos;
    private double xV, yV, xC, yC;
    
    public TNumber(Rectangle size, Renderable r, double scale) {
        super(0, 0, Sprite.scaleSprite(r, scale));
        Random ran = new Random();
        Color c = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        r.setColor(new Rectangle(0,0, r.getWidth(), r.getHeight()), c.getRGB());
        screen = size;
        int height = renderable[0].getHeight(), width = renderable[0].getWidth();
        int type = random.nextInt(4);
        if(type == 0) {
            //top
            yPos = -height;
            xPos = random.nextInt(size.width - width);
            xV = 0;
            xC = random.nextGaussian() * .7;
            yV = 0;
            yC = random.nextDouble() * .125;
        } else if(type == 1) {
            //left
            xPos = -width;
            yPos = random.nextInt(size.height - height);
            xV = 0;
            xC = random.nextDouble() * .125;
            yV = 0;
            yC = random.nextGaussian() * .7;
        } else if(type == 2) {
            //bottom
            yPos = size.height;
            xPos = random.nextInt(size.width - width);
            xV = 0;
            xC = random.nextGaussian() * .7;
            yV = -0;
            yC = -random.nextDouble() * .125;
        } else {
            //right
            xPos = size.width;
            yPos = random.nextInt(size.height - height);
            xV = -0;
            xC = -random.nextDouble() * .125;
            yV = 0;
            yC = random.nextGaussian() * .7;
        }
    }
    
    public void update() {
        xPos += xV += xC;
        yPos += yV += yC;
        if((xPos + bounds.width < 0 | yPos + bounds.height < 0) & !screen.contains(xPos, yPos)) {
            Frame.removeComponent(this);
        }
    }

    public void render() {
        renderSprite((int) xPos, (int) yPos, renderable[0]);
    }
}
