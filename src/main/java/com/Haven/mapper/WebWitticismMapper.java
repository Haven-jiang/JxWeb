package com.Haven.mapper;

import com.Haven.entity.WebWitticism;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * WebWitticism连接数据库类 WebWitticismMapper
 *
 * @author HavenJust
 * @date 23:11 周五 29 四月 2022年
 */

@Mapper
@Repository
public interface WebWitticismMapper extends BaseMapper<WebWitticism> {
}