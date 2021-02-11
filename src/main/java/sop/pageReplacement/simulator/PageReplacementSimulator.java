package sop.pageReplacement.simulator;

import sop.pageReplacement.common.Frame;

import java.util.List;
import java.util.Set;

public interface PageReplacementSimulator {

    void execute();
    List<Boolean> getHits();
}
