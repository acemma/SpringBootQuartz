package ac.demo.quartz.quartz;


import ac.demo.quartz.entity.Task;

/**
 * @author acemma
 * @date 2019/3/5 16:21
 * @Description
 */
public interface QuartzTaskService {

    void addJob(Task task);

    void edit(Task info);

    void delete(String jobName, String jobGroup);

    void pause(String jobName, String jobGroup);

    void resume(String jobName, String jobGroup);
}