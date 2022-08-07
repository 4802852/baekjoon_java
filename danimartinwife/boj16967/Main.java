import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int H, W, R, C;
  static int[][] input_map;
  static int[][] ans_map;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    H = Integer.parseInt(st.nextToken());
    W = Integer.parseInt(st.nextToken());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    input_map = new int[H + R][W + C];
    ans_map = new int[H][W];
    for (int i = 0; i < H + R; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < W + C; j++) {
        input_map[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();
    for (int r = 0; r < H; r++) {
      for (int c = 0; c < W; c++) {
        if (r < R) {
          ans_map[r][c] = input_map[r][c];
        } else {
          if (c < C) {
            ans_map[r][c] = input_map[r][c];
          } else {
            ans_map[r][c] = input_map[r][c] - ans_map[r - R][c - C];
          }
        }
      }
    }
    printans();
  }

  static void printans() {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < H; r++) {
      for (int c = 0; c < W; c++) {
        sb.append(ans_map[r][c]).append(' ');
      }
      sb.append('\n');
    }
    System.out.print(sb.toString());
  }
}
