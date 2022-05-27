package com.Haven.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 网站谚语 面向数据库类 FunctionData
 *
 * @author HavenJust
 * @date 22:35 周五 29 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebWitticism implements Serializable {

    private String witticism;

}
