import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N, M, K;
  static int ans = Integer.MAX_VALUE;
  static ArrayList<Integer> market;
  static ArrayList<Pair>[] road;
  static int[][] distance;
  static boolean[] visited;

  static class Pair implements Comparable<Pair> {

    private int dest, dist;

    public Pair(int dest, int dist) {
      this.dest = dest;
      this.dist = dist;
    }

    @Override
    public int compareTo(Pair o) {
      return this.dist - o.dist;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    market = new ArrayList<>();
    road = new ArrayList[N + 1];
    distance = new int[K][N + 1];
    for (int i = 0; i < K; i++) {
      for (int j = 1; j <= N; j++) {
        distance[i][j] = Integer.MAX_VALUE;
      }
    }
    for (int i = 1; i <= N; i++) {
      road[i] = new ArrayList<>();
    }
    for (int i = 1; i <= K; i++) {
      market.add(Integer.parseInt(br.readLine()));
    }
    for (int i = 0; i < M; i++) {
      int a, b, d;
      st = new StringTokenizer(br.readLine());
      a = Integer.parseInt(st.nextToken());
      b = Integer.parseInt(st.nextToken());
      d = Integer.parseInt(st.nextToken());
      road[a].add(new Pair(b, d));
      road[b].add(new Pair(a, d));
    }
    br.close();
    // K개의 마켓을 시작점으로 하는 다익스트라만 계산
    for (int i = 0; i < K; i++) {
      dijkstra(i);
    }
    for (int i = 0; i < K; i++) {
      visited = new boolean[K];
      visited[i] = true;
      dfs(i, i, 1, 0);
    }
    System.out.println(ans);
  }
  
  // 시작 마켓, 마지막 방문 마켓, depth, distance를 저장하는 dfs
  static void dfs(int start, int last, int n, int d) {
    if (n == K) {
      // K개의 마켓을 모두 방문하면, 처음 마켓~N마을 거리 + 마지막 마켓~N마을 거리를 더해준 값을 비교
      for (int i = 1; i <= N; i++) {
        if (!market.contains(i)) {
          ans = Math.min(ans, d + distance[start][i] + distance[last][i]);
        }
      }
      return;
    }
    for (int k = 0; k < K; k++) {
      if (visited[k]) continue;
      visited[k] = true;
      dfs(start, k, n + 1, d + distance[last][market.get(k)]);
      visited[k] = false;
    }
  }

  // 다익스트라 알고리즘
  static void dijkstra(int index) {
    int start = market.get(index);
    distance[index][start] = 0;
    PriorityQueue<Pair> queue = new PriorityQueue<>();
    queue.add(new Pair(start, 0));
    while (!queue.isEmpty()) {
      Pair now = queue.poll();
      if (distance[index][now.dest] < now.dist) continue;
      for (int i = 0; i < road[now.dest].size(); i++) {
        Pair ne = road[now.dest].get(i);
        int next = ne.dest;
        int nextDistance = now.dist + ne.dist;
        if (nextDistance < distance[index][next]) {
          distance[index][next] = nextDistance;
          queue.add(new Pair(next, nextDistance));
        }
      }
    }
  }
}
