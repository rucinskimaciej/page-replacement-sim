package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;
import sop.pageReplacement.simulator.optimal.Optimal;
import sop.pageReplacement.simulator.secondChance.SecondChance;

class simulatorTest {

    private final static int[] examInput = new int[]{4,2,8,7,4,7,5,4,1,2,8,3,5,6,1,5,4,8,1,8};
    private final static int[] lectureInput = new int[]{1, 2, 3, 4, 5, 3, 1 , 5, 2};
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
        sim = new Optimal(3, examInput);
        sim.execute();
    }

    @Test
    void secondChanceTest() {
        sim = new SecondChance(4, lectureInput);
        sim.execute();
    }
}