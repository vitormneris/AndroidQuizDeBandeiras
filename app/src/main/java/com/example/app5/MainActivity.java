package com.example.app5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    private ImageView imageView;
    private Button buttonFinish, buttonStart, buttonRanking;

    private Uri imageURI;

    private static final int REQUEST_READ_STORAGE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE_PERMISSION);
        }

        imageView = findViewById(R.id.IVUsuario);
        buttonStart = findViewById(R.id.ACPIniciar);
        buttonFinish = findViewById(R.id.ACPFinalizar);
        buttonRanking = findViewById(R.id.ButtonRanking);
        editTextName = findViewById(R.id.ETNome);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isNameFilled = editTextName.getText().toString().trim().isEmpty();
                buttonStart.setEnabled(!isNameFilled);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        editTextName.addTextChangedListener(textWatcher);

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Escolha sua imagem"), 1);
        });

        buttonStart.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Quiz1Activity.class);

            if (imageURI != null) {
                intent.putExtra("IMAGE", imageURI.toString());
                intent.putExtra("IMAGE_PRESENT", true);
            }

            intent.putExtra("NAME", editTextName.getText().toString());
            startActivity(intent);
        });

        buttonFinish.setOnClickListener(view -> finishAffinity());

        buttonRanking.setOnClickListener(view -> {
            Intent intent = new Intent(this, RankingActivity.class);
            startActivity(intent);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == 1) {
                imageView.setImageURI(data.getData());
                imageURI = data.getData();
            }
        }
    }
}