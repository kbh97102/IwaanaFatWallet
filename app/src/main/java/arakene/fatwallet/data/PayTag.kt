package arakene.fatwallet.data

data class PayTag(
    var name: String = "default",
    var count: Int = 0
) {
    
    companion object{
        val MONTHLYOUTPUT = "고정지출"
    }
    
}