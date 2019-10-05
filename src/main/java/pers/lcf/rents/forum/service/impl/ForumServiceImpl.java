package pers.lcf.rents.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.forum.mapper.PostsTypeMapper;
import pers.lcf.rents.forum.model.PostsType;
import pers.lcf.rents.forum.model.PostsTypeExample;
import pers.lcf.rents.forum.service.ForumService;
import pers.lcf.rents.userbase.model.UserStyleExample;

import java.util.List;

/**
 * @ClassName ForumServiceImpl
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/5 21:25
 **/
@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private PostsTypeMapper postsTypeMapper;

    @Override
    public List<PostsType> selectTypeByIsHave(Byte isHave) {
        PostsTypeExample example = new PostsTypeExample();
        PostsTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsHaveEqualTo(isHave);
        List<PostsType> postsTypes = postsTypeMapper.selectByExample(example);
        return postsTypes;
    }
}
