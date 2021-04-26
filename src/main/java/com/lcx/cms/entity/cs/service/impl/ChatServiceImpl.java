package com.lcx.cms.entity.cs.service.impl;

import com.lcx.cms.entity.cs.entity.Chat;
import com.lcx.cms.entity.cs.mapper.ChatMapper;
import com.lcx.cms.entity.cs.service.ChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.entity.sys.service.UserService;
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  service实现
 *
 * @author lcx
 * @since 2021-02-27
 */
@Slf4j
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Autowired
    UserService userService;

    @Override
    public List<Chat> findByCurrentUser(Long limit) {
        Integer currentUserId = RequestUtil.getCurrentUserId();
        return baseMapper.findByCurrentUser(currentUserId, limit);
    }

    @Override
    public List<Chat> findUnReadByAdmin() {
        return baseMapper.findUnReadByAdmin();
    }
}