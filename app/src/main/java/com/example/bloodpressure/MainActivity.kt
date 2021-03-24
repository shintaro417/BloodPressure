package com.example.bloodpressure

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressure.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.content_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Realmインスタンスを生成
    private lateinit var realm: Realm
    //9th commit 変数宣言
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager:RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //インスタンスを取得する
        realm = Realm.getDefaultInstance()

        var fab = binding.fab

        //fabボタンがタップされたらインテントを作成してstartActivityメソッドを呼び出す
        fab.setOnClickListener{ view ->
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)

        }
    }

    /**
     * Activityが開始されたときに呼ばれる。Activity生成されたがユーザには見えない
     */
    override fun onStart() {
        super.onStart()
        //全件取得
        val realmResults = realm.where(BloodPress::class.java).findAll().sort("id",Sort.DESCENDING)
        layoutManager = LinearLayoutManager(this)
        binding.root.recyclerView.layoutManager = layoutManager

        adapter = CustomRecyclerViewAdapter(realmResults)
        binding.root.recyclerView.adapter = this.adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}