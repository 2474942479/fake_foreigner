//package edu.zsq.acl.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import edu.zsq.acl.entity.Permission;
//import edu.zsq.acl.entity.vo.PermissionVO;
//import edu.zsq.acl.entity.vo.RouterVO;
//import org.springframework.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 张
// */
//public class MenuUtil {
//
//    /**
//     * 构建菜单 TODO 重构
//     * @param treeNodes 所有权限节点
//     * @return 权限树
//     */
//    public static List<RouterVO> bulid(List<PermissionVO> treeNodes) {
//        List<RouterVO> menu = new ArrayList<>();
//        if(treeNodes.size() == 1) {
//            Permission topNode = treeNodes.get(0);
//            //左侧一级菜单
//            List<Permission> oneMenuList = topNode.getChildren();
//            for(Permission one :oneMenuList) {
//                JSONObject oneMenu = new JSONObject();
//                oneMenu.put("path", one.getPath());
//                oneMenu.put("component", one.getComponent());
//                oneMenu.put("redirect", "noredirect");
//                oneMenu.put("name", "name_"+one.getId());
//                oneMenu.put("hidden", false);
//
//                JSONObject oneMeta = new JSONObject();
//                oneMeta.put("title", one.getName());
//                oneMeta.put("icon", one.getIcon());
//                oneMenu.put("meta", oneMeta);
//
//                List<JSONObject> children = new ArrayList<>();
//                List<Permission> twoMeunList = one.getChildren();
//                for(Permission two :twoMeunList) {
//                    JSONObject twoMeun = new JSONObject();
//                    twoMeun.put("path", two.getPath());
//                    twoMeun.put("component", two.getComponent());
//                    twoMeun.put("name", "name_"+two.getId());
//                    twoMeun.put("hidden", false);
//
//                    JSONObject twoMeta = new JSONObject();
//                    twoMeta.put("title", two.getName());
//                    twoMeun.put("meta", twoMeta);
//
//                    children.add(twoMeun);
//
//                    List<Permission> threeMeunList = two.getChildren();
//                    for(Permission three :threeMeunList) {
//                        if(StringUtils.isEmpty(three.getPath())) {
//                            continue;
//                        }
//                        JSONObject threeMeun = new JSONObject();
//                        threeMeun.put("path", three.getPath());
//                        threeMeun.put("component", three.getComponent());
//                        threeMeun.put("name", "name_"+three.getId());
//                        threeMeun.put("hidden", true);
//
//                        JSONObject threeMeta = new JSONObject();
//                        threeMeta.put("title", three.getName());
//                        threeMeun.put("meta", threeMeta);
//
//                        children.add(threeMeun);
//                    }
//                }
//                oneMenu.put("children", children);
//                menu.add(oneMenu);
//            }
//        }
//        return menu;
//    }
//}
