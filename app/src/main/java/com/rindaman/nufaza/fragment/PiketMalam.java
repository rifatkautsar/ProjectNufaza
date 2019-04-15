package com.rindaman.nufaza.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rindaman.nufaza.R;
import com.rindaman.nufaza.app.Adapter;
import com.rindaman.nufaza.app.AppController;
import com.rindaman.nufaza.app.Config;
import com.rindaman.nufaza.app.Data;
import com.rindaman.nufaza.app.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PiketMalam extends Fragment {

    TextView txt_nik,txt_keterangan,txt_status_hari;
    String nik;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private FragmentActivity mFrgAct;
    private Button button_submit;

    private static final String TAG = Absensi.class.getSimpleName();
    public static final String TAG_NIK= "nik";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piket_malam, null);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        txt_nik = (TextView) view.findViewById(R.id.txt_nik);
        txt_keterangan = (TextView) view.findViewById(R.id.txt_keterangan);
        txt_status_hari = (TextView) view.findViewById(R.id.txt_status_hari);
        final RadioGroup radioGroupketerangan = (RadioGroup) view.findViewById(R.id.radioGroupketerangan);
        radioGroupketerangan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    txt_keterangan.setText(checkedRadioButton.getText());
                }
            }
        });
        final RadioGroup radioGrouphari = (RadioGroup) view.findViewById(R.id.radioGrouphari);
        radioGrouphari.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    txt_status_hari.setText(checkedRadioButton.getText());
                }
            }
        });
        button_submit = (Button) view.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPiketMalam();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFrgAct = getActivity();
        nik = getActivity().getIntent().getExtras().getString(TAG_NIK);
        txt_nik.setText(nik);
    }

    private void addPiketMalam(){

        final String nik = txt_nik.getText().toString().trim();
        final String keterangan = txt_keterangan.getText().toString().trim();
        final String status_hari = txt_status_hari.getText().toString().trim();

        class AddPiketMalam extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_NIK,nik);
                params.put(Config.KEY_KETERANGAN,keterangan);
                params.put(Config.KEY_STATUS_HARI,status_hari);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT_PIKET, params);
                return res;
            }
        }

        AddPiketMalam apm = new AddPiketMalam();
        apm.execute();
    }

    private void addPiketMalam2(){
        final String nik = txt_nik.getText().toString().trim();
        final String keterangan = txt_keterangan.getText().toString().trim();
        final String status_hari = txt_status_hari.getText().toString().trim();

        class AddPiketMalam2 extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_NIK,nik);
                params.put(Config.KEY_KETERANGAN,keterangan);
                params.put(Config.KEY_STATUS_HARI,status_hari);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT_PIKET2, params);
                return res;
            }
        }

        AddPiketMalam2 apm2 = new AddPiketMalam2();
        apm2.execute();
    }

    private void addPiketMalam3(){
        final String nik = txt_nik.getText().toString().trim();
        final String keterangan = txt_keterangan.getText().toString().trim();
        final String status_hari = txt_status_hari.getText().toString().trim();

        class AddPiketMalam3 extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_NIK,nik);
                params.put(Config.KEY_KETERANGAN,keterangan);
                params.put(Config.KEY_STATUS_HARI,status_hari);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT_PIKET3, params);
                return res;
            }
        }

        AddPiketMalam3 apm3 = new AddPiketMalam3();
        apm3.execute();
    }

    private void DialogPiketMalam() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_piket, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Konfirmasi");

        dialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(txt_keterangan.getText().toString().equals("Tidak")){
                    addPiketMalam();
                    } else if(txt_keterangan.getText().toString().equals("Piket") || txt_status_hari.getText().toString().equals("WeekDay")){
                    addPiketMalam2();
                    } else if(txt_keterangan.getText().toString().equals("Piket") || txt_status_hari.getText().toString().equals("WeekEnd")){
                    addPiketMalam3();
                }
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
