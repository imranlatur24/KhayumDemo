package com.swack.workshop.adapters;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.swack.workshop.R;
import com.swack.workshop.activities.OngoingActivity;
import com.swack.workshop.activities.OrderListActivity;
import com.swack.workshop.data.APIService;
import com.swack.workshop.data.APIUrl;
import com.swack.workshop.model.ResponseResult;
import com.swack.workshop.model.OrderListModel2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private OrderListActivity context;

    private ArrayList<OrderListModel2.Order> arrayLists;
    private APIService apiService;
    //private ArrayList<Cartdb> list;
    private ArrayList<OrderListModel2.Order> tempArrayLists;
    int sum;
    private float prize;
    private String gar_id,ordrequniq_id,gar_custid;

    public HomeAdapter(OrderListActivity context) {
        this.context = context;
        context.prefManager.connectDB();
        gar_id = context.prefManager.getString("gar_id");
        context.prefManager.closeDB();
    }

    public List<OrderListModel2.Order> getListArray() {
        return arrayLists;
    }

    public void setListArray(ArrayList<OrderListModel2.Order> arrayLists) {
        this.arrayLists = arrayLists;
        tempArrayLists = new ArrayList<>();
        tempArrayLists.addAll(arrayLists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_dash, parent, false);

        apiService = APIUrl.getClient().create(APIService.class);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final OrderListModel2.Order mOrder= arrayLists.get(position);

        holder.txt_joborderid.setText(mOrder.getService_typ_name());
        holder.txt_joborderid.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.txt_joborderid.setSelected(true);
        holder.txt_joborderid.setSingleLine(true);
        holder.txt_jobid.setText("Locate on Map");
        holder.txt_jobid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(mOrder.getGar_lat()), Float.parseFloat(mOrder.getGar_long()));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    context.startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toasty.error(context,"Google Map App Not Found").show();
                }
            }
        });
        holder.txtJobDate.setText(context.parseDate(mOrder.getIs_create()));
        holder.txtJobUnique.setText(Html.fromHtml("<b>"+mOrder.getVehicled_regno()+"</b>"+mOrder.getVehicle_name()+"|"+mOrder.getVehicle_cat_name()));
        holder.txtJobOrder.setText("Order Id :"+mOrder.getOrdreq_id());
        holder.txtJobMobile.setText(mOrder.getCus_mob());
        holder.txtJobCustomer.setText(mOrder.getCus_name());
        holder.txtJobLocation.setText("Location : "+mOrder.getOrdreq_location());
        holder.txtJobDistance.setText("Distance : "+mOrder.getDistance());
        holder.txtJobDistance.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.txtJobDistance.setSelected(true);
        holder.txtJobDistance.setSingleLine(true);
        holder.txtJobStatus.setText(mOrder.getStatus_name());
        holder.txtJobStatus.setTextColor(Color.RED);
        holder.txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordrequniq_id = mOrder.getOrdreq_id();
                gar_custid = mOrder.getCus_id();
                confirmOrder(gar_id,ordrequniq_id,gar_custid);
            }
        });
    }


    @Override
    public int getItemCount() {
      //  return arrayLists.size();
        return (arrayLists == null) ? 0 : arrayLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtConfirm;
        public TextView txt_joborderid,txt_jobid,txtJobDate,txtJobUnique,txtJobCustomer,
                txtJobDistance,txtJobLocation,txtJobStatus,
                txtJobMobile,txtJobOrder;
        public CardView cvCategory;

        public MyViewHolder(View view) {
            super(view);
            txt_jobid = view.findViewById(R.id.txt_jobid);
            txt_joborderid = view.findViewById(R.id.txt_joborderid);
            txtJobDate = view.findViewById(R.id.txtJobDate);
            txtJobUnique = view.findViewById(R.id.txtJobUnique);
            txtJobOrder = view.findViewById(R.id.txtJobOrder);
            txtJobCustomer = view.findViewById(R.id.txtJobCustomer);
            txtJobLocation = view.findViewById(R.id.txtJobLocation);
            txtJobDistance = view.findViewById(R.id.txtJobDistance);
            txtJobStatus = view.findViewById(R.id.txtJobStatus);
            txtJobMobile = view.findViewById(R.id.txtJobMobile);
            txtConfirm = view.findViewById(R.id.txtConfirm);
            cvCategory = view.findViewById(R.id.cvCategory);


        }

    }


    private void confirmOrder(String gar_id,String ordrequniq_id,String gar_custid) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Toast.makeText(context, " gar_id  "+gar_id+" ordrequniq_id "+ordrequniq_id+"  gar_custid "+gar_custid, Toast.LENGTH_SHORT).show();
        confOrder(gar_id,ordrequniq_id,gar_custid).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                progressDialog.dismiss();
                System.out.println("response login : " + response.body().getResponse());
                if (Integer.parseInt(response.body().getResponse()) == 101) {
                    context.startActivity(new Intent(context, OngoingActivity.class));
                    context.finish();
                    Toasty.success(context, "Request Sent Successfully..", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.dismiss();
                   // System.out.println(TAG + " Else Close");
                    Toasty.error(context, "other error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                progressDialog.dismiss();
                //context.errorOut(t);
            }
        });
    }

    private Call<ResponseResult>
    confOrder(final String gar_id,final String ordrequniq_id,final String gar_custid) {
        System.out.println("#gar id : "+gar_id+" order uni id "+ordrequniq_id+" cust id "+gar_custid);
        return apiService.confirmOrder(
                APIUrl.KEY,
                gar_custid,
                ordrequniq_id,
                gar_id
        );
    }



   /* public void getFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayLists.clear();
        if (charText.length() == 0) {
            arrayLists.addAll(tempArrayLists);
        } else {
            for (OrderListModel data : tempArrayLists) {
                if (data.getTransport_name().toLowerCase(Locale.getDefault()).contains(charText)||
                        data.getTransport_mob().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrayLists.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }*/
}
