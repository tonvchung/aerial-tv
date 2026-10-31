package app.orchard.tv.update

import android.content.Context
import app.orchard.tv.BuildConfig
import app.orchard.tv.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class PersonalBuildTest {
    @Test
    fun personalBuildHasIndependentIdentity() {
        assertEquals("app.orchard.tv.icybard", BuildConfig.APPLICATION_ID)
        assertEquals(BuildConfig.APPLICATION_ID, RuntimeEnvironment.getApplication().getString(R.string.revival_account_type))
        assertEquals("Icybard 1.0", BuildConfig.PERSONAL_VERSION_LABEL)
        assertFalse(BuildConfig.CHECK_FOR_UPDATES)
    }

    @Test
    fun cachedOrForcedUpdatesStayDisabled() {
        val context = RuntimeEnvironment.getApplication()
        val prefs = context.getSharedPreferences("updates", Context.MODE_PRIVATE)
        prefs.edit().putString("latest", "99.0").putLong("checked_at", 123L).commit()

        Updates.init(context)
        Updates.checkIfStale(force = true)

        assertNull(Updates.available)
        assertEquals(123L, prefs.getLong("checked_at", 0L))
    }
}
