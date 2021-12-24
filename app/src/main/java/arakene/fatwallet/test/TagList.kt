package arakene.fatwallet.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arakene.fatwallet.data.PayTag
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class TagList : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val collection: CollectionReference =
        db.collection(auth.currentUser!!.uid).document("tags").collection("list")

    private val target: MutableLiveData<PayTag> by lazy {
        MutableLiveData()
    }

    private val list: MutableLiveData<List<PayTag>> by lazy {
        MutableLiveData()
    }

    init {
        collection.addSnapshotListener { value, error ->
            list.value = value!!.toObjects(PayTag::class.java)
        }
    }



    fun getTagList(): MutableLiveData<List<PayTag>> = list

    fun setTarget(item: PayTag) {
        target.value = item
    }

    private fun isContains(tagName: String): Boolean {
        list.value!!.forEach {
            if (it.name == tagName) {
                return true
            }
        }
        return false
    }

    fun updateTag(tagName: String) {
        if (isContains(tagName)) {
            collection.whereEqualTo("name", tagName).get()
                .addOnCompleteListener {
                    it.result.documents.forEach { documentSnapshot ->
                        val tag = documentSnapshot.toObject(PayTag::class.java)!!
                        tag.count++
                        documentSnapshot.reference.set(tag).addOnCompleteListener {
                            Log.e("Tag Update", "Success")
                        }.addOnFailureListener {
                            Log.e("Tag Update", "Fail")
                        }
                    }
                }
        } else {
            addTag(tagName)
        }
    }

    fun addTag(tagName: String) {
        collection.add(PayTag(tagName, 1)).addOnCompleteListener {
            Log.e("Tag Add", "Success")
        }.addOnFailureListener {
            Log.e("Tag Add", "Fail")
        }
    }

    fun deleteTag(removeTarget: PayTag) {
        if (list.value!!.contains(removeTarget)) {
            collection.whereEqualTo("name", removeTarget.name).get()
                .addOnCompleteListener {
                    it.result.documents.forEach { documentSnapshot ->
                        documentSnapshot.reference.delete().addOnCompleteListener {
                            Log.e("Tag Delete", "Success")
                        }.addOnFailureListener {
                            Log.e("Tag Delete", "Fail")
                        }
                    }
                }
        }
    }
}