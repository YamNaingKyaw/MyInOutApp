package myanmar.gic.com.myinoutnote.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import myanmar.gic.com.myinoutnote.AddCashOutActivity;
import myanmar.gic.com.myinoutnote.Models.CashOut;
import myanmar.gic.com.myinoutnote.R;

public class CashOutAdapter extends RecyclerView.Adapter<CashOutAdapter.MyViewHolder> {
    private LayoutInflater mInflate;
    private List<CashOut> mAllCashOut;
    private Context mContext;

    public static String UPDATE_DELETE_ID = "update delete id";
    public static String UPDATE_DELETE_DATE = "update delete date";
    public static String UPDATE_DELETE_CATEGORY = "update delete category";
    public static String UPDATE_DELETE_AMOUNT = "update delete amount";

    public CashOutAdapter(Context context) {
        mInflate = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflate.inflate(R.layout.cash_in_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (mAllCashOut != null) {
            CashOut cashOut = mAllCashOut.get(i);

            myViewHolder.tvDate.setText(cashOut.getDate());
            myViewHolder.tvCategory.setText(cashOut.getCashOutCtg());
            myViewHolder.tvAmount.setText(cashOut.getAmount() + " Ks");
            myViewHolder.tvAmount.setTextColor(Color.parseColor("#c31919"));

            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {
                        Toast.makeText(mContext, "This is long click :" + mAllCashOut.get(position).getAmount(), Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(mContext,"This is click :"+mAllCashOut.get(position).getAmount(),Toast.LENGTH_SHORT).show();
                        Intent goIntent = new Intent(mContext, AddCashOutActivity.class);
                        goIntent.putExtra(UPDATE_DELETE_ID, mAllCashOut.get(position).getId());
                        goIntent.putExtra(UPDATE_DELETE_DATE, mAllCashOut.get(position).getDate());
                        goIntent.putExtra(UPDATE_DELETE_CATEGORY, mAllCashOut.get(position).getCashOutCtg());
                        goIntent.putExtra(UPDATE_DELETE_AMOUNT, mAllCashOut.get(position).getAmount());
                        mContext.startActivity(goIntent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mAllCashOut != null)
            return mAllCashOut.size();
        else
            return 0;
    }

    public void setCashOut(List<CashOut> cashOuts) {
        mAllCashOut = cashOuts;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvDate, tvCategory, tvAmount;

        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
