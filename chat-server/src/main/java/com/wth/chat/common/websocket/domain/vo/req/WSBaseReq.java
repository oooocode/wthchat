package com.wth.chat.common.websocket.domain.vo.req;


import com.wth.chat.common.websocket.domain.enums.WSReqTypeEnum;
import lombok.Data;

/**
 * @Author: wth
 * @Create: 2023/10/17 - 22:57
 */
@Data
public class WSBaseReq {

    /**
     * @see WSReqTypeEnum
     */
    private Integer type;

    /**
     * 每个请求包具体的数据，类型不同结果不同
     */
    private String data;
}
