package com.codenjoy.dojo.services;

import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс-цепочка команд
 */
public class CommandChain {
    private final List<Command> chain = new LinkedList<>();

    /**
     * Добавляет к цепочке команд еще одну в конец
     * 
     * @param next следующая команда в цепочке
     * @return эта цепочка команд
     */
    public CommandChain then(Command direction) {
        chain.add(direction);
        return this;
    }

    /**
     * Добавляет к цепочке команд все команды из другой цепочки (в конец)
     * 
     * @param commandChain добавляемая цепочка команд
     * @return эта цепочка команд
     */
    public CommandChain then(CommandChain commandChain) {
        List<Command> list = new LinkedList<>(commandChain.chain);
        chain.addAll(list);
        return this;
    }

    /**
     * Возвращает строковое представление цепочки команд
     */
    public String toString() {
        List<String> stringList = chain.stream().map(d -> d.toString()).collect(toList());
        return String.join(", ", stringList);
    }
}
