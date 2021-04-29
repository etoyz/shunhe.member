package com.sh.cloud.utils;

/**
 * Created by jxh on 2018/1/9.
 */


import com.dubbo.user.bean.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TreeHelper {

    private TreeNode root;
    private List<TreeNode> tempNodeList;
    private boolean isValidTree = true;
    private HashMap nodeMap;

    public TreeHelper() {
    }

    public TreeHelper(List<TreeNode> treeNodeList) {
        tempNodeList = treeNodeList;
        generateTree();
    }

    public static TreeNode getTreeNodeById(TreeNode tree, String id) {
        if (tree == null)
            return null;
        TreeNode treeNode = tree.findTreeNodeById(id);
        return treeNode;
    }

    /**
     * generate a tree from the given treeNode or entity list
     */
    public void generateTree() {
        nodeMap = putNodesIntoMap();
        putChildIntoParent(nodeMap);
    }

    /**
     * put all the treeNodes into a hash table by its id as the key
     *
     * @return hashmap that contains the treenodes
     */
    protected HashMap putNodesIntoMap() {
        int maxId = Integer.MAX_VALUE;
        HashMap nodeMap = new HashMap<String, TreeNode>();
        Iterator it = tempNodeList.iterator();
        while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            String id = treeNode.getId();
//            String id = treeNode.getSelfId();
//            if (id < maxId) {
//                maxId = id;
//                this.root = treeNode;
//            }
            String keyId = String.valueOf(id);

            nodeMap.put(keyId, treeNode);
            // System.out.println("keyId: " +keyId);
        }
        return nodeMap;
    }

    /**
     * set the parent nodes point to the child nodes
     *
     * @param nodeMap a hashmap that contains all the treenodes by its id as the key
     */
    protected void putChildIntoParent(HashMap nodeMap) {
        Iterator it = nodeMap.values().iterator();
        while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            String parentId = treeNode.getParentId();
            String parentKeyId = String.valueOf(parentId);
            if (nodeMap.containsKey(parentKeyId)) {
                TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
                if (parentNode == null) {
                    this.isValidTree = false;
                    return;
                } else {
                    parentNode.addChildNode(treeNode);
                    // System.out.println("childId: " +treeNode.getSelfId()+" parentId: "+parentNode.getSelfId());
                }
            }
        }
    }

    /**
     * initialize the tempNodeList property
     */
    protected void initTempNodeList() {
        if (this.tempNodeList == null) {
            this.tempNodeList = new ArrayList<TreeNode>();
        }
    }

    /**
     * add a tree node to the tempNodeList
     */
    public void addTreeNode(TreeNode treeNode) {
        initTempNodeList();
        this.tempNodeList.add(treeNode);
    }

    /**
     * insert a tree node to the tree generated already
     *
     * @return show the insert operation is ok or not
     */
    public boolean insertTreeNode(TreeNode treeNode) {
        boolean insertFlag = root.insertJuniorNode(treeNode);
        return insertFlag;
    }

    /**
     * adapt the entities to the corresponding treeNode
     *
     * @param entityList list that contains the entities
     * @return the list containg the corresponding treeNodes of the entities
     */
    public static List<TreeNode> changeEnititiesToTreeNodes(List entityList) {
//        OrganizationEntity orgEntity = new OrganizationEntity();
        Permission permission = new Permission();
        List<TreeNode> tempNodeList = new ArrayList<TreeNode>();
        TreeNode treeNode;

        Iterator it = entityList.iterator();
        while (it.hasNext()) {
//            orgEntity = (OrganizationEntity) it.next();
//            treeNode = new TreeNode();
//            treeNode.setObj(orgEntity);
//            treeNode.setParentId(orgEntity.getParentId());
//            treeNode.setSelfId(orgEntity.getOrgId());
//            treeNode.setNodeName(orgEntity.getOrgName());
            permission = (Permission) it.next();
            treeNode = new TreeNode();
//            treeNode.setObj(groupInfo);
            treeNode.setParentId(permission.getPid());
            treeNode.setpId(permission.getPid());
            //treeNode.setParentIds(groupInfo.getParentIds());
            treeNode.setId(permission.get_id());
//            treeNode.setId(groupInfo.getId());
            treeNode.setTitle(permission.getName());
            treeNode.setName(permission.getName());
            treeNode.setValue(permission.get_id());
            treeNode.setState("closed");
            tempNodeList.add(treeNode);
        }
        return tempNodeList;
    }

    public boolean isValidTree() {
        return this.isValidTree;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public List<TreeNode> getTempNodeList() {
        return tempNodeList;
    }

    public void setTempNodeList(List<TreeNode> tempNodeList) {
        this.tempNodeList = tempNodeList;
    }

    public HashMap getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(HashMap nodeMap) {
        this.nodeMap = nodeMap;
    }

    public static void main(String[] args) {
//        List<OrganizationEntity> list = new ArrayList<OrganizationEntity>();
//        OrganizationEntity organizationEntity = new OrganizationEntity(1,0,"name1");
//        list.add(organizationEntity);
//        OrganizationEntity organizationEntity2 = new OrganizationEntity(2,1,"name2");
//        list.add(organizationEntity2);
//        OrganizationEntity organizationEntity3 = new OrganizationEntity(3,2,"name3");
//        list.add(organizationEntity3);
//        OrganizationEntity organizationEntity4 = new OrganizationEntity(4,2,"name4");
//        list.add(organizationEntity4);
//        OrganizationEntity organizationEntity5 = new OrganizationEntity(6,5,"name5");
//        list.add(organizationEntity5);
//        OrganizationEntity organizationEntity6 = new OrganizationEntity(7,6,"name6");
//        list.add(organizationEntity6);
//        List<TreeNode> lt = new TreeHelper().changeEnititiesToTreeNodes(list);
//        TreeHelper helper = new TreeHelper(lt);
//        System.out.println(helper.getTempNodeList().size());
    }
}
