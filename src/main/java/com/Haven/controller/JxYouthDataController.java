package com.Haven.controller;

import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.VO.Respond;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.Haven.enums.StatusCodeEnum.USERNAME_EXIST;
import org.springframework.stereotype.*;

import java.util.List;

/**
 * 江西青年大学习数据操作 逻辑控制类 JxYouthDataController
 *
 * @author HavenJust
 * @date 19:29 周一 09 五月 2022年
 */

@RequestMapping("/function/youthlearn/jiangxi")
@RestController
public class JxYouthDataController {

    @Autowired
    private UserYouthDataService userYouthDataService;

    @PostMapping("/register")
    public Respond<?> registerYouth(UserYouthInfoVO userYouthInfoVO) {
        userYouthDataService.addUserYouthData(userYouthInfoVO);
        return Respond.success();
    }

    @PostMapping("/search")
    public Respond<?> searchYouthById(UserYouthInfoVO userYouthInfoVO) {
        return Respond.success(userYouthDataService.queryUserYouthInfoOne(userYouthInfoVO));
    }

    @PostMapping("/search_a")
    public Respond<?> searchYouthAll() {
        return Respond.success(userYouthDataService.queryUserYouthInfoAll());
    }

    @PostMapping("/search_un")
    public Respond<?> searchYouthByStatus(Boolean status) {
        return Respond.success(userYouthDataService.queryUserYouthInfoByStatus(status));
    }

    @PostMapping("/del")
    public Respond<?> deleteYouthById(UserYouthInfoVO userYouthInfoVO) {
        userYouthDataService.removeUserYouthData(userYouthInfoVO);
        return Respond.success();
    }

    @PostMapping("/up")
    public Respond<?> updateYouthById(UserYouthInfoVO userYouthInfoVO) {
        userYouthDataService.updateUserYouthData(userYouthInfoVO);
        return Respond.success();
    }
}