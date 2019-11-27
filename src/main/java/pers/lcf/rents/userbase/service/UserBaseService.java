package pers.lcf.rents.userbase.service;

import pers.lcf.rents.userbase.model.*;
import pers.lcf.rents.utils.ResponseJson;

import java.util.List;

public interface UserBaseService {
    Integer updataUserInfoById(UserInfo userInfo);

    List<UserMatch> getUserInfoStyleById(String id);

    List<UserInfo> getusreInfoById(String id);

    Integer insertUserStyle(UserStyle userStyle);

    Integer updateUserStyle(UserStyle userStyle);

    List<UserStyle> getStyleByUserInfoId(String userInfoId);

    List<UserMatch> getRentsByLoadnum(int loadNum, List<UserStyle> userStyles);

    ResponseJson userLoginByAppInfo(UserLoginAppInfo userLoginAppInfo);

    ResponseJson userRegisteredByAppInfo(UserLoginAppInfo userLoginAppInfo);
     List<RentsOut> getUserOutsByMonth(String date,String userInfoId);
     Integer  addUserOuts(RentsOut rentsOut);

    Integer  delUserOut(String id);
}

