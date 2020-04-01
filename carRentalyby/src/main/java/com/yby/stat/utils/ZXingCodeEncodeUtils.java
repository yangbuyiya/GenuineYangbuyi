package com.yby.stat.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.EnumMap;

/**
 * 生成二维码工具包
 * 需要引入 javase依赖 和 谷歌二维码 zxing
 *   <!--zxing谷歌的  二维码  -->
 *     <dependency>
 *         <!-- 解析二维码 -->
 *       <groupId>com.google.zxing</groupId>
 *       <artifactId>core</artifactId>
 *       <version>3.4.0</version>
 *     </dependency>
 *
 *       <!-- 管生成 -->
 *     <dependency>
 *       <groupId>com.google.zxing</groupId>
 *       <artifactId>javase</artifactId>
 *       <version>3.4.0</version>
 *     </dependency>
 *
 */
public class ZXingCodeEncodeUtils {

    // 二维码颜色
    private static final int BLACK = 0xFF000000;
    // 二维码背景颜色
    private static final int WHITE = 0xFFFFFFFF;

    // 二维码格式参数
    private static final EnumMap<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(
        EncodeHintType.class);

    static {
        /*
         * 二维码的纠错级别(排错率),4个级别： L (7%)、 M (15%)、 Q (25%)、 H (30%)(最高H)
         * 纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，那么能存储的有用讯息就越少；共有四级； 选择M，扫描速度快。
         */
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码边界空白大小 1,2,3,4 (4为默认,最大)
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");// 设置放入的字符的编码
    }

    /**
     * 生成二维码
     * 保存到本地
     *
     * @param content   二维码内容
     * @param width     宽度
     * @param height    高度
     * @param savePath  保存路径
     * @param imageType 图片类型
     */
    public static void createZXingCodeSaveToDisk(String content, int width, int height, String savePath,
                                                 String imageType) {
        try {
            // 创建二维码
            BufferedImage image = createZXingCodeNormal(content, width, height);
            // 保存图片到硬盘
            File file = new File(savePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 生成图片
            ImageIO.write(image, imageType, file);
            System.out.println("生成成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码返回图片对象
     * 通过字节流进行传输到页面当中
     * 生成图片
     * BufferedImage codeImage =  ZXingCodeEncodeUtils.createZXingCodeNormal("二维码内容",300,300);
     * 创建字节流   因为图片整字节流的
     * ServletOutputStream outputStream = response.getOutputStream();
     * 通过图片io传输到页面当中 ImageIo.write(二维码,"图片类型",输出方式);
     * ImageIo.write(codeImage,"JPEG",outputStream);
     */
    public static BufferedImage createZXingCodeNormal(String content, int width, int height) {
        try {
            //  BarcodeFormat.QR_CODE  qr算法
            BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 得到二维码的宽度
            int code_width = encode.getWidth();
            int code_height = encode.getHeight();
            // 创建图片
            BufferedImage image = new BufferedImage(code_width, code_height, BufferedImage.TYPE_INT_RGB);
            // 把二维码里面的信息写到图片里面
            for (int i = 0; i < code_width; i++) {
                for (int j = 0; j < code_height; j++) {
                    //  如果 encode.get(i, j) 返回true 代表当前信号点 有东西  则为黑色背景
                    //  如果 encode.get(i, j) 返回false 代表当前信号点 没有东西  则为白色背景
                    image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
                }
            }
            // 返回二维码流
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成一张带logo的二维码
     * 保存到本地
     *
     * @param logoStream logo的流对象
     */
    public static void createZxingCodeUseLogoSaveToDisk(String content, int width, int height, String savePath, String imageType, InputStream logoStream) {
        try {
            BufferedImage codeImage = createZxingCodeUseLogo(content, width, height, logoStream);
            // 保存图片到硬盘
            File file = new File(savePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            ImageIO.write(codeImage, imageType, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成一张带logo的二维码  返回BuffredeImage
     * 返回流 返回到前端页面
     *
     * @param logoStream logo的流对象
     */
    public static BufferedImage createZxingCodeUseLogo(String content, int width, int height, InputStream logoStream) {

        try {
            //生成二维码图片
            BufferedImage codeNormal = createZXingCodeNormal(content, width, height);
            if (null != codeNormal) {
                //判断logoStream是否为空
                if (null != logoStream) {
                    //拿到可以操作当前图片的画笔
                    Graphics2D graphics = codeNormal.createGraphics();
                    //得到logo图片的对象
                    BufferedImage logoImage = ImageIO.read(logoStream);
                    //得到logo的原始宽高
                    int old_logo_width = logoImage.getWidth();
                    int old_logo_height = logoImage.getHeight();


                    //得到二维码的宽高
                    int code_width = codeNormal.getWidth();
                    int code_height = codeNormal.getHeight();

                    //算出logo在二维码里面能存在的最大值
                    int logo_max_width = code_width / 5;
                    int logo_max_height = code_height / 5;

                    /* 算法计算logo在二维码存放的位置 */
                    //计算logo的可用宽高
                    int logo_width = logo_max_width < old_logo_width ? logo_max_width : old_logo_width;
                    // 如果logo最大高度 小于原始高度  则使用logo最大高度  也就是说 那边小 使用那边
                    int logo_height = logo_max_height < old_logo_height ? logo_max_height : old_logo_height;

                    //计算logo的开始点的坐标
                    int x = (code_width - logo_width) / 2;
                    int y = (code_height - logo_height) / 2;

                    /**
                     * logoImage logo图片对象
                     * x 开始画的x轴坐标
                     * y 开始画的y轴的坐
                     * logo_width 要画的x轴的长度
                     * logo_height 要画的y车的长度
                     * arg5  null
                     */
                    graphics.drawImage(logoImage, x, y, logo_width, logo_height, null);
                    // 笔画
                    graphics.setStroke(new BasicStroke(2));
                    // 设置颜色
                    graphics.setColor(Color.WHITE);
                    //画白色边框
                    graphics.drawRoundRect(x, y, logo_width, logo_height, 15, 15);
                    graphics.dispose();//让画上的去的内容生效
                    return codeNormal;
                }
            } else {
                System.out.println("生成失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成失败");
        }
        return null;
    }


    /*
    *  测试 二维码生成
    * */
    public static void main(String[] args) {
//		createZXingCodeSaveToDisk("老雷", 400, 400, "D:/laolei.gif", "JPEG");
        // 生成带log
        InputStream logoStream = ZXingCodeEncodeUtils.class.getClassLoader().getResourceAsStream("logo.jpg");
        createZxingCodeUseLogoSaveToDisk("杨不易", 400, 400, "D:/yangbuyi.gif", "JPEG", logoStream);


        // createZXingCodeSaveToDisk("杨不易呀",400,400,"d:yby.gif","JPEG");

    }
}
