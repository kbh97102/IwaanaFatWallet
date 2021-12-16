package arakene.fatwallet.data


data class PayDTO(
    val type: PayType? = null,
    val purpose: String? = null,
    val price: Long? = null,
    val description: String? = null,
    val tags : List<Tag>? = null,
    val date : String? = null
) {

    override fun toString(): String {
        return "PayDTO(type=$type, purpose=$purpose, price=$price, description=$description, tags=$tags, date=$date)"
    }
}
