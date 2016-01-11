package com.tuccro.redtubebrowser.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tuccro.redtubebrowser.R;
import com.tuccro.redtubebrowser.model.Video;

import java.util.List;

/**
 * Created by tuccro on 1/10/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    List<Video> videoList;
    Context context;

    public VideosAdapter(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Video video = videoList.get(position);

        holder.textTitle.setText(video.getInfo().getTitle());

        Picasso.with(context)
                .load(video.getInfo().getThumb())
                .resize(200, 200)
                .centerCrop()
                .into(holder.imageThumbnail);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageThumbnail;
        TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageThumbnail = (ImageView) itemView.findViewById(R.id.imageThumbnail);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
        }
    }
}
