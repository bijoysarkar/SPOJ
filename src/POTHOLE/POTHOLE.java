import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class POTHOLE {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			int no_of_testcases = Integer.parseInt(br.readLine().trim());
			while (no_of_testcases-- > 0) {

				int no_of_vertex = Integer.parseInt(br.readLine());
				VertexList vertexList = new VertexList(no_of_vertex);
				for (int i = 1; i < no_of_vertex; i++) {
					String input[] = br.readLine().trim().split(" ");
					if (input.length == 0)
						continue;
					for (int j = 1; j < input.length; j++) {
						int end = Integer.parseInt(input[j]);
						if (end > i) {
							Edge edge;
							if (i == 1 || end == no_of_vertex)
								edge = new Edge(i, end, 1);
							else
								edge = new Edge(i, end, Integer.MAX_VALUE);
							vertexList.get(i).list.add(edge);
							vertexList.get(end).reverse.add(edge);
						}
					}
				}
				if (no_of_testcases > 0)
					br.readLine();

				while (true) {
					boolean augment = BFS(vertexList, 1, no_of_vertex);
					if (!augment) {
						break;
					}
				}
				int count = 0;
				Vertex start = vertexList.get(1);
				for (Edge edge : start.list) {
					if (edge.flow == 1)
						count++;
				}

				System.out.println(count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean BFS(VertexList vertexList, int start, int goal) {

		vertexList.unmark();
		boolean pathFound = false;
		List<Edge> path = new ArrayList<Edge>();

		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(vertexList.get(start));

		Edge backPointerQueue[] = new Edge[vertexList.list.length + 1];
		while (!queue.isEmpty()) {
			Vertex vertex = queue.poll();
			if (!vertex.isVisited()) {
				vertex.mark();
				if (vertex.name == goal) {
					pathFound = true;
					break;
				}
				for (Edge edge : vertex.list) {
					if ((edge.capacity - edge.flow) > 0) {
						Vertex neighbour = vertexList.get(edge.end);
						if (!neighbour.isVisited()) {
							backPointerQueue[neighbour.name] = edge;
							queue.add(neighbour);
						}
					}
				}
				for (Edge edge : vertex.reverse) {
					if (edge.flow > 0) {
						Vertex neighbour = vertexList.get(edge.start);
						if (!neighbour.isVisited()) {
							backPointerQueue[neighbour.name] = edge;
							queue.add(neighbour);
						}
					}
				}
			}
		}

		if (pathFound) {
			Edge edge;
			int previous = vertexList.list.length;
			while ((edge = backPointerQueue[previous]) != null) {
				path.add(edge);
				if (previous == edge.end) {
					edge.flow++;
					previous = edge.start;
				} else {
					edge.flow--;
					previous = edge.end;
				}
			}
		}
		return pathFound;
	}
}

/**
 * @author Bijoy
 *
 */
class Vertex {
	int name;
	List<Edge> list;
	List<Edge> reverse;
	boolean visited;

	public Vertex(int name) {
		this.name = name;
		list = new ArrayList<Edge>(2);
		reverse = new ArrayList<Edge>(2);
		visited = false;
	}

	void mark() {
		this.visited = true;
	}

	void unmark() {
		this.visited = false;
	}

	boolean isVisited() {
		return visited;
	}

	@Override
	public String toString() {
		return list.toString() + "\\" + reverse.toString();
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

	public void unmark() {
		for (Vertex vertex : list) {
			vertex.unmark();
		}
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
class Edge {
	int start;
	int end;
	int flow;
	int capacity;

	public Edge(int start, int end, int capacity) {
		this.start = start;
		this.end = end;
		this.flow = 0;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return start + "->" + end + "(" + capacity + "," + flow + ")";
	}
}
