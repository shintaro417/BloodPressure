package com.example.bloodpressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bloodpressure.databinding.ActivityEditBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
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
//10th commit
        //intentの付加情報であるidをintent.getLongExtra()メソッドで取得する。デフォルト値は0L
        val bpId = intent.getLongExtra("id",0L)
        if(bpId > 0L){ //既存のレコードが存在する時
            //equalTo:引数に指定されたフィールドが指定された値と等しいレコードを選択する。findFirst:一件取得
            val bloodPress = realm.where<BloodPress>().equalTo("id",bpId).findFirst()
            binding.maxEdit.setText(bloodPress?.max.toString())
            binding.minEdit.setText(bloodPress?.min.toString())
            binding.pulseEdit.setText(bloodPress?.pulse.toString())
            binding.deleteBtn.visibility = View.VISIBLE //削除ボタンの表示
        }else{
            binding.deleteBtn.visibility = View.INVISIBLE //削除ボタンの非表示
        }
//10th commit
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
            when(bpId){ //10th commit(when)
                0L -> { //レコードの追加処理
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
                }
                //修正処理(10th commit)
                else -> {
                    realm.executeTransaction{
                        val bloodPress = realm.where<BloodPress>().equalTo("id",bpId).findFirst()
                        bloodPress?.max = max
                        bloodPress?.min = min
                        bloodPress?. pulse = pulse
                    }
                }
            }
            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_LONG).show()
            finish()
        }

        var deleteBtn = binding.deleteBtn
        //削除ボタンをタップしたときの処理(10th commit)
        deleteBtn.setOnClickListener {
            realm.executeTransaction{
                val bloodPress = realm.where<BloodPress>().equalTo("id",bpId)?.findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_LONG).show()
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}