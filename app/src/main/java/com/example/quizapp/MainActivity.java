package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //global variables
    int score = 0;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void one_right_answer(View view) {
        boolean answerOneChecked = ((RadioButton) view).isChecked();
        if (answerOneChecked) {
            score += 1;
        }
    }

    public void two_right_answer(View view) {
        boolean answerTwoChecked = ((RadioButton) view).isChecked();
        if (answerTwoChecked) {
            score += 1;
        }
    }

    public void three_right_answer(View view) {
        boolean answerThreeChecked = ((RadioButton) view).isChecked();
        if (answerThreeChecked) {
            score += 1;
        }
    }

    private int four_right_answer() {
        EditText answerFourView = findViewById(R.id.four_answer_text);
        String answerFour = answerFourView.getText().toString();
        int answerFourNumber=Integer.parseInt(answerFour);
        if (answerFourNumber == 4) {
            score += 1;
        }return score;
    }

    public void submit_answers(View view) {
        showResults();

        Toast toast = Toast.makeText(getApplicationContext(),
                "Your score is " + score + " points!!",
                Toast.LENGTH_LONG);
        toast.show();
    }

    private int scoreForFifth(){
        CheckBox answerFiveACheckBox = findViewById(R.id.five_right_answer_a);
        Boolean answerFiveAChecked = answerFiveACheckBox.isChecked();
        CheckBox answerFiveBCheckBox = findViewById(R.id.five_right_answer_b);
        Boolean answerFiveBChecked = answerFiveBCheckBox.isChecked();
        CheckBox answerFiveWrongCheckBox = findViewById(R.id.five_wrong_answer);
        Boolean answerFiveWrongChecked = answerFiveBCheckBox.isChecked();

        if (answerFiveAChecked && answerFiveBChecked && !answerFiveWrongChecked) {
        score += 1;
        }
        return score;
    }

    private String showResults() {
        EditText playersName = findViewById(R.id.name_edit_text);
        name = playersName.getText().toString();
        TextView manualTextView = findViewById(R.id.manual);
        manualTextView.setVisibility(View.GONE);
        four_right_answer();
        scoreForFifth();

        String result;
        String emailResult;
        if (score <= 1) {
            emailResult = "Your score ins\'t the best one... it's " + score + ".";
            result = "Ouch... Nobodies perfect.";
        } else if (score >= 4 ){
            emailResult = "Awesome!! You have " + score +" points... Like a BOSS:)";
            result = "Grrrrrreat!";
        } else {
            emailResult = score + " points... Not bad.";
            result = "Not good, not bad.";
        }

        //Check if check box is checked
        CheckBox sendByMailCheckBox = findViewById(R.id.email_me_check_box);
        Boolean sendByMail = sendByMailCheckBox.isChecked();
        sendByMailCheckBox.setVisibility(View.GONE);

        //Get email
        EditText emailInput = findViewById(R.id.input_email);
        TextView resultsTextView = findViewById(R.id.results_text_view);
        resultsTextView.setText(result);
        Button submitAnswerButton = findViewById(R.id.submit_answer_button);
        submitAnswerButton.setVisibility(View.GONE);
        Button resetButton = findViewById(R.id.send_mail_button);

        if (sendByMail) {
            resetButton.setVisibility(View.VISIBLE);
            emailInput.setVisibility(View.VISIBLE);
        }

        return emailResult;
    }

    public void send_mail_click(View view) {
        EditText emailInput = findViewById(R.id.input_email);
        String email = emailInput.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+ email));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz app results" );
        intent.putExtra(Intent.EXTRA_TEXT,"Hi " + name + ",\n" + showResults());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
