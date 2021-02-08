package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;

class simulatorTest {

    private final static int[] input = new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
    private PageReplacementSimulator sim;

    @Test
    void fifoTest() {
        sim = new FIFO(3, input);
        sim.execute();
    }

    @Test
    void lruTest() {
        sim = new LRU(4, input);
        sim.execute();
    }
}