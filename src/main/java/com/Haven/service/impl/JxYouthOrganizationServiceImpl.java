package com.Haven.service.impl;

import com.Haven.VO.JxYouthOrganizationVO;
import com.Haven.DTO.JxYouthOrganizationMapDTO;
import com.Haven.entity.JxYouthOrganization;
import com.Haven.mapper.JxYouthOrganizationMapper;
import com.Haven.service.JxYouthOrganizationService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.Haven.utils.HttpsClientUtil.doDownloadString;

/**
 * 根据院校获取nid 服务实现类 JxYouthNidServiceImpl
 *
 * @author HavenJust
 * @date 12:58 周日 01 五月 2022年
 */

@Service
public class JxYouthOrganizationServiceImpl implements JxYouthOrganizationService {

    @Autowired
    private JxYouthOrganizationMapper jxYouthOrganizationMapper;

    @Override
    public List<JxYouthOrganizationVO> searchNextNode(String curID) {
        JxYouthOrganization jxYouthOrganization = jxYouthOrganizationMapper.selectById(curID);
        if (Objects.isNull(jxYouthOrganization)) {
            return null;
        }
        return jxYouthOrganization.gettingValue();
    }

    @Override
    public void jxOrganizationUpdate() {
        String content = doDownloadString("http://osscache.vol.jxmfkj.com/html/assets/js/org_data.js?v=908");
        content = content.substring(0, content.length()-1)+"}";
        content = content.replace("var organizationJson = ", "{\"data\":");

        JxYouthOrganizationMapDTO jxYouthOrganization = JSON.parseObject(content, JxYouthOrganizationMapDTO.class);

        for (Map.Entry<String, List<JxYouthOrganizationVO>> stringListEntry : jxYouthOrganization.getData().entrySet()) {
            JxYouthOrganization jxYouthOrganization1 = jxYouthOrganizationMapper.selectById(stringListEntry.getKey());

            if (Objects.nonNull(jxYouthOrganization1)) {
                if (stringListEntry.getValue() == jxYouthOrganization1.gettingValue()) continue;
                jxYouthOrganization1.settingValue(stringListEntry.getValue());
                jxYouthOrganizationMapper.updateById(jxYouthOrganization1);
            }else
                jxYouthOrganizationMapper.insert(new JxYouthOrganization(stringListEntry.getKey(), stringListEntry.getValue()));
        }
    }
}

//<dl class="layui-anim layui-anim-upbit" style="">
// <dd lay-value="" class="layui-select-tips layui-this">系统选择</dd>
// <dd lay-value="N0017" class="">团省委机关</dd>
// <dd lay-value="N0016" class="">省直属单位团委</dd>
// <dd lay-value="N0013" class="">省属本科院校团委</dd>
// <dd lay-value="N0014" class="">非省属本科院校团委</dd>
// <dd lay-value="N0015" class="">高职专科院校团委</dd>
// <dd lay-value="N0002" class="">南昌市</dd>
// <dd lay-value="N0003" class="">九江市</dd>
// <dd lay-value="N0004" class="">景德镇市</dd>
// <dd lay-value="N0005" class="">萍乡市</dd>
// <dd lay-value="N0006" class="">新余市</dd>
// <dd lay-value="N0007" class="">鹰潭市</dd>
// <dd lay-value="N0008" class="">赣州市</dd>
// <dd lay-value="N0009" class="">宜春市</dd>
// <dd lay-value="N0010" class="">上饶市</dd>
// <dd lay-value="N0011" class="">吉安市</dd>
// <dd lay-value="N0012" class="">抚州市</dd></dl>