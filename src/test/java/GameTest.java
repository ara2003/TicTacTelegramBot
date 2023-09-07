import com.greentree.example.telegram.Game;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    void test1() {
        var t = new Integer[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        var d = Game.diagonal(t);
        assertEquals(d, Set.of(List.of(1), List.of(4, 2), List.of(7, 5, 3), List.of(8, 6), List.of(9), List.of(7), List.of(4, 8), List.of(1, 5, 9), List.of(2, 6), List.of(3)));
    }

}
