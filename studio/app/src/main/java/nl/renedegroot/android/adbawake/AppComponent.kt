package nl.renedegroot.android.adbawake

import dagger.Component
import nl.renedegroot.android.adbawake.businessmodel.LockControl
import nl.renedegroot.android.adbawake.businessmodel.Preferences
import nl.renedegroot.android.adbawake.businessmodel.Service
import nl.renedegroot.android.adbawake.configuration.ConfigurationPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(main: ConfigurationPresenter)
    fun inject(main: Service)
    fun inject(main: LockControl)
    fun inject(main: Preferences)
}