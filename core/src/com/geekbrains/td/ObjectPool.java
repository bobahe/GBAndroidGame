package com.geekbrains.td;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализует паттерн пул объектов. Используется для повторного использования уже созданных объектов.
 * Если в пуле свободных объектов пусто, то происходит создание необходимого объекта.
 * @param <T>
 */
public abstract class ObjectPool<T extends Poolable> {
    // пул активных (используемых) объектов
    protected List<T> activeList;
    // пул неиспользуемых объектов
    protected List<T> freeList;

    public List<T> getActiveList() {
        return activeList;
    }

    public List<T> getFreeList() {
        return freeList;
    }

    /**
     * Создает объект, если он отсутствует в пуле неиспользуемых объектов.
     * Требует переопределения в классах наследниках.
     * @return
     */
    protected abstract T newObject();

    /**
     * Переносит объект из пула используемых в пул неиспользуемых
     * @param index
     */
    public void free(int index) {
        freeList.add(activeList.remove(index));
    }

    public ObjectPool() {
        this.activeList = new ArrayList<T>();
        this.freeList = new ArrayList<T>();
    }

    public ObjectPool(int size) {
        this.activeList = new ArrayList<T>();
        this.freeList = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            freeList.add(newObject());
        }
    }

    /**
     * Получает из пула объект для использования. Если объекта нет, то создает его и помещает в пул используемых
     * @return
     */
    public T getActiveElement() {
        if (freeList.size() == 0) {
            freeList.add(newObject());
        }
        T temp = freeList.remove(freeList.size() - 1);
        activeList.add(temp);
        return temp;
    }

    /**
     * Проверяет пул используемых объектов и помещает неиспользуемые в соответствующий пул
     */
    public void checkPool() {
        for (int i = activeList.size() - 1; i >= 0; i--) {
            if (!activeList.get(i).isActive()) {
                free(i);
            }
        }
    }
}
