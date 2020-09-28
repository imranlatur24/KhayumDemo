package com.swack.workshop.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.swack.workshop.R;
import com.swack.workshop.adapters.FinalBillAdapter;
import com.swack.workshop.constants.TextInputAutoCompleteTextView;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.model.FinalListModel;
import com.swack.workshop.model.ItemListModel;
import com.swack.workshop.model.OrderListModel2;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinalBillActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "OrderListActivity" ;
    private FinalBillAdapter homeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView_finalbill;
    private Button btnRetry;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private TextView txtError;
    private ArrayList<OrderListModel2.Order> templistview2;
    private ArrayList<String> temp;
    private APIService apiService;
    private String gar_id,ok;
    private Toolbar toolbar;
    //dialog content
    private Dialog dialog;
    //vehical type
    private ArrayList<ItemListModel.ItemList> vehicalLists;
    private String vehical_type;
    private ArrayList<String>  typeName;
    private String vehical_name,cus_name;
    private String ettype="";
    private String order_id;
    private Spinner edit_state;
    private Button btnAddBill;
    private EditText edtJobEstimate,edtjobfinalamt;
    private String jobFinalAmt,jobEstimate;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalbill);
        toolbar = findViewById(R.id.toolbar_finalbill);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Request History");

        init();
        finalbill();
    }

    private void init()
    {
        apiService = APIUrl.getClient().create(APIService.class);
        prefManager.connectDB();
        gar_id = prefManager.getString("gar_id");
        //type = prefManager.getString("userCountry");
        prefManager.closeDB();
        //System.out.println("conutry : "+type);

        recyclerView_finalbill = findViewById(R.id.recyclerView_finalbill);
        recyclerView_finalbill.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(FinalBillActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_finalbill.setLayoutManager(linearLayoutManager);

        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        txtError = (TextView) findViewById(R.id.error_txt_cause);
        prefManager.connectDB();
        cus_name = prefManager.getString("gar_name");
        prefManager.closeDB();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalbill();
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
                    Toasty.error(FinalBillActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    finalbill();
                }
            }
        });
    }

    //main categories data
    private void finalbill() {
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView_finalbill.setVisibility(View.GONE);

        callCategory().enqueue(new Callback<OrderListModel2>() {
            @Override
            public void onResponse(Call<OrderListModel2> call, Response<OrderListModel2> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    System.out.println("Transport Response : "+response.body().getResponse());
                    if (Integer.parseInt(response.body().getResponse()) == 101) {
                        if(!response.body().getOrderList().isEmpty()) {
                            errorLayout.setVisibility(View.GONE);
                            recyclerView_finalbill.setVisibility(View.VISIBLE);
                            homeAdapter = new FinalBillAdapter(FinalBillActivity.this);
                            recyclerView_finalbill.setAdapter(homeAdapter);
                            homeAdapter.setListArray(response.body().getOrderList());

                            templistview2 = response.body().getOrderList();  }else {
                            showErrorView("Hi "+ cus_name+"\n"+getResources().getString(R.string.create_new));
                            btnRetry.setVisibility(View.GONE);

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
        prefManager.connectDB();
        String latitude = prefManager.getString("latitude");
        String longitude = prefManager.getString("longitude");
        prefManager.closeDB();
        System.out.println("Order API KEY " + APIUrl.KEY+ " gar_id "+gar_id+" latitude "+latitude+" longitude "+longitude);
        return apiService.finaList(
                APIUrl.KEY,
                gar_id,
                latitude,
                longitude
        );
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.error_btn_retry){
            finalbill();
        }
    }

/*    @Override
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
                    Animation rotation = AnimationUtils.loadAnimation(FinalBillActivity.this, R.anim.rotate);
                    view.startAnimation(rotation);
                    // create and use new data set
                    finalbill();
                }
            });
        }
        return true;
    }*/

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
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private void addformDailog() {

        dialog = new Dialog(FinalBillActivity.this, R.style.AppCompatAlertDialogStyle);
        dialog.setContentView(R.layout.add_item_dialog);
        dialog.setCancelable(true);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ImageView image_cancel_dialog = dialog.findViewById(R.id.image_cancel_dialog);
        image_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        edit_state = dialog.findViewById(R.id.edit_state_dia);
        getItem();
        edit_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < vehicalLists.size(); i++) {
                    if (edit_state.getSelectedItem().equals(vehicalLists.get(i).getSer_part_name())) {
                        vehical_type = vehicalLists.get(i).getSer_part_id();
                        //Toast.makeText(FinalBillActivity.this, "id "+vehicalLists.get(i).getSer_part_id(), Toast.LENGTH_SHORT).show();
                        //getVehicalCategory(type);
                        ettype = "";
                        //taluka = ""
                        //System.out.println("#Item Type Id " + ettype);
                        System.out.println("#Item Type Id " + vehical_type);
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtJobEstimate = dialog.findViewById(R.id.edtJobEstimate);
        edtjobfinalamt = dialog.findViewById(R.id.edtjobfinalamt);
        btnAddBill = dialog.findViewById(R.id.btnAddBill);


        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobEstimate = edtJobEstimate.getText().toString().trim();
                jobFinalAmt = edtjobfinalamt.getText().toString().trim();
                vehical_name = edit_state.getSelectedItem().toString().trim();
                if (!isNetworkAvailable()) {
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.error_msg_no_internet), Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(vehical_name)) {
                    Toasty.error(getApplicationContext(),"select item name", Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(jobEstimate)) {
                    Toasty.error(getApplicationContext(),"enter job estimate amount", Toasty.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(jobFinalAmt)) {
                    Toasty.error(getApplicationContext(),"enter job final amount", Toasty.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("#vehical_name : "+vehical_name +" job estimate :"+jobEstimate+" jobFinalAmt : "+jobFinalAmt+" order id "+order_id);
                itemListfun(vehical_name,jobEstimate,jobFinalAmt,order_id);
            }
        });
        dialog.show();
    }

    private void itemListfun(String vehical_name, String jobEstimate, String jobFinalAmt, String order_id) {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        callCategory2(vehical_name,jobEstimate,jobFinalAmt,order_id).enqueue(new Callback<OrderListModel2>() {
            @Override
            public void onResponse(Call<OrderListModel2> call, Response<OrderListModel2> response) {
                try {
                    System.out.println("#Final Item Response : "+response.body().getResponse());

                    if (Integer.parseInt(response.body().getResponse()) == 101) {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        Toasty.success(getApplicationContext(),"Item Added Successfully",Toasty.LENGTH_SHORT).show();

                        Intent intent = new Intent(FinalBillActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        // text_total.setText(" Item: "+total+","+" Qty : "+item+","+" Total : "+getResources().getString(R.string.Rs)+price);
                    } else if (Integer.parseInt(response.body().getResponse()) == 102) {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        //linearFilter.setVisibility(View.GONE);
                        recyclerView_finalbill.setVisibility(View.GONE);

                        Toasty.error(getApplicationContext(), getResources().getString(R.string.account_block), Toast.LENGTH_LONG).show();
                    }else {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        //linearFilter.setVisibility(View.GONE);
                        recyclerView_finalbill.setVisibility(View.GONE);
                        System.out.println(TAG + " Else Close");
                        Toasty.error(getApplicationContext(), getResources().getString(R.string.serverdown), Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<OrderListModel2> call, Throwable t) {
                progressDialog.dismiss();
                dialog.dismiss();
                errorOut(t);
            }
        });
    }

    //categories
    private Call<OrderListModel2> callCategory2(String vehical_name, String jobEstimate, String jobFinalAmt, String order_id) {
        System.out.println("#Item List Details API " + APIUrl.KEY+" vehical_name : "+vehical_name+" jobEstimate : "+jobEstimate+" jobFinalAmt : "+jobFinalAmt+" order_id : "+order_id);
        return apiService.jobitemsubmit(
                APIUrl.KEY,
                vehical_name,
                jobEstimate,
                jobFinalAmt,
                order_id
        );

    }

    //Vehical Type method
    private void getItem() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

        vehicalLists = new ArrayList<>();
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        //defining the call
        Call<ItemListModel> call = service.getItem(APIUrl.KEY);

        //calling the api
        call.enqueue(new Callback<ItemListModel>() {
            @Override
            public void onResponse(Call<ItemListModel> call, Response<ItemListModel> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                System.out.println(TAG + " Response "+response.body().getResponse());
                if (Integer.parseInt(response.body().getResponse()) == 101) {
                   // Toast.makeText(FinalBillActivity.this, "response item list", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < response.body().getItemList().size(); i++) {
                        vehicalLists.add(new ItemListModel.ItemList(
                                response.body().getItemList().get(i).getSer_part_id(),
                                response.body().getItemList().get(i).getSer_part_name()));
                       // Toast.makeText(FinalBillActivity.this, "name "+response.body().getItemList().get(i).getSer_part_name(), Toast.LENGTH_SHORT).show();
                       // Toast.makeText(FinalBillActivity.this, "id "+response.body().getItemList().get(i).getSer_part_id(), Toast.LENGTH_SHORT).show();
                    }
                    vehicalListSpinner(vehicalLists);
                } else if (Integer.parseInt(response.body().getResponse()) == 102) {
                    vehicalListSpinner(vehicalLists);
                } else if (Integer.parseInt(response.body().getResponse()) == 103) {
                    vehicalListSpinner(vehicalLists);
                    System.out.println(TAG + " Required Parameter Missing");
                } else if (Integer.parseInt(response.body().getResponse()) == 104) {
                    vehicalListSpinner(vehicalLists);
                    System.out.println(TAG + " Invalid Key");
                } else if (Integer.parseInt(response.body().getResponse()) == 105) {
                    vehicalListSpinner(vehicalLists);
                    System.out.println(TAG + " Login failed");
                } else {
                    vehicalListSpinner(vehicalLists);
                    System.out.println(TAG + " Else Close");
                }
            }

            @Override
            public void onFailure(Call<ItemListModel> call, Throwable t) {
                progressDialog.dismiss();
                vehicalListSpinner(vehicalLists);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //spinner for vehical type
    private void vehicalListSpinner(ArrayList<ItemListModel.ItemList> vehicalLists) {
        typeName = new ArrayList<>();
        for (int i=0; i<vehicalLists.size(); i++) {
            typeName.add(vehicalLists.get(i).getSer_part_name());
            System.out.println("#Item Id True: "+vehicalLists.get(i).getSer_part_id().equals(vehical_type));
            if (vehicalLists.get(i).getSer_part_id().equals(vehical_type)) {
                edit_state.setSelection(i);
                System.out.println("#Item Id : "+vehical_type);
                //getState(country);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeName);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        edit_state.setAdapter(dataAdapter);
    }




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
