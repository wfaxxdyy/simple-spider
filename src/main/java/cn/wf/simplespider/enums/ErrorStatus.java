package cn.wf.simplespider.enums;

public enum ErrorStatus {

    /**
     * 内部错误
     */
    INTERNAL_SERVER_ERROR(10001, "系统错误"),
    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(10002, "参数错误"),

    /**
     * 缺少参数
     */
    MISSING_ARGUMENT(10003, "缺少参数"),
    /**
     * 业务错误
     */
    SERVICE_EXCEPTION(10004, "业务错误"),

    /**
     * 文件超过大小限制
     */
    MULTIPART_TOO_LARGE(10005, "文件太大"),
    /**
     * 非法状态
     */
    ILLEGAL_STATE(10006, "非法状态"),

    /**
     * 非法访问
     */
    ILLEGAL_ACCESS(10007, "非法访问,没有认证"),
    /**
     * 权限不足
     */
    UNAUTHORIZED(10008, "权限不足"),
    /**
     * 错误的请求
     */
    METHOD_NOT_ALLOWED(10009, "不支持的方法"),

    /**
     * 错误的请求
     */
    METHOD_NOT_FOUND(10010, "请求API不存在"),


    /**
     * 非法的请求，不支持的端、角色/身份等
     */
    ACCESS_NOT_ALLOWED(10011, "非法请求"),

    /**
     * 错误的请求，不支持的Content-Type
     */
    MEDIA_TYPE_NOT_ALLOWED(10012, "不支持的内容类型"),

    /**
     * 服务异常（一般指RPC或者依赖服务）
     */
    RPC_SERVICE_ERROR(10013, "服务异常"),

    /**
     * 重复请求异常（一般指违反数据库主键或者唯一键约束）
     */
    REPEATED_REQUEST_ERROR(10014, "请勿重复请求");

    private final long value;

    private final String message;


    ErrorStatus(int value, String message) {
        this.value = value;
        this.message = message;
    }


    /**
     * Return the integer value of this status code.
     */
    public long value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getMessage() {
        return this.message;
    }

}