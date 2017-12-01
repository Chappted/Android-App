package de.ka.chappted

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.ka.chappted.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, MainActivityFragment.newInstance())
                .commitAllowingStateLoss()
    }
}
