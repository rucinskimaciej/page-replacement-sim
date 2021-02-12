package sop.pageReplacement.simulator.fifo;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.simulator.base.SimulatorBase;

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
        int fifo = getFifoFrame().getIndex();
        replacePageInFrameAndRepeatOther(fifo);
    }

    private Frame getFifoFrame() {
        return orderFifo().poll();
    }
}
