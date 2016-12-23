package com.artlite.sqltest;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.artlite.sqlib.callbacks.SQCursorCallback;
import com.artlite.sqlib.core.SQDatabase;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.model.SQFilter;
import com.artlite.sqlib.model.SQModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<SQClass> classes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SQModel sqlUser = new SQClass();
                SQDatabase.insert(sqlUser);
            }
        });
        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SQClass sqClass = classes.get(classes.size() - 1);
                sqClass.isSpecial = false;
                SQDatabase.update(sqClass);
            }
        });
        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes.clear();
                final SQFilter<Boolean> filter = new SQFilter<Boolean>("isSpecial", false);
                classes.addAll(SQDatabase.selectAll(SQClass.class,
                        new SQCursorCallback<SQClass>() {
                            @Override
                            public SQClass convert(@NonNull Cursor cursor) {
                                return new SQClass(cursor);
                            }
                        }, filter));
                classes.size();
            }
        });
    }
}
