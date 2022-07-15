import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int R, C;
  static char[][] map;
  static boolean gravity_flipped = false;
  static int sr, sc, er, ec;
  static int dc[] = { 1, -1 };

  static class Node implements Comparable<Node> {

    private int r, c, flipped;

    public Node(int r, int c, int flipped) {
      this.r = r;
      this.c = c;
      this.flipped = flipped;
    }

    @Override
    public int compareTo(Node o) {
      return this.flipped - o.flipped;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String[] rc = br.readLine().split(" ");
    R = Integer.parseInt(rc[0]);
    C = Integer.parseInt(rc[1]);
    map = new char[R][C];
    for (int i = 0; i < R; i++) {
      String input = br.readLine();
      for (int j = 0; j < C; j++) {
        map[i][j] = input.charAt(j);
        if (map[i][j] == 'C') {
          map[i][j] = '.';
          sr = i;
          sc = j;
        } else if (map[i][j] == 'D') {
          map[i][j] = '.';
          er = i;
          ec = j;
        }
      }
    }
    br.close();
    int res = startAdventure();
    System.out.println(res);
  }

  static boolean outMap(int r, int c) {
    return (r < 0 || R <= r || c < 0 || C <= c);
  }

  static int startAdventure() {
    boolean[][][] visited = new boolean[R][C][2];
    PriorityQueue<Node> queue = new PriorityQueue<>();
    queue.add(new Node(sr, sc, 0));
    visited[sr][sc][0] = true;
    while (!queue.isEmpty()) {
      Node now = queue.poll();
      boolean out = false;
      if (now.r == er && now.c == ec) return now.flipped;
      int nowr = now.r;
      if (now.flipped % 2 == 1) {
        while (true) {
          if (nowr <= 0) {
            out = true;
            break;
          }
          if (map[nowr - 1][now.c] == '#') {
            break;
          }
          if (nowr - 1 == er && now.c == ec) {
            return now.flipped;
          }
          nowr--;
        }
      } else {
        while (true) {
          if (R - 1 <= nowr) {
            out = true;
            break;
          }
          if (map[nowr + 1][now.c] == '#') {
            break;
          }
          if (nowr + 1 == er && now.c == ec) {
            return now.flipped;
          }
          nowr++;
        }
      }
      if (out) continue;
      if (visited[nowr][now.c][(now.flipped + 1) % 2] == false) {
        queue.add(new Node(nowr, now.c, now.flipped + 1));
        visited[nowr][now.c][(now.flipped + 1) % 2] = true;
      }
      int nc = now.c + 1;
      if (nc < C && (map[nowr][nc] != '#') && visited[nowr][nc][now.flipped % 2] == false) {
        queue.add(new Node(nowr, nc, now.flipped));
        visited[nowr][nc][now.flipped % 2] = true;
      }
      nc = now.c - 1;
      if (0 <= nc && (map[nowr][nc] != '#') && visited[nowr][nc][now.flipped % 2] == false) {
        queue.add(new Node(nowr, nc, now.flipped));
        visited[nowr][nc][now.flipped % 2] = true;
      }
    }
    return -1;
  }
}
