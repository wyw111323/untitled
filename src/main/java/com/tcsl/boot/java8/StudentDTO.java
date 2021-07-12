package com.tcsl.boot.java8;

import javax.swing.plaf.PanelUI;

public class StudentDTO {
    public StudentDTO(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int code;
    public String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
