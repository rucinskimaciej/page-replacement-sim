package sop.pageReplacement2.common;

import java.util.List;
import java.util.Set;

public interface PageReplacementSimulator {

    void execute();
    List<Boolean> getHits();
    Set<Frame> getFrames();
}
