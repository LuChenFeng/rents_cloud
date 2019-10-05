package pers.lcf.rents.forum.service;

import pers.lcf.rents.forum.model.PostsType;

import java.util.List;

public interface ForumService {
    List<PostsType> selectTypeByIsHave(Byte isHave);
}
