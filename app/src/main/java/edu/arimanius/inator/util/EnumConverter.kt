package edu.arimanius.inator.util

import androidx.room.TypeConverter

open class EnumConverter<T: Enum<T>> {
    @TypeConverter
    inline fun <reified T: Enum<T>> fromString(value: String?): T? {
        return value?.let { enumValueOf<T>(it) }
    }

    @TypeConverter
    fun toString(value: T?): String? {
        return value?.name
    }
}
