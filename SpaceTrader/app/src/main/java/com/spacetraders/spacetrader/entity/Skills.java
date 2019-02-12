package com.spacetraders.spacetrader.entity;

public enum Skills {
    PILOT("pilot"),
    FIGHTER("fighter"),
    TRADER("trader"),
    ENGINEER("engineer")
    ;

    private final String skill;

    Skills(String skill) {
        this.skill = skill;
    }

}
