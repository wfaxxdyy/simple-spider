package cn.wf.simplespider.model;


import cn.wf.simplespider.enums.ErrorStatus;
import lombok.Data;

@Data
public class Resp<T, ErrT> {

    private long errcode;
    private String errmsg;
    private T data;
    private ErrT errData;
    private Long serverTime = System.currentTimeMillis();

    private Resp() {
    }

    public static <T, ErrT> Resp<T, ErrT> success() {
        return success(null);
    }

    public static <T, ErrT> Resp<T, ErrT> success(T data) {
        return success("操作成功", data);
    }

    public static <T, ErrT> Resp<T, ErrT> success(String msg, T data) {
        return create(0L, msg, data, null);
    }

    public static <T, ErrT> Resp<T, ErrT> error() {
        return error(1L, "服务在开小差，请稍等", null, null);
    }

    public static <T, ErrT> Resp<T, ErrT> error(long code, String msg) {
        return error(code, msg, null, null);
    }

    public static <T, ErrT> Resp<T, ErrT> error(long code, String msg, T data, ErrT errData) {
        return create(code, msg, data, errData);
    }

    public static <T, ErrT> Resp<T, ErrT> failure(ErrorStatus e) {
        return error(e.value(), e.getMessage());
    }

    public static <T, ErrT> Resp<T, ErrT> create(long code, String msg, T data, ErrT errData) {
        Resp<T, ErrT> resp = new Resp<>();
        resp.setErrcode(code);
        resp.setErrmsg(msg);
        resp.setData(data);
        resp.setErrData(errData);
        return resp;
    }
}