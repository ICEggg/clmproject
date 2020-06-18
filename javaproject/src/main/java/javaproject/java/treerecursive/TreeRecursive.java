package javaproject.java.treerecursive;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class TreeRecursive {
    public static void main(String[] args) {
        List<Menu>  menuList= new ArrayList<Menu>();
        /*插入一些数据*/
        menuList.add(new Menu("GN001D000","0","系统管理","/admin","Y"));
        menuList.add(new Menu("GN001D100","GN001D000","权限管理","/admin","Y"));
        menuList.add(new Menu("GN001D110","GN001D100","密码修改","/admin","Y"));
        menuList.add(new Menu("GN001D120","GN001D100","新加用户","/admin","Y"));
        menuList.add(new Menu("GN001D200","GN001D000","系统监控","/admin","Y"));
        menuList.add(new Menu("GN001D210","GN001D200","在线用户","/admin","Y"));
        menuList.add(new Menu("GN002D000","0","订阅区","/admin","Y"));
        menuList.add(new Menu("GN003D000","0","未知领域","/admin","Y"));
        /*让我们创建树*/
        MenuTree menuTree =new MenuTree(menuList);
        menuList=menuTree.builTree();
        /*转为json看看效果*/
        String jsonOutput= JSON.toJSONString(menuList);
        System.out.println(jsonOutput);
    }




}

class MenuTree {
    private List<Menu> menuList = new ArrayList<Menu>();
    public MenuTree(List<Menu> menuList) {
        this.menuList=menuList;
    }

    //建立树形结构
    public List<Menu> builTree(){
        List<Menu> treeMenus =new  ArrayList<Menu>();
        for(Menu menuNode : getRootNode()) {
            menuNode=buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    //递归，建立子树形结构
    private Menu buildChilTree(Menu pNode){
        List<Menu> chilMenus =new  ArrayList<Menu>();
        for(Menu menuNode : menuList) {
            if(menuNode.getParentId().equals(pNode.getId())) {
                chilMenus.add(buildChilTree(menuNode));
            }
        }
        pNode.setChildren(chilMenus);
        return pNode;
    }

    //获取根节点
    private List<Menu> getRootNode() {
        List<Menu> rootMenuLists =new  ArrayList<Menu>();
        for(Menu menuNode : menuList) {
            if(menuNode.getParentId().equals("0")) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }
}

class Menu {
    private String id;
    private String parentId;
    private String text;
    private String url;
    private String yxbz;
    private List<Menu> children;
    public Menu(String id,String parentId,String text,String url,String yxbz) {
        this.id=id;
        this.parentId=parentId;
        this.text=text;
        this.url=url;
        this.yxbz=yxbz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}