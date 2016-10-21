package fastaTool;

import main.FastaTool;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by janulrich on 20.10.16.
 */
public class FastaToolTest {

    @Test
    public void test() throws IOException {
        String[] params = {"/home/janulrich/Sync/Masterstudium/6.Semester/JavaForBioinformatics/Übungen/1/data01.fna"};
        FastaTool.main(params);
    }
}