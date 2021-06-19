package com.sh.cloud.utils;

/**
 * Created by jxh on 2018/1/9.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Serializable {
    protected String nodeName;
    //    protected Object obj;
//    protected TreeNode parentNode;
    protected List<TreeNode> data;
    protected List<TreeNode> children;
    private String parentId;
    private String parentIds;
    private String selfId;
    private String title;
    private String value;
    //zTree
    private String iconCls;
    private String text;
    private String state;
    private boolean checked = false;
    private boolean disabled = false;
    private String id;
    private String pId;
    private String name;

    public TreeNode() {
        initChildList();
    }

    public TreeNode(TreeNode parentNode) {
//        this.getParentNode();
        initChildList();
    }

//    public boolean isLeaf() {
//        if (data == null) {
//            return true;
//        } else {
//            if (data.isEmpty()) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }

    /* 插入一个child节点到当前节点中 */
    public void addChildNode(TreeNode treeNode) {
        initChildList();
        data.add(treeNode);
        children.add(treeNode);
    }

    public void initChildList() {
        if (data == null) {
            data = new ArrayList<TreeNode>();
            children = new ArrayList<TreeNode>();
        }
    }

    public boolean isValidTree() {
        return true;
    }

//    /* 返回当前节点的父辈节点集合 */
//    public List<TreeNode> getElders() {
//        List<TreeNode> elderList = new ArrayList<TreeNode>();
//        TreeNode parentNode = this.getParentNode();
//        if (parentNode == null) {
//            return elderList;
//        } else {
//            elderList.add(parentNode);
//            elderList.addAll(parentNode.getElders());
//            return elderList;
//        }
//    }

//    /* 返回当前节点的晚辈集合 */
//    public List<TreeNode> getJuniors() {
//        List<TreeNode> juniorList = new ArrayList<TreeNode>();
//        List<TreeNode> childList = this.getData();
//        if (childList == null) {
//            return juniorList;
//        } else {
//            int childNumber = childList.size();
//            for (int i = 0; i < childNumber; i++) {
//                TreeNode junior = childList.get(i);
//                juniorList.add(junior);
//                juniorList.addAll(junior.getJuniors());
//            }
//            return juniorList;
//        }
//    }

    /* 返回当前节点的孩子集合 */
    public List<TreeNode> getData() {
        return data;
    }

//    /* 删除节点和它下面的晚辈 */
//    public void deleteNode() {
//        TreeNode parentNode = this.getParentNode();
//        String id = this.getSelfId();
//
//        if (parentNode != null) {
//            parentNode.deleteChildNode(id);
//        }
//    }

    public void setData(List<TreeNode> childList) {
        this.data = childList;
    }

    /* 删除当前节点的某个子节点 */
    public void deleteChildNode(String childId) {
        List<TreeNode> childList = this.getData();
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            if (child.getId() == childId) {
                childList.remove(i);
                return;
            }
        }
    }

    /* 动态的插入一个新的节点到当前树中 */
    public boolean insertJuniorNode(TreeNode treeNode) {
        String juniorParentId = treeNode.getParentId();
        if (this.parentId == juniorParentId) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> childList = this.getData();
            int childNumber = childList.size();
            boolean insertFlag;

            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertJuniorNode(treeNode);
                if (insertFlag == true)
                    return true;
            }
            return false;
        }
    }

    /* 找到一颗树中某个节点 */
    public TreeNode findTreeNodeById(String id) {
        if (this.id == id)
            return this;
        if (data.isEmpty() || data == null) {
            return null;
        } else {
            int childNumber = data.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = data.get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }

    /* 遍历一棵树，层次遍历 */
    public void traverse() {
//        if (selfId < 0)
//            return;
//        print(this.selfId);
        if (data == null || data.isEmpty())
            return;
        int childNumber = data.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = data.get(i);
            child.traverse();
        }
    }

    public void print(String content) {
        System.out.println(content);
    }

    public void print(int content) {
        System.out.println(content);
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

//    public TreeNode getParentNode() {
//        return parentNode;
//    }
//
//    public void setParentNode(TreeNode parentNode) {
//        this.parentNode = parentNode;
//    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

//    public Object getObj() {
//        return obj;
//    }
//
//    public void setObj(Object obj) {
//        this.obj = obj;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
