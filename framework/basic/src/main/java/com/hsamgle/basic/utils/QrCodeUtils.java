package com.hsamgle.basic.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 *  @feture   :	    TODO		二维码生成工具类
 *	@file_name:	    QrCodeUtils.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/7 20:04
 *	@company:		江南皮革厂
 */
public final class QrCodeUtils {

    /** 二维码的默认大小 */
    private static final int QRCODE_SIZE = 300;

    private static final int LOGO_SIZE = QRCODE_SIZE /6;

    private static final int BORDER = 33;

    /**
     *
     * @method:	TODO    生成二维码
     * @time  :	2018/11/7 20:18
     * @author:	黄鹤老板
     * @param content
    * @param size
     * @return:     java.awt.image.BufferedImage
     */
    public static BufferedImage generate(String content, String logo) {

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            // 创建比特矩阵(位矩阵)的QR码编码的字符串  默认纠错级别为 L
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, Collections.singletonMap(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L));
            // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
            int matrixWidth = byteMatrix.getWidth();
            int matrixHeight = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth - 65, matrixWidth - 65, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixHeight);
            // 使用比特矩阵画并保存图像
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i - BORDER, j - BORDER, 2, 2);
                    }
                }
            }
            if(logo!=null){
                // 执行在图片中插入logo
                insertImage(image,logo);
            }

            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *
     * @method:	TODO    在二维码中插入图片
     * @time  :	2018/11/7 20:21
     * @author:	黄鹤老板
     * @param source
    * @param logoPath
    * @param needCompress
     * @return:     void
     */
    private static void insertImage(BufferedImage source, String logoPath) throws Exception {

        Image logo;
        if(logoPath.startsWith("http")){
            URL input = new URL(logoPath);
            String[] readerFormatNames = ImageIO.getReaderFormatNames();
            System.out.println(Arrays.asList(readerFormatNames));
            logo = ImageIO.read(input.openStream());
        }else{
            File file = new File(logoPath);
            if (!file.exists()) {
                throw new Exception("logo file not found.");
            }
            logo = ImageIO.read(new File(logoPath));
        }

        if(logo==null){
            return;
        }

        int width = logo.getWidth(null);
        int height = logo.getHeight(null);
        if (width > LOGO_SIZE) {
            width = LOGO_SIZE;
        }
        if (height > LOGO_SIZE) {
            height = LOGO_SIZE;
        }
        Image image = logo.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        logo = image;
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        // 定位到中间位置
        int x = (QRCODE_SIZE - width) / 2 - BORDER;
        int y = (QRCODE_SIZE - height) / 2 - BORDER;
        graph.drawImage(logo, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }


}
