import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.util.*;

public class Main {

  static class Pair {

    int first, second;

    Pair(int first, int second) {
      this.first = first;
      this.second = second;
    }
  }

  static int costCalculation(int a, int b) {
    int ans = (X[a] - X[b]) * (X[a] - X[b]) + (Y[a] - Y[b]) * (Y[a] - Y[b]);
    return ans;
  }

  static int N, C;
  static int[] X, Y;
  static ArrayList<Pair>[] adj;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String[] input = br.readLine().split(" ");
    N = Integer.parseInt(input[0]);
    C = Integer.parseInt(input[1]);

    adj = new ArrayList[N];
    X = new int[N];
    Y = new int[N];
    for (int i = 0; i < N; i++) {
      adj[i] = new ArrayList<>();
    }
    for (int i = 0; i < N; i++) {
      input = br.readLine().split(" ");
      X[i] = Integer.parseInt(input[0]);
      Y[i] = Integer.parseInt(input[1]);
    }

    for (int i = 0; i < N - 1; i++) {
      for (int j = i + 1; j < N; j++) {
        int d = costCalculation(i, j);
        if (C <= d) {
          adj[i].add(new Pair(j, d));
          adj[j].add(new Pair(i, d));
        }
      }
    }
    br.close();

    boolean[] added = new boolean[N];
    int[] minWeight = new int[N];
    Arrays.fill(minWeight, Integer.MAX_VALUE);
    int ret = 0;
    minWeight[0] = 0;
    for (int iter = 0; iter < N; iter++) {
      int u = -1;
      for (int n = 0; n < N; n++) {
        if (!added[n] && (u == -1 || minWeight[u] > minWeight[n])) {
          u = n;
        }
      }
      ret += minWeight[u];
      added[u] = true;

      for (int i = 0; i < adj[u].size(); i++) {
        int n = adj[u].get(i).first, weight = adj[u].get(i).second;
        if (!added[n] && minWeight[n] > weight) {
          minWeight[n] = weight;
        }
      }
    }
    for (int n : minWeight) {
      if (n == Integer.MAX_VALUE) {
        ret = -1;
      }
    }
    System.out.println(ret);
  }
}
