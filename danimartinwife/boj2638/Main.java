import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N, M, ans;
  static int[][] map;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };

  static class Node {

    private int r, c;

    public Node(int r, int c) {
      this.r = r;
      this.c = c;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    map = new int[N][M];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < M; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();
    while (melt()) ans++;
    System.out.println(ans);
  }

  static boolean melt() {
    LinkedList<Node> queue = new LinkedList<>();
    LinkedList<Node> melt_list = new LinkedList<>();
    int[][] visited = new int[N][M];
    queue.add(new Node(0, 0));
    visited[0][0] = -1;
    while (!queue.isEmpty()) {
      Node now = queue.poll();
      for (int i = 0; i < 4; i++) {
        int nr = now.r + dr[i];
        int nc = now.c + dc[i];
        if (mapOut(nr, nc) || visited[nr][nc] == -1) continue;
        if (map[nr][nc] == 0) {
          visited[nr][nc] = -1;
          queue.add(new Node(nr, nc));
        } else {
          visited[nr][nc]++;
          if (visited[nr][nc] >= 2) {
            melt_list.add(new Node(nr, nc));
            visited[nr][nc] = -1;
          }
        }
      }
    }
    if (melt_list.isEmpty()) return false;
    while (!melt_list.isEmpty()) {
      Node now = melt_list.poll();
      map[now.r][now.c] = 0;
    }
    return true;
  }

  static boolean mapOut(int r, int c) {
    return (r < 0 || N <= r || c < 0 || M <= c);
  }
}
