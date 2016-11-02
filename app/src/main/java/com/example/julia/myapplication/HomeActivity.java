package com.example.julia.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.julia.myapplication.Adapter.ListAdapter;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.Evento;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends Activity {

    @BindView(R.id.textView_user)
    TextView textViewUser;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Preferences pr = new Preferences();
        textViewUser.setText("Bem vindo(a), Maria das Neves"/* + pr.getCurrentUser().getName() + "!"*/);

        listView = (ListView) findViewById(R.id.recipe_list_view);

        final ArrayList<Evento> list = new ArrayList<>();
        list.add(new Evento("Evento do bozo"));
        list.add(new Evento("Evento do jao"));
        list.add(new Evento("Evento do mijo"));
        list.add(new Evento("Evento do cocozao"));
        list.add(new Evento("Evento do chatuba"));
        list.add(new Evento("Evento do delicinha"));
        list.add(new Evento("Evento do cachorrao"));
        list.add(new Evento("Evento do satanas"));
        list.add(new Evento("Evento do capetao"));
        list.add(new Evento("Evento do pe de cabra"));
        list.add(new Evento("Evento do tranca rua"));

        ArrayAdapter adapter = new ListAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

}

