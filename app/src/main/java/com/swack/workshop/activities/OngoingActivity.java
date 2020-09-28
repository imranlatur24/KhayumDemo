package com.swack.workshop.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.swack.workshop.R;
import com.swack.workshop.adapters.OngoingAdapter;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.model.ItemListModel;
import com.swack.workshop.model.OrderListModel2;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OngoingActivity extends BaseActivity {

    private static final String TAG = "OrderListActivity" ;
    private OngoingAdapter homeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView_ongoing;
    private Button btnRetry;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private TextView txtError;
    private ArrayList<OrderListModel2.Order> templistview;
    private ArrayList<OrderListModel2.Order> templistview2;
    private ArrayList<String> temp;
    private APIService apiService;
    private String gar_id,ok;
    private Toolbar toolbar;
    private String latme,logme,cus_name;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongoing);
        toolbar = findViewById(R.id.toolbar_ongoing);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Ongoing Request");
        apiService = APIUrl.getClient().create(APIService.class);

        prefManager.connectDB();
        gar_id = prefManager.getString("gar_id");
        latme = prefManager.getString("latitude");
        logme = prefManager.getString("longitude");
        cus_name = prefManager.getString("gar_name");
        prefManager.closeDB();

        recyclerView_ongoing = findViewById(R.id.recyclerView_ongoing);
        recyclerView_ongoing.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_ongoing.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList();
            }
        });
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeMainActivity);
        swipeContainer.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (!isNetworkAvailable()) {
                    swipeContainer.setRefreshing(false);
                    Toasty.error(OngoingActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                } else {

                    orderList();

                }
            }
        });
        orderList();
    }
     //main categories data
    private void orderList() {
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView_ongoing.setVisibility(View.GONE);
        templistview = new ArrayList<>();

        callCategory().enqueue(new Callback<OrderListModel2>() {
            @Override
            public void onResponse(Call<OrderListModel2> call, Response<OrderListModel2> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    System.out.println("Transport Response : "+response.body().getResponse());
                    if (Integer.parseInt(response.body().getResponse()) == 101) {
                        if(!response.body().getOrderList().isEmpty()) {
                            errorLayout.setVisibility(View.GONE);
                            recyclerView_ongoing.setVisibility(View.VISIBLE);
                            homeAdapter = new OngoingAdapter(OngoingActivity.this, gar_id);
                            recyclerView_ongoing.setAdapter(homeAdapter);
                            homeAdapter.setListArray(response.body().getOrderList());

                            templistview2 = response.body().getOrderList();
                            // text_total.setText(" Item: "+total+","+" Qty : "+item+","+" Total : "+getResources().getString(R.string.Rs)+price);
                        }else {
                            btnRetry.setVisibility(View.GONE);
                            showErrorView("Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new));
                        }
                        } else if (Integer.parseInt(response.body().getResponse()) == 102) {
                        btnRetry.setVisibility(View.GONE);
                        showErrorView("Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new));
                    }else {
                        btnRetry.setVisibility(View.GONE);
                        showErrorView("Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new));
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    btnRetry.setVisibility(View.GONE);
                    showErrorView("Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new));
                }
            }

            @Override
            public void onFailure(Call<OrderListModel2> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showErrorView(t);
            }
        });
    }

    //categories
    private Call<OrderListModel2> callCategory() {

        System.out.println("Order API KEY " + APIUrl.KEY+ " gar_id "+gar_id+" latitude "+latme+" longitude "+logme);
        return apiService.orderList(
                APIUrl.KEY,
                gar_id,
                latme,
                logme
        );
    }

    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    private void showErrorView(String error) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(error);
        }
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = "Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new);

        if (!isNetworkAvailable()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = "Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new);
        }

        return errorMsg;
    }


 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ImageView locButton = (ImageView) menu.findItem(R.id.action_refresh).getActionView();
        if (locButton != null) {
            locButton.setImageResource(R.drawable.ic_refresh);
            locButton.setPadding(8,8,8,8);
            // need some resize
            locButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation rotation = AnimationUtils.loadAnimation(OngoingActivity.this, R.anim.rotate);
                    view.startAnimation(rotation);
                    // create and use new data set
                    orderList();
                }
            });
        }
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
