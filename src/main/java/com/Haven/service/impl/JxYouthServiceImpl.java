package com.Haven.service.impl;

import com.Haven.DTO.*;
import com.Haven.constant.CalendarConst;
import com.Haven.entity.JxYouthOrganization;
import com.Haven.entity.UserYouthData;
import com.Haven.entity.YouthCourse;
import com.Haven.mapper.JxYouthOrganizationMapper;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.mapper.YouthCourseMapper;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import com.Haven.utils.HttpsClientUtil;
import com.Haven.utils.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.Haven.scheduler.util.YouthLearnJobUtils.postData;
import static com.Haven.scheduler.util.YouthLearnJobUtils.sendMimeMail;
import static com.Haven.utils.CalendarCheckUtil.checkTime;
import static com.Haven.utils.ConversionUtil.getCurrentCourse;
import static com.Haven.utils.HttpsClientUtil.doDownloadString;
import static com.Haven.utils.HttpsClientUtil.doGet;

@Service
public class JxYouthServiceImpl implements JxYouthService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserYouthDataMapper userYouthDataMapper;

    @Autowired
    private YouthCourseMapper youthCourseMapper;

    @Autowired
    private UserYouthDataService userYouthDataService;

    @Override
    public ResponsePackDTO postData(String data) {

//        http://osscache.vol.jxmfkj.com/html/h5_index.html?accessToken=eiwjro34

        String uuid = RandomUtil.getRandomUUID(28);
        String getUrl = "http://www.jxqingtuan.cn/html/h5_index.html?accessToken=" + uuid + "&openid=" + uuid;
        String postUrl = "http://www.jxqingtuan.cn/pub/vol/volClass/join?accessToken=" + uuid;
        String userid = JSON.parseObject(data, UserYouthReloadDataDTO.class).getCardNo();

        ResponsePackDTO status = new ResponsePackDTO(4000, null);

        if (doGet(getUrl).getStatus() == 200) {

            status = HttpsClientUtil.doPostJson(postUrl, data);
            if (status.getStatus() == 200 && status.getContent().contains(userid)) {

                //TODO: userid post ok 添加至数据库
                userYouthDataMapper.update(
                        new UserYouthData(), new LambdaUpdateWrapper<UserYouthData>()
                                .set(UserYouthData::isStatus, true)
                                .eq(UserYouthData::getUserid, userid)
                );
                updateFinish(userid);
            }
        }
        return status;
    }

    @Override
    public ResponsePackDTO postData(UserYouthData user) {

        String uuid = RandomUtil.getRandomUUID(28);
        String getUrl = "http://www.jxqingtuan.cn/html/h5_index.html?accessToken=" + uuid + "&openid=" + uuid;
        String postUrl = "http://www.jxqingtuan.cn/pub/vol/volClass/join?accessToken=" + uuid;
        String data = JSON.toJSONString(user.toUserYouthReloadDataDTO());

        ResponsePackDTO status = new ResponsePackDTO(4000, null);

        if (doGet(getUrl).getStatus() == 200) {

            logger.info("time: {}, user: {}, get->status: {}", LocalDateTime.now(), user.getNid() + "." + user.getUserid(), 200);

            status = HttpsClientUtil.doPostJson(postUrl, data);

            logger.info("user: {}, post->status: {}", user.getNid() + "." + user.getUserid(), status.getStatus());

            if (status.getStatus() == 200 && status.getContent().contains(user.getUserid())) {
                //TODO: userid post ok 添加至数据库
                userYouthDataMapper.update(
                        new UserYouthData(), new LambdaUpdateWrapper<UserYouthData>()
                                .set(UserYouthData::isStatus, true)
                                .eq(UserYouthData::getUserid, user.getUserid())
                                .eq(UserYouthData::getNid, user.getNid())
                );
                updateFinish(user.getUserid());
                logger.info("post成功数据 -> {}", JSON.toJSONString(status));
            }
        }
        return status;
    }

    private void updateFinish(String userid) {

        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );
        userYouthData.setLastFinishTime(LocalDateTime.now());
        userYouthData.putFinishHistory(new FinishLogDTO(LocalDateTime.now(), getCurrentCourse()));
        userYouthDataMapper.updateById(userYouthData);

    }

    private void fix(UserYouthData userYouthData) {

        ResponsePackDTO responsePackDTO = new ResponsePackDTO(404, "");

        while (responsePackDTO.getStatus() != 200) {
            responsePackDTO = postData(userYouthData);
            logger.info("responsePackDTO：{}", responsePackDTO.getStatus());
        }

        userYouthDataMapper.updateById(userYouthData.settingStatus(true));

    }

    @Override
    @Scheduled(cron = "0 30 21 ? * 5")
    public void checkAndFix() throws InterruptedException {
        userYouthDataService.fixStatus();

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::isStatus, false))){
            fix(userYouthData);
            TimeUnit.SECONDS.sleep(new Random().nextInt(600));
        }

//        sendMimeMail();

    }

    @Override
    @Scheduled(cron = "59 59 23 ? * 1")
    public void updateResult() {
        userYouthDataMapper.update(
                new UserYouthData(), new LambdaUpdateWrapper<UserYouthData>()
                        .set(UserYouthData::isStatus, false)
        );
    }

    private boolean checkTime(LocalDateTime time) {

        if (Objects.isNull(time)) return false;

        Calendar start = Calendar.getInstance(Locale.CHINA);
        Calendar end = Calendar.getInstance(Locale.CHINA);

        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        start.set(Calendar.HOUR_OF_DAY, 12);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);

        end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.add(Calendar.DATE, 7);

        Calendar curCalendar = Calendar.getInstance(Locale.CHINA);

        curCalendar.setTime(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()));

        return curCalendar.after(start) && curCalendar.before(end);
    }

    @Override
    @Scheduled(cron = "15 30 12 ? * 2")
    public void updateCourse() {

        YouthCourse course = youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        );


        boolean hasCourse = !Objects.isNull(course);

        if (!Objects.isNull(course)) if (checkTime(course.getUpdateTime())) return;

//        http://www.jxqingtuan.cn/pub/vol/volClass/current?accessToken=123

        String content = doDownloadString("http://osscache.vol.jxmfkj.com/html/assets/js/course_data.js?v=909");

        StringBuilder reverse = new StringBuilder(content).reverse();
        String jsons = new StringBuilder(reverse.substring(0, reverse.lastIndexOf("{")+1)).reverse().toString();
        course = JSON.parseObject(JSON.parseObject(jsons).get("result").toString(), YouthCourse.class).setCourse().setUpdateTime();
        if (hasCourse)
            youthCourseMapper.update(
                    new YouthCourse(), new LambdaUpdateWrapper<YouthCourse>()
                                               .set(YouthCourse::getId, course.getId())
                                               .set(YouthCourse::getUpdateTime, course.getUpdateTime())
                                               .set(YouthCourse::getTitle, course.getTitle())
                                               .set(YouthCourse::getUri, course.getUri())
                                               .eq(YouthCourse::getCourse, "course")
            );
        else
            youthCourseMapper.insert(course);
    }
}
