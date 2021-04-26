package com.lcx.cms.entity.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lcx.cms.entity.cs.entity.AttendRecord;
import com.lcx.cms.entity.cs.entity.AttendWeek;
import com.lcx.cms.entity.cs.entity.Course;
import com.lcx.cms.entity.cs.mapper.CourseMapper;
import com.lcx.cms.entity.cs.service.AttendRecordService;
import com.lcx.cms.entity.cs.service.AttendWeekService;
import com.lcx.cms.entity.cs.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.entity.sys.entity.Role;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  service实现
 *
 * @author lcx
 * @since 2021-01-25
 */
@Slf4j
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    AttendRecordService attendRecordService;

    @Autowired
    UserService userService;

    @Autowired
    AttendWeekService attendWeekService;

    @Override
    public Page<Course> findWithAttendHoursByUser(Course course, Integer userId) {
        Page<Course> page = course.getPage();
        page.setRecords(baseMapper.findWithAttendHoursByIds(course.getPage(), course, userId));
        return page;
    }

    @Override
    public List<User> compare(String ids) {
        if (StringUtils.isBlank(ids)) {
            return Lists.newArrayList();
        }
        String[] split = ids.split("_");
        ArrayList<String> idstr = Lists.newArrayList(split);
        if (CollectionUtils.isEmpty(idstr)) {
            return Lists.newArrayList();
        }
        List<User> users = baseMapper.compareList(idstr);

        for (User user : users) {
            List<Course> courses = user.getCourses();
            int sum = courses.stream().mapToInt(Course::getAttendedHours).sum();
            user.setHours(sum);
        }

        return users;
    }

    @Override
    public Page<User> checkCompare(User user, Date start, Date end,  String type) {
        Role role = new Role();
        role.setId(300);
        user.setRole(role);
        //老师信息
        Page<User> page = userService.pageAll(user);
        List<User> records = page.getRecords();
        for (User user1 : records) {
            // 查询老师录入的课时
            List<AttendRecord> byTime = attendRecordService.findByTime(user1.getId(), start, end);
            int sum = byTime.stream().mapToInt(AttendRecord::getHour).sum();
            user1.setTotalHours(sum);
            // 查询秘书录入的内容
            List<AttendWeek> list = attendWeekService.list(new QueryWrapper<AttendWeek>()
                    .ge("start_time", start)
                    .le("end_time", end)
                    .eq("user_id", user1.getId())
                    .eq("import_role", 200)
            );
            int sum1 = list.stream().mapToInt(AttendWeek::getHours).sum();
            user1.setTotalHoursByAdmin(sum1);
        }
        if ("MATCH".equals(type)) {
            List<User> collect = records.stream().filter(a -> a.getTotalHours().equals(a.getTotalHoursByAdmin())).collect(Collectors.toList());
            page.setRecords(collect);
        } else if ("NOT_MATCH".equals(type)){
            List<User> collect = records.stream().filter(a -> !a.getTotalHours().equals(a.getTotalHoursByAdmin())).collect(Collectors.toList());
            page.setRecords(collect);
        }
        return page;
    }
}