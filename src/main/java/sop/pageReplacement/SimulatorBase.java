package sop.pageReplacement;

import sop.pageReplacement.common.Frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class SimulatorBase implements PageReplacementSimulator {

    protected final List<Frame> frameList;
    protected final int[] pages;
    protected final List<Boolean> missing;

    public SimulatorBase(int numberOfFrames, int[] pages) {
        this.frameList = new ArrayList<>(numberOfFrames);
        this.pages = pages;
        this.missing = new LinkedList<>();
    }

    public SimulatorBase(int numberOfFrames, String pages) {
        this.frameList = new ArrayList<>(numberOfFrames);
        this.pages = mapToIntArr(pages);
        this.missing = new LinkedList<>();
    }

    private int[] mapToIntArr(String pages) {
        return Arrays.stream(pages.split(" "))
                .map(Integer::parseInt)
                .mapToInt(x -> x)
                .toArray();
    }

    public int countMissing() {
        return (int) missing.stream().filter(missing -> missing).count();
    }

}
