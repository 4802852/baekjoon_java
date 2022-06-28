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
  static int[][] map;
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
    map = new int[R][C];
    for (int i = 0; i < R; i++) {
      String input = br.readLine();
      for (int j = 0; j < C; j++) {
        if (input.charAt(j) == '.') {
          map[i][j] = 0;
        } else {
          map[i][j] = 1;
        }
      }
    }
    N = Integer.parseInt(br.readLine());
    st = new StringTokenizer(br.readLine());
    for (int t = 0; t < N; t++) {
      int h = R - Integer.parseInt(st.nextToken());
      throwStone(h, chang);
      chang *= -1;
    }
    br.close();
    printMap();
  }

  static void printMap() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        if (map[i][j] == 0) {
          sb.append('.');
        } else {
          sb.append('x');
        }
      }
      if (i != R - 1) sb.append('\n');
    }
    System.out.println(sb.toString());
  }

  static void throwStone(int h, int chang) {
    int c = -1;
    if (chang == 1) {
      for (int cc = C - 1; 0 <= cc; cc--) {
        if (map[h][cc] == 1) c = cc;
      }
    } else {
      for (int cc = 0; cc < C; cc++) {
        if (map[h][cc] == 1) c = cc;
      }
    }
    if (c == -1) return;
    map[h][c] = 0;
    breakAndFall(h, c);
  }

  static boolean inMap(int r, int c) {
    return (0 <= r && r < R && 0 <= c && c < C);
  }

  static void breakAndFall(int r, int c) {
    int[][] visited = new int[R][C];
    LinkedList<Node> queue = new LinkedList<>();
    LinkedList<Node> fallq = new LinkedList<>();
    boolean isRooted;
    for (int i = 0; i < 4; i++) {
      isRooted = false;
      int sr = r + dr[i];
      int sc = c + dc[i];
      if (!inMap(sr, sc) || map[sr][sc] == 0 || visited[sr][sc] == 1) continue;
      fallq.clear();
      fallq.add(new Node(sr, sc));
      queue.add(new Node(sr, sc));
      visited[sr][sc] = 1;
      while (!queue.isEmpty()) {
        Node now = queue.poll();
        for (int j = 0; j < 4; j++) {
          int nr = now.r + dr[j];
          int nc = now.c + dc[j];
          if (inMap(nr, nc) && map[nr][nc] == 1 && visited[nr][nc] == 0) {
            visited[nr][nc] = 1;
            queue.add(new Node(nr, nc));
            fallq.add(new Node(nr, nc));
            if (nr == R - 1) isRooted = true;
          }
        }
      }
      if (isRooted) continue;
      int fallDistance = 0;
      for (int j = 0; j < fallq.size(); j++) {
        Node temp = fallq.get(j);
        map[temp.r][temp.c] = 0;
      }
      boolean collision = false;
      while (collision == false) {
        fallDistance++;
        for (int j = 0; j < fallq.size(); j++) {
          Node temp = fallq.get(j);
          if (
            !inMap(temp.r + fallDistance, temp.c) ||
            map[temp.r + fallDistance][temp.c] == 1
          ) collision = true;
        }
      }
      for (int j = 0; j < fallq.size(); j++) {
        Node temp = fallq.get(j);
        map[temp.r + fallDistance - 1][temp.c] = 1;
      }
    }
  }
}
