package org.grails.plugin.umeditor

import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletRequest
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.fileupload.*
import org.apache.commons.fileupload.servlet.*;

/**
 * Created by haihxiao on 14/9/14.
 */
class UmeditorUploader {
    // 输出文件地址
    private String url = "";
    // 上传文件名
    private String fileName = "";
    // 状态
    private String state = "";
    // 文件类型
    private String type = "";
    // 原始文件名
    private String originalName = "";
    // 文件大小
    private long size = 0;

    private HttpServletRequest request = null;
    private String title = "";

    // 保存路径
    private String savePath = "upload";
    // 文件允许格式
    private String[] allowFiles = [".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"] as String[];
    // 文件大小限制，单位KB
    private int maxSize = 10000;

    private static HashMap<String, String> ERROR_INFO = new HashMap<String, String>();

    static {
        ERROR_INFO.put("SUCCESS", "SUCCESS"); //默认成功
        ERROR_INFO.put("NOFILE", "未包含文件上传域");
        ERROR_INFO.put("TYPE", "不允许的文件格式");
        ERROR_INFO.put("SIZE", "文件大小超出限制");
        ERROR_INFO.put("ENTYPE", "请求类型ENTYPE错误");
        ERROR_INFO.put("REQUEST", "上传请求异常");
        ERROR_INFO.put("IO", "IO异常");
        ERROR_INFO.put("DIR", "目录创建失败");
        ERROR_INFO.put("UNKNOWN", "未知错误");
    }
    
    UmeditorUploader(def request) {
        this.request = request;
    }

    public void upload() throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            this.state = ERROR_INFO.get("NOFILE");
            return;
        }
        try {
            MultipartFile file = (MultipartFile)request.getFile('upfile')
            this.originalName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(System.getProperty("file.separator")) + 1);
            if (!this.checkFileType(this.originalName)) {
                this.state = ERROR_INFO.get("TYPE");
                return;
            }
            this.fileName = this.getName(this.originalName);
            this.type = this.getFileExt(this.fileName);
            this.url = this.fileName;
            File ofile = new File(this.getPhysicalPath(this.url));
            ofile.getParentFile().mkdirs()
            file.transferTo(ofile)
            this.state = ERROR_INFO.get("SUCCESS");
            this.size = file.size;
        } catch (FileUploadBase.SizeLimitExceededException e) {
            this.state = ERROR_INFO.get("SIZE");
        } catch (FileUploadBase.InvalidContentTypeException e) {
            this.state = ERROR_INFO.get("ENTYPE");
        } catch (FileUploadException e) {
            this.state = ERROR_INFO.get("REQUEST");
        } catch (Exception e) {
            this.state = ERROR_INFO.get("UNKNOWN");
        }
    }

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 依据原始文件名生成新文件名
     * @return
     */
    private String getName(String fileName) {
        Random random = new Random();
        return this.fileName = Integer.toHexString(random.nextInt(256)) + "/" + System.currentTimeMillis() + "_" + fileName;
    }

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     * @param path
     * @return
     */
    private String getFolder(String path) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        File dir = new File(this.getPhysicalPath(path));
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                this.state = ERROR_INFO.get("DIR");
                return "";
            }
        }
        return path;
    }

    /**
     * 根据传入的虚拟路径获取物理路径
     *
     * @param path
     * @return
     */
    public String getPhysicalPath(String path) {
        String servletPath = this.request.getServletPath();
        String realPath = this.request.getSession().getServletContext()
                .getRealPath(servletPath);
        return new File(realPath).getParent() + "/" + savePath + "/" + path;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setAllowFiles(String[] allowFiles) {
        this.allowFiles = allowFiles;
    }

    public void setMaxSize(int size) {
        this.maxSize = size;
    }

    public long getSize() {
        return this.size;
    }

    public String getUrl() {
        return this.url;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getState() {
        return this.state;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getOriginalName() {
        return this.originalName;
    }
}
