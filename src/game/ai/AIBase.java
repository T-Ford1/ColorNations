/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.entity.mob.AIMob;
import game.entity.mob.Mob;
import java.awt.Point;

/**
 *
 * @author Terrell
 */
public abstract class AIBase {
    
    public abstract Mob nextTarget(AIMob m);
    
    public abstract Point nextPoint(AIMob m);
}
