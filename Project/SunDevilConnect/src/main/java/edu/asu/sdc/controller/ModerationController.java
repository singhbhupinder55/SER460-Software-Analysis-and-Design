package edu.asu.sdc.controller;

import edu.asu.sdc.model.FlaggedContent;

import java.util.ArrayList;
import java.util.List;

public class ModerationController {
    private List<FlaggedContent> flaggedContents;

    public ModerationController() {
        this.flaggedContents = new ArrayList<>();
    }

    public void addFlaggedContent(FlaggedContent flaggedContent) {
        flaggedContents.add(flaggedContent);
    }

    public List<FlaggedContent> reviewFlaggedContent() {
        return flaggedContents;
    }

    public boolean resolveFlaggedContent(String flagId) {
        for (FlaggedContent flaggedContent : flaggedContents) {
            if (flaggedContent.getFlagId().equalsIgnoreCase(flagId)
                    && flaggedContent.getStatus().equalsIgnoreCase("Open")) {
                flaggedContent.resolve();
                return true;
            }
        }
        return false;
    }

    public boolean dismissFlaggedContent(String flagId) {
        for (FlaggedContent flaggedContent : flaggedContents) {
            if (flaggedContent.getFlagId().equalsIgnoreCase(flagId)
                    && flaggedContent.getStatus().equalsIgnoreCase("Open")) {
                flaggedContent.dismiss();
                return true;
            }
        }
        return false;
    }
}