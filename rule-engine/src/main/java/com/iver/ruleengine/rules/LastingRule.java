package com.iver.ruleengine.rules;

public abstract class LastingRule<T, K> implements Rule {

    protected T argument;
    protected K count;

    public LastingRule(T argument, K count) {
        this.argument = argument;
        this.count = count;
    }
}
