package io.github.sds100.keymapper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sds100.keymapper.data.KeyMapDao
import io.github.sds100.keymapper.util.FlagUtils
import io.github.sds100.keymapper.util.containsFlag

/**
 * Created by sds100 on 12/07/2018.
 */

@Entity(tableName = KeyMapDao.TABLE_NAME)
class KeyMap(
        @PrimaryKey(autoGenerate = true)
        val id: Long,

        @ColumnInfo(name = KeyMapDao.KEY_TRIGGER_LIST)
        var triggerList: MutableList<Trigger> = mutableListOf(),

        var actionList: MutableList<Action> = mutableListOf(),

        @ColumnInfo(name = KeyMapDao.KEY_FLAGS)
        /**
         * Flags are stored as bits.
         */
        var flags: Int = 0,

        @ColumnInfo(name = KeyMapDao.KEY_ENABLED)
        var isEnabled: Boolean = true
) {
    val isLongPress
        get() = containsFlag(flags, FlagUtils.FLAG_LONG_PRESS)

    override fun hashCode() = id.toInt()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KeyMap

        if (id != other.id) return false

        return true
    }

    fun clone() = KeyMap(id, triggerList, actionList, flags, isEnabled)

    fun containsTrigger(keyCodes: List<Int>): Boolean {
        return triggerList.any { trigger ->
            trigger.keys.toTypedArray().contentEquals(keyCodes.toTypedArray())
        }
    }
}