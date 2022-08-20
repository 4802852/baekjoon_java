import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static class Node {

    private int r, c;

    public Node(int r, int c) {
      this.r = r;
      this.c = c;
    }
  }

  static int N, M, ans;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };
  static int[][] visited;
  static LinkedList<Node>[][] lightSwitch;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String[] input = br.readLine().split(" ");
    N = Integer.parseInt(input[0]);
    M = Integer.parseInt(input[1]);
    visited = new int[N + 1][N + 1];
    lightSwitch = new LinkedList[N + 1][N + 1];
    for (int i = 0; i <= N; i++) {
      for (int j = 0; j <= N; j++) {
        lightSwitch[i][j] = new LinkedList<>();
      }
    }
    for (int i = 0; i < M; i++) {
      input = br.readLine().split(" ");
      lightSwitch[Integer.parseInt(input[0])][Integer.parseInt(input[1])].add(new Node(Integer.parseInt(input[2]), Integer.parseInt(input[3])));
    }
    br.close();
    bfs();
    System.out.println(ans);
  }

  static void bfs() {
    LinkedList<Node> queue = new LinkedList<>();
    visited[1][1] = 2;
    queue.add(new Node(1, 1));
    ans = 1;
    while (!queue.isEmpty()) {
      Node now = queue.poll();
      while (!lightSwitch[now.r][now.c].isEmpty()) {
        Node target = lightSwitch[now.r][now.c].poll();
        if (visited[target.r][target.c] == 0) {
          ans++;
          visited[target.r][target.c] = 1;
          for (int i = 0; i < 4; i++) {
            int nr = target.r + dr[i];
            int nc = target.c + dc[i];
            if (mapOut(nr, nc)) continue;
            if (visited[nr][nc] == 2) queue.add(new Node(nr, nc));
          }
        }
      }
      for (int i = 0; i < 4; i++) {
        int nr = now.r + dr[i];
        int nc = now.c + dc[i];
        if (mapOut(nr, nc)) continue;
        if (visited[nr][nc] == 1) {
          queue.add(new Node(nr, nc));
          visited[nr][nc] = 2;
        }
      }
    }
  }

  static boolean mapOut(int r, int c) {
    return (r < 1 || N < r || c < 1 || N < c);
  }
}
