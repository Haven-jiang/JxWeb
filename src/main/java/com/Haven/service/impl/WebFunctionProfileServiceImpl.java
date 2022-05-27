package com.Haven.service.impl;

import com.Haven.VO.WebFunctionProfileVO;
import com.Haven.entity.WebFunctionProfile;
import com.Haven.mapper.WebFunctionProfileMapper;
import com.Haven.service.WebFunctionProfileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * function分区数据 服务实现类 WebFunctionProfileServiceImpl
 *
 * @author HavenJust
 * @date 00:26 周六 30 四月 2022年
 */

@Service
public class WebFunctionProfileServiceImpl implements WebFunctionProfileService {

    private final WebFunctionProfileMapper webFunctionProfileMapper;

    public WebFunctionProfileServiceImpl(WebFunctionProfileMapper webFunctionProfileMapper) {
        this.webFunctionProfileMapper = webFunctionProfileMapper;
    }

    @Override
    public void setWebFunctionProfile(WebFunctionProfileVO webFunctionProfileVO) {
        List<String> routes = new ArrayList<>();
        List<String> primaryDescribes = new ArrayList<>();
        List<String> minorDescribes = new ArrayList<>();

        List<WebFunctionProfile> webFunctionProfiles = webFunctionProfileMapper.selectList(
                new LambdaQueryWrapper<WebFunctionProfile>()
                        .select()
        );
        
        for (WebFunctionProfile webFunctionProfile : webFunctionProfiles) {
            routes.add(webFunctionProfile.getRoute());
            primaryDescribes.add(webFunctionProfile.getPrimaryDescribe());
            minorDescribes.add(webFunctionProfile.getMinorDescribe());
        }

        webFunctionProfileVO.setRoutes(routes);
        webFunctionProfileVO.setPrimaryDescribes(primaryDescribes);
        webFunctionProfileVO.setMinorDescribes(minorDescribes);
    }
}