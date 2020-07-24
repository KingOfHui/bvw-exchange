package com.darknet.bvw.config.constant;

public enum PerformanceDateEnum {

    day_14("近14天", 1),

    day_30("近30天", 2),

    day_45("近45天", 3),

    day_60("近60天", 4);

    private String name;

    private int value;

    PerformanceDateEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
