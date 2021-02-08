package sop.pageReplacement.simulator.lru;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.simulator.base.SimulatorBase;

import java.util.Comparator;

public class LRU extends SimulatorBase {

    public LRU(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public LRU(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }

    @Override
    protected boolean replaceFrameWithAlgorithm() {
        Frame lru = frames.stream().max(Comparator.comparingInt(Frame::getRecentlyUsed)).orElseThrow();
        int lruIndex = lru.getIndex();
        return replacePageInFrameAndRepeatOther(lruIndex);
    }
}
