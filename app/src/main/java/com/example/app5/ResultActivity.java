package com.example.app5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.app5.persistence.AppDatabase;
import com.example.app5.persistence.PlayerDAO;
import com.example.app5.persistence.PlayerEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewName, textViewResult;
    private ImageView imageView;
    private Button button, buttonBack, buttonRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewName = findViewById(R.id.TVNome);
        textViewResult = findViewById(R.id.TVResultado);
        imageView = findViewById(R.id.IVUsuario);
        button = findViewById(R.id.Button);
        buttonBack = findViewById(R.id.ButtonBack);
        buttonRestart = findViewById(R.id.ButtonRestart);

        String name = getIntent().getStringExtra("NAME");
        String imageUrl = getIntent().getStringExtra("IMAGE");

        textViewName.setText(name);

        boolean[] totalResponse = new boolean[10];
        for (int i = 1; i <= 10; i++)
            totalResponse[i - 1] = getIntent().getBooleanExtra("RESP" + i, false);

        int result = 0;
        for (boolean value : totalResponse) if (value) result++;

        textViewResult.setText("Você acertou " + result + " questões.");

        if (getIntent().getBooleanExtra("IMAGE_PRESENT", false)) {
            Uri imageUri = Uri.parse(imageUrl);
            imageView.setImageURI(imageUri);
        }

        insertPlayer(imageUrl, name, result);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, RankingActivity.class);
            startActivity(intent);
        });

        buttonBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        buttonRestart.setOnClickListener(view -> {
            Intent intent = new Intent(this, Quiz1Activity.class);
            intent.putExtra("NAME", name);
            if (imageUrl != null) {
                intent.putExtra("IMAGE", imageUrl);
                intent.putExtra("IMAGE_PRESENT", true);
            }
            startActivity(intent);
        });
    }

    public void insertPlayer(String imageUrl, String name, Integer result) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "quiz_database").build();

        PlayerDAO playerDAO = db.playerDAO();

        PlayerEntity player = new PlayerEntity();
        player.setImageUrl(imageUrl);
        player.setName(name);
        player.setHits(result);


        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                playerDAO.save(player);
            }
        });
    }
}