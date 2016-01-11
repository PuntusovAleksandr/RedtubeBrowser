package com.tuccro.redtubebrowser;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tuccro.redtubebrowser.api.RedTubeService;
import com.tuccro.redtubebrowser.model.Video;
import com.tuccro.redtubebrowser.model.Videos;
import com.tuccro.redtubebrowser.ui.VideosAdapter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_CATEGORY = "category";

    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    List<Video> mVideosList;
    VideosAdapter mVideosAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    String category;
    int currentPage = 1;

    private boolean mIsLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        category = getIntent().getStringExtra(ARG_CATEGORY);
        setContentView(R.layout.activity_videos);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mVideosList = new ArrayList<>();
        mVideosAdapter = new VideosAdapter(mVideosList, VideosActivity.this);
        mRecyclerView.setAdapter(mVideosAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setOnScrollListener(mRecyclerViewOnScrollListener);

        loadNextPage();
    }

    @Override
    public void onRefresh() {

        mVideosList.clear();
        currentPage = 1;
        loadNextPage();
    }

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView,
                                                 int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (!mIsLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            loadNextPage();
                        }
                    }
                }
            };

    private void loadNextPage() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.redtube.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mIsLoading = true;
        mSwipeRefreshLayout.setRefreshing(true);

        RedTubeService git = retrofit.create(RedTubeService.class);
        final Call call = git.getVideosByCategory(category, currentPage);
        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Response<Videos> response) {

                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                final Videos model = response.body();

                if (model == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        Log.e("error", responseBody.toString());
                    }
                } else {
                    //200
                    mVideosList.addAll(model.getVideos());
                    mVideosAdapter.notifyDataSetChanged();
                    currentPage++;
                }
            }

            @Override
            public void onFailure(Throwable t) {

                mIsLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);

                if (t instanceof UnknownHostException) {
                    Snackbar.make(mRecyclerView, R.string.check_internet, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
