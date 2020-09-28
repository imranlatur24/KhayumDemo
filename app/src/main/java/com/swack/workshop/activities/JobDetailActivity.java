package com.swack.workshop.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.swack.workshop.BuildConfig;
import com.swack.workshop.R;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.model.ResponseResult;
import com.swack.workshop.model.FinalListModel;
import com.swack.workshop.model.ItemListModel;
import com.swack.workshop.model.OrderListModel2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class JobDetailActivity extends BaseActivity {

    private static final String TAG = "JobDetailActivity";
    private static final int REQUEST_CAMERA = 101;
    private TableLayout mTableLayout;
    private Button btn_submit_bill;
    private String order_id,gar;
    private Spinner edit_state;
    private Button btnAddBill;
    private EditText edtJobEstimate,edtjobfinalamt;
    private String jobFinalAmt,jobEstimate;
    private Dialog dialog;
    private ArrayList<ItemListModel.ItemList> vehicalLists;
    private String vehical_type = "1",gar_id,customer_id;
    private ArrayList<String>  typeName;
    private String vehical_name;
    private File image,newFile;
    private String ettype="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        getSupportActionBar().setTitle("Order id : "+getIntent().getStringExtra("order_id"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTableLayout = (TableLayout) findViewById(R.id.tableInvoices);
        btn_submit_bill = (Button) findViewById(R.id.btn_submit_bill);

        prefManager.connectDB();
        gar_id = prefManager.getString("gar_id");
        prefManager.closeDB();
        order_id = getIntent().getStringExtra("order_id");
        customer_id = getIntent().getStringExtra("customer_id");
        addHeaders();
        addData();

        if(!getIntent().getBooleanExtra("button",false)){
            btn_submit_bill.setVisibility(View.VISIBLE);
        }
        btn_submit_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPermission()){
                    requestPermission();
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            // Error occurred while creating the File
                        }
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(JobDetailActivity.this,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    photoFile);

                            image = photoFile;
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_CAMERA);

                        }
                    }
                }

            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{CAMERA,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, 1);
    }

    public boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        final TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(4, 4, 4, 4);
        tv.setTextSize(18);
        tv.setBackgroundColor(bgColor);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setLayoutParams(getLayoutParams());

        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 2, 2, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
    }
    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableRow tr = new TableRow(this);
        tr.addView(getTextView(0, "Sr no.", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, "Part Name", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, "Qty", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, "Price", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, "Amount", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        mTableLayout.addView(tr, getTblLayoutParams());
    }

    public void addHeadersSum(int sum) {
        TableRow tr = new TableRow(this);
        tr.addView(getTextView(0, " ", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, " ", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, " ", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, "Total", Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        tr.addView(getTextView(0, " "+sum, Color.WHITE, Typeface.BOLD, ContextCompat.getColor(this, R.color.colorAccent)));
        mTableLayout.addView(tr, getTblLayoutParams());
    }

    private void addData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        System.out.println("order_id "+order_id);
        apiService.showJobItemDetails(APIUrl.KEY,order_id).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                progressDialog.dismiss();
                Log.d(TAG, "response login : " + response.body().getResponse());
                if (Integer.parseInt(response.body().getResponse()) == 101) {
                        if(!response.body().getJobItemFinalList().isEmpty()){
                            ArrayList<FinalListModel> jobItemFinalList = new ArrayList<>();
                            jobItemFinalList = response.body().getJobItemFinalList();
                            int i = 0;
                            int sum = 0;
                            for(FinalListModel data: jobItemFinalList){
                                TableRow tr = new TableRow(JobDetailActivity.this);
                                tr.setLayoutParams(getLayoutParams());
                                tr.addView(getTextView(i+2, data.getJob_id(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(JobDetailActivity.this, R.color.colorWhite)));
                                tr.addView(getTextView(i+4, data.getServicepart_id(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(JobDetailActivity.this, R.color.colorWhite)));
                                tr.addView(getTextView(i+5, data.getJob_estimate(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(JobDetailActivity.this, R.color.colorWhite)));
                                tr.addView(getTextView(i+6, String.valueOf(Integer.parseInt(data.getJob_finalaount())/Integer.parseInt(data.getJob_estimate())), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(JobDetailActivity.this, R.color.colorWhite)));
                                tr.addView(getTextView(i+7, data.getJob_finalaount(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(JobDetailActivity.this, R.color.colorWhite)));
                                mTableLayout.addView(tr, getTblLayoutParams());
                                i++;
                                sum = sum + Integer.parseInt(data.getJob_finalaount());
                            }
                            addHeadersSum(sum);
                        }else {

                        }
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                progressDialog.dismiss();
                errorOut(t);
            }
        });
    }

    private void submitBill(RequestBody key, RequestBody order_id, RequestBody customer_id, RequestBody garage_id, MultipartBody.Part file) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        progressDialog.setCancelable(false);
        System.out.println("order_id "+order_id);
        apiService.callSubmitBill(key,garage_id,customer_id,order_id,file).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                progressDialog.dismiss();
                if (Integer.parseInt(response.body().getResponse()) == 101) {
                    Toasty.success(JobDetailActivity.this,"Bill Submitted",Toasty.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.serverdown), Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                System.out.println(TAG +"Response " +t);
                progressDialog.dismiss();
                errorOut(t);
            }
        });
    }

    private void addformDailog() {

        dialog = new Dialog(JobDetailActivity.this, R.style.AppCompatAlertDialogStyle);
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
                        vehical_name = vehicalLists.get(i).getSer_part_id();
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
                        mTableLayout.removeAllViews();
                        addHeaders();
                        addData();

                    } else if (Integer.parseInt(response.body().getResponse()) == 102) {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        //linearFilter.setVisibility(View.GONE);

                        Toasty.error(getApplicationContext(), getResources().getString(R.string.account_block), Toast.LENGTH_LONG).show();
                    }else {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        //linearFilter.setVisibility(View.GONE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
           // Toasty.error(this, "Image pick cancel", Toasty.LENGTH_SHORT).show();
            return;
        } else {
                if (requestCode == REQUEST_CAMERA) {

                    try {
                        newFile = new Compressor(this)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(50)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToFile(image);
                        image = new File(newFile.getAbsolutePath());
                        RequestBody requestFile =RequestBody.create(MediaType.parse("*/*"), image);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("bill_selfi_img", image.getName(), requestFile);
                        submitBill(RequestBody.create(MediaType.parse("text/plain"), APIUrl.KEY),
                                RequestBody.create(MediaType.parse("text/plain"),order_id),
                                RequestBody.create(MediaType.parse("text/plain"), customer_id),
                                RequestBody.create(MediaType.parse("text/plain"),gar_id),
                                fileToUpload);
                        System.out.println(TAG+" gar_id "+gar_id+" customer_id "+customer_id+" order_id "+order_id);
                        System.out.println("file "+image.isFile()+" file name " +image.getAbsolutePath());
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        if(getIntent().getBooleanExtra("button",false)){
            menu.findItem(R.id.action_add).setVisible(false);
        }
        return true;
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
            case R.id.action_add:
                addformDailog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
