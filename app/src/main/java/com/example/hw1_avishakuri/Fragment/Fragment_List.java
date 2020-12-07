package com.example.hw1_avishakuri.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.Controller.CallBack_Top;
import com.example.hw1_avishakuri.R;

import java.util.ArrayList;

import static com.example.hw1_avishakuri.Activity.TopTenActivity.PACKAGE_NAME;

public class Fragment_List extends Fragment{
    private ArrayList<TextView> topTenTextView;
    private ArrayList<ImageView> topTenIMGView;
    private CallBack_Top callBack_top;
    private ArrayList<Player> topTenWinner;

    public Fragment_List(ArrayList<Player> topTenWinner) {
        this.topTenWinner = topTenWinner;
    }

    public void setCallBack_top(CallBack_Top _callBack_top) {
        this.callBack_top = _callBack_top;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        changeText();
        clickOnList();
        return view;
    }

    public void clickOnList() {
        for (int i = 0; i <10 ; i++) {
            moveLocationOfPlayer(i,topTenTextView.get(i),topTenWinner.get(i));
        }



    }

    private void moveLocationOfPlayer(int place, final TextView textViewPlayer, final Player player) {
        textViewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBack_top != null) {
                    if(player == null)
                        callBack_top.displayLocation(0,0);
                    else
                        callBack_top.displayLocation(player.getLatitude(),player.getLongitude());

                }
            }
        });
    }

    public void changeText() {

        for (int i = 0; i <10 ; i++) {
           if(topTenWinner.get(i) != null) {
                topTenTextView.get(i).setText(" "+ (i + 1) + ". Name: " + topTenWinner.get(i).getName().toString() + "\n Score: " + topTenWinner.get(i).getScore() + " \n");
                topTenIMGView.get(i).setImageResource(topTenWinner.get(i).getIdImage());
            }
            Log.d("pppp","" + topTenTextView.get(i).getText());
        }
    }

    private void findViews(View view) {
        topTenTextView = new ArrayList<TextView>();
        topTenIMGView = new ArrayList<ImageView>();
        for (int i = 1; i < 11; i++) {
            String name = "list_LBL_place" + (i);
            int idTxtView = view.getResources().getIdentifier(name, "id" , PACKAGE_NAME);
            name = "list_IMG_place" + (i);
            int idImgView = view.getResources().getIdentifier(name, "id" , PACKAGE_NAME);
            topTenTextView.add((i - 1), (TextView) view.findViewById(idTxtView));
            topTenIMGView.add((i - 1) , (ImageView) view.findViewById(idImgView));
        }
    }



}
