package com.study.java.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author Jeffrey
 * @since 2017/10/10 19:55
 */
public class ZkTest implements Watcher {

    private static final String ZK_ADDRESS = "10.186.181.228:60003,10.186.181.131:60003,10.186.181.178:60003";

    private static CountDownLatch connectedSemaphone = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        String zkAddress = System.getProperty("zk.address");
        if (zkAddress == null){
            zkAddress = ZK_ADDRESS;
        }
        ZooKeeper zooKeeper = new ZooKeeper(zkAddress, 5000,
            new ZkTest());
        System.out.println(zooKeeper.getState());
        System.out.println("正在尝试连接...");
        try {
            connectedSemaphone.await();
        } catch (Exception e) {
            System.out.println("连接失败!");
        }
        System.out.println("连接成功!");
        System.out.println("ZooKeeper session established");
        System.out.println("sessionId=" + zooKeeper.getSessionId());
        System.out.println("password=" + zooKeeper.getSessionPasswd());
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("my ZookeeperConstructorSimple watcher Receive watched event:" + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphone.countDown();
        }
    }

}
