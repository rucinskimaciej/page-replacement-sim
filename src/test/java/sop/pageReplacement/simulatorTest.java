package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;
import sop.pageReplacement.simulator.optimal.Optimal;

class simulatorTest {

    private final static int[] examInput = new int[]{5,1,6,2,7,5,4,8,5,3,6,3,8,1,8,5,2,1,8,6};
    private final static int[] lectureInput = new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
    private PageReplacementSimulator sim;

    @Test
    void fifoTest() {
        sim = new FIFO(3, examInput);
        sim.execute();
    }

    @Test
    void lruTest() {
        sim = new LRU(3, examInput);
        sim.execute();
    }

    @Test
    void optimalTest() {
        sim = new Optimal(4, lectureInput);
        sim.execute();
    }
}