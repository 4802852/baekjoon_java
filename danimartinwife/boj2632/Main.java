import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int T, res;
  static int m, n;
  static int[] A, B;
  static boolean[] check;
  static ArrayList<Integer> AList = new ArrayList<>();
  static ArrayList<Integer> BList = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    T = Integer.parseInt(br.readLine());
    String[] mn = br.readLine().split(" ");
    m = Integer.parseInt(mn[0]);
    n = Integer.parseInt(mn[1]);
    A = new int[m];
    B = new int[n];
    for (int i = 0; i < m; i++) {
      A[i] = Integer.parseInt(br.readLine());
    }
    for (int i = 0; i < n; i++) {
      B[i] = Integer.parseInt(br.readLine());
    }
    br.close();
    for (int i = 0; i < m; i++) {
      check = new boolean[m];
      check[i] = true;
      getSum(A[i], i, i + 1, check, A, AList);
    }
    for (int i = 0; i < n; i++) {
      check = new boolean[n];
      check[i] = true;
      getSum(B[i], i, i + 1, check, B, BList);
    }
    AList.add(0);
    BList.add(0);
    Collections.sort(AList);
    Collections.sort(BList);

    int leftIdx = 0;
    int rightIdx = BList.size() - 1;
    while (leftIdx < AList.size() && 0 <= rightIdx) {
      int lv = AList.get(leftIdx);
      int rv = BList.get(rightIdx);
      if (lv + rv == T) {
        int lc = 0, rc = 0;
        while (leftIdx < AList.size() && AList.get(leftIdx) == lv) {
          lc++;
          leftIdx++;
        }
        while (0 <= rightIdx && BList.get(rightIdx) == rv) {
          rc++;
          rightIdx--;
        }
        res += lc * rc;
      }
      if (lv + rv > T) rightIdx--;
      if (lv + rv < T) leftIdx++;
    }
    System.out.println(res);
  }

  static void getSum(int sum, int sIdx, int eIdx, boolean[] check, int[] arr, List<Integer> list) {
    if (eIdx == arr.length) eIdx = 0;
    list.add(sum);

    if (check[eIdx] == false && sum <= T && eIdx != sIdx - 1) {
      check[eIdx] = true;
      getSum(sum + arr[eIdx], sIdx, eIdx + 1, check, arr, list);
    }
    return;
  }
}
