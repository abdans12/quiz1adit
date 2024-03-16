package com.example.quiz1adit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText kodeBarangEditText, jumlahEditText, namaEditText; // Tambahkan EditText untuk nama
    TextView totalTextView;
    Button hitungButton;
    RadioGroup keanggotaanRadioGroup;
    RadioButton goldRadioButton, silverRadioButton, biasaRadioButton;

    double hargaSGS = 12999999;
    double hargaIPX = 5725300;
    double hargaPCO = 2730551;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kodeBarangEditText = findViewById(R.id.kodeBarangEditText);
        jumlahEditText = findViewById(R.id.jumlahEditText);
        namaEditText = findViewById(R.id.namaEditText); // Inisialisasi EditText untuk nama
        totalTextView = findViewById(R.id.totalTextView);
        hitungButton = findViewById(R.id.hitungButton);
        keanggotaanRadioGroup = findViewById(R.id.keanggotaanRadioGroup);
        goldRadioButton = findViewById(R.id.goldRadioButton);
        silverRadioButton = findViewById(R.id.silverRadioButton);
        biasaRadioButton = findViewById(R.id.biasaRadioButton);

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungTotal();
            }
        });
    }

    private void hitungTotal() {
        String kodeBarang = kodeBarangEditText.getText().toString();
        double jumlah = Double.parseDouble(jumlahEditText.getText().toString());
        String nama = namaEditText.getText().toString(); // Ambil nilai dari EditText untuk nama
        double totalHarga = 0;

        switch (kodeBarang) {
            case "SGS":
                totalHarga = jumlah * hargaSGS;
                break;
            case "IPX":
                totalHarga = jumlah * hargaIPX;
                break;
            case "PCO":
                totalHarga = jumlah * hargaPCO;
                break;
        }

        // Diskon cashback untuk transaksi di atas 10 juta
        double diskonCashback = 0;
        if (totalHarga > 10000000) {
            diskonCashback = 0.01 * totalHarga; // Diskon 1% dari total harga
        }

        // Diskon tambahan berdasarkan keanggotaan
        double diskonMembership = getDiskonMembership(totalHarga);

        // Menghitung total diskon
        double totalDiskon = diskonCashback + diskonMembership;

        // Mengurangi total diskon dari total harga untuk mendapatkan total setelah diskon
        double totalSetelahDiskon = totalHarga - totalDiskon;

        // Format harga menjadi Rupiah
        String formattedTotalSetelahDiskon = formatRupiah(totalSetelahDiskon);

        // Menampilkan total harga setelah diskon
        totalTextView.setText("Total Harga Setelah Diskon: " + formattedTotalSetelahDiskon);

        // Memanggil DetailActivity dan melewatkan data
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("kodeBarang", kodeBarang);
        intent.putExtra("jumlah", jumlah);
        intent.putExtra("totalHarga", totalHarga);
        intent.putExtra("diskonCashback", diskonCashback);
        intent.putExtra("diskonMembership", diskonMembership);
        intent.putExtra("keanggotaan", getKeanggotaan());
        intent.putExtra("nama", nama); // Sertakan nilai nama dalam Intent
        startActivity(intent);
    }

    // Method untuk mendapatkan diskon tambahan berdasarkan keanggotaan
    private double getDiskonMembership(double totalHarga) {
        double diskonTambahan = 0;

        if (goldRadioButton.isChecked()) {
            diskonTambahan = 0.10 * totalHarga; // Diskon 10% dari total harga
        } else if (silverRadioButton.isChecked()) {
            diskonTambahan = 0.05 * totalHarga; // Diskon 5% dari total harga
        } else if (biasaRadioButton.isChecked()) {
            diskonTambahan = 0.02 * totalHarga; // Diskon 2% dari total harga
        }

        return diskonTambahan;
    }

    // Method untuk mendapatkan keanggotaan yang dipilih
    private String getKeanggotaan() {
        if (goldRadioButton.isChecked()) {
            return "Gold";
        } else if (silverRadioButton.isChecked()) {
            return "Silver";
        } else if (biasaRadioButton.isChecked()) {
            return "Biasa";
        } else {
            return "Tidak Ada"; // Kondisi jika tidak ada keanggotaan yang dipilih
        }
    }

    // Method untuk format Rupiah
    private String formatRupiah(double harga) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(harga);
    }
}
