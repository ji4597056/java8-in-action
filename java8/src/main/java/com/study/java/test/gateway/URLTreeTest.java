package com.study.java.test.gateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoufeng on 2017/10/13.
 */
public class URLTreeTest {

    public static void main(String[] args) {
        List<Endpoint> list = new ArrayList<>();
        list.add(new Endpoint().setEndpointId(10001L).setEndpointURL("hehe0/ni1/za2/le3").setOperationType(RequestMethod.GET).setOperationLog(true));
        list.add(new Endpoint().setEndpointId(10002L).setEndpointURL("haha0/ma1/gan2/fsf3").setOperationType(RequestMethod.POST).setOperationLog(true));
        list.add(new Endpoint().setEndpointId(10003L).setEndpointURL("hehe0/wo1/fffa2").setOperationType(RequestMethod.GET).setOperationLog(false));
        list.add(new Endpoint().setEndpointId(10004L).setEndpointURL("hehe0/wo1/{ghege}").setOperationType(RequestMethod.GET).setOperationLog(true));
        list.add(new Endpoint().setEndpointId(10005L).setEndpointURL("feg0/wo1/aggeg2/asdgas3/le4").setOperationType(RequestMethod.DELETE).setOperationLog(true));
        list.add(new Endpoint().setEndpointId(10005L).setEndpointURL("feg0/wo1/aggeg2/asdgas3/le4").setOperationType(RequestMethod.DELETE).setOperationLog(true));

        URLTree urlTree = new URLTree(list);
        //打印树
        urlTree.preErgodicTree(urlTree.getRootNode());

        //测试路径是否存在
        URLTree.URLNode urlNode = urlTree.urlIsExist("hehe0/ni1/za2/le3/GET");//true
        URLTree.URLNode urlNode1 = urlTree.urlIsExist("hehe0/ni1/za2/le2/GET");//false
        URLTree.URLNode urlNode2 = urlTree.urlIsExist("hehe0/wo1/sagkgj/GET");//true
        URLTree.URLNode urlNode4 = urlTree.urlIsExist("hehe0/ni1/za2");//false


        System.out.println(urlNode == null ? "没找到" : urlNode.getUrlId());
        System.out.println(urlNode1 == null ? "没找到" : urlNode1.getUrlId());
        System.out.println(urlNode2 == null ? "没找到" : urlNode2.getUrlId());
        System.out.println(urlNode4 == null ? "没找到" : urlNode4.getUrlId());

    }
}
