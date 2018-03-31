import java.util.*;

public class ConvexHull {

    public class Point {
        int x,y;
        public Point (int _x, int _y) {
            x = _x;
            y = _y;
        }
    }

    public int dot(Point a, Point b) { return a.x * b.x + a.y * b.y; }
    public int cross(Point a, Point b) { return a.x * b.y - a.y * b.x; }
    public double norm(Point a) { return Math.sqrt((double) dot(a, a) * (double) dot(a, a)); }
    public Point sub(Point a, Point b) { return new Point(a.x - b.x, a.y - b.y); }

    public int counter_clockwise(Point p0, Point p1, Point p2) {
        Point a = sub(p1, p0);
        Point b = sub(p2, p0);
        if (cross(a,b) > 1.0e-8) return 1;
        if (cross(a,b) < -1.0e-8) return -1;
        if (dot(a,b) < -1.0e-8) return 2;
        if (norm(a) < norm(b)) return 2;
        return 0;
    }

    /******************************/

    List < Point > Pos = new ArrayList < Point > ();
    List < Point > CHPos = new ArrayList < Point > ();

    public int size () { return CHPos.size(); }
    public int getX (int i) { return CHPos.get(i).x; }
    public int getY (int i) { return CHPos.get(i).y; }

    public void add_point(int x, int y) {
        Pos.add(new Point(x, y));
    }

    public void build() {
        int n = (int) Pos.size(), k = 0;
        Collections.sort(Pos, new Comparator < Point > () {
            @Override
            public int compare(Point p1, Point p2) {
                int ret = p1.x - p2.x;
                if (ret == 0) ret = p1.y - p2.y;
                return ret;
            }       
        });
        Point [] ch = new Point[2 * n];
        for (int i = 0; i < n; ch[k++] = Pos.get(i++)) {
            while (k >= 2 && counter_clockwise(ch[k - 2], ch[k - 1], Pos.get(i)) <= 0) --k;
        }
        for (int i = n - 2, t = k + 1; i >= 0; ch[k++] = Pos.get(i--)) {
            while (k >= t && counter_clockwise(ch[k - 2], ch[k - 1], Pos.get(i)) <= 0) --k;
        }
        for (int i = 0; i < k - 1; i++) {
            CHPos.add(ch[i]);
        }
    }

    public double getArea () {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            int x1 = getX(i), x2 = getX((i + 1) % size());
            int y1 = getY(i), y2 = getY((i + 1) % size());
            sum += (double)(x1 * y2 - x2 * y1);
        }
        return Math.abs(sum) * 0.5;
    }

}

