package com.Haven.controller;

import com.Haven.VO.JxYouthOrganizationVO;
import com.Haven.VO.Respond;
import com.Haven.service.JxYouthOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Objects;

import static com.Haven.enums.StatusCodeEnum.NOT_FOUND;

/**
 * 查询nid逻辑控制类 JxYouthOrganizationController
 *
 * @author HavenJust
 * @date 13:16 周二 03 五月 2022年
 */

@RestController
@RequestMapping("/function/youthlearn/jiangxi")
public class JxYouthOrganizationController {

    @Autowired
    private JxYouthOrganizationService jxYouthOrganizationService;

    @PostMapping("/getnext")
    public Respond<List<JxYouthOrganizationVO>> getOrganizationByNid(String nid) {
        List<JxYouthOrganizationVO> jxYouthOrganizationVOS = jxYouthOrganizationService.searchNextNode(nid);
        if (Objects.isNull(jxYouthOrganizationVOS))
            return Respond.success(NOT_FOUND.getCode(), NOT_FOUND.getDescribe(), jxYouthOrganizationVOS);
        return Respond.success(jxYouthOrganizationVOS);
    }
}