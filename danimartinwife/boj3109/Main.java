import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int R, C, ans;
  static char[][] map;
  static int[] dr = { -1, 0, 1 };
  static int[] dc = { 1, 1, 1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    map = new char[R][C];
    for (int i = 0; i < R; i++) {
      String line = br.readLine();
      for (int j = 0; j < C; j++) {
        map[i][j] = line.charAt(j);
      }
    }
    br.close();
    for (int i = 0; i < R; i++) {
      if (dfs(i, 0)) ans++;
    }
    System.out.println(ans);
  }

  static boolean dfs(int r, int c) {
    map[r][c] = '-';
    if (c == C - 1) return true;
    for (int i = 0; i < 3; i++) {
      int nr = r + dr[i];
      int nc = c + dc[i];
      if (0 <= nr && nr < R && map[nr][nc] == '.') {
        if (dfs(nr, nc)) return true;
      }
    }
    return false;
  }
}
