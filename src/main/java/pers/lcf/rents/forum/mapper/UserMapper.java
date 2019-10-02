package pers.lcf.rents.forum.mapper;
import org.apache.ibatis.annotations.Mapper;
import pers.lcf.rents.forum.model.User;


@Mapper
public interface UserMapper {

    public User findByUserName(String name);
//    public  void insertUser(User user);
}