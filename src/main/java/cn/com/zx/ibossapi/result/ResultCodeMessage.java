package cn.com.zx.ibossapi.result;

/**
 * @author lvxuezhan
 * @version 2019-04-22 14:48
 */
public enum ResultCodeMessage {
    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION(500,"系统异常，请联系管理员"),
    UNKNOWN_EXCEPTION(501,"未知错误，请联系开发人员"),
    TOKEN_EMPTY(502,"TOKEN为空或错误"),
    TOKEN_DIFFERENT(503,"TOKEN不一致，服务请求失败"),
    COLLECT_FAIL(504,"收藏失败，请联系管理员"),
    SAVEWXUSER_FAIL(506,"创建微信用户失败"),
    SAVEACCOUNTUSER_FAIL(507,"创建系统用户失败"),
    FOLLOW_FAIL(509,"关注失败，请联系管理员"),
    WXUSERINFO_EMPTY(510,"获取微信用户信息失败"),
    COLLECTLIST_EMPTY(511,"收藏列表为空"),
    COLLETTYPE_ERROR(512,"收藏类型错误"),
    FOLLOWTYPE_ERROR(513,"关注类型错误"),
    FOLLOWLIST_EMPTY(514,"关注列表为空"),
    CANCELCOLLECT_FAIL(515,"取消收藏失败"),
    CANCELFOLLOW_FAIL(516,"取消关注失败"),
    EXPERTARTICLE_NOT_EXISTS(517,"专家文章不存在"),
    EXPERTVIDEO_NOT_EXISTS(518,"专家视频不存在"),
    EXPERTCOURSE_NOT_EXISTS(519,"线上课程不存在"),
    EDUCATECOURSE_NOT_EXISTS(520,"线下课程不存在"),
    EXPERT_NOT_EXISTS(521,"专家不存在"),
    INSERT_SUPPORT_RECOURD_FAIL(522,"添加点赞记录失败"),
    INSERT_SUPPORT_SUM_FAIL(523,"添加点赞总数失败"),
    UPDATE_SUPPORT_SUM_FAIL(524,"更新点赞总数失败"),
    USER_COMMENT_EXCEPTION(525,"用户评论异常"),
    USER_REPLY_EXCEPTION(526,"用户回复异常"),
    COLLECT_COMMENT_EXCEPTION(533,"收藏评论异常"),
    GET_REPLYLIST_EXCEPTION(535,"获取二级回复列表异常"),
    REMOVE_COMMENT_EXCEPTION(534,"取消收藏失败"),
    USER_ARRP_EXCEPTION(533,"用户点赞异常"),
    GET_COMMENTS_EXCEPTION(527,"获取评论列表失败"),
    GET_COMMENT_NUM_EXCEPTION(533,"获取评论总数失败"),
    COMMENT_ALREADY(528,"您已经评论过"),
    APPR_ALREADY(529,"您已经点赞过"),
    OPPO_ALREADY(531,"您已经点踩过"),
    ACCU_ALREADY(532,"您已经举报过"),
    REPLY_ALREADY(530,"您已经回复过"),
    PARAM_EXCEPTION(-999,"参数格错误或参数为空"),

    SUCCESS(200,"SUCCESS"),
    COLLECT_SUCCESS(201,"收藏成功"),
    CANCEL_COLLECT_SUCCESS(202,"取消收藏成功"),
    FOLLOW_SUCCESS(203,"关注成功"),
    CANCEL_FOLLOW_SUCCESS(204,"取消关注成功"),
    FAIL(400,"FAIL"),
    ;

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(int code){
        for(ResultCodeMessage responseMessage : ResultCodeMessage.values()){
            if(responseMessage.getCode() == code){
                return responseMessage.getMessage();
            }
        }
        return  null;
    }
}
