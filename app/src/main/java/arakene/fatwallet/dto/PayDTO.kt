package arakene.fatwallet.dto


data class PayDTO(
    val type: PayType? = null,
    val purpose: String? = null,
    val price: Long? = null,
    val description: String? = null
) {
    override fun toString(): String {
        return "PayDTO(type=$type, purpose=$purpose, price=$price, description=$description)"
    }
}
