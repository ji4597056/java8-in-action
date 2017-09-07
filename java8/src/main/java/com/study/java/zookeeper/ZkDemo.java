package com.study.java.zookeeper;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/08/11 18:02
 */
public class ZkDemo {

    private static final String ZK_ADDRESS = "172.24.4.10:2181,172.24.4.10:2182,172.24.4.10:2183";
    private static final String ZK_PATH = "/zktest";

    @Test
    public void test1() throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
            ZK_ADDRESS,
            new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");
        // 2.Client API test
        // 2.1 Create node
        String data1 = "hello";
        print("create", ZK_PATH, data1);
        if (client.checkExists().forPath(ZK_PATH) == null) {
            client.create().creatingParentsIfNeeded().forPath(ZK_PATH, data1.getBytes());
        }
        // 2.2 Get node and data
        print("ls", "/");
        print(client.getChildren().forPath("/"));
        print("get", ZK_PATH);
        print(client.getData().forPath(ZK_PATH));
        // 2.3 Modify data
        String data2 = "world";
        print("set", ZK_PATH, data2);
        client.setData().forPath(ZK_PATH, data2.getBytes());
        print("get", ZK_PATH);
        print(client.getData().forPath(ZK_PATH));
        // 2.4 Remove node
        print("delete", ZK_PATH);
        client.delete().forPath(ZK_PATH);
        print("ls", "/");
        print(client.getChildren().forPath("/"));
        // close
        CloseableUtils.closeQuietly(client);
    }

    @Test
    public void test2() throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
            ZK_ADDRESS,
            new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        PathChildrenCache watcher = new PathChildrenCache(
            client,
            "/",
            true    // if cache data
        );
        watcher.getListenable().addListener((client1, event) -> {
            ChildData data = event.getData();
            if (data == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println("Receive event: "
                    + "type=[" + event.getType() + "]"
                    + ", path=[" + data.getPath() + "]"
                    + ", data=[" + new String(data.getData()) + "]"
                    + ", stat=[" + data.getStat() + "]");
            }
        });
        watcher.start(StartMode.BUILD_INITIAL_CACHE);
        System.out.println("Register zk watcher successfully!");
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void print(String... cmds) {
        StringBuilder text = new StringBuilder("$ ");
        for (String cmd : cmds) {
            text.append(cmd).append(" ");
        }
        System.out.println(text.toString());
    }

    private static void print(Object result) {
        System.out.println(result instanceof byte[] ? new String((byte[]) result) : result);
    }

    @Test
    public void test3() throws Exception {
        TestingServer server = new TestingServer(3);
        TimeUnit.SECONDS.sleep(2);
        CuratorFramework client = CuratorFrameworkFactory.builder()
            .authorization("digest", "foo:true".getBytes())
            .connectString(server.getConnectString())
            .retryPolicy(new RetryNTimes(10, 5000)).build();
        client.start();
        client.create().creatingParentsIfNeeded().withACL(Ids.CREATOR_ALL_ACL)
            .forPath("/acl", "acl".getBytes());
        Logger.getGlobal().info(new String(client.getData().forPath("/acl")));
        client.delete().forPath("/acl");
        CloseableUtils.closeQuietly(client);
    }

    @Test
    public void test4() throws Exception {
        // 创建一个与服务器的连接
        ZooKeeper zk = new ZooKeeper(ZKConstant.CONNECTION_ADDRESS, 60000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
        // 创建一个目录节点
        zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
            CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
            Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath", false, null)));
        // 取出子目录节点列表
        System.out.println(zk.getChildren("/testRootPath", true));
        // 修改子目录节点数据
        zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
        System.out.println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]");
        // 创建另外一个子目录节点
        zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),
            Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo", true, null)));
        // 删除子目录节点
        zk.delete("/testRootPath/testChildPathTwo", -1);
        zk.delete("/testRootPath/testChildPathOne", -1);
        // 删除父目录节点
        zk.delete("/testRootPath", -1);
        // 关闭连接
        zk.close();
    }

    @Test
    public void test5() throws Exception {
        // test
        String testPath = "/testRootPath";
        String testData = "testRootData";
        // 创建一个与服务器的连接
        ZooKeeper zk = new ZooKeeper(ZKConstant.CONNECTION_ADDRESS, 60000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
        if (zk.exists(testPath, true) == null) {
            zk.create(testPath, testData.getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
            zk.exists(testPath, true);
        }
        zk.delete(testPath, -1);
        zk.close();
    }

    @Test
    public void test6() throws Exception {
        // test
        String testPath = "/testRootPath";
        String testData = "testRootData";
        // 创建一个与服务器的连接
        Watcher watcher = new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        };
        ZooKeeper zk = new ZooKeeper(ZKConstant.CONNECTION_ADDRESS, 60000, watcher);
        if (zk.exists(testPath, watcher) == null) {
            zk.create(testPath, testData.getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
            zk.exists(testPath, watcher);
        }
        zk.delete(testPath, -1);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        zk.close();
    }

}
