/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.team;

import game.ai.*;
import graphics.SpriteSet;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author ford.terrell
 */
public class BlueTeam extends Team {

    public BlueTeam(Dimension mapSize) {
        super(mapSize, true);
        toSpawn = 200;
        filter = new Color(0, 0, 50);
        slug = SpriteSet.getBluePlayer();
        super.c = CaptureAI.b;
        super.d = DefendAI.b;
        super.a = AttackAI.b;
    }
    
    public String toString() {
        return "Blue";
    }
}
