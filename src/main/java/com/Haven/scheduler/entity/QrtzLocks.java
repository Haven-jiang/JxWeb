package com.Haven.scheduler.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 定时时间表锁 面向数据库类 QrtzLocks
 *
 * @author HavenJust
 * @date 21:45 周二 17 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrtzLocks implements Serializable {

    /**
     * sched_name - 定时表名
     */

    private String schedName;

    /**
     * lock_name - 锁名
     *  - TRIGGER_ACCESS
     */

    private String lockName;
}
