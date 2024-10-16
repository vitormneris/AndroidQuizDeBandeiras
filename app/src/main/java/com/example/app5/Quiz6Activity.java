package com.example.app5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz6Activity extends AppCompatActivity {
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        radioButton1 = findViewById(R.id.RBResposta1);
        radioButton2 = findViewById(R.id.RBResposta2);
        radioButton3 = findViewById(R.id.RBResposta3);
        radioButton4 = findViewById(R.id.RBResposta4);
        button = findViewById(R.id.Button);

        button.setEnabled(false);

        View.OnClickListener radioButtonListener = view -> button.setEnabled(true);

        radioButton1.setOnClickListener(radioButtonListener);
        radioButton2.setOnClickListener(radioButtonListener);
        radioButton3.setOnClickListener(radioButtonListener);
        radioButton4.setOnClickListener(radioButtonListener);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, Quiz7Activity.class);

            if (getIntent().getBooleanExtra("IMAGE_PRESENT", false)) {
                intent.putExtra("IMAGE", getIntent().getStringExtra("IMAGE"));
                intent.putExtra("IMAGE_PRESENT", true);
            }

            intent.putExtra("NAME", getIntent().getStringExtra("NAME"));

            intent.putExtra("RESP1", getIntent().getBooleanExtra("RESP1", false));
            intent.putExtra("RESP2", getIntent().getBooleanExtra("RESP2", false));
            intent.putExtra("RESP3", getIntent().getBooleanExtra("RESP3", false));
            intent.putExtra("RESP4", getIntent().getBooleanExtra("RESP4", false));
            intent.putExtra("RESP5", getIntent().getBooleanExtra("RESP5", false));

            if (radioButton1.isChecked())
                intent.putExtra("RESP6", false);
            else if (radioButton2.isChecked())
                intent.putExtra("RESP6", true);
            else if (radioButton3.isChecked())
                intent.putExtra("RESP6", false);
            else if (radioButton4.isChecked())
                intent.putExtra("RESP6", false);

            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}