package me.kimakima.om.kans;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Calendar;

public class AddKans extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_kans);

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
        final EditText coffeeText = (EditText) findViewById(R.id.editCoffee);
        final EditText priceText = (EditText) findViewById(R.id.editPrice);


        Button entryButton = (Button) findViewById(R.id.insert);
        entryButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String kanname = coffeeText.getText().toString();
                String price = priceText.getText().toString();

                ContentValues insertValues = new ContentValues();
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


