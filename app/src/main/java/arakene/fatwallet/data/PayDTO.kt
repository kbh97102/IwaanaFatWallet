package arakene.fatwallet.data


data class PayDTO(
    var type: PayType? = null,
    var purpose: String? = null,
    var price: Long? = null,
    var description: String? = null,
    var tags: ArrayList<Tag> = arrayListOf(Tag("Default", 1)),
    var date: String? = null
) {

    override fun toString(): String {
        return "PayDTO(type=$type, purpose=$purpose, price=$price, description=$description, tags=$tags, date=$date)"
    }
}
