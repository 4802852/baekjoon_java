import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N, R, C, ans;
  static Node[] wormhole;
  static int[] linked;
  static int[][] map;

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
    wormhole = new Node[N + 1];
    linked = new int[N + 1];
    for (int i = 1; i <= N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int r, c;
      r = Integer.parseInt(st.nextToken());
      c = Integer.parseInt(st.nextToken());
      wormhole[i] = new Node(r, c);
      R = Math.max(R, r);
      C = Math.max(C, c);
    }
    br.close();
    dfs(0);
    System.out.println(ans);
  }

  // 웜홀의 연결 경우를 탐색하는 함수, linked[a] = b 일 경우 a 웜홀과 b 웜홀이 연결된 것
  static void dfs(int n) {
    if (n == N / 2) {
      // 모든 웜홀이 연결되었을 경우, 해당 경우에서 소가 멤도는지를 확인하여 카운트
      if (isCycling()) ans++;
      return;
    }
    // 1번 웜홀부터 시작하여, 아직 연결되지 않은 웜홀 탐색
    int link1 = 1;
    while (linked[link1] != 0) link1++;
    // link1 웜홀과 연결할 다음 웜홀 탐색
    for (int link2 = link1 + 1; link2 <= N; link2++) {
      if (linked[link2] != 0) continue;
      linked[link1] = link2;
      linked[link2] = link1;
      dfs(n + 1);
      linked[link1] = 0;
      linked[link2] = 0;
    }
  }

  static boolean isCycling() {
    // 모든 웜홀의 위치를 소의 시작점으로 탐색한다
    for (int t = 1; t <= N; t++) {
      LinkedList<Integer> queue = new LinkedList<>();
      boolean[] visited = new boolean[N + 1];
      queue.add(t);
      visited[t] = true;
      while (!queue.isEmpty()) {
        int now = queue.poll();
        int nowr = wormhole[now].r;
        int nowc = wormhole[now].c;
        // 현재 소의 위치에서 c 값이 같고, r 값이 현재 위치보다 더 큰 웜홀을 찾는다.
        // 해당하는 조건의 웜홀이 많을 경우, r 값이 가장 작은 웜홀을 선택한다.
        int next = 0;
        int nextr = Integer.MAX_VALUE;
        for (int ne = 1; ne <= N; ne++) {
          if (now == ne || wormhole[ne].c != nowc || wormhole[ne].r <= nowr) continue;
          if (wormhole[ne].r < nextr) {
            next = ne;
            nextr = wormhole[ne].r;
          }
        }
        if (next != 0) {
          // 조건에 해당하는 웜홀이 있을 경우 웜홀을 통해 반대편으로 이동시키고 위치를 저장한다.
          next = linked[next];
          if (visited[next]) return true;
          queue.add(next);
          visited[next] = true;
        }
      }
    }
    return false;
  }
}
