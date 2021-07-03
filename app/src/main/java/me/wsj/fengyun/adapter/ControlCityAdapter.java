package me.wsj.fengyun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.wsj.fengyun.R;
import me.wsj.fengyun.bean.CityBean;
import me.wsj.fengyun.bean.CityBeanList;
import me.wsj.fengyun.utils.ContentUtil;
import me.wsj.fengyun.utils.SpUtils;

import java.util.List;

public class ControlCityAdapter extends RecyclerView.Adapter<ControlCityAdapter.MyViewHolder> {

    private List<CityBean> datas;
    private Context context;

    public ControlCityAdapter(Context context, List<CityBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_follow_city, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        myViewHolder.tvItemCity.setText(datas.get(i).getCityName());
        myViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityBeanList favorCity = SpUtils.getBean(context, "cityBean", CityBeanList.class);
                CityBeanList favorCityEn = SpUtils.getBean(context, "cityBeanEn", CityBeanList.class);
                List<CityBean> cityBeans = favorCity.getCityBeans();
                List<CityBean> cityBeansEn = favorCityEn.getCityBeans();
                for (int x = 0; x < cityBeans.size(); x++) {
                    if (cityBeans.get(x).getCityId().equals(datas.get(i).getCityId())) {
                        cityBeans.remove(x);
                    }
                }
                for (int x = 0; x < cityBeansEn.size(); x++) {
                    if (cityBeansEn.get(x).getCityId().equals(datas.get(i).getCityId())) {
                        cityBeansEn.remove(x);
                    }
                }

                datas.remove(i);

                CityBeanList cityBeanList = new CityBeanList();
                cityBeanList.setCityBeans(cityBeans);
                CityBeanList cityBeanListEn = new CityBeanList();
                cityBeanListEn.setCityBeans(cityBeansEn);
                SpUtils.saveBean(context, "cityBeanEn", cityBeanListEn);
                SpUtils.saveBean(context, "cityBean", cityBeanList);
                //删除动画
                notifyItemRemoved(i);
                notifyDataSetChanged();
                ContentUtil.CITY_CHANGE = true;
//                DataUtil.deleteId(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivDelete;
        private final TextView tvItemCity;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDelete = itemView.findViewById(R.id.iv_item_delete);
            tvItemCity = itemView.findViewById(R.id.tv_item_city);

        }
    }
}