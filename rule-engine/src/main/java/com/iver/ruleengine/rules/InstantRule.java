package com.iver.ruleengine.rules;

public abstract class InstantRule<T> implements Rule {

    protected T argument;

    public InstantRule(T argument) {
        this.argument = argument;
    }
}
