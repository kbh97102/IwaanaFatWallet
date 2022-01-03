package arakene.fatwallet.test

import androidx.room.TypeConverter
import arakene.fatwallet.data.PayTag
import com.google.gson.GsonBuilder

class TagConverter {

    @TypeConverter
    fun tagToJson(value: PayTag): String? {
        val gson = GsonBuilder().create()
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToTag(value: String): PayTag {
        val gson = GsonBuilder().create()
        return gson.fromJson(value, PayTag::class.java)
    }
}