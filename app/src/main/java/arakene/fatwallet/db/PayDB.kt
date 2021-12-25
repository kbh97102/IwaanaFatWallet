package arakene.fatwallet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import arakene.fatwallet.dao.PayDao
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayType
import arakene.fatwallet.test.PayConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PayDTO::class], version = 1, exportSchema = false)
@TypeConverters(
    value = [
        PayConverter::class
    ]
)
abstract class PayDB : RoomDatabase() {

    abstract fun payDao(): PayDao

    companion object {
        @Volatile
        private var INSTANCE: PayDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PayDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PayDB::class.java,
                    "pay_database"
                )
                    .addCallback(PayCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class PayCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    var dao = it.payDao()

                    dao.deleteAll()

                    dao.insert(
                        PayDTO(
                            type = PayType.input,
                            purpose = "Test",
                            price = 123,
                            description = "",
                            date = "2021.12.21 (화요일)"
                        )
                    )
                }
            }
        }
    }
}
