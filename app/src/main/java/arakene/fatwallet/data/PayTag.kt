package arakene.fatwallet.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tag_table")
data class PayTag(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var name: String = "default",
    var count: Int = 0
) {
    
    companion object{
        val MONTHLYOUTPUT = "고정지출"
    }
    
}