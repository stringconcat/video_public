package org.example;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

@NullMarked
public class JSpecifyDemoRunner {
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрация JSpecify и NullAway ===\n");
        
        JSpecifyDemo demo = new JSpecifyDemo("RequiredField", "TestName");
        
        System.out.println("1. Работа с полями класса:");
        System.out.println("   getName(): " + demo.getName());
        System.out.println("   getRequiredField(): " + demo.getRequiredField());
        System.out.println("   getOptionalField(): " + demo.getOptionalField());
        System.out.println("   getDisplayName(): " + demo.getDisplayName());
        System.out.println();
        
        System.out.println("2. Изменение полей:");
        demo.setName("NewName");
        demo.setRequiredField("NewRequired");
        demo.setOptionalField("optional");
        System.out.println("   После изменений:");
        System.out.println("   getName(): " + demo.getName());
        System.out.println("   getRequiredField(): " + demo.getRequiredField());
        System.out.println("   getDisplayName(): " + demo.getDisplayName());
        System.out.println();
        
        System.out.println("3. Методы с @Nullable параметрами:");
        System.out.println("   processNullable(\"test\"): " + demo.processNullable("test"));
        System.out.println("   processNullable(null): " + demo.processNullable(null));
        System.out.println();
        
        System.out.println("4. Методы с @NonNull параметрами:");
        System.out.println("   processNonNull(\"test\"): " + demo.processNonNull("test"));
        System.out.println();
        System.out.println("5. Работа со списками (NonNull элементы):");
        String v = null;
        demo.addNonNullItem("123");
        demo.addNonNullItem("item2");
        demo.addNonNullItem("item3");
        List<String> nonNullItems = new ArrayList<>();
        nonNullItems.add("external1");
        nonNullItems.add("external2");
        System.out.println("   processNonNullList(): " + demo.processNonNullList(nonNullItems));
        System.out.println();
        
        System.out.println("6. Работа с Map (@Nullable значения):");
        demo.setNullableValue("key1", 100);
        demo.setNullableValue("key2", null);
        demo.setNullableValue("key3", 200);
        System.out.println("   getNullableValue(\"key1\"): " + demo.getNullableValue("key1"));
        System.out.println("   getNullableValue(\"key2\"): " + demo.getNullableValue("key2"));
        System.out.println("   getNullableValue(\"key3\"): " + demo.getNullableValue("key3"));
        System.out.println();
        
        System.out.println("7. Работа с Map (@NonNull ключи и значения):");
        demo.setRequiredValue("reqKey1", 1000);
        demo.setRequiredValue("reqKey2", 2000);
        System.out.println("   getRequiredValue(\"reqKey1\"): " + demo.getRequiredValue("reqKey1"));
        System.out.println("   getRequiredValue(\"reqKey2\"): " + demo.getRequiredValue("reqKey2"));
        System.out.println();
        
        System.out.println("8. @NullUnmarked методы:");
        System.out.println("   processUnmarked(\"test\"): " + demo.processUnmarked("test"));
        System.out.println("   processUnmarked(null): " + demo.processUnmarked(null));
        System.out.println();
        
        System.out.println("9. Обобщенные методы:");
        System.out.println("    processGeneric(\"string\"): " + demo.processGeneric("string"));
        System.out.println("    processGeneric(42): " + demo.processGeneric(42));
        System.out.println("    processGeneric(null): " + demo.processGeneric(null));
        System.out.println();
        
        System.out.println("10. Комплексный сценарий:");
        demo.setOptionalField(null);
        System.out.println("    getDisplayName() с null optionalField: " + demo.getDisplayName());
        demo.setOptionalField("Description");
        System.out.println("    getDisplayName() с optionalField: " + demo.getDisplayName());
        System.out.println();
        
        System.out.println("=== Демонстрация завершена ===");
    }
}

