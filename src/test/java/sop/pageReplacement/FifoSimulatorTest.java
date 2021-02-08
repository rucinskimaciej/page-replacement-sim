package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement2.common.FIFO;
import sop.pageReplacement2.common.PageReplacementSimulator;

class FifoSimulatorTest {

    @Test
    void fifoTest() {
        int[] input = new int[]{4,6,7,4,3,7,6,8,2,7,5,8,4,8,2,3,2,7,1,7};
        PageReplacementSimulator sim = new FIFO(4, input);
        sim.execute();
    }
}