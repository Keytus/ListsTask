package com.task;

import java.util.ConcurrentModificationException;

/**
 * Кастомная реализация ArrayList
 * @autor Трофимук Глеб
 */
public class MyArrayList<T>{
    private Object[] array;
    private final int defaultInitLength = 8;
    private int size;
    private int modCount = 0;
    /**
     * Создает лист, принимая полученный лист как встроенный.
     * @param array Массив объектов, который будет принят как встроенный
     * @param size Размерность листа
     */
    private MyArrayList(Object[] array, int size){
        this.array = array;
        this.size = size;
    }
    /**
     * Создает пустой лист
     */
    public MyArrayList(){
        this.array = new Object[defaultInitLength];
        this.size = 0;
    }
    /**
     * Создает пустой лист с указанной размерностью встроенного массива
     * @param initSize Размерность встроенного массива
     * @throws IllegalArgumentException
     */
    public MyArrayList(int initSize){
        if (initSize > 0){
            this.array = new Object[initSize];
        } else if (initSize == 0) {
            this.array = new Object[]{};
        }
        else throw new IllegalArgumentException("Initial size must be natural");
        this.size = 0;
    }
    /**
     * Увеличивает размерность внутренного массива в два раза
     */
    private void expandArray(){
        Object[] newArray = new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
    /**
     * Проверка валидности индекса элемента листа
     * @param index Индекса элемента листа
     * @throws  ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    private void checkIndex(int index){
        if (index >= size) throw new ArrayIndexOutOfBoundsException();
        if (index < 0) throw new IllegalArgumentException("Index must not be negative");
    }
    /**
     * Находит индекс первого элемента листа, равному искомому объекту.
     * Если такого нет, возвращает -1
     * @param element Искомый объект
     * @return Индекс элемента или -1, если элемент не найден
     */
    private int indexOf(T element){
        if (element == null){
            for(int i = 0;i < size; i++){
                if (array[i] == null){
                    return i;
                }
            }
        }
        else {
            for(int i = 0;i < size; i++){
                if (array[i].hashCode() == element.hashCode()){
                    if (array[i].equals(element)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    /**
     * Добавляет элемент в конец листа.
     * При необходимости расширяет внутренний массив
     * @param element Добавляемый объект
     */
    public void add(T element){
        if (size == array.length) expandArray();
        array[size] = element;
        size++;
        modCount++;
    }
    /**
     * Добавляет элемент в лист по указанному индексу.
     * Элемент, ранее занимавший индекс, смешается влево,
     * как и все стоящие далее по листу элементы.
     * При необходимости расширяет внутренний массив
     * @param element Добавляемый объект
     * @param index Индекс, по которому будет размещен объект
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public void add(T element, int index){
        checkIndex(index);
        if (size == array.length) expandArray();
        for (int i = size-1;i != index-1;i--){
            array[i+1] = array[i];
        }
        array[index] = element;
        size++;
        modCount++;
    }
    /**
     * Удаляет элемент листа по указанному индексу.
     * Элементы, стоящие дальше указанного индекса, смещаются вправо
     * @param index Индекс, удаляемого элемента
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public void remove(int index){
        checkIndex(index);
        for(int i = index;i < size-1;i++){
            array[i] = array[i+1];
        }
        size--;
        array[size] = null;
        modCount--;
    }
    /**
     * Удаляет первый элемент листа, равный искомому объекту.
     * Элементы, стоящие дальше указанного индекса, смещаются вправо
     * @param element Объкт, равный удаляемому элементу листа
     * @return Возвращает true при успешном удалении; false - равный элемент не был найден
     */
    public boolean remove(T element){
        int index = indexOf(element);
        if (index < 0) return false;
        remove(index);
        return true;
    }
    /**
     * Возвращает элемент листа по указанному индексу.
     * @param index Индекс, искомого элемента
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     * @return Элемент листа
     */
    public T get(int index){
        checkIndex(index);
        return (T) array[index];
    }
    /**
     * Замешает элемент листа по указанному индексу.
     * @param element Новый элемент листа
     * @param index Индекс, замещаемого элемента
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public void set(T element, int index){
        checkIndex(index);
        array[index] = element;
    }
    /**
     * Возвращает количество элементов листа
     * @return Количество элементов листа
     */
    public int size(){
        return size;
    }
    /**
     * Возвращает образ подлиста.
     * Это означает, что все изменения, произошедшие с образом, отразятся на исходном листе.
     * Если в исходном листе произошли изменения(добавление или удаление элемента), то
     * образ не сможет продолжить свою работу
     * @param start Начальный индекс подлиста
     * @param end Конечный индекс подлиста
     * @throws IllegalArgumentException
     * @return Образ подлиста
     */
    public MyArrayList<T> subList(int start, int end){
        checkIndex(start);
        checkIndex(end);
        if (start > end)
            throw new IllegalArgumentException("Start index must be less than end");
        int newSize = end - start + 1;
        return new SubList<>(this, start, newSize);
    }

    private static class SubList<T> extends MyArrayList<T>{
        private final MyArrayList<T> root;
        private final SubList<T> parent;
        private final int offset;
        private int size;
        private int modCount;
        /**
         * Создает образ подлиста исходного листа
         * @param root Исходный лист
         * @param offset Смещение, начальный индекс подлиста в исходном листе
         * @param size Размер образа, конечный индекс подлиста в исходном листе
         */
        public SubList(MyArrayList<T> root, int offset, int size){
            this.root = root;
            this.offset = offset;
            this.size = size;
            this.modCount = root.modCount;
            this.parent = null;
        }
        /**
         * Создает образ подлиста на основании образа подлиста.
         * Оба образа ссылкаются на исходный лист.
         * Изменения образа отразятся как на родительском образе,
         * так на исходном листе.
         * Если изменения произойдут через родительский образ,
         * то дочерний не сможет продолжить свою работу.
         * @param parent Родительский образ
         * @param offset Смещение, начальный индекс подлиста в исходном листе
         * @param size Размер образа, конечный индекс подлиста в исходном листе
         */
        private SubList(SubList<T> parent, int offset, int size) {
            this.root = parent.root;
            this.parent = parent;
            this.offset = parent.offset + offset;
            this.size = size;
            this.modCount = parent.modCount;
        }
        /**
         * Проверка иземений исходного листа
         * @throws ConcurrentModificationException
         */
        private void checkForConcurrentModification() {
            if (root.modCount != modCount)
                throw new ConcurrentModificationException();
        }
        /**
         * Проверка валидности индекса элемента листа
         * @param index Индекса элемента листа
         * @throws  ArrayIndexOutOfBoundsException
         * @throws IllegalArgumentException
         */
        private void checkIndex(int index){
            if (index >= size) throw new ArrayIndexOutOfBoundsException();
            if (index < 0) throw new IllegalArgumentException("Index must not be negative");
        }
        /**
         * Регулирование размера и счетчика изменений образа и его родителей
         * @param sizeChange Количество изменений
         */
        private void updateSizeAndModCount(int sizeChange) {
            SubList<T> subList = this;
            do {
                subList.size += sizeChange;
                subList.modCount = root.modCount;
                subList = subList.parent;
            } while (subList != null);
        }
        /**
         * Добавляет элемент в конец образа путем добавления в исходный лист
         * по следующему индексу от конечного индекса образа
         * @param element Добавляемый объект
         */
        @Override
        public void add(T element){
            checkForConcurrentModification();
            root.add(element, offset + size);
            updateSizeAndModCount(1);
        }
        /**
         * Добавляет элемент по указанному идексу.
         * В исходном листе объект будет находится по указанный индекс + смещение
         * @param element Добавляемый объект
         * @param index Индекс, по которому будет размещен объект в образе
         * @throws ArrayIndexOutOfBoundsException
         * @throws IllegalArgumentException
         */
        @Override
        public void add(T element, int index){
            checkIndex(index);
            checkForConcurrentModification();
            root.add(element, offset + index);
            updateSizeAndModCount(1);
        }
        /**
         * Удаляет элемент листа по указанному индексу образа.
         * В исходном листе удаляется элемент по индексу:указанный индекс + смещение
         * @param index Индекс, удаляемого элемента
         * @throws ArrayIndexOutOfBoundsException
         * @throws IllegalArgumentException
         */
        @Override
        public void remove(int index){
            checkIndex(index);
            checkForConcurrentModification();
            root.remove(offset + index);
            updateSizeAndModCount(-1);
        }
        /**
         * Удаляет первый элемент образа, равный искомому объекту.
         * @param element Объкт, равный удаляемому элементу листа
         * @return Возвращает true при успешном удалении; false - равный элемент не был найден
         */
        @Override
        public boolean remove(T element){
            checkForConcurrentModification();
            boolean result = root.remove(element);
            if (result){
                updateSizeAndModCount(-1);
            }
            return result;
        }
        /**
         * Возвращает элемент образа по указанному индексу,
         * т.е. элемент исходного листа по индексу:указанный индекс + смещение
         * @param index Индекс, искомого элемента
         * @throws ArrayIndexOutOfBoundsException
         * @throws IllegalArgumentException
         * @return Элемент листа
         */
        @Override
        public T get(int index){
            checkIndex(index);
            checkForConcurrentModification();
            return root.get(offset+index);
        }
        /**
         * Замешает элемент образа по указанному индексу,
         * т.е. элемент исходного листа по индексу:указанный индекс + смещение
         * @param element Новый элемент листа
         * @param index Индекс, замещаемого элемента
         * @throws ArrayIndexOutOfBoundsException
         * @throws IllegalArgumentException
         */
        @Override
        public void set(T element, int index){
            checkIndex(index);
            checkForConcurrentModification();
            root.set(element, offset+index);
        }
        /**
         * Возвращает образ подлиста, основываясь на образе, который будет родительским для него
         * Это означает, что все изменения, произошедшие с образом,
         * отразятся на родительском образе.и исходном листе
         * Если в исходном листе произошли изменения(добавление или удаление элемента), то
         * образ не сможет продолжить свою работу.
         * Если изменения произойдут через родительский образ,
         * то дочерний не сможет продолжить свою работу
         * @param start Начальный индекс подлиста
         * @param end Конечный индекс подлиста
         * @throws IllegalArgumentException
         * @return Образ подлиста
         */
        @Override
        public MyArrayList<T> subList(int start, int end){
            checkIndex(start);
            checkIndex(end);
            checkForConcurrentModification();
            if (start > end) throw new IllegalArgumentException("Start index must be less than end");
            int newSize = end - start + 1;
            return new SubList<>(this, start, newSize);
        }
        /**
         * Возвращает количество элементов образа
         * @return Количество элементов образа
         */
        @Override
        public int size(){
            checkForConcurrentModification();
            return size;
        }
    }
}
