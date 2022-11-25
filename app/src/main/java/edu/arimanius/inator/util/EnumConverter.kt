package edu.arimanius.inator.util

class EnumConverter<T: Enum<T>> {
    inline fun <reified T: Enum<T>> fromString(value: String?): T? {
        return value?.let { enumValueOf<T>(it) }
    }

    fun toString(value: T?): String? {
        return value?.name
    }
}
