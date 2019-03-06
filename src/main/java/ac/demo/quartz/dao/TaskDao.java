package ac.demo.quartz.dao;

import ac.demo.quartz.entity.Task;

/**
 * @author acemma
 * @date 2019/3/5 11:08
 * @Description
 */
public interface TaskDao {

    Task findByName(String taskName);

    Task findById(Integer id);

    int insert(Task task);

    int update(Task task);

    int delete(Integer id);


}
