package dataStructure;

public class Edge {
    public static final int WALK_SPEED = 272;
    public static final double WALK_FACTOR_U = 0.9;
    public static final double WALK_FACTOR_D = 1.1;
    public static final double SKATE_FACTOR_U = 1.1;
    public static final double SKATE_FACTOR_F = 2.0;
    public static final double SKATE_FACTOR_D = 5.0;
    public static final double STEP_FACTOR_U = 0.5;
    public static final double STEP_FACTOR_D = 0.9;
    public static final double BRIDGE_FACTOR = 1.0;

    private int ix;
    private Vertex v1, v2;
    private int length;
    private int angle;
    private String direction;
    private char type;
    private String name;

    public Edge(int ix, Vertex v1, Vertex v2, int length, int angle, String direction, char type, String name) {
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
        this.angle = angle;
        this.direction = direction;
        this.type = type;
        this.name = name;
        v1.addEdge(this);
    }

    public int getCost(boolean hasSkateboard, boolean minTime) {
        return minTime ? getTime(hasSkateboard).getSeconds() : length;
    }

    public int getIx() {
        return ix;
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public int getLength() {
        return length;
    }

    public int getAngle() {
        return angle;
    }

    public String getDirection() {
        return direction;
    }

    public char getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Time getTime(boolean hasSkateboard) {
        int t = (int)( (60 * ( (double)length / (double)WALK_SPEED) ) + 0.5 );
        switch (type) {
            case 'f':                                                break;
            case 'x':
            case 'F': t /= hasSkateboard ? SKATE_FACTOR_F : 1;           break;
            case 'u': t /= WALK_FACTOR_U;                              break;
            case 'U': t /= hasSkateboard ? SKATE_FACTOR_U : WALK_FACTOR_U; break;
            case 'd': t /= WALK_FACTOR_D;                              break;
            case 'D': t /= hasSkateboard ? SKATE_FACTOR_D : WALK_FACTOR_D; break;
            case 's': t /= STEP_FACTOR_U;                              break;
            case 't': t /= STEP_FACTOR_D;                              break;
            case 'b': t /= BRIDGE_FACTOR;                             break;
        }
        return new Time(t);
    }

    public boolean allowSkateboard() {
        return type == 'x' || type == 'F' || type == 'U' || type == 'D';
    }
}
