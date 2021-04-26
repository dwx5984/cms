package com.lcx.cms.entity.cs.service;

import com.lcx.cms.entity.cs.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  service
 *
 * @author lcx
 * @since 2021-02-27
 */
public interface ChatService extends IService<Chat> {


    List<Chat> findByCurrentUser(Long limit);

    List<Chat> findUnReadByAdmin();
}
