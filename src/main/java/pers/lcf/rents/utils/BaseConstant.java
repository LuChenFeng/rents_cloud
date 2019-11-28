package pers.lcf.rents.utils;

/**
 * @ClassName BaseConstant
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/4 14:30
 **/
public class BaseConstant {
    public  static  final  String APP_CODE="c392f2d5b84a4dca971d01da5739ccb2";
    //用户信息--用户初始状态码
    public static final byte STATE_NORMAL = 1;
    //用户信息--初始实名
    public static final byte REAL_NAME = 0;
    public static final byte YES_REAL_NAME = 1;

    //    用户状态 1 正常
    public static final byte IS_STATE = 1;
    //    用户信息--初始头像
    public static final String AVATAR_NORMAL = "http://47.102.192.121:8000/rents/rentsUpLoad/avatarImage/head.jpg";

    //室友匹配--步长值
    public static final Integer MATCH_STEP = 10;
    //室友匹配--指数最大差
    public static final float MATCH_SIMILAR = 20;
    //室友匹配--匹配参数个数
    public static final float MATCH_NUM = 5;

    //linux下文件上传路径
    public static final String IMG_PATH = "/srv/rentsUpLoad/";
    //windows 文件上传测试路径
//    public static  final  String IMG_PATH="D:/rentsUpLoad/";
    public static final String IMG_URL = "http://47.102.192.121:8000/rents/rentsUpLoad/";

    public static final Byte YES_HAS_HANDLE=1;
}
