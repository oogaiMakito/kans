package me.kimakima.om.kans;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Calendar;

public class AddKans extends Activity{

    //スピナー用変数
    private Spinner nSpinner;
    private String spinnerItems[] = {"ザ・プレミアム", "エメラルドマウンテン", "至福の微糖", "ヨーロピアン"};
    private TextView coffeeName;
    private TextView coffeePrice;

    //入力値用変数
    private int kanid;
    private String kanname;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_kans);

        //スピーナー処理
        coffeePrice = (TextView)findViewById(R.id.coffeePrice);
        nSpinner = (Spinner)findViewById(R.id.spinner1);

        // ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner に adapter をセット
        nSpinner.setAdapter(adapter);

        // リスナーを登録
        nSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            public void onItemSelected(AdapterView<?> parent, View viw, int arg2, long arg3) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();

                if (item.equals("ザ・プレミアム")) {
                    coffeePrice.setText("値段：130円");
                    kanid = 1;
                    kanname = "ザ・プレミアム";
                    price = 130;
                } else if (item.equals("エメラルドマウンテン")) {
                    coffeePrice.setText("値段：130円");
                    kanid = 2;
                    kanname = "エメラルドマウンテン";
                    price = 130;
                } else if (item.equals("至福の微糖")) {
                    coffeePrice.setText("値段：130円");
                    kanid = 3;
                    kanname = "至福の微糖";
                    price = 130;
                } else if (item.equals("ヨーロピアン")) {
                    coffeePrice.setText("値段：130円");
                    kanid = 4;
                    kanname = "ヨーロピアン";
                    price = 130;
                }
            }
            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        //DB連携
        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();


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


        //登録ボタン処理
        Button entryButton = (Button) findViewById(R.id.insert);
        entryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues insertValues = new ContentValues();
                insertValues.put("kanid", kanid);
                insertValues.put("kanname", kanname);
                insertValues.put("price", price);
                insertValues.put("date", nowDay);
                long id = db.insert("purchaseHis", kanname, insertValues);
                if (id < 0) {
                    //失敗
                } else {
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


