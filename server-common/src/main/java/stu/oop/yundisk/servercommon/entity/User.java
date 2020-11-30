package stu.oop.yundisk.servercommon.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author vega
 */
public class User implements Serializable {
    private String username;

    private String password;

    private String nickname;

    private Float space;

    private Set<File> fileSet;

    public Set<File> getFileSet() {
        return fileSet;
    }

    public void setFileSet(Set<File> fileSet) {
        this.fileSet = fileSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Float getSpace() {
        return space;
    }

    public void setSpace(Float space) {
        this.space = space;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}