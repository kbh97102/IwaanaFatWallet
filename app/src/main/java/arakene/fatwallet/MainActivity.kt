package arakene.fatwallet

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import arakene.fatwallet.databinding.ActivityMainBinding
import arakene.fatwallet.databinding.MainMenuLayoutBinding
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.dto.PayType
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var binding : MainMenuLayoutBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private
    val getLoginResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(it.data!!)!!
            if (result.isSuccess) {
                firebaseAuthWithGoogle(result.signInAccount)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainMenuLayoutBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//
//        auth = FirebaseAuth.getInstance()
//        db = Firebase.firestore
//
//        loginByEmail()
//
//        saveData()
//        saveData()
//        saveData()
//        saveData()

        binding!!.currentMonthChart.apply {
            setUsePercentValues(true)
            description.text = ""
            isDrawHoleEnabled = false
            setTouchEnabled(false)
            setDrawEntryLabels(false)
            setExtraOffsets(20f, 0f, 20f, 20f)
            setUsePercentValues(true)
            isRotationEnabled = false
            setDrawEntryLabels(false)
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.isWordWrapEnabled = true
            legend.textSize = 20f
        }

        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(72f, "Food"))
        dataEntries.add(PieEntry(26f, "Game"))
        dataEntries.add(PieEntry(2f, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FF8A65"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        binding!!.currentMonthChart.data = data
        data.setValueTextSize(30f)
        binding!!.currentMonthChart.apply {
            setExtraOffsets(5f, 10f, 5f, 5f)
            animateY(1400, Easing.EaseInOutQuad)
            holeRadius = 58f
            transparentCircleRadius = 61f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setDrawCenterText(true)
            centerText = "Share"
        }.run { invalidate() }

    }

    private fun chartTest() {

    }

    private var test = 1

    private fun saveData() {
        db.collection("arakene").document("input").collection("list")
            .add(PayDTO(PayType.input, "${test++}", 123, "For Test"))
            .addOnSuccessListener {
                Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("SaveError", e)
            }
    }

    private fun loginByEmail() {
        auth.signInWithEmailAndPassword("test@naver.com", "password")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "${task.result.user}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun loginByGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        getLoginResult.launch(googleSignInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //GoMain
                }
            }
    }
}