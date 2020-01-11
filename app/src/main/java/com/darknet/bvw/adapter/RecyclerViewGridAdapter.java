//package com.darknet.bvw.adapter;
//
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.darknet.bvw.R;
//import com.darknet.bvw.entity.FindEntity;
//import com.darknet.bvw.view.TypefaceTextView;
//
//import java.util.List;
//
//public class RecyclerViewGridAdapter extends RecyclerView.Adapter<RecyclerViewGridAdapter.GridViewHolder> implements View.OnClickListener {
//
//    private Context mContext;
//    //RecyclerView所需的类
//    List<FindEntity> list;
//
//    //构造方法，一般需要接收两个参数 1.上下文 2.集合对象（包含了我们所需要的数据）
//    public RecyclerViewGridAdapter(Context mContext, List<FindEntity> list) {
//        this.mContext = mContext;
//        this.list = list;
//    }
//
//    @Override
//    public RecyclerViewGridAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //转换一个ViewHolder对象，决定了item的样式，参数1.上下文 2.XML布局资源 3.null
//        View itemView = View.inflate(mContext, R.layout.layout_grid, null);
//        //创建一个ViewHodler对象
//        GridViewHolder gridViewHolder = new GridViewHolder(itemView);
//        //把ViewHolder传出去
//        return gridViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerViewGridAdapter.GridViewHolder holder, int position) {
//        String item = list.get(position).getTitle();
//        Integer icon = list.get(position).getIcon();
//        String des = list.get(position).getDes();
//        holder.setData(item, icon, des);
//    }
//
//    @Override
//    public int getItemCount() {
//        return null != list ? list.size() : 0;
//    }
//
//
//    private OnItemClickListener onItemClickListener;
//
//    @Override
//    public void onClick(View v) {
//        if (onItemClickListener != null) {
//            onItemClickListener.onItemClick(v, (int) v.getTag());
//        }
//    }
//
//
//    public interface OnItemClickListener {  //定义接口，实现Recyclerview点击事件
//        void onItemClick(View view, int position);
//    }
//
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {   //实现点击
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public class GridViewHolder extends RecyclerView.ViewHolder {
//        private TypefaceTextView txtName, txtDes;
//        private ImageView img;
//
//        public GridViewHolder(View itemView) {
//            super(itemView);
//            txtName = itemView.findViewById(R.id.txtName);
//            txtDes = itemView.findViewById(R.id.txtDes);
//            img = itemView.findViewById(R.id.img);
//        }
//
//        public void setData(String item, Integer icon, String des) {
//            txtName.setText(item);
//            img.setImageResource(icon);
//            txtDes.setText(des);
//        }
//
//    }
//}
