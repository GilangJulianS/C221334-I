package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cyclone.R;

/**
 * Created by macair on 1/14/16.
 */
public class TocFragment extends Fragment {

    private TextView txtToc;
    private Button btnClose;

    public TocFragment(){}

    public static TocFragment newInstance(){
        TocFragment fragment = new TocFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_toc, parent, false);

        txtToc = (TextView) v.findViewById(R.id.txt_toc);
        btnClose = (Button) v.findViewById(R.id.btn_close);

        txtToc.setText(
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse" +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non"+
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }
}
