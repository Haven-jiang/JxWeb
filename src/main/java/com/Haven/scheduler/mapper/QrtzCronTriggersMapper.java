package com.Haven.scheduler.mapper;

import com.Haven.scheduler.entity.QrtzCronTriggers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户定时表查询连接数据库类 QrtzCronTriggersMapper
 *
 * @author HavenJust
 * @date 17:06 周六 14 五月 2022年
 */

@Mapper
@Repository
public interface QrtzCronTriggersMapper extends BaseMapper<QrtzCronTriggers> {
}