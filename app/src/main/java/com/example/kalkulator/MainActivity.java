package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            // okno na wynik
            TextView result = findViewById(R.id.result);
            CharSequence result_text = result.getText();
            // pobieram przycisk który był kliknięty
            Button button = findViewById(view.getId());
            CharSequence button_text = button.getText();
            // usuwam zero wiodące
            if(result_text.equals("0")){
                if(button_text.equals(".")){
                    result.append(button_text);
                }else if(!Character.isDigit(button_text.charAt(0))){
                    return;
                }
                else{
                    result.setText(button_text);
                }
                // blokada przed dwoma znakmi kolo siebie
            }else if(!Character.isDigit(result_text.charAt(result_text.length()-1))
                    && !Character.isDigit(button_text.charAt(button_text.length()-1))){
                return;
            }
            // blokada przed ustawieniem z0C
            else if(result_text.length()>1
                    && !Character.isDigit(result_text.charAt(result_text.length()-2))
                    && result_text.charAt(result_text.length()-2) != '.'
                    && result_text.charAt(result_text.length()-1) == '0'
                    && Character.isDigit(button_text.charAt(button_text.length()-1))){
                return;
            }
            else{
                result.append(button_text);
            }
        }
    }

    class  EqualsListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            TextView result = findViewById(R.id.result);
            String result_text = result.getText().toString();
            //walidacja wyrazenia
            if(!Character.isDigit(result_text.charAt(result_text.length()-1))){
                return;
            }

            // szukam operandow (liczb)
            String[] elements = result_text.split("[+,\\-,/,*]");
            ArrayList<Double> numbers = new ArrayList<>();
            for(String s: elements){
                numbers.add(Double.parseDouble(s));
            }

            // szukam operatorow
            Pattern pattern = Pattern.compile("[+,\\-,/,*]");
            Matcher m = pattern.matcher(result_text);
            ArrayList<String> operators = new ArrayList<>();
            while(m.find()){
                operators.add(m.group());
            }

            double outocome = numbers.get(0);
            for(int i=0; i<operators.size(); i++){
                if(operators.get(i).equals("+")){
                    outocome += numbers.get(i+1);
                }
                else if(operators.get(i).equals("-")){
                    outocome -= numbers.get(i+1);
                }
                else if(operators.get(i).equals("*")){
                    outocome *= numbers.get(i+1);
                }
                else if(operators.get(i).equals("/")){
                    if(numbers.get(i+1) == 0){
                        Toast.makeText(getBaseContext(),
                                "Nie dziel przez zero", Toast.LENGTH_SHORT).show();
                        outocome = 0;
                    }
                    else{
                        outocome /= numbers.get(i+1);
                    }
                }
            }

            result.setText(Double.valueOf(outocome).toString());


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ustawienie listnerow
        findViewById(R.id.bD).setOnClickListener(new ButtonListener());
        findViewById(R.id.b0).setOnClickListener(new ButtonListener());
        findViewById(R.id.b1).setOnClickListener(new ButtonListener());
        findViewById(R.id.b2).setOnClickListener(new ButtonListener());
        findViewById(R.id.b3).setOnClickListener(new ButtonListener());
        findViewById(R.id.b4).setOnClickListener(new ButtonListener());
        findViewById(R.id.b5).setOnClickListener(new ButtonListener());
        findViewById(R.id.b6).setOnClickListener(new ButtonListener());
        findViewById(R.id.b7).setOnClickListener(new ButtonListener());
        findViewById(R.id.b8).setOnClickListener(new ButtonListener());
        findViewById(R.id.b9).setOnClickListener(new ButtonListener());
        findViewById(R.id.bM).setOnClickListener(new ButtonListener());
        findViewById(R.id.bMin).setOnClickListener(new ButtonListener());
        findViewById(R.id.bPlus).setOnClickListener(new ButtonListener());
        findViewById(R.id.bK).setOnClickListener(new ButtonListener());

        findViewById(R.id.bR).setOnClickListener(new EqualsListener());
    }

}