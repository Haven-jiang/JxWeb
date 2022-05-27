package com.Haven.mapper;

import com.Haven.entity.WebFunctionProfile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * WebFunctionProfile连接数据库类 WebFunctionProfileMapper
 *
 * @author HavenJust
 * @date 23:08 周五 29 四月 2022年
 */

@Mapper
@Repository
public interface WebFunctionProfileMapper extends BaseMapper<WebFunctionProfile> {
}