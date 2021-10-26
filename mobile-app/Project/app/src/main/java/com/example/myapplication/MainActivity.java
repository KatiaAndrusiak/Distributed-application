package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new MapsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();


//        ImageView diceImage = findViewById(R.id.dice_view);
//        rollButton.setOnClickListener(
//                view -> {
////                    rollButton.setText("Clicked!");
////                    Toast.makeText(this,"clicked!", Toast.LENGTH_SHORT).show();
//                    int random = (int) (Math.random() * 6) + 1;
//                    int drawable = 0;
//                    switch (random) {
//                        case 1:
//                            drawable = R.drawable.dice_1;
//                            break;
//                        case 2:
//                            drawable = R.drawable.dice_2;
//                            break;
//                        case 3:
//                            drawable = R.drawable.dice_3;
//                            break;
//                        case 4:
//                            drawable = R.drawable.dice_4;
//                            break;
//                        case 5:
//                            drawable = R.drawable.dice_5;
//                            break;
//                        default:
//                            drawable = R.drawable.dice_6;
//                    }
//                    ;
//                    diceImage.setImageResource(drawable);
//                }
//        );
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(  requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED))
//        {
//            Fragment fragment = new MapsFragment();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.frame_layout, fragment)
//                    .commit();
//        }else {
//            Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
//        }
//    }
}