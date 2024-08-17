package com.task;

import java.util.ConcurrentModificationException;

/**
 * Кастомная реализация LinkedList
 * @autor Трофимук Глеб
 */
public class MyLinkedList<T> {
    private Node head;
    private Node tail;
    private int size;
    private int modCount = 0;
    private class Node<T>{
        private T element;
        private Node next;
        private Node prev;

        Node(T element, Node next, Node prev){
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
    /**
     * Создает пустой лист
     */
    public MyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
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
     * Возвращает узел листа по указанному индексу
     * @param index Индекса элемента листа
     * @return Узел листа
     */
    private Node<T> getNode(int index){
        Node<T> currentNode;
        if(index < size / 2){
            currentNode = head;
            for (int i = 0; i != index; i++){
                currentNode = currentNode.next;
            }
        }
        else {
            currentNode = tail;
            for (int i = size - 1; i != index; i--){
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
    /**
     * Находит индекс первого элемента листа, равному искомому объекту.
     * Если такого нет, возвращает -1
     * @param element Искомый объект
     * @return Индекс элемента или -1, если элемент не найден
     */
    private int indexOf(T element){
        Node<T> currentNode;
        if (element == null){
            currentNode = head;
            for(int i = 0;i < size; i++){
                if (currentNode == null){
                    return i;
                }
                else {
                    currentNode = currentNode.next;
                }
            }
        }
        else {
            currentNode = head;
            for(int i = 0;i < size; i++){
                if (currentNode.element.hashCode() == element.hashCode()){
                    if (currentNode.element.equals(element)){
                        return i;
                    }
                }
                else {
                    currentNode = currentNode.next;
                }
            }
        }
        return -1;
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
        return getNode(index).element;
    }
    /**
     * Добавляет элемент в конец листа.
     * Этот элемент становится хвостовым.
     * Если это первый элемент листа он становиться головным.
     * @param element Добавляемый объект
     */
    public void add(T element){
        Node<T> newNode;
        if (head == null){
            newNode = new Node<>(element, null, null);
            head = newNode;
        }
        else {
            newNode = new Node<>(element, null, tail);
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        modCount++;
    }
    /**
     * Добавляет элемент в лист по указанному индексу.
     * Для узла, который ранее на этой позиции, новый будет предыдущим
     * Для узла(если такой был), который стоял слева от индекса, новый будет следующим
     * @param element Добавляемый объект
     * @param index Индекс, по которому будет размещен объект
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public void add(T element, int index){
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(element, currentNode, currentNode.prev);
        if (currentNode != head) currentNode.prev.next = newNode;
        else head = newNode;
        currentNode.prev = newNode;
        size++;
        modCount++;
    }
    /**
     * Удаляет элемент листа по указанному индексу.
     * Предыдущий узел старого элемента(если такой был)
     * становится предыдущим для следующего узла старого элемента.
     * Следующий узел старого элемента(если такой был)
     * становится следующий для предыдущего узла старого элемента.
     * @param index Индекс, удаляемого элемента
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public void remove(int index){
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        if (currentNode != head){
            currentNode.prev.next = currentNode.next;
        }
        else head = currentNode.next;
        if (currentNode != tail){
            currentNode.next.prev = currentNode.prev;
        }
        else tail = currentNode.prev;
        size--;
        modCount--;
    }
    /**
     * Удаляет первый элемент листа, равный искомому объекту.
     * Предыдущий узел старого элемента(если такой был)
     * становится предыдущим для следующего узла старого элемента.
     * Следующий узел старого элемента(если такой был)
     * становится следующий для предыдущего узла старого элемента.
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
     * Замешает элемент листа по указанному индексу.
     * @param element Новый элемент листа
     * @param index Индекс, замещаемого элемента
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    public void set(T element, int index){
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        currentNode.element = element;
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
    public MyLinkedList<T> subList(int start, int end){
        checkIndex(start);
        checkIndex(end);
        if (start > end) throw new IllegalArgumentException("Start index must be less than end");
        int newSize = end - start + 1;
        return new SubList<>(this, start, newSize);
    }
    /**
     * Возвращает количество элементов листа
     */
    public int size(){
        return size;
    }

    private static class SubList<T> extends MyLinkedList<T>{
        private final MyLinkedList<T> root;
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
        public SubList(MyLinkedList<T> root, int offset, int size){
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
        public MyLinkedList<T> subList(int start, int end){
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
