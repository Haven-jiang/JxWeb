package com.Haven.service;

import com.Haven.VO.JxYouthOrganizationVO;

import java.util.List;

/**
 * 根据院校获取nid服务类 JxYouthNidService
 *
 * @author HavenJust
 * @date 12:57 周日 01 五月 2022年
 */

public interface JxYouthOrganizationService {
    List<JxYouthOrganizationVO> searchNextNode(String curID);

    void jxOrganizationUpdate();
}
