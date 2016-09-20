package novel.id.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renren on 2016/9/20.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryBean> mCategoryBeen = new ArrayList<>();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public CategoryAdapter(List<CategoryBean> categoryBeen) {
        this.mCategoryBeen = categoryBeen;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        if (holder instanceof ViewHolder) {
            CategoryBean categoryBean = mCategoryBeen.get(pos);
            holder.mCategoryDes.setText(categoryBean.getCategory_des());
            holder.mCategoryTitle.setText(categoryBean.getCategory_title());
            holder.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));

                    }
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return mHeaderView == null ? mCategoryBeen.size() : mCategoryBeen.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mCategoryLl;
        private ImageView mCategoryImg;
        private TextView mCategoryTitle, mCategoryDes;

        public ViewHolder(View itemView) {
            super(itemView);

            mCategoryImg = (ImageView) itemView.findViewById(R.id.category_book_img);
            mCategoryTitle = (TextView) itemView.findViewById(R.id.category_title);
            mCategoryDes = (TextView) itemView.findViewById(R.id.category_book_des);
            mCategoryLl = (LinearLayout) itemView.findViewById(R.id.category_ll);
//            mCategoryLl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.OnItemClick(view, getLayoutPosition(), mCategoryBeen.get(getLayoutPosition()));
//                    }
//                }
//            });
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void OnItemClick(View view, int position, CategoryBean categoryBean);
    }
}
