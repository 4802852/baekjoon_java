import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.util.*;

public class Main {

  static int N, ans;
  static char[] input;
  static int[][] dp_min, dp_max;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    input = br.readLine().toCharArray();
    br.close();

    dp_min = new int[N][N];
    dp_max = new int[N][N];
    for (int i = 0; i < N; i++) {
      Arrays.fill(dp_min[i], Integer.MAX_VALUE);
      Arrays.fill(dp_max[i], Integer.MIN_VALUE);
    }
    for (int i = 0; i < N; i += 2) {
      dp_max[i][i] = input[i] - '0';
      dp_min[i][i] = input[i] - '0';
    }

    for (int j = 2; j < N; j += 2) {
      for (int i = 0; i < N - j; i += 2) {
        for (int k = 2; k <= j; k += 2) {
          char op = input[i + k - 1];
          int[] tmp = new int[4];
          tmp[0] = cal(dp_max[i][i + k - 2], dp_max[i + k][i + j], op);
          tmp[1] = cal(dp_max[i][i + k - 2], dp_min[i + k][i + j], op);
          tmp[2] = cal(dp_min[i][i + k - 2], dp_max[i + k][i + j], op);
          tmp[3] = cal(dp_min[i][i + k - 2], dp_min[i + k][i + j], op);
          Arrays.sort(tmp);
          dp_max[i][i + j] = Math.max(dp_max[i][i + j], tmp[3]);
          dp_min[i][i + j] = Math.min(dp_min[i][i + j], tmp[0]);
        }
      }
    }

    System.out.println(dp_max[0][N - 1]);
  }

  static int cal(int a, int b, char operator) {
    if (operator == '+') {
      return a + b;
    } else if (operator == '-') {
      return a - b;
    } else {
      return a * b;
    }
  }
}
