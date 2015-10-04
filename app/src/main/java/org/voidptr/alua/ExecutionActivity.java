package org.voidptr.alua;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.voidptr.alua.luainterface.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class ExecutionActivity extends AppCompatActivity {
    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //TODO: Close, we don't have anything to do
        }else{
            Uri uri = (Uri)extras.get("execution_file");

            globals = JsePlatform.standardGlobals();
            globals.load(new android(this));

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                LuaValue chunk = globals.load(new InputStreamReader(inputStream), "main");
                LuaValue result = chunk.call();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
