package com.Haven.VO;

import lombok.*;

import java.io.Serializable;

/**
 * 江西青年大学习组织数据面向传输类 JxYouthOrganizationDTO
 *
 * @author HavenJust
 * @date 22:14 周一 02 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JxYouthOrganizationVO implements Serializable {
    private String aid;
    private String id;
    private String level;
    private String memberCnt;
    private String pid;
    private String title;
}
