package ac.demo.quartz.quartz;

import ac.demo.quartz.dao.TaskDao;
import ac.demo.quartz.entity.Task;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author acemma
 * @date 2019/3/5 11:32
 * @Description
 */
@Component
public class MyJob implements Job {

    @Autowired
    TaskDao taskDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        String name = detail.getJobDataMap().getString("name");
        Task task = taskDao.findByName(name);
        System.out.println("say hello to " + name + " at " + new Date());
        System.out.println(task.getTaskName());
    }
}
