package arakene.fatwallet.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.dto.PayType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PayViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val collection: CollectionReference
    private val list: MutableLiveData<List<PayDTO>> by lazy {
        MutableLiveData()
    }

    private var testID = 1

    init {
        val test = ArrayList<PayDTO>()
        collection = db.collection(auth.currentUser!!.uid).document("pay").collection("list")
        collection.get().addOnCompleteListener {
            it.result.documents.forEach { documentSnapshot ->
                val result = documentSnapshot.toObject(PayDTO::class.java)
                test.add(result!!)
            }
        }
        list.value = test
    }

    fun getPayList(): MutableLiveData<List<PayDTO>> = list

    fun saveData() {
        if (auth.currentUser != null) {
            collection
                .add(PayDTO(PayType.input, "${testID++}", 123, "For Test"))
                .addOnSuccessListener {
                    Log.d("Save", "Success")
                }
                .addOnFailureListener { e ->
                    Log.w("SaveError", e)
                }
        }
    }

    fun deleteData(id: String) {
        if (auth.currentUser == null) {
            return
        }
        collection.document(id)
            .delete()
            .addOnCompleteListener {
                Log.d("Delete", "Success")
            }
            .addOnFailureListener {
                Log.d("Delete", "Fail", it)
            }
    }

    fun updateData(id: String, data: PayDTO) {
        if (auth.currentUser == null) {
            return
        }
        collection.document(id)
            .set(data)
            .addOnCompleteListener {
                Log.d("Update", "Success")
            }
            .addOnFailureListener {
                Log.d("Update", "Fail", it)
            }
    }

}