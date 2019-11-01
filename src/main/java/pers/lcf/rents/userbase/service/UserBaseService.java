package pers.lcf.rents.userbase.service;

import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserLoginAppInfo;
import pers.lcf.rents.userbase.model.UserMatch;
import pers.lcf.rents.userbase.model.UserStyle;
import pers.lcf.rents.utils.ResponseJson;

import java.util.List;

public interface UserBaseService {
   Integer  updataUserInfoById( UserInfo userInfo);
   List<UserInfo> getusreInfoById(String id);
    Integer insertUserStyle(UserStyle userStyle);
    Integer updateUserStyle(UserStyle userStyle);
    List<UserStyle> getStyleByUserInfoId(String userInfoId);
    List<UserMatch> getRentsByLoadnum(int loadNum, List<UserStyle> userStyles);
    ResponseJson userLoginByAppInfo(UserLoginAppInfo userLoginAppInfo);
    ResponseJson userRegisteredByAppInfo(UserLoginAppInfo userLoginAppInfo);
}

