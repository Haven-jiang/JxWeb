package com.Haven.DTO;

import com.Haven.VO.JxYouthOrganizationVO;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 组织数据map集合面向传输类 JxYouthOrganizationMapDTO
 *
 * @author HavenJust
 * @date 00:43 周二 03 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JxYouthOrganizationMapDTO implements Serializable {
    private Map<String, List<JxYouthOrganizationVO>> data;
}
