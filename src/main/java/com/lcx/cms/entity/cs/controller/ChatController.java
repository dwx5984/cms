package com.lcx.cms.entity.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.service.RoleService;
import com.lcx.cms.entity.sys.service.UserService;
import com.lcx.cms.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lcx.cms.base.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lcx.cms.base.Response;
import com.lcx.cms.enums.Bool;


import com.lcx.cms.entity.cs.service.ChatService;
import com.lcx.cms.entity.cs.entity.Chat;

import java.util.List;

/**
 * controller
 *
 * @author lcx
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/rest/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/teacher/list")
    public Response findByCurrentUser(Long limit) {
        return Response.OK(chatService.findByCurrentUser(limit));
    }

    @GetMapping("/admin/list")
    public Response findByAdmin(Long limit) {
        return Response.OK(chatService.findByCurrentUser(limit));
    }


    @GetMapping("/page")
    public Response page(Long page, Long limit, @RequestParam(value = "teacher") boolean teacher
    , @RequestParam(required = false) String content) {
        QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
        if (teacher) {
            queryWrapper.eq("teacher_id", RequestUtil.getCurrentUserId());
        }
        if (StringUtils.isNotBlank(content)) {
            queryWrapper.like("content", content);
        }
        Page<Chat> page1 = chatService.page(new Page<>(page, limit), queryWrapper);
        List<Chat> records = page1.getRecords();
        for (Chat chat : records) {
            User teacherRole = userService.findWithRole(chat.getSenderId());
            chat.setSender(teacherRole);
            if (!teacherRole.getRole().getId().equals(300)) {
                if (Bool.No.getValue().equals(chat.getReaded())) {
                    chat.setStatus("未读");
                } else {
                    chat.setStatus("已读");
                }
            }
            chat.setSenderRole(teacherRole.getRole().getName());
            chat.setTeacher(userService.findWithRole(chat.getTeacherId()));
        }
        return Response.OK(page1);
    }

    /**
     * 新增Chat
     *
     * @param chat
     * @return ChatDomain
     */
    @PostMapping
    public Response save(@RequestBody Chat chat) {
        Integer currentUserId = RequestUtil.getCurrentUserId();
        chat.setSenderId(currentUserId);
        if (chat.getTeacherId() == null) {
            chat.setTeacherId(currentUserId);
        }
        chat.setReaded(Bool.No.getValue());
        chatService.save(chat);
        return Response.OK(chat);
    }



    @PutMapping("/read")
    public Response updateRead() {
        return Response.OK(chatService.update(new UpdateWrapper<Chat>().setSql("readed = 0 where readed = 1 and teacher_id = " + RequestUtil.getCurrentUserId() + " and sender_id != teacher_id")));
    }

    @GetMapping("/unread/count")
    public Response findUnreadCount() {
        Integer currentUserId = RequestUtil.getCurrentUserId();
        User withRole = userService.findWithRole(currentUserId);

        Chat chat  = new Chat();
        chat.setReaded(Bool.No.getValue());
        QueryWrapper<Chat> queryWrapper;
        if (withRole.getRole().getId().equals(200)) {
            return Response.OK(chatService.findUnReadByAdmin().size());
        } else {
            chat.setTeacherId(currentUserId);
            queryWrapper = new QueryWrapper<>(chat).ne("sender_id", currentUserId);
        }
        return Response.OK(chatService.list(queryWrapper).size());
    }

    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id) {
        return Response.OK(chatService.removeById(id));
    }



}

