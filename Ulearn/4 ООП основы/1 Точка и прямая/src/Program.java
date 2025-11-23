import java.util.ArrayList;

public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public class Line {
        //реализуйте получение стартовой точки, конечной, toString() в формате: [x, y], [x1, y1], GetLength() и hasPoint()
        private final Point start;
        private final Point end;

        public Line(Point s, Point e) {
            this.start = s;
            this.end = e;
        }

        public Point getStartPoint() {
            return start;
        }

        public Point getEndPoint() {
            return end;
        }

        public double getLength() {
            return start.getDistance(end);
        };

        public boolean hasPoint(Point p) {
            double dx = end.getX() - start.getX();
            double dy = end.getY() - start.getY();
            double dx1 = p.getX() - start.getX();
            double dy1 = p.getY() - start.getY();

            double cross = dx * dy1 - dy * dx1;
            if (Math.abs(cross) > 1e-6) 
                return false; 

            double dot = dx1 * dx + dy1 * dy;
            if (dot < 0) 
                return false; 

            double squaredLength = dx * dx + dy * dy;
            if (dot > squaredLength) 
                return false; 

            return true;
        }

        @Override
        public String toString() {
            return start + ", " + end;
        }
    }

    public class Point {
        //реализуйте геттеры, конструктор и метод toString() в формате:[x, y]
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getDistance(Point p2) {
            return Math.sqrt(Math.pow(p2.getX() - x, 2) + Math.pow(p2.getY() - y, 2));
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }
    }

    public class PolygonalLine {
        private ArrayList<Point> pointsList = new ArrayList<>();

        public double getLength()
        {
            double length = 0;

            if (pointsList.isEmpty()) {
                return 0;
            }
           
            for (int i = 0; i < pointsList.size() - 1; i++) {
                length += new Line(pointsList.get(i), pointsList.get(i+1)).getLength();
            }

            return length;
        }
 
        public void addPoint(Point p){
            pointsList.add(p);
        }

        public void setPoints(ArrayList<Line> lines){
            pointsList.clear();

            if (lines.isEmpty()) return;

            pointsList.add(lines.get(0).getStartPoint());
            
            for (Line line : lines) {
                pointsList.add(line.getEndPoint());
            }
        }
    }
}
