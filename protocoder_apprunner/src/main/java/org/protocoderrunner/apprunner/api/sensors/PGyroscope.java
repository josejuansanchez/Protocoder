/*
* Part of Protocoder http://www.protocoder.org
* A prototyping platform for Android devices 
*
* Copyright (C) 2013 Victor Diaz Barrales victormdb@gmail.com
* 
* Protocoder is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Protocoder is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with Protocoder. If not, see <http://www.gnu.org/licenses/>.
*/

package org.protocoderrunner.apprunner.api.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.protocoderrunner.apidoc.annotation.ProtoMethod;
import org.protocoderrunner.apidoc.annotation.ProtoMethodParam;
import org.protocoderrunner.apprunner.api.other.WhatIsRunningInterface;

public class PGyroscope extends CustomSensorManager implements WhatIsRunningInterface {


    public interface GyroscopeListener extends CustomSensorListener {
        public void event(float x, float y, float z);

    }

    private final static String TAG = "PGyroscope";
    private GyroscopeListener mCallbackGyroscopeChange;

    public PGyroscope(Context c) {
        super(c);

        type = Sensor.TYPE_GYROSCOPE;
    }


    public void start() {
        if (running) {
            return;
        }
        super.start();

        listener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                mCallbackGyroscopeChange.event(event.values[0], event.values[1], event.values[2]);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                switch (accuracy) {
                    case SensorManager.SENSOR_STATUS_UNRELIABLE:
                        break;
                    case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                        break;
                    case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                        break;
                    case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                        break;
                }
            }

        };

        isSupported = sensormanager.registerListener(listener, sensor, speed);
    }


    @ProtoMethod(description = "Start the accelerometer. Returns x, y, z", example = "")
    @ProtoMethodParam(params = {"function(x, y, z)"})
    public void onChange(final GyroscopeListener callbackfn) {
        mCallbackGyroscopeChange = callbackfn;

        start();
    }

}
