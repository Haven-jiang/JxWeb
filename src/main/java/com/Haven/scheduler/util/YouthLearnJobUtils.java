package com.Haven.scheduler.util;

import com.Haven.DTO.EmailInfoDTO;
import com.Haven.DTO.FinishLogDTO;
import com.Haven.DTO.ResponsePackDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.EmailSendService;
import com.Haven.service.JxYouthService;
import lombok.NoArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.Haven.utils.ConversionUtil.*;
import static com.Haven.utils.ImageWatermarkUtil.markImageByText;
import static com.Haven.utils.LinkTableUtil.addImagePath;

@Component
public class YouthLearnJobUtils {

    private static final Logger logger = LoggerFactory.getLogger(YouthLearnJobUtils.class);

    private static JxYouthService jxYouthService;

    private static EmailSendService emailSendService;

    private static UserYouthDataMapper userYouthDataMapper;

    @Autowired
    public YouthLearnJobUtils(JxYouthService jxYouthService, EmailSendService emailSendService, UserYouthDataMapper userYouthDataMapper) {
        YouthLearnJobUtils.jxYouthService = jxYouthService;
        YouthLearnJobUtils.emailSendService = emailSendService;
        YouthLearnJobUtils.userYouthDataMapper = userYouthDataMapper;
    }

    public static void updateCourse(){
        jxYouthService.updateCourse();
    }

    public static ResponsePackDTO postData(String data) {
        ResponsePackDTO responsePackDTO = null;
        try {
            responsePackDTO = jxYouthService.postData(data);
        } catch (IOException e) {
            logger.info("============================================ post (fail) -> {} ============================================", responsePackDTO);
            e.printStackTrace();
        }
        return responsePackDTO;
    }

     public static void sendMimeMail(EmailInfoDTO emailInfoDTO) {
         emailSendService.sendMimeMail(emailInfoDTO);
     }

    public static void updateById(UserYouthData userYouthData) {
        userYouthDataMapper.updateById(userYouthData);
    }
}
