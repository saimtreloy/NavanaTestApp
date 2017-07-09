package com.moodybugs.saim.navanatestapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.ApiUrl;


public class FragmentProjectDetail extends Fragment {

    View view;

    ProgressBar progDetail;
    ImageView imgProjectDetail;
    TextView txtProDetailName, txtProDetailAddress, txtProDetailSize, txtProDetailType, txtProDetailStatus;

    String name, address, size, type, status, image;

    public FragmentProjectDetail() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_detail, container, false);

        //String strtext = getArguments().getString("edttext");
        name = getArguments().getString("Name");
        address = getArguments().getString("Address");
        size = getArguments().getString("Size");
        type = getArguments().getString("Type");
        status = getArguments().getString("Status");
        image = getArguments().getString("Image");

        init();

        return view;
    }

    public void init(){
        progDetail = (ProgressBar) view.findViewById(R.id.progDetail);
        imgProjectDetail = (ImageView) view.findViewById(R.id.imgProjectDetail);

        txtProDetailName = (TextView) view.findViewById(R.id.txtProDetailName);
        txtProDetailAddress = (TextView) view.findViewById(R.id.txtProDetailAddress);
        txtProDetailSize = (TextView) view.findViewById(R.id.txtProDetailSize);
        txtProDetailType = (TextView) view.findViewById(R.id.txtProDetailType);
        txtProDetailStatus = (TextView) view.findViewById(R.id.txtProDetailStatus);

        populateViews();
    }


    public void populateViews(){
        Glide.with(getContext())
                .load(image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progDetail.setVisibility(View.GONE);
                        imgProjectDetail.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Image Ready", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .into(imgProjectDetail);

        txtProDetailName.setText(name);
        txtProDetailAddress.setText("Address : " + address);
        txtProDetailSize.setText("Size : " + size);
        txtProDetailType.setText("Type : " + type);
        txtProDetailStatus.setText("Status : " + status);
    }

}
