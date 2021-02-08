package sop.pageReplacement2.common;

import java.util.*;

public class FIFO extends SimulatorBase {


    public FIFO(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public FIFO(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }

    @Override
    protected boolean replaceFrameWithAlgorithm() {
        int fifo = fifoIndex();
        return replacePageInFrameAndRepeatOther(fifo);
    }

    private int fifoIndex() {
        Map<Frame, Integer> lastPageRepeats = new HashMap<>();
        frames.forEach(frame -> lastPageRepeats.put(frame,getLastPageRepeatsIn(frame)));
        int max = lastPageRepeats.entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElseThrow().getValue();
        Frame fifoFrame = lastPageRepeats.entrySet().stream()
                .filter(e -> Integer.valueOf(max).equals(e.getValue()))
                .min(Comparator.comparingInt(e -> e.getKey().getIndex()))
                .orElseThrow()
                .getKey();
        return fifoFrame.getIndex();
    }

    private Integer getLastPageRepeatsIn(Frame frame) {
        Integer cur = frame.getPage();
        List<Integer> history = frame.getHistory();
        int repeats = 0;
        for (int i = history.size() - 1; i >= 0; i--) {
            if (cur == null || cur.equals(history.get(i))) repeats++;
        }
        return repeats;
    }


}