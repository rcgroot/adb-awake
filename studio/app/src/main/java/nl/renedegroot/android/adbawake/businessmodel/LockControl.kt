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
import nl.renedegroot.android.adbawake.Application
import nl.renedegroot.android.adbawake.Providers.PowerManagerProvider
import javax.inject.Inject

class LockControl {

    var isAcquired = false
    private val LOCK_TAG = "adbawake"
    private var dimLock: PowerManager.WakeLock? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private val listeners = mutableSetOf<OnLockChangedListener>()

    @Inject
    lateinit var powerManagerProvider: PowerManagerProvider

    init {
        Application.appComponent.inject(this)
    }

    fun createLocks(context: Context) {
        val powerManager = powerManagerProvider.getPowerManager(context)
        if (dimLock == null) {
            dimLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, LOCK_TAG)
        }
        if (wakeLock == null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_TAG)
        }
    }

    fun enableWakelock(context: Context, enabled: Boolean) {
        if (enabled) {
            createLocks(context)
            wakeLock?.acquire()
            dimLock?.acquire()
            isAcquired = true
        } else if (wakeLock?.isHeld ?: false) {
            wakeLock?.release()
            dimLock?.release()
            isAcquired = false
        }
        listeners.forEach { it.onLockChanged(this, enabled) }
    }

    fun addListener(listener: OnLockChangedListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: OnLockChangedListener) {
        listeners.remove(listener)
    }

    interface OnLockChangedListener {
        fun onLockChanged(control: LockControl, newValue: Boolean)
    }

}
