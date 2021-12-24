package arakene.fatwallet.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.data.PayType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PayViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val collection: CollectionReference =
        db.collection(auth.currentUser!!.uid).document("pay").collection("list")
    private val list: MutableLiveData<List<PayDTO>> by lazy {
        MutableLiveData()
    }
    private val target: MutableLiveData<PayDTO> by lazy {
        MutableLiveData()
    }

    init {
        collection.addSnapshotListener { value, error ->
            list.value = value!!.toObjects(PayDTO::class.java)
        }
    }

    fun getPayList(): MutableLiveData<List<PayDTO>> = list

    fun getChangeTarget(): MutableLiveData<PayDTO> = target

    fun getSortedPaysByDate(date: String): ArrayList<PayDTO> {
        val sortedList = ArrayList<PayDTO>()
        list.value!!.forEach {
            if (it.date == date) {
                sortedList.add(it)
            }
        }
        return sortedList
    }

    fun getSortedPaysByTag(tag: PayTag): ArrayList<PayDTO> {
        val sortedList = ArrayList<PayDTO>()
        list.value!!.forEach {
            if (it.tags.contains(tag)) {
                sortedList.add(it)
            }
        }
        return sortedList
    }

    fun saveData(
        type: PayType,
        purpose: String,
        price: String,
        des: String,
        date: String,
        tags: String
    ) {

        val tagList = ArrayList<PayTag>()
        tags.split(" ").onEach { name ->
            if (name.trim() != "") {
                tagList.add(PayTag(name, 1))
            }
        }

        if (auth.currentUser != null) {
            collection
                .add(
                    PayDTO(
                        type,
                        purpose,
                        price.toLong(),
                        des,
                        tagList,
                        date
                    )
                )
                .addOnSuccessListener {
                    Log.d("Save", "Success")
                }
                .addOnFailureListener { e ->
                    Log.w("SaveError", e)
                }
        }
    }

    fun deleteData() {
        if (auth.currentUser == null) {
            return
        }
        if (target.value == null) {
            return
        }
        collection
            .whereEqualTo("type", target.value!!.type)
            .whereEqualTo("price", target.value!!.price!!.toLong())
            .whereEqualTo("purpose", target.value!!.purpose)
            .whereEqualTo("description", target.value!!.description)
            .get()
            .addOnCompleteListener {
                it.result.documents.forEach { it2 ->
                    it2.reference.delete().addOnCompleteListener {
                        Log.e("Delete", "Success")
                    }.addOnFailureListener {
                        Log.e("Delete", "fail")
                    }
                }
                target.value = null
            }.addOnFailureListener {
                target.value = null
                Log.e("Delete Get Data", "fail")
            }
    }

    fun updateData(data: PayDTO, tags: String) {
        if (auth.currentUser == null) {
            return
        }

        val tagList = ArrayList<PayTag>()
        tags.split(" ").let {
            it.forEach { name ->
                tagList.add(PayTag(name, 1))
            }
        }

        data.tags = tagList

        collection
            .whereEqualTo("type", target.value!!.type)
            .whereEqualTo("price", target.value!!.price!!.toLong())
            .whereEqualTo("purpose", target.value!!.purpose)
            .whereEqualTo("description", target.value!!.description)
            .get()
            .addOnCompleteListener {
                it.result.documents.forEach { it2 ->
                    it2.reference.set(data).addOnCompleteListener {
                        Log.e("Update", "Success")
                    }.addOnFailureListener {
                        Log.e("Update", "fail")
                    }
                }
                target.value = null
            }.addOnFailureListener {
                target.value = null
                Log.e("Update Get Data", "fail")
            }
    }

}