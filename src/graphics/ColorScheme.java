package graphics;

import java.util.HashMap;

public class ColorScheme {

    private final HashMap<Integer, ColorFader> fades;

    public ColorScheme() {
        fades = new HashMap<>();
    }

    public void put(int color, ColorFader fade) {
        fades.put(color, fade);
    }
    
    public int get(int color) {
        ColorFader c = fades.get(color);
        return c == null ? color : c.get();
    }
    
    public int get(int color, int index) {
        ColorFader c = fades.get(color);
        return c == null ? color : c.get(index);
    }
    
    public void update() {
        fades.values().stream().forEach((c) -> {
            c.update();
        });
    }

    protected HashMap<Integer, ColorFader> getSchemes() {
        return fades;
    }
}
