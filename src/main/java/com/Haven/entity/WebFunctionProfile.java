package com.Haven.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 功能分区数据 面向数据库类 WebFunctionProfile
 *
 * @author HavenJust
 * @date 22:51 周五 29 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebFunctionProfile implements Serializable {
    private String route;
    private String primaryDescribe;
    private String minorDescribe;
}
