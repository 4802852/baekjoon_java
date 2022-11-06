import java.io.BufferedReader;
import java.io.FileReader;
// import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int start = Integer.parseInt(br.readLine());
    int cnt = 0;
    int res = start;
    do {
      res = (res % 10) * 10 + (res / 10 + res % 10) % 10;
      cnt++;
    } while (res != start);
    System.out.println(cnt);
  }
}
