package com.paradoxcat.bowlingscoreboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class GameEndedDialog extends Dialog {
    private OnNewGameClicked onNewGameClicked;
    private String totalScore;

    GameEndedDialog(@NonNull Context context, String totalScore, OnNewGameClicked onNewGameClicked) {

        super(context);
        this.onNewGameClicked = onNewGameClicked;
        this.totalScore = totalScore;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_game_ended);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ((TextView) findViewById(R.id.totalScoreTv)).append(totalScore);
        findViewById(R.id.newGameBtn).setOnClickListener(view -> {
            onNewGameClicked.onNewGameClicked();
            dismiss();
        });
        findViewById(R.id.returnBtn).setOnClickListener(view -> dismiss());

    }

    public interface OnNewGameClicked {

        void onNewGameClicked();
    }


}
