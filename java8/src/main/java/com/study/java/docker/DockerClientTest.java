package com.study.java.docker;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ExecState;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/11/24 17:08
 */
public class DockerClientTest {

    public static final String URI = "";

    @Test
    public void test() throws DockerCertificateException, DockerException, InterruptedException {
        final DockerClient docker = DefaultDockerClient.builder().uri(URI).build();
//        String containerId = "123";
//        String execId = docker.execCreate(containerId, new String[]{"sh", "-c", "exit 2"});
        String execId = "123";
        try (final LogStream stream = docker.execStart(execId)) {
            stream.readFully();
        }

        final ExecState state = docker.execInspect(execId);
        assertThat(state.id(), is(execId));
        assertThat(state.running(), is(false));
        assertThat(state.exitCode(), is(2));
        assertThat(state.openStdin(), is(true));
        assertThat(state.openStderr(), is(true));
        assertThat(state.openStdout(), is(true));
    }
}
