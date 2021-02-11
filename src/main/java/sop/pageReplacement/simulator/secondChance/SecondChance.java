package sop.pageReplacement.simulator.secondChance;

import sop.pageReplacement.common.SecondChanceFrame;
import sop.pageReplacement.simulator.base.SimulatorBase;

import java.util.*;
import java.util.stream.Collectors;

public class SecondChance extends SimulatorBase<SecondChanceFrame> {


    public SecondChance(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public SecondChance(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }


    @Override
    protected Set<SecondChanceFrame> insert(int numberOfFrames) {
        Set<SecondChanceFrame> frames = new LinkedHashSet<>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(new SecondChanceFrame(i));
        }
        return frames;
    }

    @Override
    protected SecondChanceFrame newFrame(int i) {
        return new SecondChanceFrame(i);
    }

    @Override
    public void execute() {
        List<Integer> previous = frames.stream().map(SecondChanceFrame::getPage).collect(Collectors.toList());
        super.execute();
        for (SecondChanceFrame frame : frames) {
            Integer prevPage = previous.get(frame.getIndex());
            if (frame.getPage().equals(prevPage)) frame.resetSecondChance();
        }
    }

    @Override
    protected void replaceFrameWithAlgorithm() {
        int fifoIndex = getFifoFrame().getIndex();
        replacePageInFrameAndRepeatOther(fifoIndex);
    }

    private SecondChanceFrame getFifoFrame() {
        Map<SecondChanceFrame, Integer> lastPageRepeats = new HashMap<>();
        frames.forEach(frame -> lastPageRepeats.put(frame,frame.getTimeInMemory()));
        Set<SecondChanceFrame> fifoOrdered = orderFifo(lastPageRepeats);
        for (SecondChanceFrame frame : fifoOrdered) {
            try {
                frame.useSecondChance();
            } catch (Exception e) {
                return frame;
            }
        }
        return fifoOrdered.iterator().next();
    }

    @Override
    protected void printRow(String s) {
        printRow(s, 9);
    }
}
