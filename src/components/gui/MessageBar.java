
package components.gui;

import components.GraphicsComponent;
import frame.Frame;
import java.util.ArrayList;

/**
 *
 * @author Terrell
 */
public class MessageBar {
    
    public static void addMessage(int index, String message) {
        ArrayList<GraphicsComponent> frames = Frame.getFrameComponents();
        int i = -1;
        for (GraphicsComponent c : frames) {
            if(c instanceof Tab) {
                i++;
            }
            if(i == index) {
                ((Tab) c).addMessage(message);
                break;
            }
        }
    }
}
