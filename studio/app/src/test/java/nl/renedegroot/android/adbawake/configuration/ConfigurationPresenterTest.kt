package nl.renedegroot.android.adbawake.configuration

import android.content.Context
import nl.renedegroot.android.adbawake.AppComponent
import nl.renedegroot.android.adbawake.Application
import nl.renedegroot.android.adbawake.businessmodel.LockControl
import nl.renedegroot.android.adbawake.businessmodel.Preferences
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks


class ConfigurationPresenterTest {

    lateinit var sut: ConfigurationPresenter
    lateinit var viewModel: ConfigurationViewModel

    @Mock
    lateinit var mockAppComponent: AppComponent
    @Mock
    lateinit var mockContext: Context
    @Mock
    lateinit var mockLockControl: LockControl
    @Mock
    lateinit var mockPreferences: Preferences

    @Before
    fun setup() {
        initMocks(this)
        Application.appComponent = mockAppComponent
        viewModel = ConfigurationViewModel()
        sut = ConfigurationPresenter(viewModel)
        sut.lockControl = mockLockControl
        sut.preferences = mockPreferences
    }

    @Test
    fun testAllOnStart() {
        // Arrange
        `when`(mockLockControl.isAcquired).thenReturn(true)
        `when`(mockPreferences.isServiceEnabled(mockContext)).thenReturn(true)
        // Act
        sut.start(mockContext)
        // Assert
        verify(mockLockControl).addListener(sut)
        Assert.assertThat(true, `is`(viewModel.wakeLocked.get()))
        Assert.assertThat(true, `is`(viewModel.serviceEnabled.get()))
    }

    @Test
    fun testAllOffStart() {
        // Arrange
        `when`(mockLockControl.isAcquired).thenReturn(false)
        `when`(mockPreferences.isServiceEnabled(mockContext)).thenReturn(false)
        // Act
        sut.start(mockContext)
        // Assert
        verify(mockLockControl).addListener(sut)
        Assert.assertThat(false, `is`(viewModel.wakeLocked.get()))
        Assert.assertThat(false, `is`(viewModel.serviceEnabled.get()))
    }

    @Test
    fun testStop() {
        // Act
        sut.stop()
        // Assert
        verify(mockLockControl).removeListener(sut)
    }
}