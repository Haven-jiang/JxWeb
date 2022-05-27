package com.Haven.scheduler.mapper;

import com.Haven.scheduler.entity.QrtzLocks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * QrtzLocks连接数据库类 QrtzLocksMapper
 *
 * @author HavenJust
 * @date 22:10 周二 17 五月 2022年
 */

@Mapper
@Repository
public interface QrtzLocksMapper extends BaseMapper<QrtzLocks> {
}