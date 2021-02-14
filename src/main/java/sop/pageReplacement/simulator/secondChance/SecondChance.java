package sop.pageReplacement.simulator.secondChance;

import sop.pageReplacement.common.SecondChanceFrame;
import sop.pageReplacement.simulator.base.SimulatorBase;

public class SecondChance extends SimulatorBase<SecondChanceFrame> {

    public SecondChance(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public SecondChance(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }

    @Override
    protected SecondChanceFrame newFrame(int i) {
        return new SecondChanceFrame(i);
    }

    @Override
    protected void replaceFrameWithAlgorithm() {
        int fifoIndex = getFrameToReplace().getIndex();
        replacePageInFrameAndRepeatOther(fifoIndex);
    }

    private SecondChanceFrame getFrameToReplace() {

        if (fifo.isEmpty()) throw new NullPointerException("Queue is empty - that should not happen");
        SecondChanceFrame victim = null;
        try {
            while (true) {
                victim = fifo.poll();
                fifo.offer(victim);
                if (victim != null) victim.useSecondChance();
            }
        } catch (Exception e) {
            if (victim != null) return victim;
            else throw new NullPointerException("victim is null - wtf?");
        }
    }

    @Override
    public String getName() {
        return "DRUGIEJ SZANSY";
    }

// todo IF DEBUGGING - this will justify the output
//    @Override
//    protected void printRow(String s) {
//        printRow(s, 9);
//    }
}
