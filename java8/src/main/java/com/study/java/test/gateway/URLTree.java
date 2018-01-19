package com.study.java.test.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by zoufeng on 2017/10/13.
 */
public class URLTree {

    private final URLNode<String> root = new URLNode<>("gaeway", -1, false);

    //节点数量，业务用不到先不写
//    private AtomicInteger size = new AtomicInteger(1);

    public URLTree(List<Endpoint> list) {
        list.stream().forEach(this::analysisURL);
    }

    /**
     * @ description: 分析构造树
     * @ parm: url路径
     * @ return:
     */
    private void analysisURL(Endpoint endpoint) {
        String url = endpoint.getEndpointURL().concat("/" + endpoint.getOperationType().name());
        String[] split = url.split("/");
        int length = split.length;
        List<URLNode<String>> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            String data = split[i];
            if (data.startsWith("{") && data.endsWith("}")) {
                data = "*";
            }
            if (i < (length - 1)) {
                list.add(new URLNode<>(data, i, false));
            } else {
                //添加叶子节点，设置id,name,islog等信息
                list.add(new URLNode<>(data, i, true)
                        .setUrlId(endpoint.getEndpointId())
                        .setDescribe(endpoint.getEndpointName())
                        .setOperationLog(endpoint.isOperationLog())
                );
            }
            //给他们找爸爸,认儿子
            URLNode<String> urlNode = list.get(i);
            if (i == 0) {
                urlNode.setFatherNode(root);
            } else {
                URLNode<String> fatherNode = list.get(i - 1);
                urlNode.setFatherNode(fatherNode);
                fatherNode.setChildNode(urlNode);
            }
        }
        root.addChildNode(list.get(0));
        root.refreshChildMap();
    }

    /**
     * 测试查看树
     * 先序层级打印树
     * 打印形式为 node,fathernode
     */
    public void preErgodicTree(URLNode<String> root) {
        Queue<URLNode<String>> queue = new LinkedList<>();
        queue.add(root);
        preErgodic(queue);
    }

    private void preErgodic(Queue<URLNode<String>> queue) {
        if (queue.isEmpty()) return;
        URLNode<String> poll = queue.poll();
        List<URLNode<String>> childrenNode = poll.getChildrenNode();
        System.out.println(poll.toString());
        if (childrenNode != null)
            queue.addAll(childrenNode);
        preErgodic(queue);
    }

    public URLNode<String> getRootNode() {
        return root;
    }

    /**
     * @ description: 判断路径是否在树中存在,
     * 路径不判断根节点，从第二层级开始判断
     * @ parm: url
     * @ return: URLNode 叶子节点，非叶子节点返回null
     */
    public URLNode urlIsExist(String url) {
        int i1 = url.indexOf("?");
        if (i1 != -1) {
            url = url.substring(0, i1);
        }
        String[] split = url.split("/");
        //默认跳过根节点
        Map<String, URLNode<String>> childrenMap = root.getChildrenMap();
        return checkNodeUrl(split, childrenMap, 0);
    }

    private URLNode checkNodeUrl(String[] split, Map<String, URLNode<String>> childrenMap, int level) {
        String url = split[level];
        URLNode<String> node = childrenMap.get(url);
        if (node == null) {
            //模糊匹配
            node = childrenMap.get("*");
            if (node == null) return null;
        }
        level += 1;
        if (level >= split.length) {
            return node.isLeaf ? node : null;
        }
        Map<String, URLNode<String>> map = node.getChildrenMap();
        return checkNodeUrl(split, map, level);
    }


    public class URLNode<T> {
        private T data;

        private int level;

        private boolean isRoot;

        private boolean isLeaf;

        private Long urlId;

        private String describe;

        private boolean isOperationLog;

        private List<URLNode<T>> childrenList = new ArrayList<>();

        //基本无并发,不用concurrentHashMap
        private Map<T, URLNode<T>> childrenMap = new HashMap<T, URLNode<T>>();

        private URLNode<T> fatherNode;

        public URLNode(T s, int level, boolean isLeaf) {
            this.data = s;
            this.level = level;
            if (level == -1) {
                isRoot = true;
            } else {
                isRoot = false;
            }
            this.isLeaf = isLeaf;
        }


        public boolean isRootNode() {
            return isRoot;
        }

        public boolean isLeafNode() {
            return isLeaf;
        }

        public List<URLNode<T>> getChildrenNode() {
            return isLeafNode() ? null : (childrenList.isEmpty() ? null : childrenList);
        }

        public void setFatherNode(URLNode<T> node) {
            //非根节点才能设置父节点
            if (!this.isRoot) {
                fatherNode = node;
            }
        }

        public URLNode<T> getFatherNode() {
            return isRootNode() ? null : fatherNode;
        }

        /**
         * 设置子节点,不涉及分支合并,单纯的认儿子
         */
        public void setChildNode(URLNode<T> node) {
            //不存在则设置为子节点
            if (isAbsentChild(node)) {
                childrenList.add(node);
            }
        }

        /*
        * 添加子节点,涉及子节点分支合并
        * */
        public void addChildNode(URLNode<T> newNode) {
            if (this.childrenList.isEmpty()) {
                setChildNode(newNode);
                return;
            }
            URLNode<T> oldNode = this.childrenList.stream().filter(x -> x.getData().equals(newNode.getData())).findFirst().orElse(null);
            if (oldNode == null) {
                setChildNode(newNode);
                return;
            }
            List<URLNode<T>> newNodeChildrenNode = newNode.getChildrenNode();
            if (newNodeChildrenNode == null) return;
            for (URLNode<T> myNode : newNodeChildrenNode) {
                myNode.setFatherNode(oldNode);
                oldNode.addChildNode(myNode);
            }
        }

        public void refreshChildMap() {
            if (this.childrenList.isEmpty()) return;
            for (URLNode<T> node : this.childrenList) {
                childrenMap.put(node.getData(), node);
                node.refreshChildMap();
            }
        }

        public Map<T, URLNode<T>> getChildrenMap() {
            return childrenMap;
        }

        public T getData() {
            return data;
        }

        public int getLevel() {
            return level;
        }

        public void setData(T t) {
            this.data = t;
        }

        private boolean isAbsentChild(URLNode<T> node) {
            URLNode<T> put = childrenMap.put(node.getData(), node);
            return put == null ? true : false;
        }

        @Override
        public String toString() {
            return String.join(",", data.toString(), getFatherNode() == null ? "--" : getFatherNode().getData().toString());
        }

        public Long getUrlId() {
            return urlId;
        }

        public URLNode setUrlId(Long urlId) {
            this.urlId = urlId;
            return this;
        }

        public String getDescribe() {
            return describe;
        }

        public URLNode setDescribe(String describe) {
            this.describe = describe;
            return this;
        }

        public boolean isOperationLog() {
            return isOperationLog;
        }

        public URLNode setOperationLog(boolean operationLog) {
            isOperationLog = operationLog;
            return this;
        }
    }

}
