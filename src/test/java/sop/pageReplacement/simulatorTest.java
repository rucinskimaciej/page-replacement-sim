package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;
import sop.pageReplacement.simulator.optimal.Optimal;
import sop.pageReplacement.simulator.secondChance.SecondChance;

class simulatorTest {

    private final static String examInput =


"                         2)(5)(4)(3)(6)(5)(4)(3)(6)(8)(4)(3)(2)(1)(2)(6)(5)(2)(1)(8                                "


                    .replace(")(", " ").replace(",", " ").trim();
    private final static int[] lectureInput = new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
    private PageReplacementSimulator sim;

    @Test
    void fifoTest() {
        sim = new FIFO(3, examInput);
        sim.execute();
    }

    @Test
    void lruTest() {
        sim = new LRU(4, examInput);
        sim.execute();
    }

    @Test
    void optimalTest() {
        sim = new Optimal(4, examInput);
        sim.execute();
    }

    @Test
    void secondChanceTest() {
        sim = new SecondChance(4, examInput);
        sim.execute();
    }
}