package cn.com.zx.ibossapi.common;

/**
 * @author  lvxuezhan
 */
public final class Constant {
    /**
     * 未知异常编码
     */

    public static final int UNKNOWN_EXCEPTION_CODE = -999;

    /**
     * 请求服务成功状态
     */
    public static final Integer SERVER_SUCCESS = 200;

    /**
     * 系统异常状态
     */
    public static final Integer SYSTEM_EXCEPTION = 400;

    /**
     * 开发模式
     */
    public static final String DEV = "dev";

    /**
     * 文章评论集合（表）名
     */
    public static final String ATLTBL = "atl_table";

    /**
     * 用户收藏评论集合（表）名
     */
    public static final String COLLECTTABLE = "collect_table";

    /**
     * 用户操作集合（表）名
     */
    public static final String OPRTBL = "opr_table";

    /**
     * 用户举报集合（表）名
     */
    public static final String ACCUTBL = "accu_table";

    /**
     * 文章评论集合（内容）
     */
    public static final String COMMENTS = "comments";

    /**
     * 文章编号
     */
    public static final String ATLID = "atlId";

    /**
     * 操作人的openid
     */
    public static final String OPROPENID = "oprOpenid";

    /**
     * 被操作人的openid
     */
    public static final String BEOPROPENID = "beOprOpenid";

    /**
     * 被操作人评论编号
     */
    public static final String CMTNO = "cmtNo";

    /**
     * 产品类型--专家文章
     */
    public static final Integer EXPERTARTICLE = 1;

    /**
     * 产品类型--专家视频
     */
    public static final Integer EXPERTVIDEO = 2;

    /**
     * 产品类型--专家课程
     */
    public static final Integer EXPERTCOURSE = 3;

    /**
     * 产品类型--线下课程
     */
    public static final Integer EDUCATECOURSE = 4;

    /**
     * 专家
     */
    public static final Integer EXPERT = 1;

    /**
     * 投资顾问
     */
    public static final Integer INVESTMENTADVISOR = 2;

    /**
     * token秘钥
     */
    public static final String TOKEN_SECRETKEY = "e10adc3949ba59abbe56e003r5yf883e";

    /**
     * 未收藏状态
     */
    public static final Integer NOT_COLLECTED = 0;

    /**
     * 已收藏状态
     */

    public static final Integer COLLECTED = 1;
    /**
     * 未关注状态
     */
    public static final Integer NOT_FOLLOWED = 0;

    /**
     * 已关注状态
     */
    public static final Integer FOLLOWED = 1;

    /**
     * 未点赞状态
     */
    public static final Integer NOT_SUPPORT = 0;

    /**
     * 已点赞状态
     */
    public static final Integer SUPPORT = 1;

    /**
     * redis的用户登录token
     */
    public static final String USERAUTH = "userAuth";

    /**
     * 用户点赞的对象key
     */
    public static final String USERSUPPORT = "userSupport";

    /**
     * 用户操作类型--点赞
     */
    public static final Integer USERSUPPORTTYPE = 1;

    /**
     * 用户操作类型--收藏
     */
    public static final Integer USERCOLLECTTYPE = 2;

    /**
     * 用户操作类型--阅读
     */
    public static final Integer USERREADTYPE = 3;

    /**
     * 用户操作类型--转发
     */
    public static final Integer USERTRANSFERTYPE = 4;

    /**
     * 用户操作类型--分享
     */
    public static final Integer USERSHARETYPE = 5;

    /**
     * 用户操作类型--评论
     */
    public static final Integer USERCOMMENTTYPE = 6;

    /**
     * 用户评论操作类型--点赞
     */
    public static final Integer USERAPPR = 1;

    /**
     * 用户评论操作类型--点踩
     */
    public static final Integer USEROPPO = 2;

    /**
     * 用户评论操作类型--字段名
     */
    public static final String OPRTYPE = "oprType";

    /**
     * 用户评论评论级别--字段名
     */
    public static final String COMMENTLEVEL = "commentLevel";

    /**
     * 测试token--joy
     */
    public static final String testToken1 = "8685EB9F68AE1333AD20EF579327E3AADDA155ABDAC1840282257F22668F22C139683539904DEFF505D0B0435A99E365F5A25FA6D6BCDC2C9C952E8B44F1A06F62A049C8B452561DA487EDF251CB34AD2913D8787822E061DD0522D5F8A8083DD08BB25EC8B50169AC6EDAAF396C18D945656570D68DEB7734D31E34D88CCCDF";

    /**
     * 测试token--snoopy
     */
    public static final String testToken2 = "8685EB9F68AE1333AD20EF579327E3AAC0BA00D51E473A84F4CE83F6C777A35D9B5B7A999E62CEFC621127BF54D41E3198C89E01C177CA60FD4E12E470BE112B57C3D57EBB56E880AE06F46416757F9197A8FBB5336893B51228657D8F0FC9BD9FD6FEC9D6C90B6C881A0BF00889964F64B386D7FFDF3F983E97BD223563C88A";

    /**
     * 测试token--书简
     */
    public static final String testToken3 = "8685EB9F68AE1333AD20EF579327E3AA53DC97F6CF1B73BF08EC6BE5730545C6FD4A800F19A5FCFA64E789822B9DB608FE5EA4D50E101C3F7FFB298FBC6799F2B451054FD3FBB5ED235BCBD2920849AAE335EE8C39EC1E771E94DAFAE663DD6CB21CEF72DC96F5FE72439D85DC0E7EEB46D2B27549448B2C65FA709C0FB21C74";

    /**
     * 测试token--花猫猫
     */
    public static final String testToken4 = "8685EB9F68AE1333AD20EF579327E3AA84716E795C3C9BDCFE3D84C3977078A56DED33A180ACFE962A08BEB0C0F0D9EFAA1A587F69654F85F4235003B32468F3DB7C9F5BC4D6681D2C745DB96E54B498CD2F885AD5F9122114E6EC8A394AC0D8CF1065C1EC1356A2DBF096A4D032219F991EB634961D5C8215AB8F38A602390A";




}