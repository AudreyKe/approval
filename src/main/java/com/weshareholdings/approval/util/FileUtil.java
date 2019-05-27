package com.weshareholdings.approval.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    protected static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 把文件转变成byte数组
     *
     * @param file
     * @return
     */
    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()]; //定义一个byte长度和file文件流相同数组
        InputStream is = null;
        try {
            is = new FileInputStream(file); //定义一个输入类，用于接收文件输入
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream(); //定义一个byte类型输出类
            byte[] bb = new byte[2048]; //定义一个长为2048的byte数组
            int ch = 0;
            ch = is.read(bb); //读取文件
            while (ch != -1) { //如果读取文件不为-1就循环转换
                bytestream.write(bb, 0, ch); //把文件流写入到输出类中
                ch = is.read(bb);

            }
            by = bytestream.toByteArray(); //把文件流输出类中的数据转成byte数组赋值给by返回
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
            }
        }
        return by;
    }


    /**
     * 把文件转变成String
     *
     * @param file
     * @return
     */
    public static String getFileToString(File file) {
        String str = "";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            // size  为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            str = new String(buffer, "GB2312");
        } catch (IOException e) {
            return null;
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
            }
        }
        return str;
    }


    /**
     * byte数组转文件
     *
     * @param buf
     * @param filePath
     * @param fileName
     */
    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() || dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 删除文件
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        //如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.info("删除单个文件:" + fileName + "成功！");
                return true;
            } else {
                logger.info("删除单个文件:" + fileName + "失败！");
                return false;
            }
        } else {
            logger.info("删除单个文件失败:" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        //如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.info("删除单个文件:" + file.getName() + "成功！");
                return true;
            } else {
                logger.info("删除单个文件:" + file.getName() + "失败！");
                return false;
            }
        } else {
            logger.info("删除单个文件失败:" + file.getName() + "不存在！");
            return false;
        }
    }

}
