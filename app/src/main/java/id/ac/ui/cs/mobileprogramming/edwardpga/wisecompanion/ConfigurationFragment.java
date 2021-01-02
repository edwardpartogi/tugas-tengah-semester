package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ConfigurationFragment extends Fragment {
    View rootView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    WifiManager mWifiManager;

    private Button wifi_stat_btn;
    private TextView wifi_stat_view;

    public ConfigurationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuration, container, false);

        wifi_stat_btn = root.findViewById(R.id.wifi_stat_btn);
        wifi_stat_view = root.findViewById(R.id.wifi_status);
        wifi_stat_view.setText(getResources().getString(R.string.wifi_stat,
                "undefined","undefined","undefined","undefined"
        ));

        wifi_stat_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rootView = v;
                if (!checkPermission()){
                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_CODE);

                }else {
                    showWifiStat();
                }
            }
        });

        return root;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        showWifiStat();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            popUpMessage(getString(R.string.permission_denied_msg));
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void popUpMessage(String message) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    private void showWifiStat(){
        if (!mWifiManager.isWifiEnabled())
        {
            Snackbar.make(rootView, "wifi is disabled..making it enabled",
                    Snackbar.LENGTH_LONG).show();
            mWifiManager.setWifiEnabled(true);
        }
        mWifiManager.startScan();
        List<ScanResult> mScanResults = mWifiManager.getScanResults();
        String wifi_stat1 = "";
        for (ScanResult result : mScanResults) {
            wifi_stat1 += result.SSID + "\n";
        }

        ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null){
            wifi_stat_view.setText(getResources().getString(R.string.wifi_stat,
                    wifi_stat1,
                    networkInfo,
                    networkInfo.isConnected(),
                    String.valueOf(connMgr.isActiveNetworkMetered()))
            );
        }
    }
}