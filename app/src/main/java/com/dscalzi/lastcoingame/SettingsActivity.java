package com.dscalzi.lastcoingame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText numCoins;
    private Spinner difficulty;
    private RadioGroup radioGroup;
    private Button helpButton;

    private volatile boolean validCoins = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.numCoins = (EditText) findViewById(R.id.coinsEditText);
        this.difficulty = (Spinner) findViewById(R.id.difficultySpinner);
        this.radioGroup = (RadioGroup) findViewById(R.id.orderRadioGroup);
        this.helpButton = (Button) findViewById(R.id.btnHelpMenu);
        Button startBtn = (Button) findViewById(R.id.startGameBtn);

        numCoins.setSelection(2);

        List<Map<String, String>> items = new ArrayList<>();

        Map<String, String> item0 = new HashMap<>(2);
        item0.put("text", "Easy");
        item0.put("subText", "Does not know how to win");
        items.add(item0);

        Map<String, String> item1 = new HashMap<>(2);
        item1.put("text", "Medium");
        item1.put("subText", "Makes human errors");
        items.add(item1);

        Map<String, String> item2 = new HashMap<>(2);
        item2.put("text", "Hard");
        item2.put("subText", "Plays flawlessly");
        items.add(item2);

        SimpleAdapter adapter = new SimpleAdapter(this, items,
                android.R.layout.simple_list_item_2, // This is the layout that will be used for the standard/static part of the spinner. (You can use android.R.layout.simple_list_item_2 if you want the subText to also be shown here.)
                new String[] {"text", "subText"},
                new int[] {android.R.id.text1, android.R.id.text2}
        ) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setTextColor(Color.parseColor("#FFFFFF"));
                text2.setTextColor(Color.parseColor("#FFFFFF"));

                return view;

            }

        };

        // This sets the layout that will be used when the dropdown views are shown. I'm using android.R.layout.simple_list_item_2 so the subtext will also be shown.
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);

        difficulty.setAdapter(adapter);

        final int spinnerPosition = 2;
        difficulty.setSelection(spinnerPosition);

        //Listens to the set coins box on the settings menu
        numCoins.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int val = Integer.parseInt(s.toString());
                    if (val > 5000 || val < 1) {
                        numCoins.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        validCoins = false;
                    } else {
                        numCoins.getBackground().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        validCoins = true;
                    }
                } catch (NumberFormatException ex) {
                    numCoins.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    validCoins = false;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(SettingsActivity.this, HelpActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });

        //Setup button click event
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validCoins) {
                    int coins = Integer.parseInt(numCoins.getText().toString());
                    int diff = CoinGame.parseDifficulty(difficulty.getSelectedItem().toString());
                    int goFirst = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId())); //0 first, 1 second
                    //Then switch screen to game
                    Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                    boolean first = goFirst == 0;
                    myIntent.putExtra("cgtotal", coins);
                    myIntent.putExtra("cgdiff", diff);
                    myIntent.putExtra("cgfirst", first);
                    finish();
                    SettingsActivity.this.startActivity(myIntent);

                } else {
                    Snackbar.make(view, "You can only play with 1-5000 coins.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}

