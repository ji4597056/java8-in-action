package com.study.java.flowable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

/**
 * @author Jeffrey
 * @since 2017/07/26 16:03
 */
public class BaseFlowable {

    protected static ProcessEngine processEngine;

    static {
        // config
        ProcessEngineConfiguration cfg = (ProcessEngineConfiguration) new StandaloneProcessEngineConfiguration()
            .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
            .setJdbcUsername("sa")
            .setJdbcPassword("")
            .setJdbcDriver("org.h2.Driver")
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
            .setEventListeners(Stream.of(new MyEventListener()).collect(Collectors.toList()));
        processEngine = cfg.buildProcessEngine();
        // add resource
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
            .addClasspathResource("holiday-request.bpmn20.xml")
            .addClasspathResource("develop-request.bpmn20.xml")
            .deploy();
        // definition
        List<ProcessDefinition> processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId())
            .list();
        processDefinition.forEach(
            definition -> System.out.println("Found process definition : " + definition.getId()));
    }

}
