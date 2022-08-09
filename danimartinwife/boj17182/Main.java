import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N, K;
  static int[][] dist;
  static int[][] dp;
  static int allvisit;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    dist = new int[N][N];
    dp = new int[1 << N][N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        dist[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();
    // 플로이드 와샬 알고리즘을 수행하여 dist 배열에 각 노드간의 최단거리를 갱신
    FloydWashall();
    for (int i = 0; i < N; i++) {
      allvisit = (allvisit | 1 << i);
    }
    // 백트래킹을 진행하고 그 결과를 출력
    System.out.println(dfs(1 << K, K));
  }

  // 플로이드 와샬 알고리즘 수행
  static void FloydWashall() {
    for (int k = 0; k < N; k++) {
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        }
      }
    }
  }

  // 비트마스크를 이용한 백트래킹 탐색
  static int dfs(int visit, int idx) {
    if (dp[visit][idx] != 0) return dp[visit][idx];
    if (visit == allvisit) return dp[visit][idx] = 0;
    int ret = Integer.MAX_VALUE;
    for (int next = 0; next < N; next++) {
      if (idx == next || (visit & (1 << next)) != 0) continue;
      ret = Math.min(ret, dfs((visit | (1 << next)), next) + dist[idx][next]);
    }
    return dp[visit][idx] = ret;
  }
}
