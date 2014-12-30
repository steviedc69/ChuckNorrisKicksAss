package com.example.steven.chucknorriskicksass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity implements DownloadResultReceiver.Receiver{

    private ListView list;
    private Button button;
    private DownloadResultReceiver receiver;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.list);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startService()
    {
        receiver = new DownloadResultReceiver(new Handler());
        receiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, MyIntentService.class);

        intent.putExtra("receiver",receiver);
        intent.putExtra("requestId",101);

        startService(intent);

    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        switch (resultCode)
        {
            case MyIntentService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                break;
            case MyIntentService.STATUS_FINISHED:
                setProgressBarIndeterminateVisibility(false);
                String[]results = resultData.getStringArray("result");

                arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,results);
                list.setAdapter(arrayAdapter);
                break;
            case MyIntentService.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this,error,Toast.LENGTH_LONG).show();
                break;
        }
    }
}
