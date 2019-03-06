package ac.demo.quartz.quartz;


import ac.demo.quartz.entity.Task;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;

/**
 * @author acemma
 * @date 2019/3/5 16:21
 * @Description
 */
@Service
public class QuartzTaskServiceImpl implements QuartzTaskService {

    @Autowired
    private Scheduler scheduler;


    /**
     * 保存定时任务
     *
     * @param info
     */
    @Override
    public void addJob(Task info){
        String jobName = info.getTaskName();
        String jobGroup = "GMMC";
        Date startTime = info.getStartTime();
        Date endTime = info.getEndTime();
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(startTime).endAt(endTime).build();
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobKey).usingJobData("name",jobName).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Task info) {
        String jobName = info.getTaskName();
        String jobGroup = "GMMC";
        Date startTime = info.getStartTime();
        Date endTime = info.getEndTime();
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(startTime).endAt(endTime).build();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (null == jobDetail){
                jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobKey).usingJobData("name",jobName).build();
                scheduler.scheduleJob(jobDetail, trigger);
            }else {
                HashSet<Trigger> triggerSet = new HashSet<>();
                triggerSet.add(trigger);
                scheduler.scheduleJob(jobDetail, triggerSet,true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseTrigger(triggerKey);
            scheduler.deleteJob(jobKey);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void pause(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            scheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            scheduler.resumeTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}