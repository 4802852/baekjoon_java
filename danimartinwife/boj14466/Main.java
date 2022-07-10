import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static class Pair {

    int r, c;

    public Pair(int r, int c) {
      this.r = r;
      this.c = c;
    }
  }

  static int N, K, R;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };
  static int[] cow_num;
  static int[] cow_num_count;
  static Pair[] cow_loc;
  static ArrayList<Pair>[][] bridges;
  static int[][] map;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    R = Integer.parseInt(st.nextToken());
    cow_num = new int[R + 1];
    cow_num_count = new int[R + 1];
    cow_loc = new Pair[R + 1];
    map = new int[N][N];
    // ArrayList를 이용하여 지나갈 수 없는(길로 연결된) 좌표들을 저장해준다.
    bridges = new ArrayList[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        bridges[i][j] = new ArrayList<>();
      }
    }
    int r1, c1, r2, c2;
    for (int t = 0; t < R; t++) {
      st = new StringTokenizer(br.readLine());
      r1 = Integer.parseInt(st.nextToken()) - 1;
      c1 = Integer.parseInt(st.nextToken()) - 1;
      r2 = Integer.parseInt(st.nextToken()) - 1;
      c2 = Integer.parseInt(st.nextToken()) - 1;
      bridges[r1][c1].add(new Pair(r2, c2));
      bridges[r2][c2].add(new Pair(r1, c1));
    }
    // map에 소의 번호를 표시해주고, 해당 번호 소의 위치를 빠르게 찾기 위해 위치를 cow_loc에 저장해준다.
    for (int t = 1; t <= K; t++) {
      st = new StringTokenizer(br.readLine());
      r1 = Integer.parseInt(st.nextToken()) - 1;
      c1 = Integer.parseInt(st.nextToken()) - 1;
      map[r1][c1] = t;
      cow_loc[t] = new Pair(r1, c1);
    }
    br.close();
    // t번 소에서 부터 bfs 탐색하여 t번 소가 만날 수 있는 소들을 같은 num 그룹이 되도록 저장해준다.
    int num = 1;
    for (int t = 1; t <= K; t++) {
      if (cow_num[t] != 0) continue;
      bfs(t, num++);
    }
    // 각 그룹에 속한 소들의 수를 이용해서 정답 수를 구해준다.
    int ans = 0;
    int total = K;
    for (int i = 1; i <= K; i++) {
      total -= cow_num_count[i];
      ans += cow_num_count[i] * total;
    }
    System.out.println(ans);
  }

  // 농장 바깥으로 나가는지 확인하는 함수
  static boolean isOutMap(int r, int c) {
    return (r < 0 || N <= r || c < 0 || N <= c);
  }

  // r, c 위치에서 nr, nc 위치 사이에 길로 연결되어있는지를 리턴하는 함수
  static boolean isBridged(int r, int c, int nr, int nc) {
    if (bridges[r][c].isEmpty()) {
      return false;
    } else {
      for (Pair o : bridges[r][c]) {
        if (o.r == nr && o.c == nc) return true;
      }
    }
    return false;
  }

  // start 번호를 갖는 소에서 시작하여 길을 통하지 않고 도달할 수 있는 모든 소에 n 번 그룹 표기를 해준다.
  static void bfs(int start, int n) {
    LinkedList<Pair> queue = new LinkedList<>();
    int[][] visited = new int[N][N];
    int sr = cow_loc[start].r;
    int sc = cow_loc[start].c;
    visited[sr][sc] = 1;
    queue.add(new Pair(sr, sc));
    cow_num[start] = n;
    cow_num_count[n]++;

    while (!queue.isEmpty()) {
      Pair now = queue.poll();
      for (int i = 0; i < 4; i++) {
        int nr = now.r + dr[i];
        int nc = now.c + dc[i];
        if (isOutMap(nr, nc) || visited[nr][nc] == 1) continue;
        if (isBridged(now.r, now.c, nr, nc)) continue;
        queue.add(new Pair(nr, nc));
        visited[nr][nc] = 1;
        if (map[nr][nc] != 0) {
          cow_num[map[nr][nc]] = n;
          cow_num_count[n]++;
        }
      }
    }
  }
}
