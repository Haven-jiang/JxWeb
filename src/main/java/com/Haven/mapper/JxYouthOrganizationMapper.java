package com.Haven.mapper;

import com.Haven.entity.JxYouthOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 连接数据库类 JxYouthOrganizationMapper
 *
 * @author HavenJust
 * @date 00:45 周二 03 五月 2022年
 */

@Mapper
@Repository
public interface JxYouthOrganizationMapper extends BaseMapper<JxYouthOrganization> {
}