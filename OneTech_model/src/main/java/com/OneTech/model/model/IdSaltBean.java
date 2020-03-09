package com.OneTech.model.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "id_salt")
public class IdSaltBean {
    @Id
    private String id;
    private String phone;
    private String salt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
