package cn.com.zx.ibossapi.common;

import java.io.Serializable;

/**
 * Created by huyong on 2017/10/20.
 */
public enum ChannelPayStatusEnum implements Serializable {

    PAYING(1),
    SUCCESS(2),
    FAILED(3),
    ORDER_EXPIRED(4),
    SUBMIT_FAILED(5),
    UNKNOWN(6);

    private final int value;

    public int getValue() {
        return value;
    }

    private ChannelPayStatusEnum(int value) {
        this.value = value;
    }


}
