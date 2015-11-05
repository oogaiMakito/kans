package me.kimakima.om.kans;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //queryメソッドの実行例
        Cursor c = db.query("purchaseHis", new String[]{"kanname", "price", "date"}, null, null, null, null, null);

        //先頭に移動
        c.moveToFirst();

        //総件数を取得
        String[] list = new String[c.getCount()];

        //ループでlist配列に値を代入
        for (int i = 0; i < list.length; i++) {
            list[i] = String.format("%s \n %s / %d円", c.getString(2), c.getString(0), c.getInt(1));
            c.moveToNext();
        }
        c.close();
        db.close();

        //「simple_list_item_1」は、もともと用意されている定義済みのレイアウトファイルのID
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    // メニュー選択処理
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                // 編集画面への遷移処理
                Intent intent = new Intent(MainActivity.this, AddKans.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

}