package com.Haven.service.impl;

import com.Haven.entity.WebWitticism;
import com.Haven.mapper.WebWitticismMapper;
import com.Haven.service.WebWitticismService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 名言文案导出服务实现类 WebWitticismServiceImpl
 *
 * @author HavenJust
 * @date 00:19 周六 30 四月 2022年
 */

@Service
public class WebWitticismServiceImpl implements WebWitticismService {

    @Autowired
    private WebWitticismMapper webWitticismMapper;

    @Override
    public List<String> getWitticismList(int num) {

        List<WebWitticism> webWitticisms = webWitticismMapper.selectList(
                new LambdaQueryWrapper<WebWitticism>()
                        .select()
        );

        List<String> result = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int nextInt = new Random().nextInt(webWitticisms.size() - 1);
            result.add(webWitticisms.get(nextInt).getWitticism());
            webWitticisms.remove(nextInt);
        }

        return result;
    }
}