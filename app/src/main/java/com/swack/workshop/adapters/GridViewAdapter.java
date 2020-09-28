package com.swack.workshop.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.swack.workshop.R;
import com.swack.workshop.activities.FinalBillActivity;
import com.swack.workshop.activities.LoginActivity;
import com.swack.workshop.activities.MainActivity;
import com.swack.workshop.activities.OngoingActivity;
import com.swack.workshop.activities.OrderListActivity;
import com.swack.workshop.activities.ProfileActivity;
import com.swack.workshop.activities.SupportActivity;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.model.ResponseResult;
import com.swack.workshop.model.GarList;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.swack.workshop.activities.BaseActivity.prefManager;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.MyViewHolder> {

    private MainActivity context;
    private ArrayList<GarList> garLists;
    public String gar_id,gar_list_id,f,s;
   // private TextToSpeech mTTS;

    StringTokenizer tokens ;
    String data;
    String[] items;
    private APIService apiService;
    private String TITLE []= {
            "New Request",
            "Ongoing \nRequest",
            "Call Center",
            "Request History",
            "My Profile",
            "Logout"};
    private int IMAGES []={
            R.drawable.ic_orderlist,
            R.drawable.ic_ongoing,
            R.drawable.ic_support,
            R.drawable.ic_info,
            R.drawable.ic_driver,
            R.drawable.ic_logout};

    private int COlOR []={
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite};

    private Dialog dialog1;

    public GridViewAdapter(MainActivity context,String gar_id) {
        this.context = context;
        this.gar_id = gar_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_menu_adapter, parent, false);
        apiService = APIUrl.getClient().create(APIService.class);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        int w = holder.cvCategory.getResources().getDisplayMetrics().widthPixels;
        int h = holder.cvCategory.getResources().getDisplayMetrics().heightPixels;
        holder.images.getLayoutParams().width = w/8;
        holder.images.getLayoutParams().height = w/8;
        holder.ltyCategory.getLayoutParams().height = h/5;

        holder.title.setText(TITLE[position]);
        holder.title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.title.setSelected(true);
        holder.images.setImageResource(IMAGES[position]);
        holder.ltyCategory.setBackgroundColor(context.getResources().getColor(COlOR[position]));
        holder.ltyCategory.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
            public void onClick(View v) {
                switch (position)
                {
                        case 0:
                         context.startActivity(new Intent(context, OrderListActivity.class));
                        break;
                        case 1:
                            context.startActivity(new Intent(context, OngoingActivity.class));
                        break;
                        case 2:
                            context.startActivity(new Intent(context, SupportActivity.class));
                        break;
                        case 3:
                            context.startActivity(new Intent(context, FinalBillActivity.class));

                            break;
                        case 4:
                            context.startActivity(new Intent(context, ProfileActivity.class));
                        break;
                        case 5:
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                            alertDialogBuilder.setTitle(context.getString(R.string.app_name));
                            alertDialogBuilder.setIcon(R.drawable.logo);
                            alertDialogBuilder.setMessage(context.getString(R.string.logout_now));
                           /* mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    mTTS.setLanguage(new Locale("en","IN"));
                                }
                            });
                            mTTS.speak("do you want to logout?" , TextToSpeech.QUEUE_ADD, null, null);
*/                            alertDialogBuilder.setCancelable(false);

                            alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                                @SuppressLint("NewApi")
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    prefManager.connectDB();
                                    prefManager.setBoolean("isLogin", false);
                                    prefManager.closeDB();

                                    /*mTTS.speak("logout done succefully..",TextToSpeech.QUEUE_ADD, null, null);
                                    mTTS.setPitch((float)0.8);
                                    mTTS.setSpeechRate((float)0.2);*/

                                    Toasty.success(context,"logout done succefully..",Toasty.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);
                                    context.finish();
                                }
                            });

                            alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView images;
        public LinearLayout ltyCategory;
        public CardView cvCategory;
        public MyViewHolder(View view) {
            super(view);
            images = (ImageView) view.findViewById(R.id.imageView_image);
            title = (TextView) view.findViewById(R.id.textView_title);
            ltyCategory = (LinearLayout) view.findViewById(R.id.ltyCategory);
            cvCategory = (CardView) view.findViewById(R.id.cvCategory);

        }
    }
    
    //user define function
    private void getId() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.show();

        callId().enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                progressDialog.dismiss();
                Log.d(TAG, "response Id : " + response.body().getResponse());
                if (Integer.parseInt(response.body().getResponse()) == 101) {

                    garLists = response.body().getDashOrderList();
                    gar_list_id = response.body().getDashOrderList().get(0).getGar_id();

                    System.out.println(" GAR Response IDS : "+gar_list_id);

                    data  =  gar_list_id;
                    items = data.split(",");
                    //System.out.println("#First  "+(items[0]));
                   // System.out.println("#Secondn "+(items[1]));
                  /*  int i;
                    String it;
                    for (i=0; i < items.length; i++){
                        System.out.println("#..."+items[i]);
                        it = items[i];
                        System.out.println("#."+it);*/
                       try{
                          // Toast.makeText(context, "data id"+data, Toast.LENGTH_SHORT).show();
                          // Toast.makeText(context, "gar id"+gar_id, Toast.LENGTH_SHORT).show();
                           if(gar_id.equals(data))
                           {
                               Intent intent = new Intent(context, OrderListActivity.class);
                               intent.putExtra("gar_id",data);
                               context.startActivity(intent);
                           }else{
                               Toast.makeText(context, "sorry new request not available for you ", Toast.LENGTH_SHORT).show();
                           }
                       }catch (NullPointerException e){
                           e.printStackTrace();
                       }
                   /* if(gar_id.equals(items[i]))
                    {
                        Intent intent = new Intent(context, OrderListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context, "No New Request Available For You..!", Toast.LENGTH_SHORT).show();
                    }*/



                   /* try{
                        if (gar_id.equals(items))
                        {
                            Intent intent = new Intent(context, OrderListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                        }else{
                            Toast.makeText(context, "No New Request Available For You..!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }

                    for (String item : items)
                    {
                        System.out.println("item = " + item);


                    }*/

                } else if (Integer.parseInt(response.body().getResponse()) == 102) {
                    progressDialog.dismiss();
                    Toasty.error(context, context.getResources().getString(R.string.account_block), Toast.LENGTH_LONG).show();
                }  else {
                    progressDialog.dismiss();
                    System.out.println(TAG + " Else Close");
                    Toasty.error(context, "other error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "server down", Toast.LENGTH_SHORT).show();
                //errorOut(t);
            }
        });
    }

    private Call<ResponseResult>
    callId() {
        return apiService.dashboard(
                APIUrl.KEY
        );
    }
}
