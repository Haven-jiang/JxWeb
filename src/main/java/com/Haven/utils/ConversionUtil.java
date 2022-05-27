package com.Haven.utils;

import com.Haven.DTO.EmailContentDTO;
import com.Haven.DTO.EmailInfoDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
import com.Haven.entity.*;
import com.Haven.mapper.*;
import com.Haven.scheduler.entity.QrtzCronTriggers;
import com.Haven.scheduler.mapper.QrtzCronTriggersMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.Haven.utils.CommonUtil.getImagePath;
import static com.Haven.utils.RandomUtil.getRandomUUID;

@Component
public class ConversionUtil {

    private static YouthCourseMapper youthCourseMapper;
    private static UserEmailInfoMapper userEmailInfoMapper;
    private static UserYouthDataMapper userYouthDataMapper;
    private static UserResultImageMapper userResultImageMapper;
    private static QrtzCronTriggersMapper qrtzCronTriggersMapper;

    public ConversionUtil(YouthCourseMapper youthCourseMapper,
                          UserEmailInfoMapper userEmailInfoMapper,
                          UserYouthDataMapper userYouthDataMapper,
                          UserResultImageMapper userResultImageMapper,
                          QrtzCronTriggersMapper qrtzCronTriggersMapper) {

        ConversionUtil.youthCourseMapper = youthCourseMapper;
        ConversionUtil.userEmailInfoMapper = userEmailInfoMapper;
        ConversionUtil.userYouthDataMapper = userYouthDataMapper;
        ConversionUtil.userResultImageMapper = userResultImageMapper;
        ConversionUtil.qrtzCronTriggersMapper = qrtzCronTriggersMapper;
    }

    public static UserYouthData toUserYouthData(String json) {
        UserYouthReloadDataDTO userYouthReloadDataDTO = JSON.parseObject(json, UserYouthReloadDataDTO.class);
        return userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userYouthReloadDataDTO.getCardNo())
        );
    }

    public static String getCurrentCourse() {
        return youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        ).getId();
    }

    public static String getCurrentCourseUri() {
        return youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        ).getUri();
    }

    public static UserYouthData buildUserYouthData(String userid, String nid, String cron, String watermarkText) {

        if (Objects.isNull(cron)) cron = RandomUtil.getRandomCron();

        return UserYouthData.builder()
                .imageId(getRandomUUID(28))
                .emailId(getRandomUUID(28))
                .id(userid, nid)
                .watermarkText(watermarkText)
                .cron(cron)
                .build();
    }

    public static String getCronExpression(String triggerGroup, String triggerName) {

        QrtzCronTriggers qrtzCronTriggers = qrtzCronTriggersMapper.selectOne(
                new LambdaQueryWrapper<QrtzCronTriggers>()
                        .select()
                        .eq(QrtzCronTriggers::getTriggerName, triggerName)
                        .eq(QrtzCronTriggers::getTriggerGroup, triggerGroup)
        );

        return qrtzCronTriggers == null ? null : qrtzCronTriggers.getCronExpression();
    }

    public static String getCronExpression(@NotNull UserYouthData userYouthData) {
        return getCronExpression(userYouthData.getTriggerGroup(), userYouthData.getTriggerName());
    }

    public static String getEmailById(String uuid) {

        return userEmailInfoMapper.selectOne(
                new LambdaQueryWrapper<UserEmailInfo>()
                        .select()
                        .eq(UserEmailInfo::getUuid, uuid)
        ).getEmail();
    }

    public static @NotNull String getImagePathById(String uuid) {

        UserResultImage userResultImage = userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, uuid)
        );

        return getImagePath() + userResultImage.getImagePath() + userResultImage.getCurrentImagePath();
    }

    public static int setImagePath(String uuid, String currentImage, String imagePath) {
        UserResultImage userResultImage = userResultImageMapper.selectById(uuid);
        userResultImage.setCurrentImagePath(currentImage);
        userResultImage.setImagePath(imagePath);
        return userResultImageMapper.updateById(userResultImage);
    }

    public static int setImagePath(@NotNull UserYouthData userYouthData) {
        return setImagePath(userYouthData.getImageId(), userYouthData.getTriggerGroup() + "_snapshot/" + userYouthData.getTriggerName() + "/", "finish_" + getCurrentCourse() + ".jpg");
    }

    public static @NotNull String getImageUriById(String uuid) {

        return userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, uuid)
        ).gettingMVCImage();
    }

    public static List<String> getImagePathListById(String uuid) {

        return userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, uuid)
        ).gettingHistoryImagePath();

    }

    public static List<String> getImageUriListById(String uuid) {

        return userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, uuid)
        ).gettingHistoryImageUri();

    }


    public static String toImageUri(String imagePath) {
        return "/static_file/" + imagePath.substring(imagePath.lastIndexOf("image/")+6);
    }

    public static EmailInfoDTO buildEmailInfoDTO(UserYouthData userYouthData) throws IOException {
        return EmailInfoDTO.builder()
                .recipient(getEmailById(userYouthData.getEmailId()))
                .subject("江西省 - 青年大学习 uuid:" + userYouthData.getUserid() + " 已完成")
                .inlines(new ArrayList<>(List.of(new EmailInfoDTO.Inline("finish", new File(getImagePathById(userYouthData.getImageId()))))))
                .text(buildEmailContentOne(
                        EmailContentDTO
                                .builder()
                                .username(userYouthData.getUserid())
                                .service(userYouthData.getTriggerName())
                                .textH1("你的青年大学习已完成")
                                .textH2("截图如下:")
                                .namespace("功能: 青年大学习")
                                .result(userYouthData.isStatus() ?"成功":"失败")
                                .timeTask(userYouthData.getCron())
                                .finishTime(userYouthData.getLastFinishTime().toString())
                                .build()
                ))
                .sentDate(new Date())
                .build();
    }

    public static String buildEmailContentOne(EmailContentDTO content) throws IOException {

        //加载邮件html模板
        String fileName = "pod-scale-alarm.html";
        ClassPathResource classPathResource = new ClassPathResource("pod-scale-alarm.html");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(EmailContentDTO.class).error("读取文件失败，fileName:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }

        String contentText = content.getTextH1() + "<br>" + content.getTextH2();
        //邮件表格header
        String header = "<td>分区(Namespace)</td><td>服务(Service)</td><td>结果(Result)</td><td>定时任务(TimingTask)</td><td>完成时间(Finish Date And Time)</td>";
        StringBuilder linesBuffer = new StringBuilder();
        linesBuffer.append("<tr><td>" + content.getNamespace() + "</td><td>" + content.getService() + "</td><td>" + content.getResult() + "</td>" +
                "<td>" + content.getTimeTask() + "</td><td>" + content.getFinishTime() + "</td></tr>");

        //绿色
        String emailHeadColor = "#10fa81";
        String date = LocalDateTime.now().toString();
        //填充html模板中的五个参数
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor, content.getUsername() ,contentText, date, header, linesBuffer.toString());

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }
}
