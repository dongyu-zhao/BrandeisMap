import dataStructure.Edge;
import dataStructure.Time;
import dataStructure.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Map {
    public static final double FACTOR = 2.185;
    public static final int X_CROPPED = 150;
    public static final int Y_CROPPED = 125;

    public static void main(String[] args) {
        Graph g = new Graph("data//MapDataVertices.txt", "data//MapDataEdges.txt");
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("************* WELCOME TO THE BRANDEIS MAP *************");
        System.out.print("Enter start (return to quit): ");
        String startName = sc.nextLine();
        System.out.print("Enter finish (or return to do a tour): ");
        String endName = sc.nextLine();
        System.out.print("Have a skateboard (y/n - default=n)? ");
        String skateboardFlag = sc.nextLine();
        boolean hasSkateboard = !skateboardFlag.isEmpty() && skateboardFlag.equals("y");
        System.out.print("Minimize time (y/n - default=n)? ");
        String timeFlag = sc.nextLine();
        boolean minTime = !timeFlag.isEmpty() && timeFlag.equals("y");

        Vertex start = g.findVertex(startName);
        Vertex end = g.findVertex(endName);
        List<Edge> path = g.shortestPath(start, end, hasSkateboard, minTime);

        try {
            FileWriter fileWriter1 = new FileWriter(new File("out//Route.txt"));
            FileWriter fileWriter2 = new FileWriter(new File("out//RouteCropped.txt"));
            int distance = 0;
            Time time = new Time(0);
            for (Edge e : path) {
                Time edgeTime = e.getTime(hasSkateboard);
                System.out.println();
                System.out.printf("FROM: (%s) %s\n", e.getV1().getLabel(), e.getV1().getName());
                System.out.printf("ON: %s\n", e.getName());
                System.out.printf("Walk %d feet in direction %d degrees %s.\n", e.getLength(), e.getAngle(), e.getDirection());
                System.out.printf("TO: (%s) %s\n", e.getV2().getLabel(), e.getV2().getName());
                System.out.printf("(%s%s)\n", !hasSkateboard || e.allowSkateboard() ? "" : "no skateboards allowed, ", edgeTime.toString());
                distance += e.getLength();
                time.addTime(edgeTime);
                fileWriter1.write(String.format("%d %d %d %d\n",
                        (int)(e.getV1().getX()/FACTOR), (int)(e.getV1().getY()/FACTOR),
                        (int)(e.getV2().getX()/FACTOR), (int)(e.getV2().getY()/FACTOR)));
                fileWriter2.write(String.format("%d %d %d %d\n",
                        (int)(e.getV1().getX()/FACTOR) - X_CROPPED, (int)(e.getV1().getY()/FACTOR) - Y_CROPPED,
                        (int)(e.getV2().getX()/FACTOR) - X_CROPPED, (int)(e.getV2().getY()/FACTOR) - Y_CROPPED));
            }
            fileWriter1.close();
            fileWriter2.close();
            System.out.println();
            System.out.printf("legs = %d, distance = %d feet, time = %s\n", path.size(), distance, time.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        sc.close();
    }
}
