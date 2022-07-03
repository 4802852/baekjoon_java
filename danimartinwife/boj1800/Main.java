package danimartinwife.boj1800;

import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static class Pair implements Comparable<Pair> {

    private int index, cost;

    public Pair(int index, int cost) {
      this.index = index;
      this.cost = cost;
    }

    @Override
    public int compareTo(Pair o) {
      return this.cost - o.cost;
    }
  }

  static int N, P, K;
  static ArrayList<Pair>[] map;
  static int[] dist;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(
      new FileReader(
        "/Users/hs/vscode/algorithm/algorithm/src/danimartinwife/boj1800/input.txt"
      )
    );
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    P = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    dist = new int[N + 1];
    map = new ArrayList[N + 1];
    for (int t = 1; t <= N; t++) {
      map[t] = new ArrayList<>();
    }

    int max = 0;
    int a, b, c;
    for (int t = 0; t < P; t++) {
      st = new StringTokenizer(br.readLine());
      a = Integer.parseInt(st.nextToken());
      b = Integer.parseInt(st.nextToken());
      c = Integer.parseInt(st.nextToken());
      map[a].add(new Pair(b, c));
      map[b].add(new Pair(a, c));
      max = Math.max(max, c);
    }
    br.close();

    int start = 0;
    int ans = Integer.MAX_VALUE;
    while (start <= max) {
      int mid = (start + max) / 2;
      if (dijstra(mid)) {
        ans = mid;
        max = mid - 1;
      } else {
        start = mid + 1;
      }
    }

    if (ans == Integer.MAX_VALUE) {
      ans = -1;
    }
    System.out.println(ans);
  }

  static boolean dijstra(int x) {
    PriorityQueue<Pair> queue = new PriorityQueue<>();
    Arrays.fill(dist, Integer.MAX_VALUE);

    dist[1] = 0;
    queue.add(new Pair(1, 0));

    while (!queue.isEmpty()) {
      Pair tmp = queue.poll();
      int now = tmp.index;
      int cost = tmp.cost;

      if (dist[now] < cost) continue;

      for (Pair n : map[now]) {
        int next = n.index;
        int nextCost = cost;
        if (n.cost > x) {
          nextCost++;
        }
        if (nextCost < dist[next]) {
          dist[next] = nextCost;
          queue.add(new Pair(next, nextCost));
        }
      }
    }
    return dist[N] <= K;
  }
}
