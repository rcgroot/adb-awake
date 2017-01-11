/*------------------------------------------------------------------------------
 **    Author: René de Groot
 ** Copyright: (c) 2017 René de Groot All Rights Reserved.
 **------------------------------------------------------------------------------
 ** No part of this file may be reproduced
 ** or transmitted in any form or by any
 ** means, electronic or mechanical, for the
 ** purpose, without the express written
 ** permission of the copyright holder.
 *------------------------------------------------------------------------------
 *
 *   This file is part of "Stay-awake on adb".
 *
 *   "Stay-awake on adb" is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   "Stay-awake on adb" is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with "Stay-awake on adb".  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package nl.renedegroot.android.adbawake.businessmodel

import android.content.Context
import android.os.PowerManager
import nl.renedegroot.android.adbawake.configuration.SharedModel

class LockControl(context: Context) {

    val TAG = "adbawake"

    companion object {
        var dimLock: PowerManager.WakeLock? = null
        var wakeLock: PowerManager.WakeLock? = null
    }

    init {
        if (wakeLock == null) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            dimLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG)
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)
        }
    }

    fun enableWakelock(enabled: Boolean) {
        val model = SharedModel.instance
        model.wakeLocked.set(enabled)
        if (enabled) {
            wakeLock?.acquire()
            dimLock?.acquire()
        } else if (wakeLock?.isHeld ?: false) {
            wakeLock?.release()
            dimLock?.release()
        }
    }
}