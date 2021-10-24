package arakene.fatwallet.dto


data class PayDTO(
    val type: PayType? = null,
    val purpose: String? = null,
    val price: Long? = null,
    val description: String? = null
)
