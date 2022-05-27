package com.Haven.scheduler.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 任务清单面向数据库类 QrtzJobDetails
 *
 * @author HavenJust
 * @date 21:43 周二 17 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrtzJobDetails implements Serializable {

    /**
     * schedName = 任务清单数据
     */

    private String schedName;

    /**
     * jobName = 任务清单数据
     */

    private String jobName;

    /**
     * jobGroup = 任务清单数据
     */

    private String jobGroup;

    /**
     * description = 任务清单数据
     */

    private String description;

    /**
     * jobClassName = 任务清单数据
     */

    private String jobClassName;

    /**
     * isDurable = 任务清单数据
     */

    private String isDurable;

    /**
     * isNonconcurrent = 任务清单数据
     */

    private String isNonconcurrent;

    /**
     * isUpdateData = 任务清单数据
     */

    private String isUpdateData;

    /**
     * requestsRecovery = 任务清单数据
     */

    private String requestsRecovery;

    /**
     * jobData = 任务清单数据
     */

    private byte[] jobData;
}
