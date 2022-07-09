package danimartinwife.boj5852;

import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int R, C;
  static char[][] map;
  static int[][] intmap;
  static int island_num;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };
  static int[][] cost;
  static int[][] dp;
  static int allvisit;

  static class Node implements Comparable<Node> {

    int val, r, c;

    public Node(int val, int r, int c) {
      this.val = val;
      this.r = r;
      this.c = c;
    }

    @Override
    public int compareTo(Node o) {
      return this.val - o.val;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(
      new FileReader(
        "/Users/hs/vscode/algorithm/algorithm/src/danimartinwife/boj5852/input.txt"
      )
    );
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    map = new char[R][C];
    intmap = new int[R][C];
    for (int i = 0; i < R; i++) {
      String input = br.readLine();
      for (int j = 0; j < C; j++) {
        map[i][j] = input.charAt(j);
      }
    }
    br.close();
    island_num = numberingAndGraph();
    dp = new int[1 << (island_num + 1)][island_num + 1];
    getAllVisit();
    int res = getSwinNumber(0, 0);
    System.out.println(res);
  }

  static boolean outMap(int r, int c) {
    return r < 0 || R <= r || c < 0 || C <= c;
  }

  // 섬들을 번호 매겨 intmap에 위치를 표시하고, 이 지도를 기반으로 섬에서 섬으로 이동하는데 몇번의 수영이 필요한지 탐색
  static int numberingAndGraph() {
    LinkedList<Node> queue = new LinkedList<>();
    int num = 1;
    // map을 탐색하면서 해당 위치가 섬이라면 bfs 탐색을 통해 같은 번호로 묶어줌
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        if (intmap[i][j] != 0 || map[i][j] != 'X') continue;
        queue.add(new Node(0, i, j));
        intmap[i][j] = num;
        while (!queue.isEmpty()) {
          Node now = queue.poll();
          for (int k = 0; k < 4; k++) {
            int nr = now.r + dr[k];
            int nc = now.c + dc[k];
            if (
              outMap(nr, nc) || intmap[nr][nc] != 0 || map[nr][nc] != 'X'
            ) continue;
            intmap[nr][nc] = num;
            queue.add(new Node(0, nr, nc));
          }
        }
        num++;
      }
    }
    // cost 배열을 만들고 cost[i][j] 는 i번 섬에서 j번 섬까지 이동하는데 몇번의 수영이 필요한지 저장
    cost = new int[num][num];
    // minimum 값을 저장하기 위해 max 값으로 초기화
    for (int i = 1; i < num; i++) {
      for (int j = 1; j < num; j++) {
        cost[i][j] = Integer.MAX_VALUE;
      }
    }
    PriorityQueue<Node> pq = new PriorityQueue<>();
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        if (map[i][j] != 'X') continue;
        int[][] visited = new int[R][C];
        pq.add(new Node(0, i, j));
        visited[i][j] = 1;
        int nown = intmap[i][j];
        while (!pq.isEmpty()) {
          Node now = pq.poll();
          for (int k = 0; k < 4; k++) {
            int nr = now.r + dr[k];
            int nc = now.c + dc[k];
            int nextD = now.val;
            if (
              outMap(nr, nc) ||
              visited[nr][nc] != 0 ||
              intmap[nr][nc] == nown ||
              map[nr][nc] == '.'
            ) continue;
            if (intmap[nr][nc] != 0) {
              cost[nown][intmap[nr][nc]] =
                Math.min(cost[nown][intmap[nr][nc]], nextD);
            } else {
              nextD++;
            }
            pq.add(new Node(nextD, nr, nc));
            visited[nr][nc] = 1;
          }
        }
      }
    }
    // 섬의 갯수를 리턴
    return num - 1;
  }

  static void getAllVisit() {
    for (int i = 1; i <= island_num; i++) {
      allvisit = (allvisit | 1 << i);
    }
  }

  // 비트마스크를 이용한 dp 탐색 함수
  static int getSwinNumber(int visit, int idx) {
    if (dp[visit][idx] != 0) return dp[visit][idx];
    if (visit == allvisit) return dp[visit][idx] = 0;
    int ret = Integer.MAX_VALUE;
    for (int next = 1; next <= island_num; next++) {
      if (idx == next || (visit & (1 << next)) != 0) continue;
      ret =
        Math.min(
          ret,
          getSwinNumber((visit | (1 << next)), next) + cost[idx][next]
        );
    }
    return dp[visit][idx] = ret;
  }
}
