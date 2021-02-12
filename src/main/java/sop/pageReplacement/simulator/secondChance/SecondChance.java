package sop.pageReplacement.simulator.secondChance;

import sop.pageReplacement.common.Frame;
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

    private SecondChanceFrame getFifo() {
        Queue<SecondChanceFrame> fifoQueue = orderFifo();
        SecondChanceFrame fifo = null;
        do {
            if (fifo != null) fifoQueue.offer(fifo);
            fifo = fifoQueue.poll();
        } while (fifo != null && fifo.hasSecondChance());
        return fifo;
    }

    private SecondChanceFrame getFifoFrame() {
        Map<SecondChanceFrame, Integer> lastPageRepeats = new HashMap<>();
        frames.forEach(frame -> lastPageRepeats.put(frame,frame.getTimeInMemory()));
        Queue<SecondChanceFrame> fifoOrdered = orderFifo();
        SecondChanceFrame scFrame = null;
        do {
            if (scFrame != null) {
                try {
                    scFrame.useSecondChance();
                } catch (Exception e) {
                    return scFrame;
                }
                fifoOrdered.offer(scFrame);
            }
            scFrame = fifoOrdered.poll();
        } while (scFrame != null && scFrame.hasSecondChance());

//        for (SecondChanceFrame frame : fifoOrdered) {
//            try {
//                frame.useSecondChance();
//            } catch (Exception e) {
//                return frame;
//            }
//        }
        return scFrame;
    }

    @Override
    protected void printRow(String s) {
        printRow(s, 9);
    }
}
