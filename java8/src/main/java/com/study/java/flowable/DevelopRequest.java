package com.study.java.flowable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricTaskInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/07/26 15:59
 */
public class DevelopRequest extends BaseFlowable {

    private static final String PROCESS_KEY = "developRequest";

    private static final String DEVELOPER_ID = "Mr. Wang";
    private static final String TESTER_ID = "Mr. Zhang";
    private static final String PRE_PRODUCER_ID = "Mr. Li";
    private static final String PRODUCER_ID = "Mr. Ji";

    private static final String DEVELOPER_GROUP = "developer";
    private static final String TESTER_GROUP = "tester";
    private static final String PRE_PRODUCER_GROUP = "pre-producer";
    private static final String PRODUCER_GROUP = "producer";


    @Test
    public void test1() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        IdentityService identityService = processEngine.getIdentityService();
        Map<String, Object> variables = new HashMap<>(1);
        variables.put(FlowableEnums.STARTER_ID.value(), DEVELOPER_ID);
        ProcessInstance processInstance;
        // set user id
        try {
            identityService.setAuthenticatedUserId(DEVELOPER_ID);
            processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, variables);
            runtimeService
                .setProcessInstanceName(processInstance.getProcessInstanceId(), "develop_request");
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        // developer
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(DEVELOPER_GROUP)
            .active().list();
        Assert.assertTrue(tasks.size() == 1);

        Task task = tasks.get(0);
        taskService.claim(task.getId(), DEVELOPER_ID);
        variables = new HashMap<>(2);
        variables.put(FlowableEnums.DEVELOPER_SEND.value(), true);
        variables.put(FlowableEnums.DEVELOPER_ID.value(), DEVELOPER_ID);
        taskService.complete(task.getId(), variables);

        // tester
        tasks = taskService.createTaskQuery().taskCandidateGroup(TESTER_GROUP).active().list();
        Assert.assertTrue(tasks.size() == 1);
        task = tasks.get(0);
        taskService.claim(task.getId(), TESTER_ID);
        variables = new HashMap<>(2);
        variables.put(FlowableEnums.TESTER_APPROVED.value(), true);
        variables.put(FlowableEnums.TO_PRE_PRODUCER.value(), false);
        variables.put(FlowableEnums.TESTER_ID.value(), TESTER_ID);
        taskService.complete(task.getId(), variables);

        // producer
        tasks = taskService.createTaskQuery().taskCandidateGroup(PRODUCER_GROUP).active().list();
        Assert.assertTrue(tasks.size() == 1);
        task = tasks.get(0);
        taskService.claim(task.getId(), PRODUCER_ID);
        variables = new HashMap<>(2);
        variables.put(FlowableEnums.PRODUCER_APPROVED.value(), false);
        taskService.complete(task.getId(), variables);

        // tester
        tasks = taskService.createTaskQuery().taskAssignee(TESTER_ID).active().list();
        Assert.assertTrue(tasks.size() == 1);
        task = tasks.get(0);
        variables = new HashMap<>(2);
        variables.put(FlowableEnums.TESTER_APPROVED.value(), true);
        variables.put(FlowableEnums.TO_PRE_PRODUCER.value(), true);
        taskService.complete(task.getId(), variables);

        // pre-producer
        tasks = taskService.createTaskQuery().taskCandidateGroup(PRE_PRODUCER_GROUP).active()
            .list();
        Assert.assertTrue(tasks.size() == 1);
        task = tasks.get(0);
        taskService.claim(task.getId(), PRE_PRODUCER_ID);
        variables = new HashMap<>(2);
        variables.put(FlowableEnums.PRE_PRODUCER_APPROVED.value(), true);
        variables.put(FlowableEnums.PRE_PRODUCER_ID.value(), PRE_PRODUCER_ID);
        taskService.complete(task.getId(), variables);

        // delete process
//        runtimeService
//            .deleteProcessInstance(processInstance.getProcessInstanceId(), "I want to detele it!");

        // producer
        tasks = taskService.createTaskQuery().taskCandidateGroup(PRODUCER_GROUP).active().list();
        Assert.assertTrue(tasks.size() == 1);
        task = tasks.get(0);
        taskService.claim(task.getId(), PRODUCER_ID);
        variables = new HashMap<>(1);
        variables.put(FlowableEnums.PRODUCER_APPROVED.value(), true);
        taskService.complete(task.getId(), variables);

        // history
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService
            .createHistoricProcessInstanceQuery()
            .processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        System.out.println("process name : " + historicProcessInstance.getName());
        System.out.println("start user id : " + historicProcessInstance.getStartUserId());
        System.out.println("start time : " + historicProcessInstance.getStartTime());
        System.out.println("end time : " + historicProcessInstance.getEndTime());
        System.out.println("duration : " + historicProcessInstance.getDurationInMillis());
        System.out.println("delete reason : " + historicProcessInstance.getDeleteReason());
        System.out.println("====================");

        List<HistoricTaskInstance> historicTaskInstances = historyService
            .createHistoricTaskInstanceQuery().finished()
            .processInstanceId(processInstance.getProcessInstanceId()).list();
        historicTaskInstances.forEach(historicTaskInstance -> {
            System.out.println("=====================");
            System.out.println("task name : " + historicTaskInstance.getName());
            System.out.println("assignee : " + historicTaskInstance.getAssignee());
            System.out.println("start time : " + historicTaskInstance.getStartTime());
            System.out.println("end time : " + historicTaskInstance.getEndTime());
            System.out.println("duration : " + historicTaskInstance.getDurationInMillis());
            System.out.println("=====================");
        });
    }

}
