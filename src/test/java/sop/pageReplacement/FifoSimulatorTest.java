package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement2.common.FIFO;
import sop.pageReplacement2.common.PageReplacementSimulator;

class FifoSimulatorTest {

    @Test
    void fifoTest() {
        int[] input = new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        PageReplacementSimulator sim = new FIFO(3, input);
        sim.execute();
    }
}