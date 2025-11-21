package org.example;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalDemo {
    
    // Примеры работы с map()
    public static void demonstrateMap() {
        System.out.println("=== Демонстрация map() ===");
        
        // map() - преобразование значения, если оно присутствует
        Optional<String> optional = Optional.of("hello");
        Optional<String> upperCase = optional.map(String::toUpperCase);
        System.out.println("   Optional.of(\"hello\").map(String::toUpperCase): " + upperCase.get());
        
        // map() с пустым Optional
        Optional<String> empty = Optional.empty();
        Optional<String> emptyMapped = empty.map(String::toUpperCase);
        System.out.println("   Optional.empty().map(String::toUpperCase): " + emptyMapped);
        System.out.println("   isEmpty(): " + emptyMapped.isEmpty());
        
        // Цепочка map()
        Optional<Integer> number = Optional.of(5);
        Optional<String> result = number
            .map(n -> n * 2)
            .map(n -> "Result: " + n);
        System.out.println("   Цепочка map(): " + result.get());
        
        System.out.println();
    }
    
    // Примеры работы с flatMap()
    public static void demonstrateFlatMap() {
        System.out.println("=== Демонстрация flatMap() ===");
        
        // flatMap() - для избежания вложенных Optional
        Optional<String> optional = Optional.of("hello");
        
        // Без flatMap - получаем Optional<Optional<String>>
        Optional<Optional<String>> nested = optional.map(s -> Optional.of(s.toUpperCase()));
        
        // С flatMap - получаем Optional<String>
        Optional<String> flat = optional.flatMap(s -> Optional.of(s.toUpperCase()));
        System.out.println("   flatMap() результат: " + flat.get());
        
        // Практический пример: получение длины строки, если она не null
        Optional<String> name = Optional.of("John");
        Optional<Integer> length = name.flatMap(s -> s.isEmpty() ? Optional.empty() : Optional.of(s.length()));
        System.out.println("   Длина строки через flatMap(): " + length.get());
        
        System.out.println();
    }
    
    // Примеры работы с filter()
    public static void demonstrateFilter() {
        System.out.println("=== Демонстрация filter() ===");
        
        Optional<Integer> number = Optional.of(10);
        
        // filter() - оставляет значение, если условие выполнено
        Optional<Integer> even = number.filter(n -> n % 2 == 0);
        System.out.println("   filter(n -> n % 2 == 0) для 10: " + even.get());
        
        Optional<Integer> odd = number.filter(n -> n % 2 != 0);
        System.out.println("   filter(n -> n % 2 != 0) для 10: " + odd.isEmpty());
        
        // Комбинация filter() и map()
        Optional<String> text = Optional.of("hello");
        Optional<String> result = text
            .filter(s -> s.length() > 3)
            .map(String::toUpperCase);
        System.out.println("   filter + map: " + result.get());
        
        System.out.println();
    }
    
    // Примеры работы с orElse(), orElseGet(), orElseThrow()
    public static void demonstrateOrElse() {
        System.out.println("=== Демонстрация orElse(), orElseGet(), orElseThrow() ===");
        
        // orElse() - возвращает значение по умолчанию
        Optional<String> empty = Optional.empty();
        String result1 = empty.orElse("default");
        System.out.println("   empty.orElse(\"default\"): " + result1);
        
        Optional<String> present = Optional.of("value");
        String result2 = present.orElse("default");
        System.out.println("   present.orElse(\"default\"): " + result2);
        
        // orElseGet() - ленивое вычисление значения по умолчанию
        String result3 = empty.orElseGet(() -> {
            System.out.println("      Вычисление значения по умолчанию...");
            return "computed default";
        });
        System.out.println("   empty.orElseGet(() -> ...): " + result3);
        
        // orElseThrow() - выбрасывает исключение, если значение отсутствует
        try {
            empty.orElseThrow(() -> new IllegalArgumentException("Значение отсутствует"));
        } catch (IllegalArgumentException e) {
            System.out.println("   empty.orElseThrow(): " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Примеры работы с ifPresent() и ifPresentOrElse()
    public static void demonstrateIfPresent() {
        System.out.println("=== Демонстрация ifPresent() и ifPresentOrElse() ===");
        
        Optional<String> present = Optional.of("hello");
        System.out.print("   ifPresent() для present: ");
        present.ifPresent(s -> System.out.println("Значение: " + s));
        
        Optional<String> empty = Optional.empty();
        System.out.print("   ifPresent() для empty: ");
        empty.ifPresent(s -> System.out.println("Значение: " + s));
        System.out.println("   (ничего не выведено)");
        
        // ifPresentOrElse() - выполняет действие, если значение есть, иначе другое действие
        System.out.print("   ifPresentOrElse() для present: ");
        present.ifPresentOrElse(
            s -> System.out.println("Значение: " + s),
            () -> System.out.println("Значение отсутствует")
        );
        
        System.out.print("   ifPresentOrElse() для empty: ");
        empty.ifPresentOrElse(
            s -> System.out.println("Значение: " + s),
            () -> System.out.println("Значение отсутствует")
        );
        
        System.out.println();
    }
    
    // Примеры работы с or()
    public static void demonstrateOr() {
        System.out.println("=== Демонстрация or() ===");
        
        // or() - возвращает альтернативный Optional, если текущий пуст
        Optional<String> empty = Optional.empty();
        Optional<String> alternative = Optional.of("alternative");
        
        Optional<String> result1 = empty.or(() -> alternative);
        System.out.println("   empty.or(() -> alternative): " + result1.get());
        
        Optional<String> present = Optional.of("present");
        Optional<String> result2 = present.or(() -> alternative);
        System.out.println("   present.or(() -> alternative): " + result2.get());
        
        System.out.println();
    }
    
    // Примеры работы с stream()
    public static void demonstrateStream() {
        System.out.println("=== Демонстрация stream() ===");
        
        // stream() - преобразует Optional в Stream
        Optional<String> present = Optional.of("hello");
        List<String> list1 = present.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("   present.stream(): " + list1);
        
        Optional<String> empty = Optional.empty();
        List<String> list2 = empty.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("   empty.stream(): " + list2);
        
        // Полезно для фильтрации списка Optional
        List<Optional<String>> optionals = List.of(
            Optional.of("one"),
            Optional.empty(),
            Optional.of("two"),
            Optional.empty(),
            Optional.of("three")
        );
        
        List<String> values = optionals.stream()
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
        System.out.println("   flatMap(Optional::stream) для списка: " + values);
        
        System.out.println();
    }
    
    // Комплексный пример: цепочка операций
    public static void demonstrateChaining() {
        System.out.println("=== Комплексный пример: цепочка операций ===");
        
        // Симуляция получения данных из базы
        Optional<String> userId = Optional.of("123");
        
        // Цепочка преобразований
        String result = userId
            .map(Integer::parseInt)
            .filter(id -> id > 0)
            .map(id -> "User-" + id)
            .map(String::toUpperCase)
            .orElse("INVALID_USER");
        
        System.out.println("   Цепочка: userId -> parseInt -> filter -> map -> map -> orElse");
        System.out.println("   Результат для \"123\": " + result);
        
        // С пустым значением
        Optional<String> emptyUserId = Optional.empty();
        String result2 = emptyUserId
            .map(Integer::parseInt)
            .filter(id -> id > 0)
            .map(id -> "User-" + id)
            .map(String::toUpperCase)
            .orElse("INVALID_USER");
        System.out.println("   Результат для empty: " + result2);
        
        System.out.println();
    }
    
    // Практический пример: работа с вложенными объектами
    public static void demonstrateNestedObjects() {
        System.out.println("=== Практический пример: вложенные объекты ===");
        
        class Address {
            private String street;
            public Address(String street) { this.street = street; }
            public String getStreet() { return street; }
        }
        
        class Person {
            private String name;
            private Address address;
            public Person(String name, Address address) {
                this.name = name;
                this.address = address;
            }
            public String getName() { return name; }
            public Address getAddress() { return address; }
        }
        
        // Безопасное получение вложенных значений
        Person person1 = new Person("John", new Address("Main St"));
        Optional<Person> optPerson1 = Optional.of(person1);
        
        String street1 = optPerson1
            .map(Person::getAddress)
            .map(Address::getStreet)
            .orElse("Unknown");
        System.out.println("   Улица для person1: " + street1);
        
        Person person2 = new Person("Jane", null);
        Optional<Person> optPerson2 = Optional.of(person2);
        
        String street2 = optPerson2
            .map(Person::getAddress)
            .map(Address::getStreet)
            .orElse("Unknown");
        System.out.println("   Улица для person2 (address = null): " + street2);
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрация возможностей Optional ===\n");
        
        demonstrateMap();
        demonstrateFlatMap();
        demonstrateFilter();
        demonstrateOrElse();
        demonstrateIfPresent();
        demonstrateOr();
        demonstrateStream();
        demonstrateChaining();
        demonstrateNestedObjects();
        
        System.out.println("=== Демонстрация завершена ===");
    }
}

