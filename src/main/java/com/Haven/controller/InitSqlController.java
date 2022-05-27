package com.Haven.controller;

import com.Haven.VO.UserYouthInfoVO;
import com.Haven.entity.WebFunctionProfile;
import com.Haven.entity.WebWitticism;
import com.Haven.mapper.WebFunctionProfileMapper;
import com.Haven.mapper.WebWitticismMapper;
import com.Haven.service.JxYouthOrganizationService;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一次性初始化数据逻辑控制类 InitSqlController
 *
 * @author HavenJust
 * @date 00:31 周二 26 四月 2022年
 */

@Controller
public class InitSqlController {

    private final UserYouthDataService userYouthDataService;

    private final WebWitticismMapper webWitticismMapper;

    private final WebFunctionProfileMapper webFunctionProfileMapper;

    private final JxYouthService jxYouthService;

    private final JxYouthOrganizationService jxYouthOrganizationService;

    public InitSqlController(
            UserYouthDataService userYouthDataService,
            WebWitticismMapper webWitticismMapper,
            WebFunctionProfileMapper webFunctionProfileMapper,
            JxYouthService jxYouthService,
            JxYouthOrganizationService jxYouthOrganizationService) {

        this.userYouthDataService = userYouthDataService;
        this.webWitticismMapper = webWitticismMapper;
        this.webFunctionProfileMapper = webFunctionProfileMapper;
        this.jxYouthService = jxYouthService;
        this.jxYouthOrganizationService = jxYouthOrganizationService;
    }

    @PostMapping("/function/youthlearn/jiangxi/course/init15978")
    public void initUserYouthData() {

        jxYouthService.updateCourse();

//        List<String> witticisms = List.of(
//                "我亦可贪恋烟火，殷实人家，几间瓦房，四方小院.",
//                "信仰与日月同辉，亘古不灭。",
//                "功成不必在我。－－胡适",
//                "你我山前沒相見，山後別相逢。",
//                "買人們的爭執，釀酒湯。",
//                "太平湖底陈年水墨与黄金世界万物法则。",
//                "少年的肩上应担起清风明月和草长莺飞。",
//                "少女的眼里应藏下万丈光芒和星辰大海。",
//                "想牵你的手，敬来宾的酒。",
//                "长安尽头无故里，故里从此别长安。",
//                "花开如火，也如寂寞。",
//                "生活原本沉闷，但跑起来就有风。",
//                "一定要爱着点什么，恰似草木对光阴的钟情。",
//                "少年的热血和青春的诗，才刚刚开始。",
//                "人生的旅程就是这样，用大把时间迷茫，在几个瞬间成长。",
//                "一花调零，荒芜不了整个春天。",
//                "你尽管善良，上天自会衡量。",
//                "成熟不是为了走向复杂，而是为了抵达天真。",
//                "我们终其一生，就是要摆脱他人的期待，找到真正的自己。",
//                "自愛，沉穩，而後愛人。",
//                "我有一座房子，面朝大海，春暖花开。",
//                "以后要开心，要飞扬跋扈，肆意妄为，无人能挡。",
//                "光而不耀，与光同尘。",
//                "大人不说再见，只计划重逢。",
//                "滚烫的馨香淹没过稻草人的胸膛，草扎的精神，从此万寿无疆。",
//                "仲夏，烟火，梅子酒，沙丁鱼",
//                "我不摘月亮，要它永远高悬天上，皎洁流芳。",
//                "我本可以容忍黑暗，如果我不曾见过太阳。",
//                "我记得，这场雨曾经下过。",
//                "我匮乏的是，一场酣畅的睡眠，而不是优渥的光明。",
//                "大不列颠的广场上，有没有鸽子飞翔。",
//                "山野笨拙，我也笨拙。",
//                "你不一定非得长成玫瑰。",
//                "做茉莉，做雏菊，做无名小花，做千千万万。",
//                "你没看我，我没看日落。",
//                "拿不出像样的东西，献给，饱含热泪的生命。",
//                "那些我难以言表、不作声响、暗自发力的日子其实并不是想要的生活。",
//                "后来许多人问我一个人夜晚踟蹰路上的心情，我想起的却不是孤单和路长。",
//                "我正在下著雨的無錫，乞討著生活的權利。"
//        );
//
//        for (String witticism : witticisms) webWitticismMapper.insert(new WebWitticism(witticism));
//
//        List<String[]> profiles = Arrays.asList(
//                new String[]{"/function/youthlearn", "青年大摸鱼", "青年大学习定时自动学习"},
//                new String[]{"/function/tencentmeeting", "花果山高级会议", "腾讯会议定时自动挂机"},
//                new String[]{"#", "这是标题", "这是一段描述"},
//                new String[]{"#", "这是标题", "这是一段描述"},
//                new String[]{"#", "这是标题", "这是一段描述"}
//        );
//
//        for (String[] profile : profiles) webFunctionProfileMapper.insert(new WebFunctionProfile(profile[0], profile[1], profile[2]));


        List<UserYouthInfoVO> userYouthInfoVOS = Arrays.asList(

                new UserYouthInfoVO("20010301", "N0014000210051021", "陈春柳", "2793932390@qq.com", null),
                new UserYouthInfoVO("20010302", "N0014000210051021", "陈道旭", "1192170220@qq.com", null),
                new UserYouthInfoVO("20010303", "N0014000210051021", "陈俊杰", "1522020883@qq.com", null),
                new UserYouthInfoVO("20010304", "N0014000210051021", "陈琦环", "3382965810@qq.com", null),
                new UserYouthInfoVO("20010305", "N0014000210051021", "陈诗宇", "1318531679@qq.com", null),
                new UserYouthInfoVO("20010306", "N0014000210051021", "范菲菲", "2069631473@qq.com", null),
                new UserYouthInfoVO("21010408", "N0014000210101032", "董阳洋", "2998249334@qq.com", null),
                new UserYouthInfoVO("20010307", "N0014000210051021", "高海娇", "1419245502@qq.com", null),
                new UserYouthInfoVO("20010308", "N0014000210051021", "官佐森", "1749254581@qq.com", null),
                new UserYouthInfoVO("20010309", "N0014000210051021", "郭晓璇", "1620861728@qq.com", null),
                new UserYouthInfoVO("20010310", "N0014000210051021", "何海", "1964384326@qq.com", null),
                new UserYouthInfoVO("20010311", "N0014000210051021", "侯帅峰", "1796186808@qq.com", null),
                new UserYouthInfoVO("20010312", "N0014000210051021", "黄恒", "1735450371@qq.com", null),
                new UserYouthInfoVO("20010313", "N0014000210051021", "黄懿", "2419931953@qq.com", null),
                new UserYouthInfoVO("20010314", "N0014000210051021", "黄永良", "1272631785@qq.com", null),
                new UserYouthInfoVO("20010315", "N0014000210051021", "姜涵文", "haven-just@qq.com",null),
                new UserYouthInfoVO("20010316", "N0014000210051021", "揭翔宇", "1835819049@qq.com", null),
                new UserYouthInfoVO("20010317", "N0014000210051021", "金坤建", "1597255256@qq.com", null),
                new UserYouthInfoVO("20010318", "N0014000210051021", "李泓兴", "1329164133@qq.com", null),
                new UserYouthInfoVO("20010319", "N0014000210051021", "李惠", "2862965094@qq.com", null),
                new UserYouthInfoVO("20010320", "N0014000210051021", "李钊锋", "3443543946@qq.com", null),
                new UserYouthInfoVO("20010321", "N0014000210051021", "刘可明", "2407039626@qq.com", null),
                new UserYouthInfoVO("20010322", "N0014000210051021", "刘漠沙", "1079347928@qq.com", null),
                new UserYouthInfoVO("20010323", "N0014000210051021", "刘幸子", "1914561684@qq.com", null),
                new UserYouthInfoVO("20010324", "N0014000210051021", "罗家辉", "1747771784@qq.com", null),
                new UserYouthInfoVO("20010325", "N0014000210051021", "彭旋", "2691160907@qq.com", null),
                new UserYouthInfoVO("20010326", "N0014000210051021", "苏俊雄", "2328625823@qq.com", null),
                new UserYouthInfoVO("20010327", "N0014000210051021", "谭文龙", "2316288716@qq.com", null),
                new UserYouthInfoVO("20010328", "N0014000210051021", "涂心宇", "2973809401@qq.com", null),
                new UserYouthInfoVO("20010329", "N0014000210051021", "王晨欣", "2685587621@qq.com", null),
                new UserYouthInfoVO("20010330", "N0014000210051021", "王佳文", "3263391838@qq.com", null),
                new UserYouthInfoVO("20010331", "N0014000210051021", "王明驹", "2870556297@qq.com", null),
                new UserYouthInfoVO("20010332", "N0014000210051021", "王轶军", "2425536252@qq.com", null),
                new UserYouthInfoVO("20010333", "N0014000210051021", "韦麟", "1514462206@qq.com", null),
                new UserYouthInfoVO("20010334", "N0014000210051021", "魏南烽", "1941123420@qq.com", null),
                new UserYouthInfoVO("20010335", "N0014000210051021", "温倬儒", "1428170070@qq.com", null),
                new UserYouthInfoVO("20010336", "N0014000210051021", "吴送粮", "2233712060@qq.com", null),
                new UserYouthInfoVO("20010337", "N0014000210051021", "夏忻宸", "2923593517@qq.com", null),
                new UserYouthInfoVO("20010338", "N0014000210051021", "谢旭博", "756652613@qq.com", null),
                new UserYouthInfoVO("20010339", "N0014000210051021", "徐洪伟", "2911852518@qq.com", null),
                new UserYouthInfoVO("20010340", "N0014000210051021", "徐锦波", "1484439079@qq.com", null),
                new UserYouthInfoVO("20010341", "N0014000210051021", "许景怡", "2994054772@qq.com", null),
                new UserYouthInfoVO("20010342", "N0014000210051021", "许祖俊", "3123303176@qq.com", null),
                new UserYouthInfoVO("20010343", "N0014000210051021", "薛骏", "1842994498@qq.com", null),
                new UserYouthInfoVO("20010344", "N0014000210051021", "杨朝龙", "1986238301@qq.com", null),
                new UserYouthInfoVO("20010345", "N0014000210051021", "杨文婕", "1460698062@qq.com", null),
                new UserYouthInfoVO("20010346", "N0014000210051021", "袁启宝", "2073878463@qq.com", null),
                new UserYouthInfoVO("20010347", "N0014000210051021", "张启仲", "1224758107@qq.com", null),
                new UserYouthInfoVO("20010348", "N0014000210051021", "张馨月", "2379878163@qq.com", null),
                new UserYouthInfoVO("20010349", "N0014000210051021", "赵逸凡", "2673129323@qq.com", null),
                new UserYouthInfoVO("20010350", "N0014000210051021", "郑小龙", "2944232934@qq.com", null),
                new UserYouthInfoVO("20010351", "N0014000210051021", "郑毅", "3453821669@qq.com", null),
                new UserYouthInfoVO("20010352", "N0014000210051021", "朱杰", "1985472781@qq.com", null),
                new UserYouthInfoVO("20010353", "N0014000210051021", "朱俊杰", "2036506047@qq.com", null),
                new UserYouthInfoVO("20010354", "N0014000210051021", "左悦平", "1812887397@qq.com", null),
                new UserYouthInfoVO("20020601", "N0014000210051021", "陈思寒", "1157930658@qq.com", null),
                new UserYouthInfoVO("20030402", "N0014000210051021", "蔡永麒", "1826751569@qq.com", null),
                new UserYouthInfoVO("20070622", "N0014000210051021", "刘啸毅", "2997497206@qq.com", null),
                new UserYouthInfoVO("20130108", "N0014000210051021", "范德玄", "1780168093@qq.com", null),
                new UserYouthInfoVO("20010505", "N0014000210051023", "陈俊荣", "1272673681@qq.com", null),
                new UserYouthInfoVO("恩克吉雅", "N0013001610031073", "20本会计4班", "1879975061@qq.com", null),
                new UserYouthInfoVO("20010625", "N0014000210051024", "万佳莹", "1621312272@qq.com", null)
        );

        userYouthDataService.addUserYouthData(userYouthInfoVOS);

//        jxYouthOrganizationService.jxOrganizationUpdate();

    }
}