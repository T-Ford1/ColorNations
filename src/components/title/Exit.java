/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.title;

import frame.Frame;

/**
 *
 * @author ford.terrell
 */
public class Exit extends Button {

    public Exit(String name, int x, int y) {
        super(name, x, y);
    }
    
    protected void onClick() {
        Frame.close();
    }
}
