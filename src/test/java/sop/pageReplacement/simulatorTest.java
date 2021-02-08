package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;

class simulatorTest {

    private final static int[] input = new int[]{5,1,6,2,7,5,4,8,5,3,6,3,8,1,8,5,2,1,8,6};
    private PageReplacementSimulator sim;

    @Test
    void fifoTest() {
        sim = new FIFO(3, input);
        sim.execute();
    }

    @Test
    void lruTest() {
        sim = new LRU(3, input);
        sim.execute();
    }
}