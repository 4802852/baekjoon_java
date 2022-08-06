import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int R, C, N;
  static int[][][] map;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    N = Integer.parseInt(st.nextToken());
    map = new int[R][C][6];
    for (int i = 0; i < R; i++) {
      String input = br.readLine();
      for (int j = 0; j < C; j++) {
        if (input.charAt(j) == '.') {
          map[i][j][0] = -1;
        } else {
          map[i][j][0] = 0;
        }
      }
    }
    br.close();
    for (int i = 0; i < 5; i++) {
      calculate(i);
    }
    if (N < 2) {
      printmap(N);
    } else {
      printmap((N - 2) % 4 + 2);
    }
  }

  static void printmap(int n) {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (map[r][c][n] == -1) {
          sb.append('.');
        } else {
          sb.append('O');
        }
      }
      sb.append('\n');
    }
    System.out.print(sb.toString());
  }

  static void calculate(int n) {
    if (n % 2 == 0 || n == 0) {
      for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
          if (map[r][c][n] == -1) {
            map[r][c][n + 1] = -1;
          } else {
            map[r][c][n + 1] = map[r][c][n] + 1;
          }
        }
      }
      for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
          if (map[r][c][n] == 2) {
            map[r][c][n + 1] = -1;
            for (int i = 0; i < 4; i++) {
              int nr = r + dr[i];
              int nc = c + dc[i];
              if (nr < 0 || R <= nr || nc < 0 || C <= nc) continue;
              map[nr][nc][n + 1] = -1;
            }
          }
        }
      }
    } else {
      for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
          map[r][c][n + 1] = map[r][c][n] + 1;
        }
      }
    }
  }
}
