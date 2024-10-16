package com.example.app5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import com.example.app5.persistence.AppDatabase;
import com.example.app5.persistence.PlayerDAO;
import com.example.app5.persistence.PlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RankingActivity extends AppCompatActivity {

    private LinearLayout playerListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ranking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playerListContainer = findViewById(R.id.player_list_container);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "quiz_database").build();

        PlayerDAO playerDAO = db.playerDAO();

        LiveData<List<PlayerEntity>> liveData = playerDAO.findAll();

        liveData.observe(this, playerEntities -> {

            if (playerEntities != null) {
                playerEntities = playerEntities.stream().sorted((p1, p2) -> Integer.compare(p2.getHits(), p1.getHits())).collect(Collectors.toList());
                playerListContainer.removeAllViews();

                LayoutInflater inflater = LayoutInflater.from(this);
                for (PlayerEntity player : playerEntities) {
                    View playerView = inflater.inflate(R.layout.player_item, playerListContainer, false);

                    ImageView imageView = playerView.findViewById(R.id.player_image);
                    TextView nameView = playerView.findViewById(R.id.player_name);
                    TextView hitsView = playerView.findViewById(R.id.player_hits);

                    if (player.getImageUrl() != null) {
                        Glide.with(this)
                                .load(player.getImageUrl())
                                .placeholder(R.drawable.image_user)
                                .error(R.drawable.image_user)
                                .into(imageView);
                    } else imageView.setImageResource(R.drawable.image_user);

                    nameView.setText("Nome: " + player.getName());
                    hitsView.setText("Acertou: " + player.getHits());

                    playerListContainer.addView(playerView);
                }
            }
        });
    }
}