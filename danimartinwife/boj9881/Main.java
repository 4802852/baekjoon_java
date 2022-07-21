import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int N;
  static int[] height;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    height = new int[N];
    for (int i = 0; i < N; i++) {
      height[i] = Integer.parseInt(br.readLine());
    }
    br.close();
    int tmp, res;
    res = Integer.MAX_VALUE;
    for (int i = 1; i < 100 - 17; i++) {
      tmp = 0;
      for (int j = 0; j < N; j++) {
        tmp += compare(height[j], i);
      }
      res = Math.min(res, tmp);
    }
    System.out.println(res);
  }

  static int compare(int n, int s) {
    if (s <= n && n <= s + 17) {
      return 0;
    } else if (n < s) {
      return (s - n) * (s - n);
    } else {
      return (n - (s + 17)) * (n - (s + 17));
    }
  }
}
