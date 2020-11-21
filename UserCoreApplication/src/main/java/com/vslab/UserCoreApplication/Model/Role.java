package com.vslab.UserCoreApplication.Model;

import io.micrometer.core.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class Role {

    //Primärschlüssel, dessen ID automatische erzeugt wird
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String typ;
    @NonNull
    private int level;

    public Role(String typ, int level) {
        this.typ = typ;
        this.level = level;
    }

    public Role() {
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
