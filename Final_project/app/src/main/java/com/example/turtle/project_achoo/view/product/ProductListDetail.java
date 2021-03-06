package com.example.turtle.project_achoo.view.product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.example.turtle.project_achoo.function.backgroundTask.DownloadImageTask;
import com.example.turtle.project_achoo.function.model.product.LikeProductDTO;
import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.networkService.LikeProductService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListDetail extends AppCompatActivity {

    private SharedPreferences appData;

    private ImageView productImage, likeIt;
    private TextView productBrand, productName, productPrice;

    private Intent intent;
    private String pImg, pBrand, pName, pPrice, pCode, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_detail);

        setView();


    } // onCreate

    private void setView() {

        appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        productImage = findViewById(R.id.productImage);
        productBrand = findViewById(R.id.productBrand);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        likeIt = findViewById(R.id.likeIt);

        intent = getIntent();
        pCode = intent.getStringExtra("Pcode");
        pImg = intent.getStringExtra("Pimg");
        pBrand = intent.getStringExtra("Pbrand");
        pName = intent.getStringExtra("Pname");
        pPrice = intent.getStringExtra("Pprice");

        //new DownloadImageTask(productImage).execute(pImg);
        String img_url = "http://192.168.0.24:4000/static/images/" + pImg;
        Glide.with(this).load(img_url).apply(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)).into(productImage);

        productBrand.setText(pBrand);
        productName.setText(pName);
        productPrice.setText(pPrice);

        //좋아요 누르면 관심 상품에 추가 like_product
        likeIt.setOnClickListener(v -> {

            addLikeProduct();

        });


    } // setView()

    private void addLikeProduct() {

        LikeProductDTO likeProductDTO = new LikeProductDTO(id, pCode); // 회원 닉네임과 상품 코드
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(likeProductDTO));

        LikeProductService likeProductService = RetrofitInstance.getLikeProductService();
        Call<Integer> call = likeProductService.addToCart(requestBody);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int result = response.body();
                if (result == 1)
                    Toast.makeText(getApplicationContext(), "관심 상품에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                else if (result == 0)
                    Toast.makeText(getApplicationContext(), "이미 관심 상품에 추가되있는 상품입니다.", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

                Log.d(HomeActivity.TAG, "요청 실패");

            }
        });
    }
}
