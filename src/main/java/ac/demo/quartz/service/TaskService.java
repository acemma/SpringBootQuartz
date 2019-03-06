package ac.demo.quartz.service;


import ac.demo.quartz.entity.Task;

/**
 * @author acemma
 * @date 2019/3/5 16:20
 * @Description
 */
public interface TaskService {

    void insert(Task task);

    void update(Task task);

    void delete(Integer id);

}
