import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static int T, n;
  static int[] preorder, inorder;
  static LinkedList<Integer> backorder;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    T = Integer.parseInt(br.readLine());
    StringBuilder sb = new StringBuilder();
    for (int t = 0; t < T; t++) {
      n = Integer.parseInt(br.readLine());
      preorder = new int[n + 1];
      inorder = new int[n + 1];
      backorder = new LinkedList<>();
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < n; i++) {
        preorder[i] = Integer.parseInt(st.nextToken());
      }
      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < n; i++) {
        inorder[i] = Integer.parseInt(st.nextToken());
      }
      makeBackorder(0, 0, n);
      for (int i = 0; i < n; i++) {
        sb.append(backorder.poll()).append(' ');
      }
      sb.append('\n');
    }
    br.close();
    System.out.print(sb.toString());
  }

  static void makeBackorder(int rootIdx, int s, int e) {
    int root = preorder[rootIdx];
    for (int i = s; i < e; i++) {
      if (inorder[i] == root) {
        makeBackorder(rootIdx + 1, s, i);
        makeBackorder(rootIdx + i + 1 - s, i + 1, e);
        backorder.add(root);
      }
    }
  }
}
