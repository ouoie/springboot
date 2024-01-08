package com.greathealth.greathealth.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class FilesUtils {

    public static String getSize(byte[] file) {
        int size = file.length;
        size = size / 1024 * 100 / 1024;
        return size / 100 + "." + size % 100 + "MB";
    }

    public static String getSize(File file) {
        long size = file.length();
        size = size / 1024 * 100 / 1024;
        return size / 100 + "." + size % 100 + "MB";
    }


    public static byte[] readFileToByte(String path) throws IOException {
        path = RepalceSeparator(path);
        File file = new File(path);
        byte[] bytes = new byte[(int) file.length()];
        if (file.exists() && file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                bufferedInputStream.read(bytes);
            }
            return bytes;
        }
        return null;
    }

    public static void createFile(String path, String fileName, byte[] file) throws IOException {
        createFile(path + File.separator + fileName, file);
    }

    public static void createFile(String path, byte[] file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            bufferedOutputStream.write(file);
            bufferedOutputStream.flush();
        }
    }

    public static void createFileBase64(String path, byte[] file) {
        String s = new String(file);
        byte[] buffer = decode(s);
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(buffer);
            fos.flush();
        } catch (IOException e) {
            log.error("错误:" + path, e);
        }
    }


    public static byte[] decode(String base64String) {
        return Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean creatFolder(String folderPath) {

        File filePath = new File(RepalceSeparator(folderPath));
        if (!filePath.exists()) {
            return filePath.mkdirs();
        } else {
            return true;
        }
    }

    // 根据系统自动修改斜杆和反斜杠
    public static String RepalceSeparator(String s) {
        return s.replace("\\", File.separator).replace("/", File.separator);
    }

    /**
     * 压缩文件夹
     *
     * @param zipFileName 全路径的zip文件包的名字
     * @param dir         要压缩的文件夹的路径
     */
    public static void zipDir(String zipFileName, String dir) throws IOException {

        File zipFile = new File(zipFileName);
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        File dirFile = new File(dir);
        addEntry(dirFile, zos, "");
        zos.closeEntry();
        zos.close();
    }

    private static void addEntry(File file, ZipOutputStream zos, String root) throws IOException {
        byte[] buffer = new byte[10240];
        for (File file1 : Objects.requireNonNull(file.listFiles())) {
            if (file1.isDirectory()) {
                addEntry(file1, zos, file1.getName());
            } else {
                String fileName = file1.getName();
                ZipEntry zipEntry =
                        new ZipEntry((StringUtils.isEmpty(root) ? "" : root + File.separator) + fileName);
                zos.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(file1);
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                fis.close();
            }
        }
    }

    public static void writeFile(String path, String s) throws IOException {
        writeFile(path, s, StandardCharsets.UTF_8, true);
    }

    public static void writeFile(String path, String s, Charset charset) throws IOException {
        writeFile(path, s, charset, true);
    }

    public static void writeFile(String path, String s, Charset charset, boolean append) throws IOException {
        path = RepalceSeparator(path);
        File file = new File(path);
        if (!file.exists()) {
            creatFile(path);
        }
        try (FileWriter fileWriter = new FileWriter(file, append);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(s);
            bufferedWriter.flush();
        }
    }

    public static void writeFileUTF8(String path, String s) throws IOException {
        path = RepalceSeparator(path);
        File file = new File(path);
        if (!file.exists()) {
            creatFile(path);
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(path, true);
             OutputStreamWriter outputStreamWriter =
                     new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

            bufferedWriter.write(s);
            bufferedWriter.flush();
        }
    }

    public static boolean creatFileOnly(String filePath) throws IOException {

        File fileName = new File(RepalceSeparator(filePath));
        if (!fileName.exists()) {
            return fileName.createNewFile();
        } else {
            return true;
        }
    }

    public static void creatFile(String filePath) throws IOException {
        filePath = RepalceSeparator(filePath);
        if (filePath.lastIndexOf(File.separator) < 0) {
            throw new FileNotFoundException("文件路径不正确");
        }
        String FolderPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        File Folder = new File(FolderPath);
        if (!Folder.exists()) {
            creatFolder(FolderPath);
        }
        creatFileOnly(filePath);
    }

    public static MultipartFile getMultipartFile(File file) {
        FileItem item =
                new DiskFileItemFactory()
                        .createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE, true, file.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }

    public static String getMD5(String path) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(path.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5 = new BigInteger(1, md.digest()).toString(16);
            // BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:" + e.getMessage(), e);
        }
    }

    public static String fillMD5(String md5) {
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }


    private final static String[] strHex = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String getMD5One(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(FileUtils.readFileToByteArray(new File(path)));
            for (int j : b) {
                int d = j;
                if (d < 0) {
                    d += 256;
                }
                int d1 = d / 16;
                int d2 = d % 16;
                sb.append(strHex[d1]).append(strHex[d2]);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("md5错误  " + path, e);
        }
        return sb.toString();
    }


    public static void delFiles(String paths) {
        File file = new File(paths);
        File zip = new File(paths + ".zip");
        zip.delete();
        if (file.isDirectory()) { // 为文件夹
            String[] childFilePath = file.list(); // 获取文件夹下所有文件相对路径
            if (childFilePath == null) {
                return;
            }
            for (String path : childFilePath) {
                delFiles(file.getAbsoluteFile() + File.separator + path); // 递归，对每个都进行判断
            }
        }
        file.delete();
    }

    public static String readFile(String path) throws IOException {
        return readFile(path, StandardCharsets.UTF_8);
    }


    public static String readFile(String path, Charset charset) throws IOException {
        path = RepalceSeparator(path);
        File file = new File(path);
        StringBuilder stringBuffer = new StringBuilder();
        try (FileReader fReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fReader)) {
            char[] buf = new char[1024 * 10];
            int temp = 0;
            while ((temp = bufferedReader.read(buf)) > 0) {
                stringBuffer.append(new String(buf, 0, temp));
            }
            return stringBuffer.toString();
        }
    }
}
