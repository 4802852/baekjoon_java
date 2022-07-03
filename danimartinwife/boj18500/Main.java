package danimartinwife.boj18500;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStreamReader;
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
      // 높이 h에서 창영이 던졌으면 chang = 1, 그렇지 않다면 chang = -1
      throwStone(h, chang);
      chang *= -1;
      // 각 미네랄 덩이를 BFS 로 탐색하되, 공중에 떠있는 미네랄 덩이를 찾는다.
      int flyNum = flyCheckBFS();
      if (flyNum == 0) continue;
      // 공중에 떠있는 미네랄 덩이를 움직일 수 있는 만큼 아래로 이동시킨다.
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

  // h의 높이에서 돌을 던져 깨지는 미네랄 삭제하는 함수
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
    // c == -1 이라면 돌을 던져 허공을 가르는 경우
    if (c == -1) return;
    // 그렇지 않다면 깨진 미네랄 삭제
    map[h][c] = '.';
  }

  static boolean inMap(int r, int c) {
    return (0 <= r && r < R && 0 <= c && c < C);
  }

  // map에서 미네랄을 BFS 탐색하여 공중에 있는 미네랄 번호를 리턴
  static int flyCheckBFS() {
    int num = 1;
    LinkedList<Node> queue = new LinkedList<>();
    visited = new int[R][C];
    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (map[r][c] == '.' || visited[r][c] != 0) continue;
        // isFall: 미네랄이 공중에 떠있는지 저장하는 boolean
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
              // 미네랄 덩이가 바닥에 붙어있을 경우 isFall = false
              if (nr == R - 1) isFall = false;
            }
          }
        }
        // 미네랄이 공중에 떠있을 경우 해당 미네랄 번호 리턴
        if (isFall) return num;
        num++;
      }
    }
    return 0;
  }

  // num 번호에 해당하는 미네랄 fd 만큼 하강
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
  
  // num 번호에 해당하는 미네랄이 얼마만큼 하강할 수 있는지 거리를 구해주는 함수
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
