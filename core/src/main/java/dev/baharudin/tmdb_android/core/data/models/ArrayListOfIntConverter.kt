package dev.baharudin.tmdb_android.core.data.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArrayListOfIntConverter {
    @TypeConverter
    fun fromArrayListToString(value: ArrayList<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToArrayList(value: String): ArrayList<Int> {
        return try {
            Gson().fromJson(value, object : TypeToken<ArrayList<Int>>() {}.type)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}