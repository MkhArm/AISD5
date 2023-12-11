import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // Создаем хеш-таблицу
        HashTable<String, Integer> hashTable = new HashTable<>();

        for (int i = 1; i <= 10000; i++) {
            String key = "Key" + i;
            int value = i;
            hashTable.add(key, value);
        }

        for (KeyValue<String, Integer> entry : hashTable) {
            System.out.println(entry);
        }

        printCollisions(hashTable);

        // Проверяем размер и емкость
        System.out.println();
        System.out.println("Size: " + hashTable.size());
        System.out.println("Capacity: " + hashTable.capacity());
        System.out.println("Количество коллизий: " + hashTable.getCollisions());


//        // Добавляем элементы
//        hashTable.add("One", 1);
//        hashTable.add("Two", 2);
//        hashTable.add("Three", 3);
//        hashTable.add("Four", 4);
//        hashTable.add("Five", 5);
//
//        // Выводим содержимое хеш-таблицы
//        System.out.println("Начальная Хеш-Таблица:");
//        for (KeyValue<String, Integer> entry : hashTable) {
//            System.out.println(entry);
//        }
//
//        // Проверяем размер и емкость
//        System.out.println("\nSize: " + hashTable.size());
//        System.out.println("Capacity: " + hashTable.capacity());
//
//        // Добавляем еще элементы, чтобы хеш-таблица увеличилась
//        hashTable.add("Six", 6);
//        hashTable.add("Seven", 7);
//        hashTable.add("Eight", 8);
//        hashTable.add("Nine", 9);
//        hashTable.add("Ten", 10);
//        hashTable.add("Eleven", 11);
//        hashTable.add("Twelve", 12);
//        hashTable.add("Thirteen", 13);
//
//        // Выводим содержимое хеш-таблицы после увеличения
//        System.out.println("\nХеш-Таблица после добавления элементов:");
//        for (KeyValue<String, Integer> entry : hashTable) {
//            System.out.println(entry);
//        }
//
//        // Проверяем размер и емкость после изменений
//        System.out.println("\nSize: " + hashTable.size());
//        System.out.println("Capacity: " + hashTable.capacity());
//
//        // Получаем значение по ключу
//        System.out.println("\nЗначение для ключа 'Three': " + hashTable.get("Three"));
//
//        // Удаляем элемент по ключу
//        hashTable.remove("Four");
//        System.out.println("\nПосле удаления 'Four':");
//        for (KeyValue<String, Integer> entry : hashTable) {
//            System.out.println(entry);
//        }
//
//        // Проверяем размер и емкость после изменений
//        System.out.println("\nSize: " + hashTable.size());
//        System.out.println("Capacity: " + hashTable.capacity());
    }

    private static <K, V> void printCollisions(HashTable<K, V> hashTable) {
        int k = 0;
        for (int i = 0; i < hashTable.capacity(); i++) {
            LinkedList<KeyValue<K, V>> slot = hashTable.slots[i];
            if (slot != null && slot.size() > 1) {
                k++;
                System.out.println("Коллизия в слоте " + i + ":");
                for (KeyValue<K, V> entry : slot) {
                    System.out.println("   " + entry);
                }
            }
        }
        System.out.println();
        System.out.println("Количество коллизий: " + k);
    }


}

