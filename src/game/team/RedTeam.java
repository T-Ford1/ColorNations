package game.team;

import game.ai.*;
import graphics.SpriteSet;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author RanceLori
 */
public class RedTeam extends Team{

    public RedTeam(Dimension mapSize) {
        super(mapSize, false);
        filter = new Color(50, 0, 0);
        slug = SpriteSet.getRedPlayer();
        super.c = CaptureAI.r;
        super.d = DefendAI.r;
        super.a = AttackAI.r;
    }
    
    public String toString() {
        return "Red";
    }
}
