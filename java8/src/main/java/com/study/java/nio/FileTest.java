package com.study.java.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javax.sql.rowset.spi.SyncResolver;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/13 10:50
 */
public class FileTest {

    private static final Path DEFAULT_PATH = Paths
        .get("C:", "Users", "jixiaogang", "Desktop", "名词.txt");

    private static final Path DEFAULT_TEST_PATH = Paths
        .get("C:", "Users", "jixiaogang", "Desktop", "test.txt");


    @Test
    public void test1() {
        Path path = Paths.get("C:", "Users", "jixiaogang", "Desktop", "名词.txt");
        System.out.println(path.getParent());
        System.out.println(path.getFileName());
        System.out.println(path.toFile());
    }

    @Test
    public void test2() throws IOException {
        Path fromPath = Paths.get("C:", "Users", "jixiaogang", "Desktop", "名词.txt");
        byte[] bytes = Files.readAllBytes(fromPath);
        String content = new String(bytes, Charset.forName("gbk"));
        System.out.println(content);
        System.out.println("=============");
        List<String> lines = Files.readAllLines(fromPath, Charset.forName("gbk"));
        System.out.println(lines);

        Path toPath = Paths.get("C:", "Users", "jixiaogang", "Desktop", "test.txt");
        Files.write(toPath, content.getBytes(Charset.forName("gbk")));
        Files.write(toPath, lines, Charset.forName("gbk"), StandardOpenOption.APPEND);
    }

    @Test
    public void test3() throws IOException {
//        InputStream in = Files.newInputStream(DEFAULT_PATH);
//        OutputStream out = Files.newOutputStream(DEFAULT_PATH);
//        Reader reader = Files.newBufferedReader(DEFAULT_PATH);
//        Writer writer = Files.newBufferedWriter(DEFAULT_PATH);
//        Files.createFile(DEFAULT_TEST_PATH);
        Logger.getGlobal().info(String.format("hello,%s,%s", "Jeffrey", 1));
        Files.copy(DEFAULT_PATH, DEFAULT_TEST_PATH, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        Files.deleteIfExists(DEFAULT_TEST_PATH);
    }
}
