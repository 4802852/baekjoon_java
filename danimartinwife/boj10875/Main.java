import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  static long L, ans;
  static long cx, cy;
  static int cd;
  static int N;
  static ArrayList<Line> al = new ArrayList<>();
  static int[] dx = { 1, 0, -1, 0 };
  static int[] dy = { 0, -1, 0, 1 };

  static class Line {

    private long x1, y1, x2, y2;
    private String dir = "";

    public Line(long x1, long y1, long x2, long y2) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      if (x1 == x2) {
        dir = "vertical";
      } else {
        dir = "horizontal";
      }
      lineSort();
    }

    // Line의 비교를 용이하게 할 수 있도록 x1, y1 이 x2, y2 보다 작도록 저장한다.
    public void lineSort() {
      if (this.x2 < this.x1) {
        long tmp = this.x2;
        this.x2 = this.x1;
        this.x1 = tmp;
      }
      if (this.y2 < this.y1) {
        long tmp = this.y2;
        this.y2 = this.y1;
        this.y1 = tmp;
      }
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    L = Integer.parseInt(br.readLine());
    N = Integer.parseInt(br.readLine());
    cx = cy = 0;
    al.add(new Line(-L - 1, L + 1, L + 1, L + 1));
    al.add(new Line(-L - 1, -L - 1, L + 1, -L - 1));
    al.add(new Line(-L - 1, -L - 1, -L - 1, L + 1));
    al.add(new Line(L + 1, -L - 1, L + 1, L + 1));
    long count;
    String dir;
    for (int t = 0; t <= N; t++) {
      if (t == N) {
        count = Long.MAX_VALUE;
        dir = "L";
      } else {
        StringTokenizer st = new StringTokenizer(br.readLine());
        count = Long.parseLong(st.nextToken());
        dir = st.nextToken();
      }
      long m = Long.MAX_VALUE;
      for (int i = 0; i < al.size(); i++) {
        if (al.get(i).dir.equals("horizontal")) {
          // al.get(i) 선분이 수평선일 경우
          if (cd == 0) {
            if (cy == al.get(i).y1 && cx < al.get(i).x1) {
              m = Math.min(m, al.get(i).x1 - cx);
            }
          } else if (cd == 1) {
            if (al.get(i).x1 <= cx && cx <= al.get(i).x2 && al.get(i).y1 < cy) {
              m = Math.min(m, cy - al.get(i).y1);
            }
          } else if (cd == 2) {
            if (cy == al.get(i).y1 && al.get(i).x2 < cx) {
              m = Math.min(m, cx - al.get(i).x2);
            }
          } else if (cd == 3) {
            if (al.get(i).x1 <= cx && cx <= al.get(i).x2 && cy < al.get(i).y1) {
              m = Math.min(m, al.get(i).y2 - cy);
            }
          }
        } else {
          // al.get(i) 선분이 수직선일 경우
          if (cd == 0) {
            if (al.get(i).y1 <= cy && cy <= al.get(i).y2 && cx < al.get(i).x1) {
              m = Math.min(m, al.get(i).x1 - cx);
            }
          } else if (cd == 1) {
            if (cx == al.get(i).x1 && al.get(i).y2 < cy) {
              m = Math.min(m, cy - al.get(i).y2);
            }
          } else if (cd == 2) {
            if (al.get(i).y1 <= cy && cy <= al.get(i).y2 && al.get(i).x1 < cx) {
              m = Math.min(m, cx - al.get(i).x1);
            }
          } else if (cd == 3) {
            if (cx == al.get(i).x1 && cy < al.get(i).y1) {
              m = Math.min(m, al.get(i).y1 - cy);
            }
          }
        }
      }
      if (count < m) {
        // 입력으로 주어진 진행 거리가 진행가능한 거리 m 보다 작을 경우, 뱀은 해당 방향으로 진행이 가능함
        al.add(new Line(cx, cy, cx + dx[cd] * count, cy + dy[cd] * count));
        cx += dx[cd] * count;
        cy += dy[cd] * count;
        ans += count;
        if (dir.equals("L")) {
          cd--;
          if (cd == -1) cd += 4;
        } else {
          cd++;
          cd %= 4;
        }
      } else {
        // 입력으로 주어진 거리를 진행할 경우 몸통을 만나거나 맵 바깥으로 나갈 경우,
        // 가능한 최대 거리를 더해주고 탐색을 중단한다.
        ans += m;
        break;
      }
    }
    br.close();
    System.out.println(ans);
  }
}
