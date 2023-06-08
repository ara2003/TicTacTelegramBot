import com.greentree.example.telegram.Game;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void test1() {
        var t = new Integer[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        var d = Game.diagonal(t);
        System.out.println(d);
    }

}
