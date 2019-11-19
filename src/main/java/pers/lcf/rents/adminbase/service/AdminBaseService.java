package pers.lcf.rents.adminbase.service;

import pers.lcf.rents.adminbase.model.*;

import java.util.List;

public interface AdminBaseService {

    UserDTO getOrdinaryUsersByDTO(UserDTO userInfoPage);

    UserDTO getAdminUsersDTO(UserDTO userInfoPage);

    Integer updateOrdinaryUser(User user);

    Integer delOrdinaryUserById(List<String> ids);

    OrdinaryUsersPei getOrdinaryUsersPei();

    PostsReportDTO getPostsReportsByDTO(PostsReportDTO postsReportDTO);

    Integer updatePostsReports(Report report);

    Integer delPostsReportById(List<String> ids);

    UserRealNameDTO getUserRealNamesByDTO(UserRealNameDTO userRealNameDTO);

    Integer updateUserRealNames(UserRealName userRealName);

    Integer delUserRealNameById(List<String> ids);

    PostDetailsDTO getPostsInfoByDTO(PostDetailsDTO postDetailsDTO);
}
