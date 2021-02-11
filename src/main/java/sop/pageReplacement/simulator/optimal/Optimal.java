package sop.pageReplacement.simulator.optimal;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.simulator.base.SimulatorBase;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Optimal extends SimulatorBase {

    public Optimal(int numberOfFrames, int[] pages) {
        super(numberOfFrames, pages);
    }

    public Optimal(int numberOfFrames, String pages) {
        super(numberOfFrames, pages);
    }

    @Override
    protected boolean replaceFrameWithAlgorithm() {
        int frameIndex = findFrameIndexToReplace();
        return replacePageInFrameAndRepeatOther(frameIndex);
    }

    private int findFrameIndexToReplace() {
        Map<Integer, Integer> mapFurthestUse = mapFurthestUse();
        return mapFurthestUse.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    private Map<Integer, Integer> mapFurthestUse() {
        Map<Integer, Integer> map = new HashMap<>(frames.size());
        for (Frame frame : frames) {
            map.put(frame.getIndex(), furthestUseOfFrame(frame.getIndex()));
        }
        return map;
    }

    private Integer furthestUseOfFrame(int frameIndex) {
        int page = frames.stream().filter(frame -> frame.getIndex() == frameIndex).iterator().next().getPage();
        return getIndexOfNextUse(page);
    }

    /**
     * Returns index of next occurrence equal to 'page'
     * Returns 'Integer.MAX_VALUE' if no next occurrence found
    * */
    private int getIndexOfNextUse(int page) {
        for (int i = currentIndex + 1; i < pages.length; i++) {
            if (pages[i] == page) return i;
        }
        return Integer.MAX_VALUE;
    }


}
