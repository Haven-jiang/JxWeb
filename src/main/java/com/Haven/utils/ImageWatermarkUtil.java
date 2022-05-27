package com.Haven.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Tag;
import org.apache.tomcat.jni.Directory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 处理图片添加水印 markImageUtil
 *
 * @author HavenJust
 * @date 23:18 周日 24 四月 2022年
 */

public class ImageWatermarkUtil {



    static final String NEW_IMAGE_NAME_PRE_STR = "_water";
    // 水印透明度
    private static float alpha = 0.5f;
    // 水印文字颜色
    private static final Color color = Color.red;


    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     * @param degree
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;

        try {
            FastImageInfoUtil imageInfo = new FastImageInfoUtil(new File(srcImgPath));

            int width = (imageInfo.getWidth() / 3 * 2) / logoText.length();
            int height = imageInfo.getHeight();
            float positionWidth = imageInfo.getWidth() / 6.0f;
            float positionHeight = height * 8.5f / 10;

            ClassPathResource classPathResource = new ClassPathResource("FZHCK.TTF");

            // 水印文字字体
            Font font = Font.createFont(Font.TRUETYPE_FONT, classPathResource.getFile()).deriveFont(Font.BOLD, (float) (width*1.7));

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            ge.registerFont(font);

            if (StringUtils.isBlank(targerPath)) {
                targerPath = srcImgPath.substring(0, srcImgPath.lastIndexOf(".")) + NEW_IMAGE_NAME_PRE_STR + "f" + srcImgPath.substring(srcImgPath.lastIndexOf("."));
            }
            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);


            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            // 水印横向位置
            g.drawString(logoText, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加水印文字");
            new File(targerPath).renameTo(new File(srcImgPath));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
