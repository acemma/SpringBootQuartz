package ac.demo.quartz.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author acemma
 * @date 2019/3/5 11:05
 * @Description
 */
public class Task implements Serializable{

    private static final long serialVersionUID = -6525467980837032791L;

    private Integer id;

    private String taskName;

    private Date startTime;

    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
