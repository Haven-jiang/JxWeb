package com.Haven.scheduler.util;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.scheduler.entity.QrtzCronTriggers;
import com.Haven.scheduler.entity.QrtzJobDetails;
import com.Haven.scheduler.entity.QrtzLocks;
import com.Haven.scheduler.entity.QrtzTriggers;
import com.Haven.scheduler.mapper.QrtzCronTriggersMapper;
import com.Haven.scheduler.mapper.QrtzJobDetailsMapper;
import com.Haven.scheduler.mapper.QrtzLocksMapper;
import com.Haven.scheduler.mapper.QrtzTriggersMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class QuartzUtil {

    private static final Logger logger = LoggerFactory.getLogger(QuartzUtil.class);

    private static QrtzJobDetailsMapper qrtzJobDetailsMapper;
    private static QrtzTriggersMapper qrtzTriggersMapper;
    private static QrtzLocksMapper qrtzLocksMapper;
    private static QrtzCronTriggersMapper qrtzCronTriggersMapper;

    public QuartzUtil(){}

    @Autowired
    public QuartzUtil(
            QrtzJobDetailsMapper qrtzJobDetailsMapper,
            QrtzTriggersMapper qrtzTriggersMapper,
            QrtzLocksMapper qrtzLocksMapper,
            QrtzCronTriggersMapper qrtzCronTriggersMapper) {

        QuartzUtil.qrtzJobDetailsMapper = qrtzJobDetailsMapper;
        QuartzUtil.qrtzTriggersMapper = qrtzTriggersMapper;
        QuartzUtil.qrtzLocksMapper = qrtzLocksMapper;
        QuartzUtil.qrtzCronTriggersMapper = qrtzCronTriggersMapper;

    }

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * @Description: 添加定时任务
     *
     * @param jobKey 任务job描述
     * @param triggerKey 任务触发器描述
     * @param jobClass 任务类 - 即执行哪个任务
     * @param cron 任务执行时间表
     * @param jsonData 任务数据
     */
    public static boolean addJob(
            JobKey jobKey,
            TriggerKey triggerKey,
            Class jobClass,
            String cron,
            String jsonData
    ) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("data", jsonData);
            JobDetail jobDetail = JobBuilder.newJob(jobClass).setJobData(jobDataMap).withIdentity(jobKey).build();

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerKey);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));

            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();

            if (!scheduler.isShutdown()) scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addJob(CronTaskDTO taskDTO, Class jobClass) {
        return addJob(taskDTO.getJobKey(), taskDTO.getTriggerKey(), jobClass, taskDTO.getCron(), taskDTO.getContent());
    }

    /**
     * @Description: 修改一个任务的触发时间
     *
     * @param triggerKey 任务触发器描述
     * @param cron   时间设置，参考quartz说明文档
     */
    public static boolean modifyJobTime(
            TriggerKey triggerKey,
            String cron) {

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) return false;

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /* 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerKey);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);

                /* 方式一 ：调用 rescheduleJob 结束 */

                /* 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /* 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean modifyJobTime(CronTaskDTO taskDTO) {
        return modifyJobTime(taskDTO.getTriggerKey(), taskDTO.getCron());
    }

    public static boolean modifyJobTime(CronTaskDTO lastTask, CronTaskDTO currentTask, Class jobClass) {
        boolean addJob;
        do {
            addJob = addJob(currentTask, jobClass);
            logger.info("添加成功: {} -> {}", currentTask, jobClass);
        }while (!removeJob(lastTask));
        logger.info("删除成功: {}", lastTask);
        return addJob;
    }

    /**
     * @Description: 移除一个任务
     *
     *
     * @param jobKey 任务描述
     * @param triggerKey 任务触发器描述
     */
    public static boolean removeJob(
            JobKey jobKey,
            TriggerKey triggerKey) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.rescheduleJob(triggerKey, new CalendarIntervalTriggerImpl());
            scheduler.deleteJob(jobKey);// 删除任务

            deleteScheduler(jobKey, triggerKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean removeJob(CronTaskDTO taskDTO) {
        return removeJob(taskDTO.getJobKey(), taskDTO.getTriggerKey());
    }

    /**
     * @Description: 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int deleteCronTrigger(TriggerKey triggerKey) {

        if (Objects.isNull(queryCronTrigger(triggerKey))) return 1;

        return qrtzCronTriggersMapper.delete(
                new LambdaQueryWrapper<QrtzCronTriggers>()
                        .select()
                        .eq(QrtzCronTriggers::getTriggerName, triggerKey.getName())
                        .eq(QrtzCronTriggers::getTriggerGroup, triggerKey.getGroup())
        );
    }

    public static int deleteTrigger(JobKey jobKey, TriggerKey triggerKey) {

        if (Objects.isNull(queryTrigger(jobKey, triggerKey))) return 1;

        return qrtzTriggersMapper.delete(
                new LambdaQueryWrapper<QrtzTriggers>()
                        .select()
                        .eq(QrtzTriggers::getTriggerName, triggerKey.getName())
                        .eq(QrtzTriggers::getTriggerGroup, triggerKey.getGroup())
                        .eq(QrtzTriggers::getJobName, jobKey.getName())
                        .eq(QrtzTriggers::getJobGroup, jobKey.getGroup())
        );
    }

    public static int deleteJobDetail(JobKey jobKey) {

        if (Objects.isNull(queryJobDetail(jobKey))) return 1;

        return qrtzJobDetailsMapper.delete(
                new LambdaQueryWrapper<QrtzJobDetails>()
                        .select()
                        .eq(QrtzJobDetails::getJobGroup, jobKey.getGroup())
                        .eq(QrtzJobDetails::getJobName, jobKey.getName())
        );
    }

    public static QrtzCronTriggers queryCronTrigger(TriggerKey triggerKey) {

        return qrtzCronTriggersMapper.selectOne(
                new LambdaQueryWrapper<QrtzCronTriggers>()
                        .select()
                        .eq(QrtzCronTriggers::getTriggerName, triggerKey.getName())
                        .eq(QrtzCronTriggers::getTriggerGroup, triggerKey.getGroup())
        );
    }

    public static QrtzTriggers queryTrigger(JobKey jobKey, TriggerKey triggerKey) {

        return qrtzTriggersMapper.selectOne(
                new LambdaQueryWrapper<QrtzTriggers>()
                        .select()
                        .eq(QrtzTriggers::getTriggerName, triggerKey.getName())
                        .eq(QrtzTriggers::getTriggerGroup, triggerKey.getGroup())
                        .eq(QrtzTriggers::getJobName, jobKey.getName())
                        .eq(QrtzTriggers::getJobGroup, jobKey.getGroup())
        );
    }

    public static QrtzJobDetails queryJobDetail(JobKey jobKey) {

        return qrtzJobDetailsMapper.selectOne(
                new LambdaQueryWrapper<QrtzJobDetails>()
                        .select()
                        .eq(QrtzJobDetails::getJobGroup, jobKey.getGroup())
                        .eq(QrtzJobDetails::getJobName, jobKey.getName())
        );
    }

    public static void deleteScheduler(JobKey jobKey, TriggerKey triggerKey) {

        while(true) if(deleteCronTrigger(triggerKey) != 0) break;
        while(true) if(deleteTrigger(jobKey, triggerKey) != 0) break;
        while(true) if(deleteJobDetail(jobKey) != 0) break;

    }

}
