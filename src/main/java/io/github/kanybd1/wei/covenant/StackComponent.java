package io.github.kanybd1.wei.covenant;

public class StackComponent {
    private int stacks;

    public StackComponent(int stacks) {
        this.stacks = stacks;
    }

    public StackComponent() {
        this(0);
    }

    public int getStacks() {
        return stacks;
    }

    public void addStacks(int amount) {
        this.stacks += amount;
    }
}