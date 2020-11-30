package stu.oop.yundisk.servercommon.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author vega
 */
public class File implements Serializable {
    private Integer fileid;

    private String filename;

    private String filetype;

    private String filepath;

    private String username;

    private String md5;

    private String filepathGuide;

    private String localpath;

    private Long filesize;

    private Integer upstatus;

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Integer getUpstatus() {
        return upstatus;
    }

    public void setUpstatus(Integer upstatus) {
        this.upstatus = upstatus ;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getFilepathGuide() {
        return filepathGuide;
    }

    public void setFilepathGuide(String filepathGuide) {
        this.filepathGuide = filepathGuide == null ? null : filepathGuide.trim();
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath == null ? null : localpath.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(md5, file.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(md5);
    }

    @Override
    public String toString() {
        return "File{" +
                "fileid=" + fileid +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", filepath='" + filepath + '\'' +
                ", username='" + username + '\'' +
                ", md5='" + md5 + '\'' +
                ", filepathGuide='" + filepathGuide + '\'' +
                ", localpath='" + localpath + '\'' +
                ", filesize=" + filesize +
                ", upstatus=" + upstatus +
                '}';
    }
}