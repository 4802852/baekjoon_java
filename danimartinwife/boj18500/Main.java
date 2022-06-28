package danimartinwife.boj18500;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

  static int R, C, N;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };
  static char[][] map;
  static int[][] visited;
  static int chang = 1;

  static class Node {

    int r, c;

    Node(int r, int c) {
      this.r = r;
      this.c = c;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(
      new FileReader(
        "/Users/hs/vscode/algorithm/algorithm/src/danimartinwife/boj18500/input.txt"
      )
    );
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    map = new char[R][C];
    for (int i = 0; i < R; i++) {
      String input = br.readLine();
      for (int j = 0; j < C; j++) {
        map[i][j] = input.charAt(j);
      }
    }
    N = Integer.parseInt(br.readLine());
    st = new StringTokenizer(br.readLine());
    for (int t = 0; t < N; t++) {
      int h = R - Integer.parseInt(st.nextToken());
      throwStone(h, chang);
      chang *= -1;
      int flyNum = flyCheckBFS();
      if (flyNum == 0) continue;
      fall(flyNum);
    }
    br.close();
    printMap();
  }

  static void printMap() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        sb.append(map[i][j]);
      }
      if (i != R - 1) sb.append('\n');
    }
    System.out.println(sb.toString());
  }

  static void throwStone(int h, int chang) {
    int c = -1;
    if (chang == 1) {
      for (int cc = C - 1; 0 <= cc; cc--) {
        if (map[h][cc] == 'x') c = cc;
      }
    } else {
      for (int cc = 0; cc < C; cc++) {
        if (map[h][cc] == 'x') c = cc;
      }
    }
    if (c == -1) return;
    map[h][c] = '.';
  }

  static boolean inMap(int r, int c) {
    return (0 <= r && r < R && 0 <= c && c < C);
  }

  static int flyCheckBFS() {
    int num = 1;
    LinkedList<Node> queue = new LinkedList<>();
    visited = new int[R][C];
    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (map[r][c] == '.' || visited[r][c] != 0) continue;
        boolean isFall = true;
        queue.add(new Node(r, c));
        visited[r][c] = num;
        while (!queue.isEmpty()) {
          Node now = queue.poll();
          for (int i = 0; i < 4; i++) {
            int nr = now.r + dr[i];
            int nc = now.c + dc[i];
            if (inMap(nr, nc) && map[nr][nc] == 'x' && visited[nr][nc] == 0) {
              visited[nr][nc] = num;
              queue.add(new Node(nr, nc));
              if (nr == R - 1) isFall = false;
            }
          }
        }
        if (isFall) return num;
        num++;
      }
    }
    return 0;
  }

  static void fall(int num) {
    int fd = fallDistance(num);
    for (int r = R - 1; r >= 0; r--) {
      for (int c = 0; c < C; c++) {
        if (map[r][c] == 'x' && visited[r][c] == num) {
          map[r + fd][c] = 'x';
          map[r][c] = '.';
        }
      }
    }
  }

  static int fallDistance(int num) {
    int fd = Integer.MAX_VALUE;
    for (int c = 0; c < C; c++) {
      int high = -1, low = R;
      for (int r = 0; r < R; r++) {
        if (map[r][c] == 'x') {
          if (visited[r][c] == num) {
            high = r;
          } else {
            low = r;
          }
          if (high < low && high != -1) {
            fd = Math.min(fd, low - high);
          }
        }
      }
    }
    return (fd - 1);
  }
}
