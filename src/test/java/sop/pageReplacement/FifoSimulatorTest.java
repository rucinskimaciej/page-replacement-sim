package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement2.common.FIFO;
import sop.pageReplacement2.common.PageReplacementSimulator;

class FifoSimulatorTest {

    @Test
    void fifoTest() {
        int[] input = new int[]{8,5,3,1,8,3,6,3,1,7,1,4,8,7,5,2,4,1,7,4};
        PageReplacementSimulator sim = new FIFO(3, input);
        sim.execute();
        System.out.println("Hello, World");
    }
}