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
 * @author RanceLori
 */
public class GreenTeam extends Team{

    public GreenTeam(Dimension mapSize) {
        super(mapSize);
        filter = new Color(0, 50, 0);
        slug = SpriteSet.getGreenPlayer();
        super.c = CaptureAI.g;
        super.d = DefendAI.g;
        super.a = AttackAI.g;
    }
    
    public String toString() {
        return "Green";
    }
}
