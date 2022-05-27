package com.Haven.scheduler.mapper;

import com.Haven.scheduler.entity.QrtzTriggers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * QrtzTriggers连接数据库类 QrtzTriggersMapper
 *
 * @author HavenJust
 * @date 22:11 周二 17 五月 2022年
 */

@Mapper
@Repository
public interface QrtzTriggersMapper extends BaseMapper<QrtzTriggers> {
}