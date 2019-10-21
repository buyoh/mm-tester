public class Edge
{
    public final int x1,y1;
    public final int x2,y2;
    
    public Edge (int _x1, int _y1, int _x2, int _y2)
    {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
    }

    public double calcDist ()
    {
        double dx = (double)(x1 - x2);
        double dy = (double)(y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
