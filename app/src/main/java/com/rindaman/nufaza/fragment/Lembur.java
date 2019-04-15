package com.rindaman.nufaza.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import android.text.format.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Lembur extends Fragment {

    Spinner spinner_nama_pekerjaan,spinner_nama_tempat,spinner_status_hari;
    TextView txt_nik,txt_hasil,txt_hasil2;
    EditText txt_jammulai,txt_jamselesai,txt_tglmulai;
    String nik;
    private FragmentActivity mFrgAct;
    private Button button_submit;
    ProgressDialog pDialog;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    private SimpleDateFormat dateFormatter;
    View dialogView;
    Adapter adapter;
    List<Data> listNama_pekerjaan = new ArrayList<Data>();
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public static final String url1 = "http://172.16.8.187/android/test/showabsensi.php";
    private static final String TAG = Kegiatan.class.getSimpleName();
    public static final String TAG_NAMA_PEKERJAAN= "nama_pekerjaan";
    public static final String TAG_NIK= "nik";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lembur, null);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        spinner_nama_pekerjaan = view.findViewById(R.id.spinner_nama_pekerjaan);
        spinner_nama_tempat = view.findViewById(R.id.spinner_nama_tempat);
        spinner_status_hari = view.findViewById(R.id.spinner_status_hari);
        txt_hasil = (TextView) view.findViewById(R.id.txt_hasil);
        txt_hasil2 = (TextView) view.findViewById(R.id.txt_hasil2);
        txt_nik = (TextView) view.findViewById(R.id.txt_nik);
        button_submit = (Button) view.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLembur();
            }
        });
        txt_jammulai = (EditText) view.findViewById(R.id.txt_jammulai);
        txt_jammulai.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txt_jammulai.setText(selectedHour+":"+selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        txt_jamselesai = (EditText) view.findViewById(R.id.txt_jamselesai);
        txt_jamselesai.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txt_jamselesai.setText(selectedHour+":"+selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        txt_tglmulai = (EditText) view.findViewById(R.id.txt_tglmulai);
        txt_tglmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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

        spinner_status_hari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil2.setText(spinner_status_hari.getSelectedItem().toString());
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

    private void addLembur(){

        final String nik = txt_nik.getText().toString().trim();
        final String nama_pekerjaan = txt_hasil.getText().toString().trim();
        final String nama_tempat = spinner_nama_tempat.getSelectedItem().toString().trim();
        final String tanggal = txt_tglmulai.getText().toString().trim();
        final String status_hari = txt_hasil2.getText().toString().trim();
        final String jam_mulai = txt_jammulai.getText().toString().trim();
        final String jam_selesai = txt_jamselesai.getText().toString().trim();



        class AddLembur extends AsyncTask<Void,Void,String>{

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
                params.put(Config.KEY_NAMA_TEMPAT,nama_tempat);
                params.put(Config.KEY_NAMA_PEKERJAAN,nama_pekerjaan);
                params.put(Config.KEY_TANGGAL,tanggal);
                params.put(Config.KEY_STATUS_HARI,status_hari);
                params.put(Config.KEY_JAM_MULAI,jam_mulai);
                params.put(Config.KEY_JAM_SELESAI,jam_selesai);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT_LEMBUR, params);
                return res;
            }
        }

        AddLembur al = new AddLembur();
        al.execute();
    }

    private void DialogLembur() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_kegiatan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Konfirmasi");

        dialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txt_jammulai.length() > 0) {
                    addLembur();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();

            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                txt_tglmulai.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}





