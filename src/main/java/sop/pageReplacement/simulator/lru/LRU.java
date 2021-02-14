package sop.pageReplacement.simulator.lru;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.simulator.base.SimulatorBase;

import java.util.Comparator;

public class LRU extends SimulatorBase<Frame> {

    public LRU(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public LRU(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }

    @Override
    protected Frame newFrame(int i) {
        return new Frame(i);
    }

    @Override
    protected void replaceFrameWithAlgorithm() {
        Frame lru = frames.stream().max(Comparator.comparingInt(Frame::getRecentlyUsed)).orElseThrow();
        int lruIndex = lru.getIndex();
        replacePageInFrameAndRepeatOther(lruIndex);
    }

    @Override
    public String getName() {
        return "LRU";
    }
}
