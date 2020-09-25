package cn.wf.simplespider.model.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: fan.wang
 * @Date: 2020/9/25 10:33
 * @description:
 */
@Data
public class VideoResponse implements Serializable {
    private static final long serialVersionUID = -506855362001927099L;

    private Integer code;
    private String message;
    private Integer ttl;
    private VideoExtendResponse data;
}
