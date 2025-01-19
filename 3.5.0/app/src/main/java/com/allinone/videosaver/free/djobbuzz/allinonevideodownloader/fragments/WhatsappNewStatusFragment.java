package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.AsyncTaskCoroutine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.AdapterWhatsAppStatusHorizontal;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.ModelWhatsAppStatus;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;

import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment.formoj;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.dirformoj;

public class WhatsappNewStatusFragment extends Fragment {
    private static final String TAG = "WhatsappNewStatusFra";
    private int valueDownloadedStatus = 1;
    private int valueNewStatus = 0;
    private AdapterWhatsAppStatusHorizontal adapterWhatsAppStatusHorizontal;
    private ArrayList<ModelWhatsAppStatus> arrWhatsappStatus = new ArrayList();
    private RecyclerView recyclerNewStatus;
    private int selectedValue = 0;
    private String what = "";
    private SwipeRefreshLayout swipeToRefreshStatus;
    private TextView txtError;
    private View v;
    FloatingActionButton add_fab;

    public void setValue(int i) {
        this.selectedValue = i;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public WhatsappNewStatusFragment newInstance(int i, String what) {
        WhatsappNewStatusFragment whatsappNewStatusFragment = new WhatsappNewStatusFragment();
        whatsappNewStatusFragment.setValue(i);
        whatsappNewStatusFragment.setWhat(what);
        return whatsappNewStatusFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.e(TAG, "onCreateView: called");
        this.v = layoutInflater.inflate(R.layout.fragment_whatsapp_new_status, viewGroup, false);
        init();
        this.swipeToRefreshStatus.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                setStatusData();
                swipeToRefreshStatus.setRefreshing(false);
            }
        });
        return this.v;
    }

    public void onResume() {
        super.onResume();
        setStatusData();
    }

    public WhatsappNewStatusFragment() {
    }

    @SuppressLint("StaticFieldLeak")
    private void setStatusData() {
        Log.e(TAG, "setStatusData: called");
        new AsyncTaskCoroutine<Object, Object>() {
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                arrWhatsappStatus.clear();
            }

            @Override
            public Object doInBackground(Object... params) {
                getStatusFromWhastapp();
                return null;
            }

            @Override
            public void onPostExecute(Object result) {
                super.onPostExecute(result);
                if (arrWhatsappStatus == null || arrWhatsappStatus.size() <= 0) {
                    Log.e(TAG, "onPostExecute:  arrWhatsappStatus is null or empty");
                    if (selectedValue == valueNewStatus) {
                        txtError.setText(requireContext().getResources().getString(R.string.nostatusavailable));
                    } else {
                        txtError.setText(requireContext().getResources().getString(R.string.nosavedstatusavailable));
                    }
                    txtError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    Log.e(TAG, "onPostExecute: arrWhatsappStatus has data:" + arrWhatsappStatus.size());
                }
                txtError.setVisibility(View.GONE);
                requireActivity().runOnUiThread(() -> adapterWhatsAppStatusHorizontal.notifyDataSetChanged());
            }
        }.execute();

    }


    public void hideDeleteButton() {
        add_fab.setVisibility(View.GONE);

    }

    public void showDeleteButton() {
        add_fab.setVisibility(View.VISIBLE);

    }

    private void init() {
        add_fab = v.findViewById(R.id.add_fab);
        this.txtError = (TextView) this.v.findViewById(R.id.txtError);
        this.recyclerNewStatus = (RecyclerView) this.v.findViewById(R.id.recyclerNewStatus);
        recyclerNewStatus.setHasFixedSize(true);
        this.adapterWhatsAppStatusHorizontal = new AdapterWhatsAppStatusHorizontal(getActivity(), this.arrWhatsappStatus, this.selectedValue, what);
        this.recyclerNewStatus.setAdapter(this.adapterWhatsAppStatusHorizontal);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        this.recyclerNewStatus.setLayoutManager(gridLayoutManager);
        if (this.selectedValue == this.valueDownloadedStatus) {
            this.adapterWhatsAppStatusHorizontal.setOnSelactionListener(new AdapterWhatsAppStatusHorizontal.SelactionChangeListener() {
                public void onSelactionChange(boolean z) {
                    if (z) {
                        showDeleteButton();
                    } else {
                        hideDeleteButton();
                    }//todo
                }
            });
        }
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelaction();
                hideDeleteButton();
            }
        });
        this.swipeToRefreshStatus = (SwipeRefreshLayout) this.v.findViewById(R.id.swipeToRefreshStatus);
        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int i) {

                return 1;


            }
        });
    }

    private void getStatusFromWhastapp() {
        StringBuilder stringBuilder;
        File file;
        if (this.selectedValue == this.valueNewStatus) {
            //selectedValue ==0 staus
            String path = "";
            if (what.equals("whatsapp")) {
                path = Utility.dirforwhatsapp;
            } else if (what.equals("whatsappb")) {
                path = Utility.dirforwbussines;
            } else if (what.equals(formoj)) {
                path = dirformoj;
            }
            file = new File(path);
        } else {
            // saved status selectedValue==1
            Log.e(TAG, "getStatusFromWhastapp: what==:> " + what);
            String path = "";
            path = Utility.STORAGEDIR;//+ what;
            file = new File(path);
        }
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                Arrays.sort(listFiles, new Comparator() {
                    public int compare(Object obj, Object obj2) {
                        File file = (File) obj;
                        File file2 = (File) obj2;
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
                this.arrWhatsappStatus.clear();
                for (File file3 : listFiles) {
                    // Log.e(TAG, "getStatusFromWhastapp: " + file3.getAbsolutePath());
                    if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {

                        if ((what.equals("whatsapp") || what.equals("whatsappb") || what.equals(formoj)) && selectedValue == 0) {
                            ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                            modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                            modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                            this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                        } else {
                            if (file3.getName().contains(what)) {
                                ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                                modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                                modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                                this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                            }

                        }

                    }

                }
            }
        }
    }

    public void deleteSelaction() {
        this.adapterWhatsAppStatusHorizontal.deleteAllSelaction();
        setStatusData();
    }
}
