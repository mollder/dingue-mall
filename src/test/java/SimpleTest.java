import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SimpleTest {

    @Test
    public void IntegerTest() {
        Integer a = 100;
        Integer b = 100;

        boolean c = (a == b);

        assertThat(c).isEqualTo(true);
    }
}
