package com.qbb.shortmsg.shortmsg.mapper;

import com.qbb.shortmsg.shortmsg.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import java.util.List;

@Repository
public interface UserMapper {

        public List<User> getUserList();

        public int add(User user);

        public User getUserByPhone(String phone);

}
