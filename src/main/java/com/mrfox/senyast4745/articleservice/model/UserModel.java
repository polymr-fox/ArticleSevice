package com.mrfox.senyast4745.articleservice.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "links", unique = true)
    private String links;

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Column(name = "update_at", nullable = false)
    private Date updateAt;

    @Column(name = "delete_at")
    private Date deleteAt;

    @Column(name = "role", nullable = false)
    private String role;

    public UserModel() {
        super();
    }

    public UserModel(String fullName, String login, String password, String mail, String links, Date createAt, Date updateAt, Date deleteAt, String role) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.links = links;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
