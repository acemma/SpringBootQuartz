package ac.demo.quartz.service.impl;

import ac.demo.quartz.dao.TaskDao;
import ac.demo.quartz.entity.Task;
import ac.demo.quartz.quartz.QuartzTaskService;
import ac.demo.quartz.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author acemma
 * @date 2019/3/5 16:21
 * @Description
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private QuartzTaskService quartzTaskService;

    @Override
    public void insert(Task task) {
        taskDao.insert(task);
        quartzTaskService.addJob(task);
    }

    @Override
    public void update(Task task) {
        taskDao.update(task);
        quartzTaskService.edit(task);
    }

    @Override
    public void delete(Integer id) {
        Task task = taskDao.findById(id);
        quartzTaskService.delete(task.getTaskName(),"GMMC");
        taskDao.delete(id);
    }
}
