package com.study.java.zookeeper;

import java.util.concurrent.CompletionStage;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.curator.x.async.AsyncEventException;
import org.apache.curator.x.async.WatchMode;
import org.apache.zookeeper.WatchedEvent;

/**
 * @author Jeffrey
 * @since 2017/08/15 13:26
 */
public class AsyncExamples {

    public static AsyncCuratorFramework wrap(CuratorFramework client) {
        // wrap a CuratorFramework instance so that it can be used async.
        // do this once and re-use the returned AsyncCuratorFramework instance
        return AsyncCuratorFramework.wrap(client);
    }

    public static void create(CuratorFramework client, String path, byte[] payload) {
        AsyncCuratorFramework async = AsyncCuratorFramework
            .wrap(client);   // normally you'd wrap early in your app and reuse the instance

        // create a node at the given path with the given payload asynchronously
        async.create().forPath(path, payload).whenComplete((name, exception) -> {
            if (exception != null) {
                // there was a problem
                exception.printStackTrace();
            } else {
                System.out.println("Created node name is: " + name);
            }
        });
    }

    public static void createThenWatch(CuratorFramework client, String path) {
        AsyncCuratorFramework async = AsyncCuratorFramework
            .wrap(client);   // normally you'd wrap early in your app and reuse the instance

        // this example shows to asynchronously use watchers for both event
        // triggering and connection problems. If you don't need to be notified
        // of connection problems, use the simpler approach shown in createThenWatchSimple()

        // create a node at the given path with the given payload asynchronously
        // then watch the created node
        async.create().forPath(path).whenComplete((name, exception) -> {
            if (exception != null) {
                // there was a problem creating the node
                exception.printStackTrace();
            } else {
                handleWatchedStage(async.watched().checkExists().forPath(path).event());
            }
        });
    }

    public static void createThenWatchSimple(CuratorFramework client, String path) {
        AsyncCuratorFramework async = AsyncCuratorFramework
            .wrap(client);   // normally you'd wrap early in your app and reuse the instance

        // create a node at the given path with the given payload asynchronously
        // then watch the created node
        async.create().forPath(path).whenComplete((name, exception) -> {
            if (exception != null) {
                // there was a problem creating the node
                exception.printStackTrace();
            } else {
                // because "WatchMode.successOnly" is used the watch stage is only triggered when
                // the EventType is a node event
                async.with(WatchMode.successOnly).watched().checkExists().forPath(path).event()
                    .thenAccept(event -> {
                        System.out.println(event.getType());
                        System.out.println(event);
                    });
            }
        });
    }

    private static void handleWatchedStage(CompletionStage<WatchedEvent> watchedStage) {
        // async handling of Watchers is complicated because watchers can trigger multiple times
        // and CompletionStage don't support this behavior

        // thenAccept() handles normal watcher triggering.
        watchedStage.thenAccept(event -> {
            System.out.println(event.getType());
            System.out.println(event);
            // etc.
        });

        // exceptionally is called if there is a connection problem in which case
        // watchers trigger to signal the connection problem. "reset()" must be called
        // to reset the watched stage
        watchedStage.exceptionally(exception -> {
            AsyncEventException asyncEx = (AsyncEventException) exception;
            asyncEx.printStackTrace();    // handle the error as needed
            handleWatchedStage(asyncEx.reset());
            return null;
        });
    }

}
