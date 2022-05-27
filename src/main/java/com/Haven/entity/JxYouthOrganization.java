package com.Haven.entity;

import com.Haven.VO.JxYouthOrganizationVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.*;

/**
 * 江西青年大学习组织数据 面向数据库类 JxYouthOrganization
 *
 * @author HavenJust
 * @date 22:12 周一 02 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "youth_jx_organization")
public class JxYouthOrganization implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String organizationKey;

    private List<JxYouthOrganizationVO> organizationValue;

    public byte[] getOrganizationValue() {
        return JSON.toJSONBytes(organizationValue);
    }

    public void setOrganizationValue(byte[] organizationValue) {
        this.organizationValue = conversionJsonList(organizationValue);
    }


    public List<JxYouthOrganizationVO> gettingValue() {
        return organizationValue;
    }

    public void settingValue(List<JxYouthOrganizationVO> value) {
        this.organizationValue = value;
    }

    @JSONField(serialize = false)
    private List<JxYouthOrganizationVO> conversionJsonList(byte[] content) {
        List<JxYouthOrganizationVO> finish = new ArrayList<>();
        List<JSONObject> list = JSON.parseObject(content, List.class);
        if (Objects.isNull(list)) return finish;
        for (JSONObject obj : list) if (!obj.isEmpty())
            finish.add(JSON.parseObject(obj.toString(), JxYouthOrganizationVO.class));
        return finish;
    }
}
