package com.example.hw1_avishakuri.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hw1_avishakuri.Class.MyListAdapter;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.Other.CallBack_Top;
import com.example.hw1_avishakuri.R;

import java.util.ArrayList;

public class Fragment_List extends Fragment{
    private TextView textView;
    private ImageView imageView;
    private String[] listItemString;
    private Integer[] listItemInt;
    ListView listView;
    private ImageView list_IMG_background;
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
        findView(view);
        return view;
    }

    private void findView(View view) {
        listView = (ListView)view.findViewById(R.id.listView);
        textView=(TextView)view.findViewById(R.id.list_LBL_place);
        imageView=(ImageView)view.findViewById(R.id.list_IMG_place);
        list_IMG_background = (ImageView) view.findViewById(R.id.list_IMG_background);
        Glide
                .with(getActivity())
                .load(R.drawable.board_topten)
                .into(list_IMG_background);
        initListItem();//init the list of topTen for listItem
        initListView();// divide the name and score to string array and id of image to integer array and do listView

    }

    private void initListView() {
        for (int i = 0; i <10 ; i++) {
            if(topTenWinner.get(i)!=null) {
                listItemString[i] = ((i + 1)) + ". " + topTenWinner.get(i).getName() + ", Score " + ": " + topTenWinner.get(i).getScore();
                listItemInt[i] = topTenWinner.get(i).getIdImage();
            }
            else
                break;

        }
        MyListAdapter adapter;
        if(listItemString.length == 0) {
            listItemString = new String[1];
            listItemInt = new Integer[1];
            listItemString[0] = "The list is null ";
            listItemInt[0] = R.drawable.draw_flag;
            adapter = new MyListAdapter(getActivity(), listItemString, listItemInt);
        }
        else
            adapter=new MyListAdapter(getActivity(), listItemString, listItemInt);

        listView.setAdapter(adapter);
        clickOnItem();
    }

    private void initListItem() {
        ArrayList<String> tempArrayList = new ArrayList<String>() ;
        for (int i = 0; i <10 ; i++) {
            if(topTenWinner.get(i)!=null)
                tempArrayList.add (i, "In");
            else {
                break;
            }
        }
        listItemString = new String[tempArrayList.size()];
        listItemInt = new Integer[tempArrayList.size()];
    }

    private void clickOnItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//click on item move the location of the item that pressed
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(  position == 0 && listItemString[0] == "The list is null ") {//if the list null the defult is :(0,0)
                    callBack_top.displayLocation(0, 0);
                }
                    else{
                        callBack_top.displayLocation(topTenWinner.get(position).getLatitude(), topTenWinner.get(position).getLongitude());
                    }
//
            }
        });
    }


}
