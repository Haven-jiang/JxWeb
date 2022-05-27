package com.Haven.scheduler.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 泛时间触发器面向数据库类 QrtzTriggers
 *
 * @author HavenJust
 * @date 21:41 周二 17 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrtzTriggers implements Serializable {

    /**
     * sched_name - 泛时间触发器数据
     */

    private String schedName;

    /**
     * trigger_name - 泛时间触发器数据
     */

    private String triggerName;

    /**
     * trigger_group - 泛时间触发器数据
     */

    private String triggerGroup;

    /**
     * job_name - 泛时间触发器数据
     */

    private String jobName;

    /**
     * job_group - 泛时间触发器数据
     */

    private String jobGroup;

    /**
     * description - 泛时间触发器数据
     */

    private String description;

    /**
     * next_fire_time - 泛时间触发器数据
     */

    private Long nextFireTime;

    /**
     * prev_fire_time - 泛时间触发器数据
     */

    private Long prevFireTime;

    /**
     * priority - 泛时间触发器数据
     */

    private Integer priority;

    /**
     * trigger_state - 泛时间触发器数据
     */

    private String triggerState;

    /**
     * trigger_type - 泛时间触发器数据
     */

    private String triggerType;

    /**
     * start_time - 泛时间触发器数据
     */

    private Long startTime;

    /**
     * end_time - 泛时间触发器数据
     */

    private Long endTime;

    /**
     * calendar_name - 泛时间触发器数据
     */

    private String calendarName;

    /**
     * misfire_instr - 泛时间触发器数据
     */

    private Integer misfireInstr;

    /**
     * job_data - 泛时间触发器数据
     */

    private byte[] jobData;
}