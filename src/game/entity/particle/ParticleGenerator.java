/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entity.particle;

import game.Level;

/**
 *
 * @author ford.terrell
 */
public class ParticleGenerator {
    
    public ParticleGenerator(Level l, Particle p, int num) {
        for (int i = 0; i < num; i++) {
            l.add(p);
        }
    }
}
