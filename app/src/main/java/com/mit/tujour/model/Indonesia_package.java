package com.mit.tujour.model;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mit.tujour.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Indonesia_package extends AppCompatActivity implements PaymentResultListener {

    Button indBuyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_indonesia_package);
        indBuyBtn=findViewById(R.id.indoBuyPackage);
        String Samount="50000";
        int amount=Math.round(Float.parseFloat(Samount) *100);
        indBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout=new Checkout();
                checkout.setKeyID("rzp_test_8VgGIjAulD1UKC");
                checkout.setImage(R.mipmap.toujour_logo);
                JSONObject object=new JSONObject();
                try {
                    object.put("name", "TUJOUR");
                    object.put("desc","testing payment");
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",amount);
                    object.put("prefill.contact","7872611723");
                    object.put("prefill.email","rohitcse7872@gmail.com");
                    checkout.open(Indonesia_package.this,object);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}

