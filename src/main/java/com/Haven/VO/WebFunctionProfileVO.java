package com.Haven.VO;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * function分区配置数据 面向前端类 WebFunctionProfileVO
 *
 * @author HavenJust
 * @date 00:29 周六 30 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebFunctionProfileVO implements Serializable {

    private List<String> codes;

    private List<String> routes;

    private List<String> primaryDescribes;

    private List<String> minorDescribes;
}
