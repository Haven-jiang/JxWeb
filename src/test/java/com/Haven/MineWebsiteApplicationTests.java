package com.Haven;

import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.VO.JxYouthOrganizationVO;
import com.Haven.DTO.JxYouthOrganizationMapDTO;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.entity.JxYouthOrganization;
import com.Haven.mapper.JxYouthOrganizationMapper;
import com.Haven.mapper.WebFunctionProfileMapper;
import com.Haven.mapper.WebWitticismMapper;
import com.Haven.service.JxYouthOrganizationService;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import com.Haven.service.UserYouthTaskService;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.Haven.utils.ConversionUtil.getImagePathById;
import static com.Haven.utils.FileUtil.copy;
import static com.Haven.utils.HttpsClientUtil.doDownloadString;
import static com.Haven.utils.ImageWatermarkUtil.markImageByText;

@SpringBootTest
class MineWebsiteApplicationTests {

//    @Autowired
//    private UserYouthDataService userYouthDataService;
//
    @Autowired
    private JxYouthService jxYouthService;
//
//    @Autowired
//    private UserYouthDataMapper userYouthDataMapper;
//
//    @Autowired
//    private EmailSendService emailSendService;
//
    @Autowired
    private UserYouthTaskService userYouthTask;

    @Autowired
    private WebWitticismMapper webWitticismMapper;

    @Autowired
    private WebFunctionProfileMapper webFunctionProfileMapper;

    @Autowired
    private JxYouthOrganizationService jxYouthOrganizationService;

    @Autowired
    private UserYouthDataService userYouthDataService;



//    private List<String> conversionJsonList(String content) {
//        List<String> finish = new ArrayList<>();
//        List<JSONObject> list = JSON.parseObject(content, List.class);
//        for (JSONObject obj : list) if (!obj.isEmpty())
//            finish.add(JSON.parseObject(obj.toString(), String.class));
//        return finish;
//    }

    @Test
    void contextLoads() throws IOException, InterruptedException {
        jxYouthService.checkAndFix();
//        jxYouthService.updateCourse();
//        userYouthDataService.removeUserYouthData("20010308.", "N0014000210051021");
//        userYouthDataService.removeUserYouthData("200103051", "N0014000210051021");
//        userYouthDataService.removeUserYouthData("200103053", "N0014000210051021");
//        userYouthDataService.removeUserYouthData("200103054", "N0014000210051021");
//        userYouthDataService.removeUserYouthData("20010318.", "N0014000210051021");
//        userYouthDataService.removeUserYouthData("2003245", "N0014000310031009");
//        userYouthDataService.removeUserYouthData("a2", "N0014000310031009");
//        System.out.println(System.getProperty("os.name"));
//        copy("/Users/jianghanwen/Library/Mobile Documents/com~apple~CloudDocs/JavaIdea2021/mineWebsite/target/image/snapshot/end.jpg", "/Users/jianghanwen/Library/Mobile Documents/com~apple~CloudDocs/JavaIdea2021/mineWebsite/target/image/tg-YouthLearn-jx-N0014000210051021_snapshot/t-20010315/finish_12.jpg");
//        markImageByText("20010315 姜涵文", "/Users/jianghanwen/Library/Mobile Documents/com~apple~CloudDocs/JavaIdea2021/mineWebsite/target/image/tg-YouthLearn-jx-N0014000210051021_snapshot/t-20010315/finish_12.jpg", null, null);

//        getImageUri();
//        for (UserYouthDataDTO userYouthDataDTO : userYouthDataService.queryUserYouthInfoAll()) {
//
//            System.out.println(getImageUri(getImagePathById(userYouthDataDTO.)));
//        }

//        jxYouthOrganizationService.jxOrganizationUpdate();

//        System.out.println(JSON.toJSONString(jxYouthOrganizationService.searchNextNode("N0014")));


//        String content = doDownloadString("http://osscache.vol.jxmfkj.com/html/assets/js/org_data.js?v=908");
//        content = content.substring(0, content.length()-1)+"}";
//        content = content.replace("var organizationJson = ", "{\"data\":");
//
//        JxYouthOrganizationMapDTO jxYouthOrganization = JSON.parseObject(content, JxYouthOrganizationMapDTO.class);
//
//        for (Map.Entry<String, List<JxYouthOrganizationVO>> stringListEntry : jxYouthOrganization.getData().entrySet()) {
//            JxYouthOrganization jxYouthOrganization1 = jxYouthOrganizationMapper.selectById(stringListEntry.getKey());
//
//            if (Objects.nonNull(jxYouthOrganization1)) {
//                if (stringListEntry.getValue() == jxYouthOrganization1.gettingValue()) continue;
//                jxYouthOrganization1.settingValue(stringListEntry.getValue());
//                jxYouthOrganizationMapper.updateById(jxYouthOrganization1);
//            }else
//            jxYouthOrganizationMapper.insert(new JxYouthOrganization(stringListEntry.getKey(), stringListEntry.getValue()));
//        System.out.println(jxYouthOrganization);



//        jxYouthService.updateCourse();
//        jxYouthService.updateCourse"那些我难以言表、不作声响、暗自发力的日子其实并不是想要的生活。",
    }

    @Test
    void Test() {
        userYouthDataService.updateUserYouthData(new UserYouthInfoVO("20010315", "N0014000210051021", "姜涵文", "haven-just@qq.com","20 46 10 ? * 3"));
//        markImageByText("20010315 姜涵文", "/Users/jianghanwen/Library/Mobile Documents/com~apple~CloudDocs/pys/image/end 2.jpg", null, null);
    }

}
