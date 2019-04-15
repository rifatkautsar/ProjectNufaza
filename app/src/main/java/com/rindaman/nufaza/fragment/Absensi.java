package com.rindaman.nufaza.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Absensi extends Fragment {

    Spinner spinner_nama_pekerjaan,spinner_nama_tempat,spinner_keterangan;
    TextView txt_nik,txt_hasil,txt_hasil2;
    TextInputEditText txt_uraian;
    String nik;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_tpekerjaan,txt_alert,txt_uraian_izin;
    SharedPreferences sharedpreferences;
    private FragmentActivity mFrgAct;
    private Button button_submit;
    ProgressDialog pDialog;
    Adapter adapter;
    List<Data> listNama_pekerjaan = new ArrayList<Data>();

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String url1 = "http://172.16.8.187/android/test/showabsensi.php";
    private static final String TAG = Absensi.class.getSimpleName();
    public static final String TAG_NAMA_PEKERJAAN= "nama_pekerjaan";
    public static final String TAG_NIK= "nik";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.absensi, null);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        spinner_nama_tempat = view.findViewById(R.id.spinner_nama_tempat);
        spinner_nama_pekerjaan = view.findViewById(R.id.spinner_nama_pekerjaan);
        spinner_keterangan = view.findViewById(R.id.spinner_keterangan);
        txt_uraian = view.findViewById(R.id.txt_uraian);
        txt_hasil = (TextView) view.findViewById(R.id.txt_hasil);
        txt_hasil2 = (TextView) view.findViewById(R.id.txt_hasil2);
        txt_nik = (TextView) view.findViewById(R.id.txt_nik);
        button_submit = (Button) view.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAbsensi();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFrgAct = getActivity();
        nik = getActivity().getIntent().getExtras().getString(TAG_NIK);
        txt_nik.setText(nik);

        spinner_nama_pekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil.setText(listNama_pekerjaan.get(position).getNama_pekerjaan());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spinner_keterangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil2.setText(spinner_keterangan.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        adapter = new Adapter(getActivity(), listNama_pekerjaan);
        spinner_nama_pekerjaan.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        callData();

    }

    private void callData() {
        listNama_pekerjaan.clear();
        adapter.notifyDataSetChanged();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Data item = new Data();

                                item.setNama_pekerjaan(obj.getString(TAG_NAMA_PEKERJAAN));

                                listNama_pekerjaan.add(item);
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void addAbsensi(){

        final String nama_tempat = spinner_nama_tempat.getSelectedItem().toString().trim();
        final String nama_pekerjaan = txt_hasil.getText().toString().trim();
        final String keterangan= spinner_keterangan.getSelectedItem().toString().trim();
        final String uraian = txt_uraian.getText().toString().trim();
        final String nik = txt_nik.getText().toString().trim();

        class AddAbsensi extends AsyncTask<Void,Void,String>{

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
                params.put(Config.KEY_NAMA_TEMPAT,nama_tempat);
                params.put(Config.KEY_NAMA_PEKERJAAN,nama_pekerjaan);
                params.put(Config.KEY_KETERANGAN,keterangan);
                params.put(Config.KEY_URAIAN,uraian);
                params.put(Config.KEY_NIK,nik);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT, params);
                return res;
            }
        }

        AddAbsensi aa = new AddAbsensi();
        aa.execute();
    }

    private void addIzin(){

        final String keterangan= txt_hasil2.getText().toString().trim();
        final String uraian = txt_uraian.getText().toString().trim();
        final String nik = txt_nik.getText().toString().trim();

        class AddIzin extends AsyncTask<Void,Void,String>{

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
                params.put(Config.KEY_KETERANGAN,keterangan);
                params.put(Config.KEY_URAIAN,uraian);
                params.put(Config.KEY_NIK,nik);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT_IZIN, params);
                return res;
            }
        }

        AddIzin ai = new AddIzin();
        ai.execute();
    }

    private void addTelat1(){

        final String nik = txt_nik.getText().toString().trim();

        class AddTelat1 extends AsyncTask<Void,Void,String>{

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
                String res = rh.sendPostRequest(Config.URL_INSERT_TELAT1, params);
                return res;
            }
        }

        AddTelat1 at1 = new AddTelat1();
        at1.execute();
    }

    private void addTelat2(){

        final String nik = txt_nik.getText().toString().trim();

        class AddTelat2 extends AsyncTask<Void,Void,String>{

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
                String res = rh.sendPostRequest(Config.URL_INSERT_TELAT2, params);
                return res;
            }
        }

        AddTelat2 at2 = new AddTelat2();
        at2.execute();
    }

    private void addTelat3(){

        final String nik = txt_nik.getText().toString().trim();

        class AddTelat3 extends AsyncTask<Void,Void,String>{

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
                String res = rh.sendPostRequest(Config.URL_INSERT_TELAT3, params);
                return res;
            }
        }

        AddTelat3 at3 = new AddTelat3();
        at3.execute();
    }

    private void addAlpa(){

        final String nik = txt_nik.getText().toString().trim();

        class AddAlpa extends AsyncTask<Void,Void,String>{

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
                String res = rh.sendPostRequest(Config.URL_INSERT_ALPA, params);
                return res;
            }
        }

        AddAlpa aa = new AddAlpa();
        aa.execute();
    }

    private void DialogAbsensi() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_absensi, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Konfirmasi");
        dialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(txt_hasil2.getText().toString().equals("Izin")) {
                    addIzin();
                } else{
                    Calendar currTime = Calendar.getInstance();
                    int hour = currTime.get(Calendar.HOUR_OF_DAY);

                    if (hour >= 7.59 && hour < 8) {
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else if (hour >= 8 && hour < 9) {
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else if (hour >= 9 && hour < 10) {
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                            addTelat1();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else if(hour >= 10 && hour < 11){
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                            addTelat2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else if(hour >= 11 && hour < 12){
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                            addTelat3();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else if(hour >= 12 && hour < 23.59){
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                            addAlpa();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else if(hour >= 23.59 && hour < 24){
                        if (txt_uraian.length() > 0) {
                            addAbsensi();
                            addAlpa();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Tunggu Jam 8 Pagi untuk Absen", Toast.LENGTH_LONG).show();
                    }
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





