package net.peakgames.libgdx.stagebuilder.core.model;

import java.util.ArrayList;
import java.util.List;

public class GroupModel extends BaseModel {

    private List<BaseModel> children = new ArrayList<BaseModel>();

    public List<BaseModel> getChildren() {
        return children;
    }

    public void setChildren(List<BaseModel> children) {
        this.children = children;
    }
}
