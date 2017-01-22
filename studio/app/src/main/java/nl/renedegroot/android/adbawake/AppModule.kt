package nl.renedegroot.android.adbawake

import dagger.Module
import dagger.Provides
import nl.renedegroot.android.adbawake.Providers.PowerManagerProvider
import nl.renedegroot.android.adbawake.Providers.SharedPreferencesProvider
import nl.renedegroot.android.adbawake.Providers.SystemTextProvider
import nl.renedegroot.android.adbawake.businessmodel.LockControl
import nl.renedegroot.android.adbawake.businessmodel.Preferences
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun systemTextProvider(): SystemTextProvider = SystemTextProvider()

    @Provides
    fun sharedPreferencesProvider(): SharedPreferencesProvider = SharedPreferencesProvider()

    @Provides
    fun powerManagerProvider(): PowerManagerProvider = PowerManagerProvider()

    @Provides
    fun preferences(): Preferences = Preferences()

    @Provides
    @Singleton
    fun lockControl(): LockControl = LockControl()
}