package com.study.java.zookeeper;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import java.util.Collection;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.utils.CloseableUtils;

/**
 * @author Jeffrey
 * @since 2017/08/15 12:53
 */
public class TransactionExamples {

    public static Collection<CuratorTransactionResult> transaction(CuratorFramework client)
        throws Exception {
        // this example shows how to use ZooKeeper's transactions

        CuratorOp createOp = client.transactionOp().create()
            .forPath("/a", "some data".getBytes());
        CuratorOp setDataOp = client.transactionOp().setData()
            .forPath("/a", "other data".getBytes());
        CuratorOp deleteOp = client.transactionOp().delete().forPath("/a");

        Collection<CuratorTransactionResult> results = client.transaction()
            .forOperations(createOp, setDataOp, deleteOp);

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + " - " + result.getType());
        }
        return results;
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(ZKConstant.CONNECTION_ADDRESS);
        client.start();
        TransactionExamples.transaction(client);
        CloseableUtils.closeQuietly(client);
    }

}
