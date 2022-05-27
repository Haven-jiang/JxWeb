package com.Haven.scheduler.mapper;

import com.Haven.scheduler.entity.QrtzJobDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * QrtzJobDetails连接数据库类 QrtzJobDetailsMapper
 *
 * @author HavenJust
 * @date 22:06 周二 17 五月 2022年
 */

@Mapper
@Repository
public interface QrtzJobDetailsMapper extends BaseMapper<QrtzJobDetails> {
}