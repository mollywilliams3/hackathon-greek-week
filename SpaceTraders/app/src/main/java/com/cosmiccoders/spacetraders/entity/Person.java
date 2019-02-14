package com.cosmiccoders.spacetraders.entity;

import java.util.EnumMap;

public class Person {
    /**
     * Creating all the necessary fields for a player
     * String name = name of player
     * All int values refer to the skills of the player
     * Difficulty refers the the difficulty level that the player selected
     **/
    private String name;
    private int currency;
    private String ship;
    private String difficulty;

    private EnumMap<Skills, Integer> skillsPoints = new EnumMap<>(Skills.class);

    /**
     * One param constructor for the player
     * @param name is the name of the person
     */
    public Person(String name) {
        this(name, 0, 0, 0, 0,
                1000, "Gnat", "easy");
    }

    /**
     * Parameter for every attribute of the person
     * @param name is the name of the person
     * @param pilot is the skill points in that category
     * @param fighter is the skill points in that category
     * @param trader is the skill points in that category
     * @param engineer is the skill points in that category
     * @param difficulty is the difficulty the player choses to play at
     */
    public Person(String name, int pilot, int fighter, int trader, int engineer,
                  String difficulty) {
        this(name, pilot, fighter, trader, engineer, 1000, "Gnat", difficulty);
    }

    /**
     * Parameter for every attribute of the person
     * @param name is the name of the person
     * @param pilot is the skill points in that category
     * @param fighter is the skill points in that category
     * @param trader is the skill points in that category
     * @param engineer is the skill points in that category
     * @param currency is the amount of money the person currently has
     * @param ship is the name of the ship of the person
     * @param difficulty is the difficulty the player choses to play at
     */
    public Person(String name, int pilot, int fighter, int trader, int engineer,
                  int currency, String ship, String difficulty) {
        this.name = name;

        skillsPoints.put(Skills.PILOT, pilot);
        skillsPoints.put(Skills.FIGHTER, fighter);
        skillsPoints.put(Skills.TRADER, trader);
        skillsPoints.put(Skills.ENGINEER, engineer);

        this.currency = currency;
        this.ship = ship;
        this.difficulty = difficulty;
    }

    public String getName() { return name; }

    public String getShip() { return ship; }

    public int getCurrency() { return currency; }

    public String getDifficulty() { return difficulty; }

    public int getSkill(Skills skill) { return skillsPoints.get(skill); }

    public void setName(String name) { this.name = name; }

    public void setShip(String ship) { this.ship = ship; }

    public void setCurrency(int curr) { currency = curr; }

    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public void setSkills(Skills skill, int points) { skillsPoints.put(skill, points); }

    public boolean checkSkillPointsInitial() {
        int currTotal = skillsPoints.get("pilot") + skillsPoints.get("figher")
                + skillsPoints.get("trader") + skillsPoints.get("engineer");

        return currTotal == 16;
    }
}