import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N;
  static ArrayList<Integer>[] graph;
  static boolean[] visited;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // initialize
    N = Integer.parseInt(br.readLine());
    graph = new ArrayList[N + 1];
    for (int i = 0; i < N + 1; i++) {
      graph[i] = new ArrayList<>();
    }
    visited = new boolean[N + 1];

    // Graph
    int m = Integer.parseInt(br.readLine());
    for (int i = 0; i < m; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      graph[a].add(b);
      graph[b].add(a);
    }
    br.close();

    // bfs();
    dfs(1);

    // Virus Count
    int res = 0;
    for (int i = 1; i < N + 1; i++) {
      if (visited[i]) res++;
    }
    System.out.println(res - 1);
  }

  // BFS
  static void bfs() {
    LinkedList<Integer> queue = new LinkedList<>();
    queue.add(1);
    visited[1] = true;
    while (!queue.isEmpty()) {
      int now = queue.poll();
      for (int next : graph[now]) {
        if (visited[next] == false) {
          visited[next] = true;
          queue.add(next);
        }
      }
    }
  }

  // DFS
  static void dfs(int now) {
    for (int next : graph[now]) {
      if (visited[next] == false) {
        visited[next] = true;
        dfs(next);
      }
    }
  }
}
