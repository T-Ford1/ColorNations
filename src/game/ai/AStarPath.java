package game.ai;

import game.tile.Tile;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import game.Game;

/**
 *
 * @author Terrell
 */
public class AStarPath {

    /**
     * returns an arraylist of points to the designated endpoint, assuming its
     * valid it returns the first discovered path
     *
     * the arraylist has the last point at index 0
     *
     * @param start
     * @param end
     * @return
     */
    public static ArrayList<Point> getPath(Point start, Point end) {
        if (end == null || start == null || start.equals(end)) {
            return null;
        }
        ArrayList<Point> path = new ArrayList<>();
        if(start.distance(end) < 2) {
            path.add(end);
            return path;
        }
        ArrayList<Node> open = new ArrayList<>();
        ArrayList<Node> closed = new ArrayList<>();
        open.add(new Node(null, start, end, 0, 1));
        while (!open.isEmpty()) {
            Collections.sort(open);
            Node nearest = open.get(open.size() - 1);
            open.remove(open.size() - 1);
            if (nearest.getHCost() == 0) {
                while (nearest.getParent() != null) {
                    path.add(nearest.getPos());//the end point is at the index 0
                    nearest = nearest.getParent();
                }
                break;
            } else {
                addNodes(open, closed, nearest, end);
            }
        }
        open.clear();
        closed.clear();
        return path;
    }

    private static void addNodes(ArrayList<Node> open, ArrayList<Node> closed, Node pos, Point end) {
        closed.add(pos);
        Point p = pos.getPos();
        for (int y = p.y - 1; y <= p.y + 1; y++) {
            for (int x = p.x - 1; x <= p.x + 1; x++) {
                if (x == p.x && y == p.y) {
                    continue;
                }
                Tile t = Game.LEVEL.getTile(x, y);
                if (t.isSolid()) {
                    continue;
                }
                Point nPos = new Point(x, y);
                Node n = new Node(pos, nPos, end, pos.pathCost(), 1);
                if (!isInList(closed, nPos) && !isInList(open, nPos)) {
                    open.add(n);
                }
            }
        }
    }

    private static boolean isInList(ArrayList<Node> closed, Point pos) {
        for (Node c : closed) {
            if (c.getPos().equals(pos)) {
                return true;
            }
        }
        return false;
    }
}
