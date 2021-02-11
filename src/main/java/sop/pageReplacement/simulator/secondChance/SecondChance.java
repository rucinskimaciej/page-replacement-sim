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
        int fifoIndex = -1;

        for (SecondChanceFrame fifoOrderedFrame : getFifoOrderedFrames()) {
            if (fifoOrderedFrame.hasSecondChance()) {
                try {
                    fifoOrderedFrame.useSecondChance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                fifoIndex = fifoOrderedFrame.getIndex();
                break;
            }
        }

        fifoIndex = fifoIndex >= 0 ? fifoIndex : getFifoFrame(frames).getIndex();

        replacePageInFrameAndRepeatOther(fifoIndex);
    }

    private Set<SecondChanceFrame> getFifoOrderedFrames() {
        Set<SecondChanceFrame> backup = new LinkedHashSet<>(frames);
        Set<SecondChanceFrame> ordered = new LinkedHashSet<>();

        while (!backup.isEmpty()) {
            SecondChanceFrame fifo = getFifoFrame(backup);
            backup.remove(fifo);
            ordered.add(fifo);
        }
        return ordered;
    }

    private SecondChanceFrame getFifoFrame(Set<SecondChanceFrame> frames) {
        Map<SecondChanceFrame, Integer> lastPageRepeats = new HashMap<>();
        frames.forEach(frame -> lastPageRepeats.put(frame,getLastPageRepeatsIn(frame)));
        int max = lastPageRepeats.entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElseThrow().getValue();
        return lastPageRepeats.entrySet().stream()
                .filter(e -> Integer.valueOf(max).equals(e.getValue()))
                .min(Comparator.comparingInt(e -> e.getKey().getIndex()))
                .orElseThrow()
                .getKey();
    }

    private Integer getLastPageRepeatsIn(SecondChanceFrame frame) {
        Integer cur = frame.getPage();
        List<Integer> history = frame.getHistory();
        int repeats = 0;
        for (int i = history.size() - 1; i >= 0; i--) {
            if (cur == null || cur.equals(history.get(i))) repeats++;
        }
        return repeats;
    }
}
