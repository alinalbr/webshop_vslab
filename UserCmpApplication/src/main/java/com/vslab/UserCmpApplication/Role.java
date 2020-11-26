package com.vslab.UserCmpApplication;

import io.micrometer.core.lang.NonNull;

public class Role {

    private Long id;
    private String typ;
    private int level;

    public Role() {
    }

    public Role(Long id, String typ, int level) {
        this.id = id;
        this.typ = typ;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
