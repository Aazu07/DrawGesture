package com.aastha.gesture

import android.gesture.*
import android.gesture.GestureOverlayView.OnGesturePerformedListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aastha.gesture.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {
    lateinit var gesturelib: GestureLibrary
    lateinit var mBinding: MainActivityBinding
    val gestureListener: OnGesturePerformedListener =
        OnGesturePerformedListener { gestureOverlayView: GestureOverlayView, gesture: Gesture ->
            val predictions = gesturelib.recognize(gesture) as ArrayList<Prediction>
            if (predictions.size > 0) {
                predictions.forEach {
                    if (it.score > 1.0) {
                        Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
                        return@OnGesturePerformedListener
                    }
                }
                Toast.makeText(this, "No match found.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        gesturelib = GestureLibraries.fromRawResource(this, R.raw.gestures)
        if (!gesturelib.load()) {
            Log.e("MAIN_ACTIVITY", "UNABLE TO LOAD GESTURE")
            Toast.makeText(
                this, "No Prediction file to match.",
                Toast.LENGTH_SHORT
            ).show()
        }
        mBinding.gestureOverlay.addOnGesturePerformedListener(gestureListener)

    }
}