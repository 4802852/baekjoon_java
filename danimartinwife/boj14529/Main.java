import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N, ans;
  static char[][] map;
  static boolean[][][][] mark;
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
    N = Integer.parseInt(br.readLine());
    map = new char[N][N];
    mark = new boolean[N][N][N][N];
    for (int i = 0; i < N; i++) {
      String tmp = br.readLine();
      for (int j = 0; j < N; j++) {
        map[i][j] = tmp.charAt(j);
      }
    }
    br.close();
    for (int h = N - 1; 0 <= h; h--) {
      for (int w = N - 1; 0 <= w; w--) {
        // 크기가 w, h 인 상자 탐색
        for (int r = 0; r + h < N; r++) {
          for (int c = 0; c + w < N; c++) {
            // 탐색 중인 크기가 w, h 인 상자가 PCL인지 아닌지 확인
            if (mark[r][c][r + h][c + w]) continue;
            Node from = new Node(r, c);
            Node to = new Node(r + h, c + w);
            if (isPCL(from, to)) {
              marking(from, to);
              ans++;
            }
          }
        }
      }
    }
    System.out.println(ans);
  }

  static boolean isPCL(Node from, Node to) {
    boolean[][] visited = new boolean[N][N];
    int[] visited_color = new int[27];
    int color_num = 0;
    for (int r = from.r; r <= to.r; r++) {
      for (int c = from.c; c <= to.c; c++) {
        if (visited[r][c]) continue;
        char now_color = map[r][c];
        if (visited_color[now_color - 'A'] == 0) color_num++;
        // 현재 탐색중인 상자에 알파벳의 종류가 3종 이상일 경우 PCL 이 아니므로 탐색 종료
        if (color_num > 2) return false;
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(new Node(r, c));
        while (!queue.isEmpty()) {
          Node now = queue.poll();
          for (int i = 0; i < 4; i++) {
            int nr = now.r + dr[i];
            int nc = now.c + dc[i];
            if (nr < from.r || to.r < nr || nc < from.c || to.c < nc || visited[nr][nc] || map[nr][nc] != now_color) continue;
            visited[nr][nc] = true;
            queue.add(new Node(nr, nc));
          }
        }
        visited_color[now_color - 'A']++;
      }
    }
    int color1 = 0;
    int color2 = 0;
    for (int i = 0; i < 26; i++) {
      if (visited_color[i] == 0) continue;
      if (visited_color[i] == 1) {
        color1 = visited_color[i];
      } else {
        color2 = visited_color[i];
      }
    }
    // PCL의 조건에 부합할 경우 true 리턴
    if (color1 == 1 && color2 >= 2) return true;
    return false;
  }

  // PCL 이라고 탐색된 상자의 내부를 모두 PCL로 마킹해주는 함수
  static void marking(Node from, Node to) {
    for (int r = from.r; r <= to.r; r++) {
      for (int c = from.c; c <= to.c; c++) {
        for (int i = r; i <= to.r; i++) {
          for (int j = c; j <= to.c; j++) {
            mark[r][c][i][j] = true;
          }
        }
      }
    }
  }
}
