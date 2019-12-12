package MapFolder.dataStructure;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    private int val;
    private List<TreeNode> children;

    public TreeNode(int val) {
        this.val = val;
        children = new LinkedList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public int getVal() {
        return val;
    }

    public List<TreeNode> getChildren() {
        return children;
    }
}
