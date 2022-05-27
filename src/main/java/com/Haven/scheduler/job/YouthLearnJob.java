package com.Haven.scheduler.job;

import com.Haven.DTO.FinishLogDTO;
import com.Haven.DTO.ResponsePackDTO;
import com.Haven.entity.UserYouthData;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.Haven.scheduler.util.YouthLearnJobUtils.*;
import static com.Haven.utils.ConversionUtil.*;
import static com.Haven.utils.ImageWatermarkUtil.markImageByText;
import static com.Haven.utils.LinkTableUtil.addImagePath;

//jobDataMap持久化
public class YouthLearnJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        updateCourse();

        try {
            String data = (String) context.getJobDetail().getJobDataMap().get("data");

            logger.info("YouthLearnJob ==> time: {}", LocalDateTime.now());
            logger.info("data: {}", data);

            ResponsePackDTO responsePackDTO = new ResponsePackDTO(404, "");

            while (responsePackDTO.getStatus() != 200) {
                responsePackDTO = postData(data);
                logger.info("responsePackDTO：{}", responsePackDTO.getStatus());
            }

            //TODO: 下载创建初始化截图
            UserYouthData userYouthData = addImagePath(toUserYouthData(data));

            //TODO: 截图添加水印
            markImageByText(userYouthData.getWatermarkText(), getImagePathById(userYouthData.getImageId()), null, null);

            //TODO: 发邮件
            if (!userYouthData.getEmailId().isEmpty()) {
                sendMimeMail(buildEmailInfoDTO(userYouthData));
                userYouthData.putSendHistory(new FinishLogDTO(LocalDateTime.now(), getImagePathById(userYouthData.getImageId())));
                updateById(userYouthData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        jxYouthService.postData(new UserYouthData());
    }
}
