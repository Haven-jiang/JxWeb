package com.Haven.scheduler.entity;

import lombok.*;

import java.io.Serializable;

/**
 * cron时间触发器面向数据库类 QrtzCronTriggers
 *
 * @author HavenJust
 * @date 17:00 周六 14 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrtzCronTriggers implements Serializable {
    private String schedName;
    private String triggerName;
    private String triggerGroup;
    private String cronExpression;
    private String timeZoneId;
}
