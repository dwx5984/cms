package com.lcx.cms.entity.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.lcx.cms.base.AppException;
import com.lcx.cms.base.DateUtil;
import com.lcx.cms.entity.cs.entity.AttendRecord;
import com.lcx.cms.entity.cs.entity.AttendWeek;
import com.lcx.cms.entity.cs.entity.Course;
import com.lcx.cms.entity.cs.mapper.AttendWeekMapper;
import com.lcx.cms.entity.cs.service.AttendRecordService;
import com.lcx.cms.entity.cs.service.AttendWeekService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcx.cms.entity.cs.service.CourseService;
import com.lcx.cms.entity.sys.entity.RoleUser;
import com.lcx.cms.entity.sys.entity.User;
import com.lcx.cms.entity.sys.service.RoleUserService;
import com.lcx.cms.entity.sys.service.UserService;
import com.lcx.cms.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 周课时统计 service实现
 *
 * @author lcx
 * @since 2021-01-25
 */
@Slf4j
@Service
public class AttendWeekServiceImpl extends ServiceImpl<AttendWeekMapper, AttendWeek> implements AttendWeekService {

    @Autowired
    CourseService courseService;

    @Autowired
    AttendRecordService attendRecordService;
    @Autowired
    RoleUserService roleUserService;

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Page<AttendWeek> pageAll(AttendWeek week) {
        Page<AttendWeek> page = week.getPage();
        Integer currentUserId = RequestUtil.getCurrentUserId();
        RoleUser roleUser = roleUserService.getOne(new QueryWrapper<RoleUser>().eq("user_id", currentUserId));
        if (roleUser.getRoleId().equals(300)) {
            week.setUserId(currentUserId);
        }
        List<AttendWeek> attendWeeks = baseMapper.pageAll(page, week);
        for (AttendWeek week1 : attendWeeks) {
            String[] split = week1.getCourseIds().split(",");
            List<Course> byIds = courseService.list(new QueryWrapper<Course>().in("id", Lists.newArrayList(split)));
            week1.setCourses(byIds);
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);
            calendar.setTime(week1.getStartTime());
            week1.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
        }
        page.setRecords(attendWeeks);
        return page;
    }

    @Override
    public List<User> findUserWithHours() {
        List<User> records = userService.findByRole(300, false);
        for (User user1 : records) {
            Date startDate = DateUtil.getThisWeekStartDate(new Date());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DATE, 7);
            cal.add(Calendar.SECOND, -1);
            Date endDate = cal.getTime();
            List<AttendRecord> byTime = attendRecordService.findByTime(user1.getId(), startDate, endDate);
            int sum = byTime.stream().mapToInt(AttendRecord::getHour).sum();
            user1.setHours(sum);

            List<AttendRecord> byUser = attendRecordService.list(new QueryWrapper<AttendRecord>().eq("user_id", user1.getId()));
            int total = byUser.stream().mapToInt(AttendRecord::getHour).sum();
            user1.setTotalHours(total);

            List<AttendWeek> byRole = this.list(new QueryWrapper<AttendWeek>().eq("import_role", 200).eq("user_id", user1.getId()));
            int totalByAdmin = byRole.stream().mapToInt(AttendWeek::getHours).sum();
            user1.setTotalHoursByAdmin(totalByAdmin);
        }
        return records;
    }

    @Override
    public void genWeek(Integer currentUserId, boolean isLastWeek) {
        Date startDate;
        if (isLastWeek) {
            startDate = DateUtil.getLastWeekStartDate(new Date());
        } else {
            startDate = DateUtil.getThisWeekStartDate(new Date());
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, 7);
        cal.add(Calendar.SECOND, -1);
        Date endDate = cal.getTime();

        // 删除已生成记录
        remove(new QueryWrapper<AttendWeek>().ge("start_time", startDate).le("end_time", endDate));

        List<AttendRecord> byTime = attendRecordService.findByTime(currentUserId, startDate, endDate);

        if (CollectionUtils.isNotEmpty(byTime)) {
            Map<Integer, List<AttendRecord>> collect = byTime.stream().collect(Collectors.groupingBy(AttendRecord::getUserId));
            for (Integer userId : collect.keySet()) {
                List<AttendRecord> attendRecords = collect.get(userId);
                Set<String> courIds = attendRecords.stream().map(i -> i.getCourseId().toString()).collect(Collectors.toSet());
                int sum = attendRecords.stream().mapToInt(AttendRecord::getHour).sum();
                String courseIds = String.join(",", courIds);
                AttendWeek week = new AttendWeek();
                week.setStartTime(startDate);
                week.setEndTime(endDate);
                week.setUserId(userId);
                week.setCourseIds(courseIds);
                week.setHours(sum);
                week.setCreateTime(new Date());
                save(week);
            }

        } else {
            throw AppException.illegal("没有课时记录");
        }
    }

    @Override
    public void genWeekByDate(Date startDate, Date endDate) {
        // 删除已生成记录
        remove(new QueryWrapper<AttendWeek>().ge("start_time", startDate).le("end_time", endDate).eq("import_role", 300));
        List<AttendRecord> byTime = attendRecordService.findByTime(RequestUtil.getCurrentUserId(), startDate, endDate);
        if (CollectionUtils.isNotEmpty(byTime)) {
            Map<Integer, List<AttendRecord>> collect = byTime.stream().collect(Collectors.groupingBy(AttendRecord::getUserId));
            for (Integer userId : collect.keySet()) {
                List<AttendRecord> attendRecords = collect.get(userId);
                Set<String> courIds = attendRecords.stream().map(i -> i.getCourseId().toString()).collect(Collectors.toSet());
                int sum = attendRecords.stream().mapToInt(AttendRecord::getHour).sum();
                String courseIds = String.join(",", courIds);
                AttendWeek week = new AttendWeek();
                week.setStartTime(startDate);
                week.setEndTime(endDate);
                week.setUserId(userId);
                week.setCourseIds(courseIds);
                week.setHours(sum);
                week.setCreateTime(new Date());
                week.setImportRole(300);
                save(week);
            }

        } else {
            throw AppException.illegal("没有课时记录");
        }
    }

    @Override
    public void importWeek(Date start, Date end, Integer hour, Integer userId) {
        remove(new QueryWrapper<AttendWeek>().ge("start_time", start).le("end_time", end).eq("user_id", userId).eq("import_role", 200));
        AttendWeek week = new AttendWeek();
        week.setStartTime(start);
        week.setEndTime(end);
        week.setUserId(userId);
        week.setHours(hour);
        week.setImportRole(200);
        save(week);
    }
}