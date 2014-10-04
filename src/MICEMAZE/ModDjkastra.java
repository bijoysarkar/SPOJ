import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Bijoy
 *
 */
public class ModDjkastra {

  public static void main(String[] args) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(System.in));

      int no_of_vertex = Integer.parseInt(br.readLine().trim());
      VertexList vertexList = new VertexList(no_of_vertex);

      int exit_cell = Integer.parseInt(br.readLine().trim());

      int time = Integer.parseInt(br.readLine().trim());

      int no_of_edges = Integer.parseInt(br.readLine().trim());
      while (no_of_edges-- > 0) {
        String parts[] = br.readLine().split(" ");
        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);
        int weight = Integer.parseInt(parts[2]);
        // Reverse adjacency list
        vertexList.get(end).list.add(new Edge(end, start, weight));
      }

      PriorityQueue<Vertex> priorityQueue = new PriorityQueue<Vertex>(100, new VertexComparator());
      Vertex start = vertexList.get(exit_cell);
      start.min_distance = 0;
      priorityQueue.offer(start);
      int reached = 1;
      while (!priorityQueue.isEmpty()) {
        Vertex v = priorityQueue.poll();
        if (!v.isVisited()) {
          v.mark();
          for (Edge e : v.list) {
            Vertex destination = vertexList.get(e.end);
            if (!destination.isVisited()) {
              int alt = v.min_distance + e.weight;
              if (alt <= time && alt < destination.min_distance) {
                if (destination.min_distance > time)
                  reached++;
                destination.min_distance = alt;
                priorityQueue.remove(destination);
                priorityQueue.offer(destination);
              }
            }
          }
        }
      }

      System.out.println(reached);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}


/**
 * @author Bijoy
 *
 */
class Vertex {
  int name;
  Integer min_distance;
  List<Edge> list;
  boolean visited;

  public Vertex(int name) {
    this.name = name;
    this.min_distance = Integer.MAX_VALUE;
    list = new ArrayList<Edge>(100);
    visited = false;
  }

  void mark() {
    this.visited = true;
  }

  boolean isVisited() {
    return visited;
  }

  @Override
  public String toString() {
    return list.toString() + "[" + min_distance + "]";
  }
}


/**
 * @author Bijoy
 *
 */
class VertexList {

  Vertex list[];

  public VertexList(int N) {
    list = new Vertex[N];
    for (int i = 0; i < N; i++) {
      list[i] = new Vertex(i + 1);
    }
  }

  public Vertex get(int n) {
    return list[n - 1];
  }

  @Override
  public String toString() {
    return Arrays.toString(list);
  }
}


/**
 * @author Bijoy
 *
 */
class VertexComparator implements Comparator<Vertex> {
  @Override
  public int compare(Vertex vertex1, Vertex vertex2) {
    return vertex1.min_distance.compareTo(vertex2.min_distance);
  }
}


/**
 * @author Bijoy
 *
 */
class Edge {
  int start;
  int end;
  int weight;

  public Edge(int start, int end, int weight) {
    this.start = start;
    this.end = end;
    this.weight = weight;
  }

  @Override
  public String toString() {
    return start + "->" + end + "(" + weight + ")";
  }
}
