package com.Haven.service;

import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.entity.UserYouthData;

import java.util.List;

/**
 * 青年大学习数据服务类 UserYouthDataService
 *
 * @author HavenJust
 * @date 23:21 周二 19 四月 2022年
 */

public interface UserYouthDataService {

    void addUserYouthData(String userid, String nid, String cron, String email, String realName);

    void addUserYouthData(UserYouthInfoVO userYouthInfo);

    void addUserYouthData(List<UserYouthInfoVO> userYouthInfoList);

    void removeUserYouthData(String userid, String nid);

    void removeUserYouthData(UserYouthInfoVO userYouthInfo);

    void updateUserYouthData(String uuid, String userid, String nid, String cron, String email, String realName);

    void updateUserYouthData(UserYouthInfoVO userYouthInfo);

    UserYouthDataDTO queryUserYouthInfoOne(String userid, String nid);

    UserYouthDataDTO queryUserYouthInfoOne(UserYouthInfoVO userYouthInfoVO);

    List<UserYouthDataDTO> queryUserYouthInfoByStatus(boolean isFinish);

    List<UserYouthDataDTO> queryUserYouthInfoAll();

    void fixStatus();

    UserYouthData selectYouthData(String userid);

    UserYouthReloadDataDTO selectYouthDataDTO(String userid);

}
