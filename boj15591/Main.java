package boj15591;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

  static int N, Q, ans;
  static ArrayList<int[]>[] adj;
  static boolean visited[];

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(
      new FileReader("./src/boj15591/input.txt")
    );
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] input = br.readLine().split(" ");
    N = Integer.parseInt(input[0]);
    Q = Integer.parseInt(input[1]);
    adj = new ArrayList[N + 1];

    for (int i = 1; i <= N; i++) {
      adj[i] = new ArrayList<>();
    }
    for (int i = 1; i < N; i++) {
      input = br.readLine().split(" ");
      int p = Integer.parseInt(input[0]);
      int q = Integer.parseInt(input[1]);
      int r = Integer.parseInt(input[2]);
      adj[p].add(new int[] { q, r });
      adj[q].add(new int[] { p, r });
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < Q; i++) {
      input = br.readLine().split(" ");
      int k = Integer.parseInt(input[0]);
      int v = Integer.parseInt(input[1]);

      visited = new boolean[N + 1];
      visited[v] = true;
      Queue<Integer> queue = new LinkedList<>();
      queue.add(v);
      ans = 0;

      while (!queue.isEmpty()) {
        int cur = queue.poll();
        for (int[] a : adj[cur]) {
          if (!visited[a[0]] && a[1] >= k) {
            queue.add(a[0]);
            visited[a[0]] = true;
            ans++;
          }
        }
      }
      sb.append(ans).append('\n');
    }
    System.out.println(sb.toString());
    br.close();
  }
}
