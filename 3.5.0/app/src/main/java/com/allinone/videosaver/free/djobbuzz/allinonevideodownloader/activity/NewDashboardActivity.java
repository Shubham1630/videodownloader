package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.ViewPagerAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DownloadFilesFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DownloadsFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.DialogUtils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.LocaleManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Objects;

import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.*;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.handledarkmode;

public class NewDashboardActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    private static final String TAG = "NewDashboardActivity";
    private ArrayList<String> mTitles = new ArrayList<>();
    private SmoothBottomBar bottomBar;
    private ViewPager viewPager;
    private PrefManager prefManager;
    private DashboardFragment callsFragment;
    private DownloadsFragment callsFragment1;
    //   private StoryFragment callsFragment2;
    private DownloadFilesFragment callsFragment2;
    public ConstraintLayout constraint;
    public ConstraintLayout cl_purchase;
    public Fragment curFragment = null;
    private int checkPermition = 1001;
    public Snackbar snackbar = null;
    private int requestPermissionSetting = 1002;
    private String copyKey = "";
    private String copyValue = "";
    private RelativeLayout adView;
    private ClipboardManager clipBoard;
    private NewDashboardActivity activity;
    private boolean isexit = false;
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    ImageView imageViewuser;
    ImageView imgappmode;
    ImageView imgprivacy;
    ImageView imgpurchase;
    ImageView imgshare;
    ImageView imgrate;
    TextView tv_version_name;
    TextView tvpurchseme;

    ViewPagerAdapter adapter;
    /* for in app updates*/
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;

    /* in app purchase*/
    public BillingProcessor billingProcessor;
    private Dialog purchaseDialog;
    private SkuDetails skuDetails;

    @Override
    protected void onStart() {
        super.onStart();
        handleInAppUpdate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handledarkmode(this);
        FirebaseApp.initializeApp(this);
        activity = this;
        LocaleManager.setLocale(activity);
        setContentView(R.layout.activity_new_dashboard);
        clipBoard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        getintentdata();
        initbottombar();
        initpref();
        initdialog();
        bindviews();
        handlenotification();
        initbilling();
    }

    /* In app purchase impl on 11-apr-21 */
    private void initbilling() {
        if (!IS_ALLOW_IN_APP_PURCHASE) {
            cl_purchase.setVisibility(View.GONE);
            PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(false);
            return;
        }
        billingProcessor = new BillingProcessor(this, IAP_LICENCE, this);
        billingProcessor.initialize();


    }

    public void startpurchase() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (billingProcessor == null) return;
            billingProcessor.consumePurchase(IAP_ID);
            billingProcessor.purchase(NewDashboardActivity.this, IAP_ID);

        }, 500);

    }

    private void initpref() {
        prefManager = new PrefManager(this);
    }

    private void initdialog() {
        DialogUtils.with(this)
                .setRateDialogImageRes(R.drawable.rate_image)
                .setTitleAndDescTextColors(ContextCompat.getColor(getApplicationContext(), R.color.setTitleAndDescTextColors), ContextCompat.getColor(getApplicationContext(), R.color.setTitleAndDescTextColors))
                .setButtonColors(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)).setButtonTextColor(ContextCompat.getColor(getApplicationContext(), R.color.setButtonTextColor));
        DialogUtils.showAppRaterIfNeeded(this);
    }

    private void bindviews() {
        constraint = (ConstraintLayout) findViewById(R.id.constraint);
        cl_purchase = findViewById(R.id.cl_purchase);
        adView = findViewById(R.id.adView);
        imgappmode = findViewById(R.id.imgappmode);
        imgprivacy = findViewById(R.id.imgprivacy);
        imgshare = findViewById(R.id.imgshare);
        imgrate = findViewById(R.id.imgrate);
        imgpurchase = findViewById(R.id.imgpurchase);
        tv_version_name = findViewById(R.id.tv_version_name);
        tvpurchseme = findViewById(R.id.tvpurchseme);
        cl_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                showpurchaseDialog();


            }
        });
        imgrate.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_black_24dp, null));
        imgshare.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_share_black_24dp, null));
        imgprivacy.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lock_black_24dp, null));
        imgappmode.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_round_settings_24, null));
        imgpurchase.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shopping_cart, null));
        tv_version_name.setText(BuildConfig.VERSION_NAME);
    }

    private void showpurchaseDialog() {
        purchaseDialog = new Dialog(NewDashboardActivity.this, R.style.AppTheme);
        purchaseDialog.setContentView(R.layout.layout_subscription);

        ImageView ic_back_iv = (ImageView) purchaseDialog.findViewById(R.id.iv_close_sub);
        TextView txt_priceright = (TextView) purchaseDialog.findViewById(R.id.txt_priceright);
        TextView txt_total_price = (TextView) purchaseDialog.findViewById(R.id.txt_total_price);
        View llrestorepurchase = purchaseDialog.findViewById(R.id.llrestorepurchase);
        if (skuDetails != null && skuDetails.priceText != null) {
            txt_priceright.setText(skuDetails.priceText);
            txt_total_price.setText(String.format("Total price- %s", skuDetails.priceText));
        } else {
            // default price set but purchase won't made
            txt_priceright.setText(activity.getResources().getString(R.string.default_price));
            txt_total_price.setText(String.format("Total price- %s", activity.getResources().getString(R.string.default_price)));
        }

        LinearLayout one_mounth_ll = (LinearLayout) purchaseDialog.findViewById(R.id.three_mounth_ll);
        LinearLayout paynow = (LinearLayout) purchaseDialog.findViewById(R.id.paynow);
        ic_back_iv.setOnClickListener(view -> purchaseDialog.dismiss());
        one_mounth_ll.setOnClickListener(v -> {
            purchaseDialog.dismiss();
            startpurchase();


        });
        llrestorepurchase.setOnClickListener(v -> {
            purchaseDialog.dismiss();
            if (billingProcessor != null) {
                billingProcessor.loadOwnedPurchasesFromGoogle();
            }
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startActivity(new Intent(NewDashboardActivity.this, SplashActivity.class));
                finish();
            }, 1000);

        });
        paynow.setOnClickListener(v -> {
            purchaseDialog.dismiss();
            startpurchase();
        });

        purchaseDialog.show();
    }

    private void handlenotification() {
        if (clipBoard != null) {
            clipBoard.addPrimaryClipChangedListener(() -> {
                try {
                    showNotification(Objects.requireNonNull(clipBoard.getPrimaryClip().getItemAt(0).getText()).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public void showNotification(String Text) {
        if (iscontains(Text) && !prefManager.getServiceStatus()) {
            Intent intent = new Intent(activity, NewDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Notification", Text);
            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name),
                        getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                mChannel.enableLights(true);
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder notificationBuilder;
            notificationBuilder = new NotificationCompat.Builder(activity, getResources().getString(R.string.app_name))
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(getResources().getColor(R.color.blck))
                    .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(),
                            R.mipmap.ic_launcher))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(getResources().getString(R.string.copiedlink))
                    .setContentText(Text)
                    .setChannelId(getResources().getString(R.string.app_name))
                    .setFullScreenIntent(pendingIntent, true);
            notificationManager.notify(1, notificationBuilder.build());
        }
    }

    private boolean iscontains(String text) {
        for (String s : DashboardFragment.FORLIST) {
            if (text.contains(s)) {
                return true;
            }

        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        clipBoard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
    }

    private void getintentdata() {
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                copyKey = key;

                if (copyKey.equals("android.intent.extra.TEXT")) {
                    copyValue = getIntent().getExtras().getString(copyKey);
                } else {
                    copyValue = "";
                }
            }
        }
    }

    private void initbottombar() {
        bottomBar = findViewById(R.id.bottomBar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        imageViewuser = findViewById(R.id.imageViewuser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        handleDrawer();
        setupViewPager(viewPager);
        bottomBar.setOnItemReselectedListener(new OnItemReselectedListener() {
            @Override
            public void onItemReselect(int i) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                viewPager.setCurrentItem(i);
                curFragment = adapter.getItem(i);
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                bottomBar.setActiveItem(position);
                curFragment = adapter.getItem(position);
                switch (position) {
                    case 0:
                        setTitle(getResources().getString(R.string.app_name));
                        break;
                    case 1:
                        setTitle(getResources().getString(R.string.saved));
                        break;
                    case 2:
                        setTitle(getResources().getString(R.string.downloadstab));
                        break;
                    default:
                        setTitle(getResources().getString(R.string.app_name));
                        break;
                }
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setActiveItem(position);
                switch (position) {
                    case 0:
                        setTitle(getResources().getString(R.string.app_name));
                        break;
                    case 1:
                        setTitle(getResources().getString(R.string.saved));
                        break;
                    case 2:
                        setTitle(getResources().getString(R.string.downloadstab));
                        break;
                    default:
                        setTitle(getResources().getString(R.string.app_name));
                        break;
                }
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

            }
        });


    }

    private void handleDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator((int) R.drawable.ic_round_menu_24);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible((int) GravityCompat.START)) {
                    drawerLayout.closeDrawer((int) GravityCompat.START);
                } else {
                    drawerLayout.openDrawer((int) GravityCompat.START);
                }
            }
        });
        this.drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        Glide.with(getApplicationContext()).load(R.drawable.playstore).apply(RequestOptions.circleCropTransform()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(imageViewuser);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment = new DashboardFragment(getIntent());
        callsFragment1 = new DownloadsFragment();
        //  callsFragment2 = new StoryFragment();
        callsFragment2 = new DownloadFilesFragment();

        adapter.addFragment(callsFragment);
        adapter.addFragment(callsFragment1);
        adapter.addFragment(callsFragment2);
        viewPager.setAdapter(adapter);
        viewPager.setHorizontalScrollBarEnabled(false);


    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0) {
            if (curFragment != null && curFragment instanceof DashboardFragment) {
                ((DashboardFragment) curFragment).checkDownloadVideo();
            }

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, checkPermition);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.storageheader));

            builder.setMessage(getResources().getString(R.string.storagedescription));

            builder.setPositiveButton(getResources().getString(R.string.grantpermission), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, checkPermition);
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancelpermission), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == checkPermition) {
            String str = strArr[0];
            if (iArr.length <= 0 || iArr[0] != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
                    Snackbar make = Snackbar.make(constraint, (CharSequence) getResources().getString(R.string.allowpermission), BaseTransientBottomBar.LENGTH_SHORT);
                    ((TextView) make.getView().findViewById(R.id.snackbar_text)).setTextColor(-1);
                    make.show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.storageheader));
                builder.setMessage(getResources().getString(R.string.storagedescription));
                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, requestPermissionSetting);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancelpermission), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            } else if (Build.VERSION.SDK_INT >= 23) {
                if (curFragment != null && curFragment instanceof DashboardFragment) {
                    ((DashboardFragment) curFragment).checkDownloadVideo();
                }

            }
        } else if (i == requestPermissionSetting) {
            checkPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* for in app update*/
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "onActivityResult: app download failed");
            }
        }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
//
//    InstallStateUpdatedListener installStateUpdatedListener = new
//            InstallStateUpdatedListener() {
//                @Override
//                public void onStateUpdate(InstallState state) {
//                    if (state.installStatus() == InstallStatus.DOWNLOADED){
//                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
//                        popupSnackbarForCompleteUpdate();
//                    } else if (state.installStatus() == InstallStatus.INSTALLED){
//                        if (mAppUpdateManager != null){
//                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
//                        }
//
//                    } else {
//                        Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
//                    }
//                }
//            };

    @Override
    protected void onDestroy() {
        if (purchaseDialog != null)
            purchaseDialog.dismiss();
        if (billingProcessor != null) {
            billingProcessor.release();
        }
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (isexit) {
            super.onBackPressed();
        } else {
            isexit = true;
            Toast.makeText(activity, "Press back again to Exit", Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    isexit = false;
                }
            }, 3000);
        }
    }


    public void darkmodedialog() {
        RadioGroup group;
        RadioButton defaultTheme, darkTheme, lightheme, auto;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_back);
        dialog.setContentView(R.layout.appdialog_mode);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 17;
        dialog.getWindow().setAttributes(layoutParams);
        group = (RadioGroup) dialog.findViewById(R.id.group);
        defaultTheme = (RadioButton) dialog.findViewById(R.id.defaultTheme);
        darkTheme = (RadioButton) dialog.findViewById(R.id.darkTheme);
        lightheme = (RadioButton) dialog.findViewById(R.id.lightheme);
        auto = (RadioButton) dialog.findViewById(R.id.auto);

        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
        String string = sharedPreferences.getString(getString(R.string.dark_mode), getString(R.string.dark_mode_def_value));
        if (string != null) {
            if (string.equalsIgnoreCase(darkModeValues[0])) {
                defaultTheme.setChecked(true);
            } else if (string.equalsIgnoreCase(darkModeValues[1])) {
                lightheme.setChecked(true);
            } else if (string.equalsIgnoreCase(darkModeValues[2])) {
                darkTheme.setChecked(true);
            } else if (string.equalsIgnoreCase(darkModeValues[3])) {
                auto.setChecked(true);
            }
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
                if (checkedId == R.id.defaultTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    applyTheme(darkModeValues[0]);
                } else if (checkedId == R.id.darkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    applyTheme(darkModeValues[2]);
                } else if (checkedId == R.id.lightheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    applyTheme(darkModeValues[1]);
                } else if (checkedId == R.id.auto) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    applyTheme(darkModeValues[3]);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {

                dialog.dismiss();
            }
        });
    }


    public void applyTheme(String name) {
        Log.e(TAG, "applyTheme: called:" + name);
        // Create preference to store theme name
        SharedPreferences preferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.dark_mode), name);
        editor.apply();
        startActivity(new Intent(activity, SplashActivity.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.local_english:
                setNewLocale(this, LANG_EN);
                return true;
            case R.id.local_hindi:
                setNewLocale(this, LANG_HI);
                return true;
            case R.id.local_arabic:
                setNewLocale(this, LANG_AR);
                return true;
            case R.id.local_indo:
                setNewLocale(this, LANG_IN);
                return true;
            case R.id.local_korea:
                setNewLocale(this, LANG_KO);
                return true;
            case R.id.local_itali:
                setNewLocale(this, LANG_IT);
                return true;

            case R.id.local_Russian:
                setNewLocale(this, LANG_RU);
                return true;
            case R.id.local_marathi:
                setNewLocale(this, LANG_MA);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void rateus(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        RateApp(this);
    }

    public void shareapp(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        ShareApp(this);
    }

    public void privacy(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Utility.privacyurl));
        startActivity(browserIntent);
    }

    public void darkmode(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        darkmodedialog();
    }

    private void handleInAppUpdate() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)) {

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, NewDashboardActivity.this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });
    }

    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.constraint),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, TransactionDetails details) {
        Log.e(TAG, "onProductPurchased: called");
        if (details != null && productId != null && billingProcessor != null) {
            if (billingProcessor.isPurchased(IAP_ID)) {
                PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.payment_success), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewDashboardActivity.this, SplashActivity.class));
                finish();


            } else {
                PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(false);
                Log.e(TAG, "onProductPurchased: details is null");
            }


        } else {
            Log.e(TAG, "onProductPurchased: details is null");
        }


    }

    @Override
    public void onPurchaseHistoryRestored() {
        Log.e(TAG, "onPurchaseHistoryRestored: called");
        if (billingProcessor != null) {
            if (billingProcessor.isPurchased(IAP_ID)) {
                PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(true);
                recreate();

            } else {
                PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(false);

            }


        }
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Log.e(TAG, "onBillingError: " + errorCode);

    }

    @Override
    public void onBillingInitialized() {
        if (billingProcessor != null) {
            Log.e(TAG, "onBillingInitialized:isPurchased? " + billingProcessor.isPurchased(IAP_ID));
            if (billingProcessor.isPurchased(IAP_ID)) {
                cl_purchase.setVisibility(View.GONE);
                PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(true);
            } else {
                tvpurchseme.setText(activity.getResources().getString(R.string.purchase));
                cl_purchase.setVisibility(View.VISIBLE);
                PrefManager.getInstance(NewDashboardActivity.this).setIsPurchased(false);
            }


            /* only for debug purpose*/
            skuDetails = billingProcessor.getPurchaseListingDetails(IAP_ID);
            if (skuDetails != null) {
                Log.e(TAG, "onBillingInitialized: skuDetails currency-> " + skuDetails.currency);
                Log.e(TAG, "onBillingInitialized: skuDetails description->" + skuDetails.description);
                Log.e(TAG, "onBillingInitialized: skuDetails introductoryPricePeriod->" + skuDetails.introductoryPricePeriod);
                Log.e(TAG, "onBillingInitialized: skuDetails introductoryPriceText->" + skuDetails.introductoryPriceText);
                Log.e(TAG, "onBillingInitialized: skuDetails priceText->" + skuDetails.priceText);
                Log.e(TAG, "onBillingInitialized: skuDetails productId->" + skuDetails.productId);
                Log.e(TAG, "onBillingInitialized: skuDetails title->" + skuDetails.title);
                Log.e(TAG, "onBillingInitialized: skuDetails priceLong->" + skuDetails.priceLong);
            }


        } else {

        }


    }
}