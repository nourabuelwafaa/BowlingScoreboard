package com.paradoxcat.bowlingscoreboard;

import android.arch.lifecycle.ViewModelProviders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<FrameView> list = new ArrayList<>();
    private List<Integer> acceptedNums = new ArrayList<>();
    private GameViewModel viewModel;
    private String totalScore;
    private View scoreBoardCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        scoreBoardCv = findViewById(R.id.recyclerViewCv);
        final Button restartBtn = findViewById(R.id.restartBtn);
        final RecyclerView framesRv = findViewById(R.id.recyclerView);
        final RecyclerView numRv = findViewById(R.id.numRv);
        final TextView totalScoreTv = findViewById(R.id.totalScoreTv);

        framesRv.setLayoutManager(new GridLayoutManager(this, 5));
        framesRv.setAdapter(new FrameAdapter(list));

        numRv.setLayoutManager(new GridLayoutManager(this, 3));
        numRv.setAdapter(new NumberAdapter(acceptedNums, (num -> viewModel.onNextClicked(num))));

        restartBtn.setOnClickListener(v -> viewModel.onNewGameClicked());

        viewModel.getLiveFramesToView().observe(this, frameViews -> {
            if (frameViews != null) {
                list.clear();
                list.addAll(frameViews);
                framesRv.getAdapter().notifyItemRangeChanged(0, frameViews.size());

                if (frameViews.isEmpty()) {
                    restartBtn.setVisibility(View.GONE);
                    framesRv.getAdapter().notifyDataSetChanged();
                    scoreBoardCv.setVisibility(View.INVISIBLE);

                } else {
                    scoreBoardCv.setVisibility(View.VISIBLE);
                    restartBtn.setVisibility(View.VISIBLE);

                }

            } else {
                scoreBoardCv.setVisibility(View.INVISIBLE);

            }
        });

        viewModel.getTotalScore().observe(this, s -> {
            if (s != null) {
                totalScore = s;
                totalScoreTv.setText(R.string.total_score);
                totalScoreTv.append(s);
            } else {
                totalScoreTv.setText(R.string.enter_you_first_roll);
            }
        });

        viewModel.getIsGameEnded().observe(this, isGameEnded -> {
            if (isGameEnded != null && isGameEnded) {
                showGameEndedDialog();
            }

        });

        viewModel.getNextAcceptedNums().observe(this, maxNums -> {
            if (maxNums != null) {
                acceptedNums.clear();
                acceptedNums.addAll(maxNums);
                numRv.getAdapter().notifyDataSetChanged();
            }

        });
    }

    private void showGameEndedDialog() {
        GameEndedDialog dialog = new GameEndedDialog(this,
                totalScore, () -> viewModel.onNewGameClicked());
        dialog.show();
    }

}
