package com.example.foodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class Convertor {
    @TypeConverter
    fun fromAnyToString(value: Any?): String {
        return value?.toString() ?: ""
    }

    @TypeConverter
    fun fromStringToAny(value: String?): Any? {
        return value
    }
}