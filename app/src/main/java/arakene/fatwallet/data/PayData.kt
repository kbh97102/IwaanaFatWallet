package arakene.fatwallet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pay_table")
data class PayData(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var type: PayType? = null,
    var purpose: String? = null,
    var price: Long? = null,
    var description: String? = null,
    var tags: List<PayTag> = listOf(),
    var date: String? = null
) {

    override fun toString(): String {
        return "PayDTO(type=$type, purpose=$purpose, price=$price, description=$description, tags=$tags, date=$date)"
    }
}
