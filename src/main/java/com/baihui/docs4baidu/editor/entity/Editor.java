package com.baihui.docs4baidu.editor.entity;

import java.io.File;

/**
 * 编辑器配置
 *
 * @author xiayouxue
 * @date 2014/3/31 20:08
 * @modify 新增XX功能 xiayouxue 2014/3/31 20:08
 */
public class Editor implements Cloneable {


    private String name;
    private String serverUrl;
    private String apikey;
    private String persistence;
    private String format;
    private File content;
    private String fileUrl;
    private String filename;
    private String id;
    private String saveUrl;
    private String com;
    private String way;

    public Editor clone() {
        try {
            return (Editor) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //TODO 此异常处理方式
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getPersistence() {
        return persistence;
    }

    public void setPersistence(String persistence) {
        this.persistence = persistence;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaveUrl() {
        return saveUrl;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}
