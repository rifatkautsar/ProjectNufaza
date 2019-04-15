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

public class Keluar extends Fragment {

    TextView txt_nik;
    String nik;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private FragmentActivity mFrgAct;
    private Button button_submit;
    ProgressDialog pDialog;
    Adapter adapter;

    private static final String TAG = Keluar.class.getSimpleName();
    public static final String TAG_NIK= "nik";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keluar, null);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        txt_nik = (TextView) view.findViewById(R.id.txt_nik);
        button_submit = (Button) view.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogKeluar();
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

    private void addKeluar(){

        final String nik = txt_nik.getText().toString().trim();

        class AddKeluar extends AsyncTask<Void,Void,String>{

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

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT_KELUAR, params);
                return res;
            }
        }

        AddKeluar ak = new AddKeluar();
        ak.execute();
    }

    private void DialogKeluar() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_keluar, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Konfirmasi");

        dialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar currTime = Calendar.getInstance();
                int hour = currTime.get(Calendar.HOUR_OF_DAY);
                if ( hour > 7.59 && hour < 17.59) {
                        addKeluar();
                }else if ( hour >= 17.59 && hour < 18) {
                    addKeluar();
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "Sudah Ditutup/Anda Absen 2 Kali", Toast.LENGTH_LONG).show();
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