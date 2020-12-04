package com.codenjoy.dojo.services;

/**
 * Имплементит возможные направления движения чего либо
 */
public enum Command {
    LEFT,
    RIGHT,
    DOWN,
    ROTATE_CLOCKWISE_90("ACT"),
    ROTATE_CLOCKWISE_180("ACT(2)"),
    ROTATE_CLOCKWISE_270("ACT(3)"),
    SUICIDE("ACT(0,0)");

    private final String text;

    Command() {
        this.text = this.name();
    }

    Command(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

    /**
     * @param dice Given dice.
     * @return Random direction for given dice.
     */
    public static Command random(Dice dice) {
        return Command.values()[dice.next(4)];
    }

    /**
     * Возвращает пустую цепочку команд
     * 
     * @return цепочка команд
     */
    public static CommandChain newChain() {
        return new CommandChain();
    }

    /**
     * Возвращает пустую цепочку команд с одной командой, от которой вызван метод
     * 
     * @return цепочка команд
     */
    public CommandChain asChain() {
        CommandChain chain = new CommandChain();
        return chain.then(this);
    }

    /**
     * Возвращает пустую цепочку команд с двумя командами: первая - от которой
     * вызван метод
     * 
     * @param next вторая команда в цепочке
     * @return цепочка команд
     */
    public CommandChain then(Command next) {
        return this.asChain().then(next);
    }
}