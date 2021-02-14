package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;
import sop.pageReplacement.simulator.optimal.Optimal;
import sop.pageReplacement.simulator.secondChance.SecondChance;

class simulatorTest {

    private final static String examInput =


"                         4)(3)(1)(5)(8)(3)(8)(4)(7)(8)(5)(3)(5)(4)(5)(2)(6)(7)(6)(5                                "


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
        sim = new SecondChance(3, examInput);
        sim.execute();
    }
}