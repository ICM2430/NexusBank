package com.example.snaphunters

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.rotationMatrix
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityCompassBinding
import org.w3c.dom.Text

class CompassActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var binding : ActivityCompassBinding

    private lateinit var sensorManager: SensorManager

    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private lateinit var compassImageView: ImageView
    private lateinit var directionTextView: TextView
    private lateinit var directionStatusTextView: TextView

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationValues = FloatArray(3)

    private var currentDegree = 0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        compassImageView = binding.compassImageView
        directionTextView = binding.directionTextView
        directionStatusTextView = binding.directionStatusTextView

    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        magnetometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent){
        when (event.sensor.type){
            Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
            Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }

        updateCompass()

    }

    override fun onAccuracyChanged(sensor: Sensor, acurracy: Int){

    }

    private fun updateCompass(){
        val success = SensorManager.getRotationMatrix(rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )

        if (success){
            SensorManager.getOrientation(rotationMatrix, orientationValues)

            val azimuthInRadians = orientationValues[0]
            val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()

            val smoothDegree = currentDegree * 0.9f + azimuthInDegrees * 0.1f

            compassImageView.rotation = -smoothDegree

            val direction = getDirection(smoothDegree)
            directionTextView.text = "Dirección: $direction"

            val directionStatus = getDirectionStatus(direction)
            directionStatusTextView.text = "Estado: $directionStatus"

            currentDegree = smoothDegree
        }

    }

    private fun getDirection(degree: Float): String {
        val directions = listOf("N", "NE", "E", "SE", "S", "SO", "O", "NO")
        val normalizedDegree = (degree + 360) % 360
        val directionIndex = (normalizedDegree / 45).toInt()
        return directions[directionIndex]
    }

    private fun getDirectionStatus(direction: String): String{
        return when (direction){
            "N" -> "Norte"
            "NE", "SE", "SO", "NO" -> "Apuntando a una direccion intermedia"
            "E" -> "Este"
            "S" -> "Sur"
            "O" -> "Oeste"
            else -> "Desconocido"

        }
    }

}