import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.croc.stakeholder.QuadrantSeparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuadrantSeparatorTest {
    @Test
    public void testMostImportant() {
        List<String> expected = List.of("1", "4");
        List<String> actual = new ArrayList<>(QuadrantSeparator.getMostImportant(List.of(
                new QuadrantSeparator.Point("1", 4, 4),
                new QuadrantSeparator.Point("2", 3.5, 1),
                new QuadrantSeparator.Point("3", 1, 1),
                new QuadrantSeparator.Point("4", 3, 2.5),
                new QuadrantSeparator.Point("5", 1.5, 5)
        )));
        Collections.sort(actual);
        Assertions.assertEquals(expected, actual);
    }
}
