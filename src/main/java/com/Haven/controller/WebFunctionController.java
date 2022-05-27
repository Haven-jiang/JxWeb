package com.Haven.controller;

import com.Haven.VO.WebFunctionProfileVO;
import com.Haven.service.WebFunctionProfileService;
import com.Haven.service.WebWitticismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

/**
 * 网站功能分区逻辑控制类 WebFunctionController
 *
 * @author HavenJust
 * @date 00:47 周六 30 四月 2022年
 */

@RestController
@RequestMapping("/function")
public class WebFunctionController {

    @Autowired
    private WebWitticismService webWitticismService;

    @Autowired
    private WebFunctionProfileService webFunctionProfileService;


    @PostMapping("/getValue")
    @ResponseBody
    public WebFunctionProfileVO getValue() {
        WebFunctionProfileVO webFunctionProfileVO = new WebFunctionProfileVO();
        webFunctionProfileService.setWebFunctionProfile(webFunctionProfileVO);
        webFunctionProfileVO.setCodes(webWitticismService.getWitticismList(5));
        return webFunctionProfileVO;
    }
}