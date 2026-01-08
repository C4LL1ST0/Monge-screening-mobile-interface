package com.example.mongescreeninginterface.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mongescreeninginterface.R;

import java.util.HashMap;


public class ControlActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nameText, num1Text, num2Text, num3Text, pt1Text, pt2Text;

    private Spinner objectSelectorSpinner;

    private HashMap<String, Runnable> instertSelector = new HashMap<>();
    private HashMap<String, Runnable> visibilitySelector = new HashMap<>();
    private DrawModel drawModel;

    private String[] objectOptions = {
            "point", "line", "segment", "cube", "pyramid"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control);

        initiateVisibilitySelector();

        instertSelector.put(objectOptions[0], this::insertPt);
        instertSelector.put(objectOptions[1], this::insertLine);
        instertSelector.put(objectOptions[2], this::insertSegment);
        instertSelector.put(objectOptions[3], this::insertCube);
        instertSelector.put(objectOptions[4], this::insertPyramid);

        Button btnToCanvas = findViewById(R.id.btnToCanvas);
        btnToCanvas.setOnClickListener(v -> finish());

        drawModel = DrawModel.getInstance();

        nameText = findViewById(R.id.nameText);
        num1Text = findViewById(R.id.num1Text);
        num2Text = findViewById(R.id.num2Text);
        num3Text = findViewById(R.id.num3Text);
        pt1Text = findViewById(R.id.pt1Text);
        pt2Text = findViewById(R.id.pt2Text);

        objectSelectorSpinner = findViewById(R.id.objectSelectorSpinner);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                objectOptions
        );
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        objectSelectorSpinner.setAdapter(ad);
        objectSelectorSpinner.setOnItemSelectedListener(this);
    }

    private void initiateVisibilitySelector(){
        visibilitySelector.put(objectOptions[0], () -> {
            makeAllTextsVisible();
            pt1Text.setVisibility(View.GONE);
            pt2Text.setVisibility(View.GONE);
            num1Text.setHint("x: ");
            num2Text.setHint("y: ");
            num3Text.setHint("z: ");
        });
        visibilitySelector.put(objectOptions[1], () -> {
            makeAllTextsVisible();
            num1Text.setVisibility(View.GONE);
            num2Text.setVisibility(View.GONE);
            num3Text.setVisibility(View.GONE);
            pt1Text.setHint("pt1: ");
            pt2Text.setHint("pt2: ");
        });
        visibilitySelector.put(objectOptions[2], () -> {
            makeAllTextsVisible();
            num1Text.setVisibility(View.GONE);
            num2Text.setVisibility(View.GONE);
            num3Text.setVisibility(View.GONE);
            pt1Text.setHint("pt1: ");
            pt2Text.setHint("pt2: ");
        });
        visibilitySelector.put(objectOptions[3], () -> {
            makeAllTextsVisible();
            pt2Text.setVisibility(View.GONE);
            num2Text.setVisibility(View.GONE);
            num3Text.setVisibility(View.GONE);
            pt1Text.setHint("c: ");
            num1Text.setHint("a: ");
        });
        visibilitySelector.put(objectOptions[4], () -> {
            makeAllTextsVisible();
            pt2Text.setVisibility(View.GONE);
            pt1Text.setHint("c: ");
            num1Text.setHint("r: ");
            num2Text.setHint("h: ");
            num3Text.setHint("pc: ");
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        visibilitySelector.get(objectSelectorSpinner.getSelectedItem().toString()).run();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void makeAllTextsVisible(){
        nameText.setVisibility(View.VISIBLE);
        nameText.setText("");
        pt1Text.setVisibility(View.VISIBLE);
        pt1Text.setText("");
        pt2Text.setVisibility(View.VISIBLE);
        pt2Text.setText("");
        num1Text.setVisibility(View.VISIBLE);
        num1Text.setText("");
        num2Text.setVisibility(View.VISIBLE);
        num2Text.setText("");
        num3Text.setVisibility(View.VISIBLE);
        num3Text.setText("");
    }

    private void insertPt() {
        float x = 0, y = 0, z = 0;
        try{
            x = Integer.parseInt(num1Text.getText().toString());
            y = Integer.parseInt(num2Text.getText().toString());
            z = Integer.parseInt(num3Text.getText().toString());
        }catch (NumberFormatException e){
           Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
           return;
        }
        var name = nameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));
        try {
            drawModel.addPoint(name.toUpperCase(), x,y,z);
        }catch (RuntimeException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertLineLike(boolean asSegment) {
        var name = nameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));

        try{
            drawModel.addLineLike(name, asSegment, pt1Text.getText().toString()
                    , pt2Text.getText().toString());
        }catch (RuntimeException e){
            Toast.makeText(ControlActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertLine(){
        insertLineLike(false);
    }

    private void insertSegment(){
        insertLineLike(true);
    }

    private void insertCube() {
        float a;
        try {
            a = Float.parseFloat(num1Text.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        var name = nameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));
        try {
            drawModel.addCube(name.toUpperCase(), pt1Text.getText().toString(), a);
        }catch (RuntimeException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertPyramid() {
        float r = 0, h = 0;
        int pointCount = 0;
        try{
            r = Float.parseFloat(num1Text.getText().toString());
            h = Float.parseFloat(num2Text.getText().toString());
            pointCount = Integer.parseInt(num3Text.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        var name = nameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));
        try {
            drawModel.addPyramid(name, pt1Text.getText().toString(),
                    pointCount, r, h);
        } catch (RuntimeException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onInsertClick(View view) {
        instertSelector.get(objectSelectorSpinner.getSelectedItem().toString()).run();
    }
}