package me.kimakima.om.kans;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //queryメソッドの実行例
        Cursor c = db.query("purchaseHis", new String[]{"_id","kanid", "kanname", "price", "date"}, null, null, null, null, "_id DESC");
        //Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_CREATED_TIME + " DESC")

        //先頭に移動
        c.moveToFirst();

        //総件数を取得
        Integer list_length = c.getCount();

        //総件数を取得
        List<ListItem> list = new ArrayList<ListItem>();

        for (int i = 0; i < list_length; i++) {
            ListItem item = new ListItem();

            //画像名を取得
            int imgId = getResources().getIdentifier(c.getString(1),"drawable",getPackageName());
            //テキストを取得
            item.setText(String.format("%s\n %s\n 値段: %d円", c.getString(4), c.getString(2), c.getInt(3)));

            item.setImageId(imgId);
            list.add(item);

            c.moveToNext();
        }
        c.close();
        db.close();

        // adapterのインスタンスを作成
        ImageArrayAdapter adapter = new ImageArrayAdapter(this, R.layout.list_view_image_item, list);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //リスト項目が長押しされた時のイベントを追加
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = position + "番目のアイテムが長押しされました";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return false;
            }
        });



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