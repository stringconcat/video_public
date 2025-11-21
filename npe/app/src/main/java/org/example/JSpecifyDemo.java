package org.example;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.*;

@NullMarked
public class JSpecifyDemo {
    
    private String name;
    
    private @Nullable String optionalField;
    
    private @NonNull String requiredField;
    
    private List<String> nonNullList;
    
    private List<@Nullable String> nullableList;
    
    private Map<String, @Nullable Integer> nullableMap;
    
    private Map<@NonNull String, @NonNull Integer> requiredMap;
    
    public JSpecifyDemo(@NonNull String requiredField, String name) {
        this.requiredField = requiredField;
        this.name = name;
        this.optionalField = null;
        this.nonNullList = new ArrayList<>();
        this.nullableList = new ArrayList<>();
        this.nullableMap = new HashMap<>();
        this.requiredMap = new HashMap<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public @Nullable String getOptionalField() {
        return optionalField;
    }
    
    public void setOptionalField(@Nullable String value) {
        this.optionalField = value;
    }
    
    public @NonNull String getRequiredField() {
        return requiredField;
    }
    
    public void setRequiredField(@NonNull String value) {
        this.requiredField = value;
    }
    
    public String processNullable(@Nullable String input) {
        if (input == null) {
            return "null";
        }
        return input.toUpperCase(Locale.getDefault());
    }
    
    public String processNonNull(@NonNull String input) {
        return input.toUpperCase(Locale.getDefault());
    }
    
    public void addNonNullItem(String item) {
        this.nonNullList.add(item);
    }
    
    public void addNullableItem(@Nullable String item) {
        this.nullableList.add(item);
    }
    
    public String processNonNullList(List<String> items) {
        return String.join(", ", items);
    }
    
    public String processNullableList(List<@Nullable String> items) {
        StringBuilder result = new StringBuilder();
        for (@Nullable String item : items) {
            if (item != null) {
                result.append(item).append(", ");
            }
        }
        return result.length() > 0 
            ? result.substring(0, result.length() - 2) 
            : "пусто";
    }
    
    public void setNullableValue(String key, @Nullable Integer value) {
        this.nullableMap.put(key, value);
    }
    
    public @Nullable Integer getNullableValue(String key) {
        return this.nullableMap.get(key);
    }
    
    public void setRequiredValue(@NonNull String key, @NonNull Integer value) {
        this.requiredMap.put(key, value);
    }
    
    public @NonNull Integer getRequiredValue(@NonNull String key) {
        Integer value = this.requiredMap.get(key);
        if (value == null) {
            throw new IllegalStateException("Required value not found for key: " + key);
        }
        return value;
    }
    
    @NullUnmarked
    public String processUnmarked(String input) {
        if (input == null) {
            return "null input";
        }
        return "Processed: " + input;
    }
    
    @NullUnmarked
    public <T> T processGeneric(T item) {
        return item;
    }
    
    public String getDisplayName() {
        if (optionalField != null) {
            return name + " (" + optionalField + ")";
        }
        return name;
    }
}

