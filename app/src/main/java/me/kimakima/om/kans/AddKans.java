package me.kimakima.om.kans;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Calendar;

public class AddKans extends Activity{

    //缶の名称と、値段の変数
    String kanid;
    String kanname;
    Integer price;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_kans);

        //レイアウトの中からspinnerを取得
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // ArrayAdapter を、string-array とデフォルトのレイアウトを引数にして生成
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kans_array, android.R.layout.simple_spinner_item);

        // 選択肢が表示された時に使用するレイアウトを指定
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // スピナーにアダプターを設定
        spinner.setAdapter(adapter);

        //イベント処理
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // 選択した要素で TextView を書き換え
                TextView kan_priceTextView = (TextView) findViewById(R.id.kan_price);

                // スピナー要素の文字列を取得
                String itemString = (String) parent.getItemAtPosition(position);

                // スピナー要素のIDを取得
                //long itemStringId = (long) parent.getItemIdAtPosition(position);
                //TypedArray kanidName  = getResources().obtainTypedArray(R.array.kans_array);
                //Integer itemStringId = kanidName.getResourceId(position, 0);

                // 選択されているアイテムのIndexを取得
                Integer idx = parent.getSelectedItemPosition();

                String priceTx = "0";
                if(idx == 0){
                    priceTx = "130";
                    price = 130;
                    kanid = "g1";
                }else if(idx == 1){
                    priceTx = "130";
                    price = 130;
                    kanid = "g2";
                }else if(idx == 2){
                    priceTx = "130";
                    price = 130;
                    kanid = "g3";
                }else if(idx == 3){
                    priceTx = "130";
                    price = 130;
                    kanid = "g4";
                }else if(idx == 4){
                    priceTx = "130";
                    price = 130;
                    kanid = "g5";
                }
                kan_priceTextView.setText(priceTx);
                kanname = itemString;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddKans.this, "NULL", Toast.LENGTH_SHORT).show();
            }
        });


        MyOpenHelper helper = new MyOpenHelper(this);

        //日付取得
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);
        final int ms = calendar.get(Calendar.MILLISECOND);
        final String nowDay = year+"/"+month+"/"+day+"　"+hour+":"+minute;
        final SQLiteDatabase db = helper.getWritableDatabase();


        Button entryButton = (Button) findViewById(R.id.insert);
        entryButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

                ContentValues insertValues = new ContentValues();
                insertValues.put("kanid", kanid);
                insertValues.put("kanname", kanname);
                insertValues.put("price", price);
                insertValues.put("date", nowDay);
                long id = db.insert("purchaseHis", kanname, insertValues);
                if (id < 0){
                    //失敗
                }else {
                    //成功
                    // 通知ダイアログの表示
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddKans.this);
                    builder.setMessage("登録が完了致しました。").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // ボタンをクリックしたときの動作
                            Intent dbIntent = new Intent(AddKans.this, MainActivity.class);
                            finish();
                            startActivity(dbIntent);
                        }
                    });
                    builder.show();
                }
            }
        });
    }
}


