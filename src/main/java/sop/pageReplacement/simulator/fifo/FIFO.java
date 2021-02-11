package sop.pageReplacement.simulator.fifo;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.simulator.base.SimulatorBase;

import java.util.*;

public class FIFO extends SimulatorBase<Frame> {

    public FIFO(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public FIFO(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }

    @Override
    protected Frame newFrame(int i) {
        return new Frame(i);
    }

    @Override
    protected void replaceFrameWithAlgorithm() {
        int fifo = fifoIndex();
        replacePageInFrameAndRepeatOther(fifo);
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
        List<String> history = frame.getHistory();
        int repeats = 0;
        for (int i = history.size() - 1; i >= 0; i--) {
            String historicalValueString = history.get(i);
            Integer value = historicalValueString == null ?
                    null : Integer.parseInt(historicalValueString.split(" ")[0]);
            if (cur == null || cur.equals(value)) repeats++;
        }
        return repeats;
    }


}
