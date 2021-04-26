package com.lcx.cms.entity.cs.mapper;

import com.lcx.cms.entity.cs.entity.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*  Mapper 接口
*
* @author lcx
* @since 2021-02-27
*/
public interface ChatMapper extends BaseMapper<Chat> {

    List<Chat> findByCurrentUser(@Param("userId") Integer userId, @Param("limit") Long limit);

    List<Chat> findUnReadByAdmin();

}
