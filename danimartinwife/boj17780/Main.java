import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.util.*;

public class Main {

  static class Horse {

    int num, dir;

    Horse(int num, int dir) {
      this.num = num;
      this.dir = dir;
    }
  }

  static int N, K;
  static int color[][], loc[][];
  static LinkedList<Horse>[][] map;
  static int[] dr = { 0, -1, 0, 1 };
  static int[] dc = { 1, 0, -1, 0 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    color = new int[N][N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        color[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    loc = new int[K][2];
    map = new LinkedList[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        map[i][j] = new LinkedList<>();
      }
    }
    int r, c, d;
    for (int i = 0; i < K; i++) {
      st = new StringTokenizer(br.readLine());
      r = Integer.parseInt(st.nextToken()) - 1;
      c = Integer.parseInt(st.nextToken()) - 1;
      d = Integer.parseInt(st.nextToken()) - 1;
      switch (d) {
        case 1:
          d = 2;
          break;
        case 2:
          d = 1;
          break;
        default:
          break;
      }
      loc[i][0] = r;
      loc[i][1] = c;
      map[r][c].add(new Horse(i, d));
    }
    br.close();
    // printMap();
    System.out.println(Solve());
  }

  static int Solve() {
    for (int t = 1; t <= 1000; t++) {
      for (int k = 0; k < K; k++) {
        int r = loc[k][0];
        int c = loc[k][1];
        if (map[r][c].get(0).num != k) continue;
        int d = map[r][c].get(0).dir;
        int nr = r + dr[d];
        int nc = c + dc[d];
        if (colorBlue(nr, nc)) {
          d = (d + 2) % 4;
          map[r][c].get(0).dir = d;
          nr = r + dr[d];
          nc = c + dc[d];
          if (colorBlue(nr, nc)) {
            continue;
          }
        }
        if (moveAndCount(r, c, nr, nc) >= 4) {
          return t;
        }
      }
      // printMap();
    }
    return -1;
  }

  static void printMap() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        sb.append(map[i][j].size()).append(' ');
      }
      sb.append('\n');
    }
    System.out.println(sb.toString());
  }

  static boolean colorBlue(int r, int c) {
    return r < 0 || N <= r || c < 0 || N <= c || color[r][c] == 2;
  }

  static int moveAndCount(int r, int c, int nr, int nc) {
    if (color[nr][nc] == 0) {
      while (map[r][c].size() > 0) {
        Horse temp = map[r][c].pollFirst();
        loc[temp.num][0] = nr;
        loc[temp.num][1] = nc;
        map[nr][nc].add(temp);
      }
    } else {
      while (map[r][c].size() > 0) {
        Horse temp = map[r][c].pollLast();
        loc[temp.num][0] = nr;
        loc[temp.num][1] = nc;
        map[nr][nc].add(temp);
      }
    }
    return map[nr][nc].size();
  }
}
