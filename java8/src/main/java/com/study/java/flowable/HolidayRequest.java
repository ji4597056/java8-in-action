package com.study.java.flowable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricTaskInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Task;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/07/20 15:39
 */
public class HolidayRequest extends BaseFlowable{

    public static void main(String[] args) {
        // input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();
        // start task
        // 1)
        RuntimeService runtimeService1 = processEngine.getRuntimeService();
        Map<String, Object> first = new HashMap<>(3);
        first.put("employee", employee);
        first.put("nrOfHolidays", nrOfHolidays);
        first.put("description", description);
//        Map<String, Object> map = new HashMap<>();
//        map.put("username", "123");
//        first.put("person", map);
        ExecutionEntity processInstance1 =
            (ExecutionEntity) runtimeService1.startProcessInstanceByKey("holidayRequest", first);
        // 2)
        RuntimeService runtimeService2 = processEngine.getRuntimeService();
        Map<String, Object> second = new HashMap<>(3);
        second.put("employee", "second");
        second.put("nrOfHolidays", 5);
        second.put("description", "=_+");
        ProcessInstance processInstance2 = runtimeService2
            .startProcessInstanceByKey("holidayRequest", second);
        // show task
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }
        // choose task
        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " +
            processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
        List<Task> userTask = taskService.createTaskQuery()
            .taskAssignee((String) processVariables.get("employee")).list();
        System.out.println(
            processVariables.get("employee") + " has received " + userTask.size() + " job ");
        // do task
        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);
//        taskService.claim(task.getId(), (String) processVariables.get("employee"));
//        userTask = taskService.createTaskQuery().taskAssignee((String) processVariables.get("employee")).list();
//        System.out.println(processVariables.get("employee") + " has received " + userTask.size() + " job ");
        taskService.complete(task.getId(), variables);
        // task history
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
            historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance1.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took "
                + activity.getDurationInMillis() + " milliseconds");
        }

        List<HistoricProcessInstance> processInstances = historyService
            .createHistoricProcessInstanceQuery().list();
        for (HistoricProcessInstance processInstance : processInstances) {
            System.out.println(processInstance);
        }

    }

    @Test
    public void test() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> map = new HashMap<>(3);
        map.put("nrOfHolidays", 5);
        map.put("description", "=_+");
        map.put("starter", "owner1");
        map.put("first", "first");
        ExecutionEntity processInstance = (ExecutionEntity) runtimeService
            .startProcessInstanceByKey("holidayRequest", map);
        processInstance.setStartUserId((String) map.get("starter"));

        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("managers have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(
                (i + 1) + ") " + tasks.get(i).getName());
        }

        Task task = tasks.get(0);
        map = new HashMap<>(2);
        map.put("employee", "user1");
        map.put("approved", true);
        taskService.claim(task.getId(), "u1");
        taskService.complete(task.getId(), map);

        tasks = taskService.createTaskQuery().taskAssignee("user1").list();
        task = tasks.get(0);
        map = new HashMap<>(1);
        map.put("end", true);
        taskService.complete(task.getId(), map);

        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> historicTaskInstances = historyService
            .createHistoricTaskInstanceQuery().processInstanceId(processInstance.getId()).list();
        historicTaskInstances.forEach(historicTaskInstance -> {
            System.out.println(historicTaskInstance);
        });
    }

}
