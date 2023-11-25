package com.wth.chat.common.common.enums;

import cn.hutool.core.annotation.scanner.FieldAnnotationScanner;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 22:42
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {

    NO(0, "否"),
    YES(1, "是"),
    ;

    private final Integer status;
    private final String desc;
}
