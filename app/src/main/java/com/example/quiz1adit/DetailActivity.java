package com.example.quiz1adit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView kodeBarangTextView, jumlahTextView, totalHargaTextView, keanggotaanTextView, diskonMembershipTextView, diskonCashbackTextView, totalDiskonTextView, totalSetelahDiskonTextView, namaTextView;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize TextViews
        kodeBarangTextView = findViewById(R.id.kodeBarangTextView);
        jumlahTextView = findViewById(R.id.jumlahTextView);
        totalHargaTextView = findViewById(R.id.totalHargaTextView);
        keanggotaanTextView = findViewById(R.id.keanggotaanTextView);
        diskonMembershipTextView = findViewById(R.id.diskonMembershipTextView);
        diskonCashbackTextView = findViewById(R.id.diskonCashbackTextView);
        totalDiskonTextView = findViewById(R.id.totalDiskonTextView);
        totalSetelahDiskonTextView = findViewById(R.id.totalSetelahDiskonTextView);
        namaTextView = findViewById(R.id.namaTextView);

        // Initialize shareButton
        shareButton = findViewById(R.id.shareButton);

        // Set click listener for shareButton
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDetails();
            }
        });

        // Retrieve data from Intent and display it in TextViews
        Intent intent = getIntent();
        if (intent != null) {
            String kodeBarang = intent.getStringExtra("kodeBarang");
            double jumlah = intent.getDoubleExtra("jumlah", 0);
            double totalHarga = intent.getDoubleExtra("totalHarga", 0);
            String keanggotaan = intent.getStringExtra("keanggotaan");
            double diskonMembership = intent.getDoubleExtra("diskonMembership", 0);
            double diskonCashback = intent.getDoubleExtra("diskonCashback", 0);
            String nama = intent.getStringExtra("nama");

            kodeBarangTextView.setText("Kode Barang: " + kodeBarang);
            jumlahTextView.setText("Jumlah: " + jumlah);
            totalHargaTextView.setText("Total Harga: " + formatRupiah(totalHarga));
            keanggotaanTextView.setText("Keanggotaan: " + keanggotaan);
            diskonMembershipTextView.setText("Diskon Membership: " + String.format("%.2f", diskonMembership) + "%");
            diskonCashbackTextView.setText("Diskon Cashback: " + formatRupiah(diskonCashback));
            totalDiskonTextView.setText("Total Diskon: " + formatRupiah(diskonMembership + diskonCashback));
            totalSetelahDiskonTextView.setText("Total Setelah Diskon: " + formatRupiah(totalHarga - (diskonMembership + diskonCashback)));
            namaTextView.setText("Nama Pelanggan: " + nama);
        }
    }

    private void shareDetails() {
        String shareText = "Nama Pelanggan: " + namaTextView.getText().toString() + "\n" +
                "Kode Barang: " + kodeBarangTextView.getText().toString() + "\n" +
                "Jumlah: " + jumlahTextView.getText().toString() + "\n" +
                "Total Harga: " + totalHargaTextView.getText().toString() + "\n" +
                "Keanggotaan: " + keanggotaanTextView.getText().toString() + "\n" +
                "Diskon Membership: " + diskonMembershipTextView.getText().toString() + "\n" +
                "Diskon Cashback: " + diskonCashbackTextView.getText().toString() + "\n" +
                "Total Diskon: " + totalDiskonTextView.getText().toString() + "\n" +
                "Total Setelah Diskon: " + totalSetelahDiskonTextView.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Bagikan Detail via"));
    }

    private String formatRupiah(double harga) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(harga);
    }
}
