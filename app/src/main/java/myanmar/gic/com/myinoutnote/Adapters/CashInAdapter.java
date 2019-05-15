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

import myanmar.gic.com.myinoutnote.AddCashInActivity;
import myanmar.gic.com.myinoutnote.Models.CashIn;
import myanmar.gic.com.myinoutnote.R;

public class CashInAdapter extends RecyclerView.Adapter<CashInAdapter.MyViewHolder> {
    private LayoutInflater mInflate;
    private List<CashIn> mAllCashIn;
    private Context mContext;

    public static String UPDATE_DELETE_ID = "update delete id";
    public static String UPDATE_DELETE_DATE = "update delete date";
    public static String UPDATE_DELETE_CATEGORY = "update delete category";
    public static String UPDATE_DELETE_AMOUNT = "update delete amount";

    public CashInAdapter(Context context) {
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
        if (mAllCashIn != null) {
            CashIn cashIn = mAllCashIn.get(i);

            myViewHolder.tvDate.setText(cashIn.getDate());
            myViewHolder.tvCategory.setText(cashIn.getCashInCtg());
            myViewHolder.tvAmount.setText(cashIn.getAmount() + " Ks");
            myViewHolder.tvAmount.setTextColor(Color.parseColor("#0df58c"));

            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {
                        Toast.makeText(mContext, "This is long click :" + mAllCashIn.get(position).getAmount(), Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(mContext,"This is click :"+mAllCashOut.get(position).getAmount(),Toast.LENGTH_SHORT).show();
                        Intent goIntent = new Intent(mContext, AddCashInActivity.class);
                        goIntent.putExtra(UPDATE_DELETE_ID, mAllCashIn.get(position).getId());
                        goIntent.putExtra(UPDATE_DELETE_DATE, mAllCashIn.get(position).getDate());
                        goIntent.putExtra(UPDATE_DELETE_CATEGORY, mAllCashIn.get(position).getCashInCtg());
                        goIntent.putExtra(UPDATE_DELETE_AMOUNT, mAllCashIn.get(position).getAmount());
                        mContext.startActivity(goIntent);
                    }
                }
            });
        }
    }

    public void setCashIns(List<CashIn> cashIns) {
        mAllCashIn = cashIns;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAllCashIn != null)
            return mAllCashIn.size();
        else return 0;
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
