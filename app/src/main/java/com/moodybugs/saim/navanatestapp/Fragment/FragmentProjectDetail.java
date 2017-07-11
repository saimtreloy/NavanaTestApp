package com.moodybugs.saim.navanatestapp.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
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
import com.moodybugs.saim.navanatestapp.Activity.MapsActivity;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.ApiUrl;


public class FragmentProjectDetail extends Fragment {

    View view;

    ProgressBar progDetail;
    ImageView imgProjectDetail;
    TextView txtProDetailName, txtProDetailAddress, txtProDetailSize, txtProDetailType, txtProDetailStatus;

    String id, name, type,status, size, city, area_name, address, image;
    Double longitude, latitude;

    ImageView imgBtnLocation, imgBtnShare, imgBtnMessage, imgBtnCall;

    public FragmentProjectDetail() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_detail, container, false);

        //String strtext = getArguments().getString("edttext");

        id = getArguments().getString("Id");
        name = getArguments().getString("Name");
        type = getArguments().getString("Type");
        status = getArguments().getString("Status");
        size = getArguments().getString("Size");
        city = getArguments().getString("City");
        area_name = getArguments().getString("AreaName");
        address = getArguments().getString("Address");
        longitude = getArguments().getDouble("Longitude");
        latitude = getArguments().getDouble("Latitude");
        image = getArguments().getString("Image");


        if (haveStoragePermission()){

        }else {
            haveStoragePermission();
        }

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

        imgBtnLocation = (ImageView) view.findViewById(R.id.imgBtnLocation);
        imgBtnShare = (ImageView) view.findViewById(R.id.imgBtnShare);
        imgBtnMessage = (ImageView) view.findViewById(R.id.imgBtnMessage);
        imgBtnCall = (ImageView) view.findViewById(R.id.imgBtnCall);

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

        btnClicked();
    }


    public void btnClicked(){
        imgBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("Lat", latitude);
                intent.putExtra("Lon", longitude);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://navana-realestate.com/");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "16254"));
                startActivity(intent);
            }
        });
    }


    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

}
