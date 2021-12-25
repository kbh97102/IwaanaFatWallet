package arakene.fatwallet.test

import androidx.room.TypeConverter
import arakene.fatwallet.data.PayTag
import com.google.gson.GsonBuilder

class PayConverter {

    @TypeConverter
    fun tagsToJson(value: List<PayTag>): String? {
        val gson = GsonBuilder().create()
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToTags(value: String): List<PayTag> {
        val gson = GsonBuilder().create()
        return gson.fromJson(value, Array<PayTag>::class.java).asList()
    }

}