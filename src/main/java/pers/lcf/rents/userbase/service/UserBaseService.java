package pers.lcf.rents.userbase.service;

import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserMatch;
import pers.lcf.rents.userbase.model.UserStyle;

import java.util.List;

public interface UserBaseService {
    Integer insertUserStyle(UserStyle userStyle);
    Integer updateUserStyle(UserStyle userStyle);
    List<UserStyle> getStyleByUserInfoId(String userInfoId);
    List<UserMatch> getRentsByLoadnum(int loadNum, List<UserStyle> userStyles);
}

