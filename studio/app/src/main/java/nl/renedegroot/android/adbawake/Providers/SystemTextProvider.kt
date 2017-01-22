package nl.renedegroot.android.adbawake.Providers

import android.content.res.Resources
import android.util.Log

class SystemTextProvider {

    fun getSystemText(resourceName: String): String {
        var systemText: String = ""
        try {
            val stringId = Resources.getSystem().getIdentifier(resourceName, "string", "android")
            systemText = Resources.getSystem().getString(stringId)
            Log.d("Service", "For resource $resourceName the system text is $systemText")
        } catch (exception: Exception) {
            // NO-OP: Internal API usage safety-catch
        }

        return systemText
    }
}