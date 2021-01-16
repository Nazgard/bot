package dev.makarov.bot.utils;

import java.util.ArrayList;

public class FixedArrayList<E> extends ArrayList<E> {

    private final int maxSize;

    public FixedArrayList(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @Override
    synchronized public boolean add(E e) {
        actualizeList();
        return super.add(e);
    }

    @Override
    synchronized public void add(int index, E element) {
        actualizeList();
        super.add(index, element);
    }

    private void actualizeList() {
        if (limitReached()) {
            deleteFirst();
        }
    }

    private boolean limitReached() {
        return size() == maxSize;
    }

    private void deleteFirst() {
        remove(0);
    }

}
