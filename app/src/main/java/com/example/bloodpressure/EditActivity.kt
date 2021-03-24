package com.example.bloodpressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bloodpressure.databinding.ActivityEditBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var realm: Realm
    private val tag = "BloodPressure"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        realm = Realm.getDefaultInstance()

        var saveBtn = binding.saveBtn //保存ボタン
        //保存ボタンを押したときの処理
        saveBtn.setOnClickListener {
            //各値の初期化
            var max:Long = 0
            var min:Long = 0
            var pulse:Long = 0

            var maxEdit = binding.maxEdit
            var minEdit = binding.minEdit
            var pulseEdit = binding.pulseEdit

            //textプロパティがNullや空文字ではないことをチェックする
            if(!maxEdit.text.isNullOrEmpty()){
                max = maxEdit.text.toString().toLong()
            }
            if(!minEdit.text.isNullOrEmpty()){
                min = minEdit.text.toString().toLong()
            }
            if(!pulseEdit.text.isNullOrEmpty()){
                pulse = pulseEdit.text.toString().toLong()
            }
            //トランザクションの実行
            realm.executeTransaction{
                //クエリの作成idの最大値を取得する
                val maxId = realm.where<BloodPress>().max("id")
                //maxIdがNullの時は0Lを返し、それ以外の場合はmaxIdを返す。
                val nextId = (maxId?.toLong() ?: 0L) + 1L
                //モデルのインスタンスを作成する(プライマリーキーの値を設定する)
                val bloodPress = realm.createObject<BloodPress>(nextId)
                bloodPress.dateTime = Date()
                bloodPress.max = max
                bloodPress.min = min
                bloodPress.pulse = pulse
            }
            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_LONG).show()
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}