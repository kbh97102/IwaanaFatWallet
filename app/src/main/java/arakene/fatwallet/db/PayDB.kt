package arakene.fatwallet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import arakene.fatwallet.dao.PayDao
import arakene.fatwallet.dao.TagDao
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.test.PayConverter
import kotlinx.coroutines.CoroutineScope

@Database(entities = [PayDTO::class, PayTag::class], version = 2)
@TypeConverters(
    value = [
        PayConverter::class
    ]
)
abstract class PayDB : RoomDatabase() {

    abstract fun payDao(): PayDao
    abstract fun tagDao(): TagDao

    companion object {
        @Volatile
        private var INSTANCE: PayDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PayDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PayDB::class.java,
                    "pay_database"
                ).addCallback(PayCallBack(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class PayCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            db.execSQL("insert into tag_table (name, count) values ('고정지출', 1)")

//            INSTANCE?.let {
//                scope.launch {
//                    val dao = it.tagDao()
//
//                    val list = withContext(Dispatchers.IO) {
//                        dao.getAllTags()
//                    }
//
//                    list.asLiveData().value?.let {
//                        it.forEach { tag ->
//                            if (tag.name == PayTag.MONTHLYOUTPUT) {
//                                return@launch
//                            }
//                        }
//                    }
//
//                    dao.insert(PayTag(name = PayTag.MONTHLYOUTPUT, count = 1))
//                }
//            }
        }
    }
}
