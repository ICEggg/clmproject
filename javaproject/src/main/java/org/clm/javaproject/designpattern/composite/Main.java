package org.clm.javaproject.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式，树状结构专用模式
 */
abstract class Node{
    abstract public void p();
}

class LeafNode extends Node{
    private String content;

    public LeafNode(String content) {
        this.content = content;
    }

    @Override
    public void p() {
        System.out.println("这是叶子节点:"+content);
    }
}

class BranchNode extends Node{
    public List<Node> list = new ArrayList<>();
    private String content;

    public BranchNode(String content) {
        this.content = content;
    }

    @Override
    public void p() {
        System.out.println("这是树枝节点:"+content);
    }

    public void add(Node n){
        list.add(n);
    }
}

public class Main {
    public static void main(String[] args) {
        BranchNode root = new BranchNode("root");
        BranchNode branchNode = new BranchNode("分类1");
        BranchNode branchNode2 = new BranchNode("分类2");
        LeafNode leafnode1 = new LeafNode("分类1-1");
        LeafNode leafnode2 = new LeafNode("分类1-2");
        LeafNode leafnode3 = new LeafNode("分类2-1");

        root.add(branchNode);
        root.add(branchNode2);

        branchNode.add(leafnode1);
        branchNode.add(leafnode2);
        branchNode2.add(leafnode3);

        tree(root,0);
    }

    static void tree(Node b,int depth){
        for (int i = 0; i < depth; i++) System.out.print("--");
        b.p();
        if(b instanceof BranchNode){
            for (Node n : ((BranchNode) b).list) {
                tree(n,depth+1);
            }
        }
    }

}


