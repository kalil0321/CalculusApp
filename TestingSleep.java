import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestingSleep {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 10; i++) {
            Thread.sleep(1_000);
            System.out.println(i);
        }

    }

}
