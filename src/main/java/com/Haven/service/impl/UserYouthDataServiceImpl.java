package com.Haven.service.impl;

import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.entity.UserEmailInfo;
import com.Haven.entity.UserResultImage;
import com.Haven.entity.UserYouthData;
import com.Haven.exception.GlobalException;
import com.Haven.mapper.UserEmailInfoMapper;
import com.Haven.mapper.UserResultImageMapper;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.UserYouthDataService;
import com.Haven.service.UserYouthTaskService;
import com.Haven.utils.CommonUtil;
import com.Haven.utils.LinkTableUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.Haven.enums.StatusCodeEnum.*;
import static com.Haven.utils.CalendarCheckUtil.checkTime;
import static com.Haven.utils.ConversionUtil.*;
import static com.Haven.utils.LinkTableUtil.initImage;

/**
 * 大学习数据服务实现类 UserYouthDataServiceImpl
 *
 * @author HavenJust
 * @date 23:22 周二 19 四月 2022年
 */

@Service
public class UserYouthDataServiceImpl implements UserYouthDataService {

    @Autowired
    private UserYouthDataMapper userYouthDataMapper;

    @Autowired
    private UserYouthTaskService userYouthTaskService;

    @Autowired
    private UserEmailInfoMapper userEmailInfoMapper;

    @Autowired
    private UserResultImageMapper userResultImageMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //TODO = CRUD 增删改查
    //TODO - 信息增加

    /**
     * 添加一个青年大学习用户信息
     *
     * @param userid 学号
     * @param nid 团委
     * @param cron 执行时间
     * @param email 邮箱地址
     * @param realName 真实姓名
     */

    @Override
    public void addUserYouthData(String userid, String nid, String cron, String email, String realName) {

        //查询用户 判断是否存在
        if (checkYouthData(userid, nid))
            throw new GlobalException(USERNAME_EXIST.getCode(), USERNAME_EXIST.getDescribe()); //用户已存在

        //构建UserYouthData对象
        UserYouthData userYouthData = buildUserYouthData(userid, nid, cron, realName);

        //绑定邮箱和图片路径
        bindEmail(email, userYouthData);
        bindImage(userYouthData);

        //插入数据库
        userYouthDataMapper.insert(userYouthData);

        //注册定时任务
        userYouthTaskService.registerTask(userYouthData);
        //成功
    }

    /**
     * 添加一个青年大学习用户信息
     * @param userYouthInfo 封装前端类
     */

    @Override
    public void addUserYouthData(UserYouthInfoVO userYouthInfo) {
        addUserYouthData(userYouthInfo.getUserid(), userYouthInfo.getNid(), userYouthInfo.getCron(), userYouthInfo.getEmail(), userYouthInfo.getWatermarkText());
    }

    /**
     * 添加一堆青年大学习用户信息
     * @param userYouthInfoList 一堆前端类
     */

    @Override
    public void addUserYouthData(List<UserYouthInfoVO> userYouthInfoList) {
        for (UserYouthInfoVO userYouthInfoVO : userYouthInfoList)
            addUserYouthData(userYouthInfoVO);
    }

    //TODO - 信息删除

    @Override
    public void removeUserYouthData(String userid, String nid) {

        //查询用户信息
        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
                        .eq(UserYouthData::getNid, nid)
        );

        if (Objects.isNull(userYouthData)) throw new GlobalException(USERNAME_NOT_EXIST.getCode(), USERNAME_NOT_EXIST.getDescribe()); //用户不存在

        //删除用户
        while (true) if(userYouthDataMapper.deleteById(userYouthData) != 0) break;

        //删除对应imagePath
        userResultImageMapper.delete(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, userYouthData.getImageId())
        );

        //删除对应email
        userEmailInfoMapper.delete(
                new LambdaQueryWrapper<UserEmailInfo>()
                        .select()
                        .eq(UserEmailInfo::getUuid, userYouthData.getEmailId())
        );

        //删除定时任务
        userYouthTaskService.removeTask(userYouthData);

        //删除成功
    }

    @Override
    public void removeUserYouthData(UserYouthInfoVO userYouthInfo) {
        removeUserYouthData(userYouthInfo.getUserid(), userYouthInfo.getNid());
    }

    //TODO - 信息修改

    @Override
    public void updateUserYouthData(String uuid, String userid, String nid, String cron, String email, String realName) {

        logger.info("执行updateUserYouthData 时间: {}", LocalDateTime.now());

        //查询并实例化UserYouthData实体类
        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUuid, uuid)
        );

        logger.info("updateUserYouthData ==> {}", userYouthData);

        UserYouthData lastYouthData = UserYouthData.builder().id(userYouthData.getUserid(), userYouthData.getNid()).build();

        if (Objects.isNull(userYouthData)) {
            logger.info("updateUserYouthData ===> 查无此人");
            throw new GlobalException(USERNAME_NOT_EXIST.getCode(), USERNAME_NOT_EXIST.getDescribe());
        }

//        if (!Objects.equals(userYouthData.getUserid(), userid) || !Objects.equals(userYouthData.getNid(), nid)) userYouthTaskService.removeTask(userYouthData);
        //修改数据

        userYouthData
                .settingId(nid, userid)
                .settingCron(cron)
                .settingRealName(realName);

        //UserYouthData类上传数据库
        userYouthDataMapper.updateById(userYouthData);

        if (Objects.equals(lastYouthData.getUserid(), userid) || Objects.equals(lastYouthData.getNid(), nid)) {
            userYouthTaskService.modifyTask(lastYouthData, userYouthData);
            logger.info("updateUserYouthData > modifyTask(lastYouthData, userYouthData) 修改");
        }
        else {
            userYouthTaskService.modifyTask(userYouthData);
            logger.info("updateUserYouthData > modifyTask(userYouthData) 修改");
        }

        modifyImage(userYouthData);
        logger.info("updateUserYouthData > modifyImage(userYouthData) 修改");

        bindEmail(email, userYouthData);
        logger.info("updateUserYouthData > bindEmail(email, userYouthData) 修改");
    }

    @Override
    public void updateUserYouthData(UserYouthInfoVO userYouthInfo) {
        updateUserYouthData(userYouthInfo.getUuid(), userYouthInfo.getUserid(), userYouthInfo.getNid(), userYouthInfo.getCron(), userYouthInfo.getEmail(), userYouthInfo.getWatermarkText());
    }

    //TODO - 信息查询

    @Override
    public UserYouthDataDTO queryUserYouthInfoOne(String userid, String nid) {

        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
                        .eq(UserYouthData::getNid, nid)
        );

        if (Objects.isNull(userYouthData)) throw new GlobalException(USERNAME_NOT_EXIST.getCode(), USERNAME_NOT_EXIST.getDescribe());

        return userYouthData.toUserYouthDataDTO();
    }

    @Override
    public UserYouthDataDTO queryUserYouthInfoOne(UserYouthInfoVO userYouthInfoVO) {
        return queryUserYouthInfoOne(userYouthInfoVO.getUserid(), userYouthInfoVO.getNid());
    }

    @Override
    public List<UserYouthDataDTO> queryUserYouthInfoByStatus(boolean isFinish) {

        fixStatus();

        List<UserYouthDataDTO> youthInfoVOList = new ArrayList<>();

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::isStatus, isFinish)))

            youthInfoVOList.add(userYouthData.toUserYouthDataDTO());

        if(youthInfoVOList.isEmpty()) throw new GlobalException(USERNAME_NOT_EXIST.getCode(), USERNAME_NOT_EXIST.getDescribe());

        return youthInfoVOList;
    }

    @Override
    public List<UserYouthDataDTO> queryUserYouthInfoAll() {

        List<UserYouthDataDTO> youthInfoVOList = new ArrayList<>();

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()))
            youthInfoVOList.add(userYouthData.toUserYouthDataDTO());

        if(youthInfoVOList.isEmpty()) throw new GlobalException(USERNAME_NOT_EXIST.getCode(), USERNAME_NOT_EXIST.getDescribe());

        return youthInfoVOList;
    }

    @Override
    public void fixStatus() {

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select())) {

            if (Objects.nonNull(userYouthData.getLastFinishTime()))
                if (checkTime(userYouthData.getLastFinishTime()))
                    userYouthDataMapper.updateById(userYouthData.settingStatus(true));
            else userYouthDataMapper.updateById(userYouthData.settingStatus(false));
        }
    }



    private void bindEmail(String email, UserYouthData userYouthData) {

        if (Objects.isNull(email) || CommonUtil.checkEmail(email)) {

            UserEmailInfo userEmailInfo = userEmailInfoMapper.selectOne(
                    new LambdaQueryWrapper<UserEmailInfo>()
                            .select()
                            .eq(UserEmailInfo::getUuid, userYouthData.getEmailId())
            );

            if (Objects.nonNull(userEmailInfo)) {

                userEmailInfo.setEmail(email);
                userYouthDataMapper.updateById(userYouthData);
                userEmailInfoMapper.updateById(userEmailInfo);

            }
            else
                userEmailInfoMapper.insert(
                    UserEmailInfo
                            .builder()
                            .uuid(userYouthData.getEmailId())
                            .email(email)
                            .build()
                );

        }
    }

    private boolean checkYouthData(String userid, String nid) {
        return Objects.nonNull(
                userYouthDataMapper.selectOne(
                        new LambdaQueryWrapper<UserYouthData>()
                                .select()
                                .eq(UserYouthData::getUserid, userid)
                                .eq(UserYouthData::getNid, nid)
                )
        );
    }

    @Override
    public UserYouthData selectYouthData(String userid) {

        return userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );
    }

    @Override
    public UserYouthReloadDataDTO selectYouthDataDTO(String userid) {

        return selectYouthData(userid).toUserYouthReloadDataDTO();

    }

    private void bindImage(UserYouthData userYouthData) {

        initImage(userYouthData);

        userResultImageMapper.insert(
                UserResultImage
                        .builder()
                        .uuid(userYouthData.getImageId())
                        .currentImagePath("finish_" + getCurrentCourse() + ".jpg")
                        .imagePath(userYouthData.getTriggerGroup() + "_snapshot/" + userYouthData.getTriggerName() + "/")
                        .historyImagePath(new ArrayList<>(List.of("finish_" + getCurrentCourse() + ".jpg")))
                        .build()
        );

    }

    private void modifyImage(UserYouthData userYouthData) {

        UserResultImage userResultImage = userResultImageMapper.selectById(userYouthData.getImageId());

        String imagePath = userYouthData.getTriggerGroup() + "_snapshot/" + userYouthData.getTriggerName() + "/";

        UserResultImage currentImage = UserResultImage
                .builder()
                .uuid(userYouthData.getImageId())
                .imagePath(imagePath)
                .currentImagePath("finish_" + getCurrentCourse() + ".jpg")
                .historyImagePath(userResultImage.gettingHistoryImagePath())
                .build();

        if (!userResultImage.getImagePath().equals(imagePath)) LinkTableUtil.modifyImage(userResultImage, currentImage);

        userResultImageMapper.updateById(currentImage);

    }
}