package com.moodybugs.saim.navanatestapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.moodybugs.saim.navanatestapp.Fragment.FragmentProjectDetail;
import com.moodybugs.saim.navanatestapp.Model.ModelFutureProject;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.ApiUrl;

import java.util.ArrayList;

/**
 * Created by Android on 7/9/2017.
 */

public class AdapterFutureProject extends RecyclerView.Adapter<AdapterFutureProject.AdapterFutureProjectHolder> {

    ArrayList<ModelFutureProject> adapterList = new ArrayList<>();
    Context mContext;
    Fragment fragment;

    public AdapterFutureProject(ArrayList<ModelFutureProject> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterFutureProject(ArrayList<ModelFutureProject> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    public AdapterFutureProject(ArrayList<ModelFutureProject> adapterList, Context mContext, Fragment fragment) {
        this.adapterList = adapterList;
        this.mContext = mContext;
        this.fragment = fragment;
    }

    @Override
    public AdapterFutureProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_future_project, parent, false);
        AdapterFutureProjectHolder adapterFutureProjectHolder = new AdapterFutureProjectHolder(view, adapterList);
        return adapterFutureProjectHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterFutureProjectHolder holder, int position) {
        holder.txtProjectList.setText(adapterList.get(position).getName());
        Glide.with(holder.txtProjectList.getContext())
                .load(ApiUrl.imageLink + adapterList.get(position). getImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progProject.setVisibility(View.GONE);
                        holder.imgProjectList.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.imgProjectList);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class AdapterFutureProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtProjectList;
        ImageView imgProjectList;
        ProgressBar progProject;
        ArrayList<ModelFutureProject> listFutureProject = new ArrayList<>();

        public AdapterFutureProjectHolder(View itemView, ArrayList<ModelFutureProject> albumList) {

            super(itemView);

            txtProjectList = (TextView) itemView.findViewById(R.id.txtProjectList);
            imgProjectList = (ImageView) itemView.findViewById(R.id.imgProjectList);
            progProject = (ProgressBar) itemView.findViewById(R.id.progProject);
            this.listFutureProject = albumList;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            String id = adapterList.get(getAdapterPosition()).getId();
            String name = adapterList.get(getAdapterPosition()).getName();
            String type = adapterList.get(getAdapterPosition()).getType();
            String status = adapterList.get(getAdapterPosition()).getStatus();
            String size = adapterList.get(getAdapterPosition()).getSize();
            String city = adapterList.get(getAdapterPosition()).getCity();
            String area_name = adapterList.get(getAdapterPosition()).getArea_name();
            String address = adapterList.get(getAdapterPosition()).getAddress();
            String longitude = adapterList.get(getAdapterPosition()).getLongitude();
            String latitude = adapterList.get(getAdapterPosition()).getLatitude();
            String image = ApiUrl.imageLink + adapterList.get(getAdapterPosition()).getImage();
            Bundle bundle = new Bundle();
            bundle.putString("Id", id);
            bundle.putString("Name", name);
            bundle.putString("Type", type);
            bundle.putString("Status", status);
            bundle.putString("Size", size);
            bundle.putString("City", city);
            bundle.putString("AreaName", area_name);
            bundle.putString("Address", address);
            bundle.putDouble("Longitude", Double.parseDouble(longitude) );
            bundle.putDouble("Latitude", Double.parseDouble(latitude));
            bundle.putString("Image", image);

            FragmentProjectDetail fragmentProjectDetail = new FragmentProjectDetail();
            fragmentProjectDetail.setArguments(bundle);

            FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, fragmentProjectDetail);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
