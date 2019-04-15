package com.rindaman.nufaza.app;

public class Config {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_INSERT="http://172.16.8.187/android/test/insert.php";
    public static final String URL_INSERT_IZIN="http://172.16.8.187/android/test/insert_izin.php";
    public static final String URL_INSERT_PEKERJAAN="http://172.16.8.187/android/test/insert_pekerjaan.php";
    public static final String URL_INSERT_KEGIATAN="http://172.16.8.187/android/test/insert_kegiatan.php";
    public static final String URL_INSERT_TELAT1="http://172.16.8.187/android/test/insert_telat1.php";
    public static final String URL_INSERT_TELAT2="http://172.16.8.187/android/test/insert_telat2.php";
    public static final String URL_INSERT_TELAT3="http://172.16.8.187/android/test/insert_telat3.php";
    public static final String URL_INSERT_ALPA="http://172.16.8.187/android/test/insert_alpa.php";
    public static final String URL_INSERT_KELUAR="http://172.16.8.187/android/test/insert_keluar.php";
    public static final String URL_INSERT_PIKET="http://172.16.8.187/android/test/piket_malam.php";
    public static final String URL_INSERT_PIKET2="http://172.16.8.187/android/test/piket_malam2.php";
    public static final String URL_INSERT_PIKET3="http://172.16.8.187/android/test/piket_malam3.php";
    public static final String URL_INSERT_LEMBUR="http://172.16.8.187/android/test/insert_lembur.php";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_NAMA_TEMPAT = "nama_tempat";
    public static final String KEY_NAMA_PEKERJAAN = "nama_pekerjaan";
    public static final String KEY_URAIAN = "uraian";
    public static final String KEY_NIK = "nik";
    public static final String KEY_JAM_MULAI = "jam_mulai";
    public static final String KEY_JAM_SELESAI = "jam_selesai";
    public static final String KEY_TGL_MULAI = "tgl_mulai";
    public static final String KEY_TGL_AKHIR = "tgl_akhir";
    public static final String KEY_TANGGAL = "tanggal";
    public static final String KEY_PERAN = "peran";
    public static final String KEY_KETERANGAN = "keterangan";
    public static final String KEY_STATUS_HARI = "status_hari";
}
