package com.example.interaction_with_other_apps

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var button:Button
    lateinit var map:Button
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        Toast.makeText(this,uri.toString(),Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button=findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Toast.makeText(this,"Button Clicked",Toast.LENGTH_SHORT).show()
            val callIntent: Intent = Uri.parse("tel:23435646456").let { number ->
                Intent(Intent.ACTION_DIAL, number)
            }
            startActivity(callIntent)
        }
        map=findViewById<Button>(R.id.map)
        map.setOnClickListener {
            Toast.makeText(this,"Map Clicked",Toast.LENGTH_SHORT).show()
            val mapIntent: Intent = Uri.parse(
                "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"
            ).let { location ->
                // Or map point based on latitude/longitude
                // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent)
        }

        val selectButton = findViewById<Button>(R.id.select_button)

        selectButton.setOnClickListener {
            // Pass in the mime type you want to let the user select
            // as the input
            getContent.launch("image/*")
        }

        val data: Uri? = intent?.data
        Log.d("jashwant", "onCreate: data-"+data)

        // Figure out what to do based on the intent type
        if (intent?.type?.startsWith("image/") == true) {
            Toast.makeText(this,data.toString(),Toast.LENGTH_SHORT).show()
        } else if (intent?.type == "text/plain") {
            Toast.makeText(this,data.toString(),Toast.LENGTH_SHORT).show()
        }
        Intent("android.intent.action.SEND", Uri.parse("content://result_uri")).also { result ->
            Log.d("jashwant", "onCreate: result-"+result)
            setResult(Activity.RESULT_OK, result)
        }
        finish()

    }
}