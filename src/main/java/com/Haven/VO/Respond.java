package com.Haven.VO;

import lombok.*;

import java.io.Serializable;

import static com.Haven.enums.StatusCodeEnum.FAIL;
import static com.Haven.enums.StatusCodeEnum.SUCCESS;

/**
 * 返回包装面向前端类 Respond
 *
 * @author HavenJust
 * @date 13:17 周一 09 五月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Respond<Type> implements Serializable {

    private Integer status;
    private String desc;
    private Boolean flag;
    private Type content;

    public static<Type> Respond<Type> success() {
        return setRespond(SUCCESS.getCode(), SUCCESS.getDescribe(), true, null);
    }

    public static<Type> Respond<Type> success(Type content) {
        return setRespond(SUCCESS.getCode(), SUCCESS.getDescribe(), true, content);
    }

    public static<Type> Respond<Type> success(Integer status, Type content) {
        return setRespond(status, SUCCESS.getDescribe(), true, content);
    }

    public static<Type> Respond<Type> success(Integer status, String desc, Type content) {
        return setRespond(status, desc, true, content);
    }

    public static<Type> Respond<Type> fail(Integer status, String desc, Type content) {
        return setRespond(status, desc, true, content);
    }

    public static<Type> Respond<Type> fail(Integer status, Type content) {
        return setRespond(status, FAIL.getDescribe(), true, content);
    }

    public static<Type> Respond<Type> fail(Type content) {
        return setRespond(FAIL.getCode(), FAIL.getDescribe(), true, content);
    }

    public static<Type> Respond<Type> setRespond(
            Integer status,
            String desc,
            Boolean flag,
            Type content ) {

        return new Respond<Type>(status, desc, flag, content);
    }
}
