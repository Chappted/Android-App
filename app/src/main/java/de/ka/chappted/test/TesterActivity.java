package de.ka.chappted.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import de.ka.chappted.R;

/**
 * Created by th on 05.01.18.
 */

public class TesterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tester);


        Button button = (Button) findViewById(R.id.content);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* repository.getUser(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });*/
            }
        });
    }
}
