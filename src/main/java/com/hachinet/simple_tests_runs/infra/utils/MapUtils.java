package com.hachinet.simple_tests_runs.infra.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {


    public static Object pathValue(String attributePath, Object objectSource) {
        if (objectSource instanceof Map) return pathValue(attributePath, (Map<String, Object>) objectSource);

        throw new IllegalArgumentException("Attribute objectSource must be a java.util.Map.");
    }

    public static Object pathValue(String attributePath, Map<String, Object> sourceMap) {
        return pathValue(Arrays.stream(attributePath.split("\\.")).collect(Collectors.toList()), sourceMap, 0);
    }

    public static Object pathValue(final List<String> attributePath, final Map<String, Object> sourceMap, int index) {

        if (attributePath == null || attributePath.isEmpty() || sourceMap == null) {
            throw new IllegalArgumentException("Attribute path and map cannot be null or empty.");
        }

        String attribute = attributePath.get(index);

        Object currentObject = sourceMap.get(attribute);

        if (attributePath.size() - 1 > index) {
            if (currentObject instanceof Map)
                return pathValue(attributePath, (Map<String, Object>) currentObject, index + 1);
            return null;
        }

        return currentObject;
    }

}