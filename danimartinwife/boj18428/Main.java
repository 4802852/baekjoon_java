import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static class Node {

    private int r, c;

    public Node(int r, int c) {
      this.r = r;
      this.c = c;
    }

    @Override
    public boolean equals(Object object) {
      Node o = (Node) object;
      if (this.r == o.r && this.c == o.c) {
        return true;
      } else {
        return false;
      }
    }
  }

  static int N;
  static boolean ans;
  static char[][] map;
  static ArrayList<Node> teacher = new ArrayList<>();
  static ArrayList<Node> position = new ArrayList<>();
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    map = new char[N][N];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        map[i][j] = st.nextToken().charAt(0);
        if (map[i][j] == 'T') teacher.add(new Node(i, j));
      }
    }
    br.close();
    setPosition();
    dfs(3, -1);
    if (ans) {
      System.out.println("YES");
    } else {
      System.out.println("NO");
    }
  }

  // 선생님의 시야 안에 있는 위치를 장애물 위치 후보군으로 저장
  static void setPosition() {
    for (int t = 0; t < teacher.size(); t++) {
      Node tmp_teacher = teacher.get(t);
      for (int i = 0; i < 4; i++) {
        for (int j = 1; j < N; j++) {
          int nr = tmp_teacher.r + dr[i] * j;
          int nc = tmp_teacher.c + dc[i] * j;
          if (mapOut(nr, nc) || map[nr][nc] == 'O') break;
          if (map[nr][nc] != 'X') continue;
          if (!position.contains(new Node(nr, nc))) position.add(new Node(nr, nc));
        }
      }
    }
  }

  static boolean mapOut(int r, int c) {
    return (r < 0 || N <= r || c < 0 || N <= c);
  }

  // 현재 선생님의 위치에서 학생이 보일 경우, false 리턴
  static boolean check() {
    for (int t = 0; t < teacher.size(); t++) {
      Node tmp_teacher = teacher.get(t);
      for (int i = 0; i < 4; i++) {
        for (int j = 1; j < N; j++) {
          int nr = tmp_teacher.r + dr[i] * j;
          int nc = tmp_teacher.c + dc[i] * j;
          if (mapOut(nr, nc) || map[nr][nc] == 'O') break;
          if (map[nr][nc] == 'S') return false;
        }
      }
    }
    return true;
  }

  // 장애물을 놓고, 장애물이 3개 전부 위치되었을 경우 학생이 선생님으로부터 무사한지 체크하는 dfs 함수
  static void dfs(int depth, int last) {
    // 이미 정답이 있음이 확인되었다면 탐색을 종료
    if (ans == true) return;
    // 장애물을 3개 다 놓았을 경우, check 하여 학생이 무사할 경우 ans = true
    if (depth == 0) {
      if (check()) ans = true;
      return;
    }
    for (int i = last + 1; i <= position.size() - depth; i++) {
      Node np = position.get(i);
      map[np.r][np.c] = 'O';
      dfs(depth - 1, i);
      map[np.r][np.c] = 'X';
    }
  }
}
