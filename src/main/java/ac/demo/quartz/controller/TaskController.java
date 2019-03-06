package ac.demo.quartz.controller;

import ac.demo.quartz.entity.Task;
import ac.demo.quartz.service.TaskService;
import ac.demo.quartz.share.JsonMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author acemma
 * @date 2019/3/5 16:30
 * @Description
 */
@Api("任务")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("新增任务")
    @ApiImplicitParam(name = "task",value = "Task",dataType = "Task",paramType = "body")
    @PostMapping(value = "/add")
    public JsonMessage<Object> addTask(@RequestBody Task task){
        taskService.insert(task);
        return new JsonMessage<>("SUCCESS","SUCCESS");
    }

    @ApiOperation("修改任务")
    @ApiImplicitParam(name = "task",value = "Task",dataType = "Task",paramType = "body")
    @PutMapping(value = "/update")
    public JsonMessage<Object> updateTask(@RequestBody Task task){
        taskService.update(task);
        return new JsonMessage<>("SUCCESS","SUCCESS");
    }

    @ApiOperation("删除任务")
    @ApiImplicitParam(name = "id",value = "ID",dataType = "int",paramType = "path")
    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage<Object> deleteTask(@PathVariable Integer id){
        taskService.delete(id);
        return new JsonMessage<>("SUCCESS","SUCCESS");
    }
}
