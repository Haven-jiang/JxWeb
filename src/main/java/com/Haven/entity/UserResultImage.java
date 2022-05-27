package com.Haven.entity;

import com.Haven.utils.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.Haven.utils.ConversionUtil.toImageUri;

/**
 * 青年大学习结果图片面向数据库类 UserResultImage
 *
 * @author HavenJust
 * @date 19:50 周日 24 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResultImage implements Serializable {

    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    private String imagePath;

    private String currentImagePath;

    private List<String> historyImagePath;


    public byte[] getHistoryImagePath() {
        return JSON.toJSONBytes(historyImagePath);
    }

    public void setHistoryImagePath(byte[] finishHistory) {
        this.historyImagePath = JSON.parseObject(finishHistory, List.class);
    }

    public String gettingMVCImage() {
        return "/static_file/" + imagePath + currentImagePath;
    }

    public String gettingMVCImage(String path) {
        return "/static_file/" + imagePath + path;
    }

    public String gettingMVCImagePath() {
        return "/static_file/" + imagePath;
    }

    public String gettingFileImage() {
        return CommonUtil.getImagePath() + imagePath + currentImagePath;
    }

    public String gettingFileImagePath() {
        return CommonUtil.getImagePath() + imagePath;
    }

    public List<String> gettingHistoryImagePath() {
        return this.historyImagePath;
    }

    public List<String> gettingHistoryImageUri() {
        List<String> historyImageUri = new ArrayList<>();
        for (String path : this.historyImagePath) historyImageUri.add("/static_file/" + imagePath + path);
        return historyImageUri;
    }

    public UserResultImage putSendHistory(String historyImagePath) {
        this.historyImagePath.add(historyImagePath);
        return this;
    }

}
