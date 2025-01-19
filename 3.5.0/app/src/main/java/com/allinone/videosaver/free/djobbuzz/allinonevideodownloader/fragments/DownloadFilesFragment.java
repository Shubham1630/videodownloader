package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.DownloadListAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database.DatabaseRoom;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.OnItemClickListener;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.FileItem;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.AsyncTaskCoroutine;

import java.util.ArrayList;
import java.util.List;

import ir.siaray.downloadmanagerplus.model.DownloadItem;
import videodownload.com.newmusically.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DownloadFilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadFilesFragment extends Fragment implements OnItemClickListener<CommonModel>, SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "DownloadFilesFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout activity_list;

    List<FileItem> list = new ArrayList<>();
    DownloadListAdapter adapter;
    private TextView txt_nofiles;
    private RecyclerView rvDownloads;
    private SwipeRefreshLayout refressme;
    private List<CommonModel> commonModelArrayList = new ArrayList<>();


    public DownloadFilesFragment() {
        // Required empty public constructor
    }


    public static DownloadFilesFragment newInstance(String param1, String param2) {
        DownloadFilesFragment fragment = new DownloadFilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download_files, container, false);
        InitUi(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @SuppressLint("StaticFieldLeak")
    private void getData() {
        new AsyncTaskCoroutine<Object, Object>() {
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                if (!refressme.isRefreshing())
                    refressme.setRefreshing(true);
            }

            @Override
            public Object doInBackground(Object... params) {
                commonModelArrayList = DatabaseRoom.with(requireContext()).getAllDownloads();
                return null;
            }

            @Override
            public void onPostExecute(Object result) {
                super.onPostExecute(result);
                refressme.setRefreshing(false);
                if (commonModelArrayList != null && !commonModelArrayList.isEmpty()) {
                    rvDownloads.setVisibility(View.VISIBLE);
                    txt_nofiles.setVisibility(View.GONE);
                    Log.e(TAG, "onPostExecute: GetAllDownloadsfiles is not null");
                    if (list != null) {
                        list.clear();
                    }
                    for (CommonModel commonModel : commonModelArrayList) {
                        Log.e(TAG, "onPostExecute: commonModel:" + commonModel.toString());
                        DownloadItem item = new DownloadItem();
                        item.setToken(String.valueOf(commonModel.getUid()));
                        item.setUri(commonModel.getSaved_video_url());
                        FileItem fileItem = new FileItem(item);

                        fileItem.setUuid(commonModel.getUid());
                        fileItem.setCommonModel(commonModel);
                        // fileItem.setListener(DownloadFilesFragment.this);
                        list.add(fileItem);
                    }

                    adapter = new DownloadListAdapter(requireActivity(), rvDownloads, list, R.layout.download_list_item, activity_list.getWidth(), activity_list.getHeight(), (del, pos) -> handledelfile(del, pos));
                    LinearLayoutManager llm = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    adapter.setOnItemClickListener(DownloadFilesFragment.this);
                    rvDownloads.setLayoutManager(llm);
                    rvDownloads.setAdapter(adapter);
                } else {
                    rvDownloads.setVisibility(View.GONE);
                    txt_nofiles.setVisibility(View.VISIBLE);
                    Log.e(TAG, "onPostExecute: is null");
                }
            }
        }.execute();

    }

    private void InitUi(View view) {
        rvDownloads = (RecyclerView) view.findViewById(R.id.rv_downloads);
        txt_nofiles = view.findViewById(R.id.txt_nofiles);
        activity_list = view.findViewById(R.id.activity_list);
        refressme = view.findViewById(R.id.refressme);
        refressme.setOnRefreshListener(this);
    }

    @SuppressLint("StaticFieldLeak")
    private void handledelfile(long del, int pos) {
        new AsyncTaskCoroutine<Object, Object>() {
            @Override
            public Object doInBackground(Object... params) {
                DatabaseRoom.with(requireContext()).delefile(del);
                return null;
            }

            @Override
            public void onPostExecute(Object result) {
                super.onPostExecute(result);
                Log.e(TAG, "onPostExecute: pos:" + pos);
                commonModelArrayList.remove(pos);
                list.remove(pos);

                if (commonModelArrayList.isEmpty()) {
                    rvDownloads.setVisibility(View.GONE);
                    txt_nofiles.setVisibility(View.VISIBLE);
                } else {
                    adapter.UpdateAdapter(list, rvDownloads, pos);
                }
                getData();
            }
        }.execute();

    }

    @Override
    public void onItemClick(View v, int position, CommonModel commonModel) {

    }

    @Override
    public void onRefresh() {
        refressme.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1000);

    }
}