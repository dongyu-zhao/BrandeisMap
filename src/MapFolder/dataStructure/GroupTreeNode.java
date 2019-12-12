package MapFolder.dataStructure;

import java.util.LinkedList;
import java.util.List;

public class GroupTreeNode {
    private Vertex v;
    private List<List<GroupTreeNode>> children;

    public GroupTreeNode(Vertex v) {
        this.v = v;
        children = new LinkedList<>();
    }

    public void addChild(GroupTreeNode child) {
        for (List<GroupTreeNode> ls : children) {
            if (ls.get(ls.size() - 1).v.findEdge(child.v) != null) {
                ls.add(child);
                return;
            }
        }
        List<GroupTreeNode> newGroup = new LinkedList<>();
        newGroup.add(child);
        children.add(newGroup);
    }

    public Vertex getVertex() {
        return v;
    }

    public List<List<GroupTreeNode>> getChildren() {
        return children;
    }
}
