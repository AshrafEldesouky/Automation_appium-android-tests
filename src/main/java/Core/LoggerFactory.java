// src/main/java/Core/LoggerFactory.java
package Core;

import org.slf4j.Logger;

public final class LoggerFactory {
    private LoggerFactory() {}

    public static Logger getLogger(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz); // ← لاحظ التسمية المؤهَّلة بالكامل
    }
}
