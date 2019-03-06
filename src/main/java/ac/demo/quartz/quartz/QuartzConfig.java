package ac.demo.quartz.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author acemma
 * @date 2019/3/5 15:18
 * @Description
 */
@Configuration
public class QuartzConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(QuartzJobFactory myJobFactory) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        //使job实例支持spring 容器管理
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        // 延迟10s启动quartz
        schedulerFactoryBean.setStartupDelay(10);

        return schedulerFactoryBean;
    }


    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }


    /**
     * 设置quartz属性
     */
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        // 调度标识名 集群中每一个实例都必须使用相同的名称
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        // ID设置为自动获取 每一个必须不同
//        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");

        // 线程池的实现类（一般使用SimpleThreadPool即可满足几乎所有用户的需求）
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        // #数据保存方式为数据库持久化
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 数据库代理类，一般org.quartz.impl.jdbcjobstore.StdJDBCDelegate可以满足大部分数据库
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        // 表的前缀，默认QRTZ_
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        // 是否加入集群
        prop.put("org.quartz.jobStore.isClustered", "true");

        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        // 指定线程数，至少为1（无默认值）(一般设置为1-100直接的整数合适)
        prop.put("org.quartz.threadPool.threadCount", "5");

//		prop.put("org.quartz.dataSource.quartzDataSource.driver", druidProperties.getDriverClassName());
//		prop.put("org.quartz.dataSource.quartzDataSource.URL", druidProperties.getUrl());
//		prop.put("org.quartz.dataSource.quartzDataSource.user", druidProperties.getUsername());
//		prop.put("org.quartz.dataSource.quartzDataSource.password", druidProperties.getPassword());
//		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", druidProperties.getMaxActive());
        return prop;
    }


}
