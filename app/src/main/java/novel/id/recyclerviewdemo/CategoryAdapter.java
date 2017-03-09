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
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryBean> mCategoryBeen = new ArrayList<>();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
//        notifyItemChanged(1,1);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        if (mHeaderView != null && position + 1 == getItemCount()) return TYPE_FOOTER;
        if (mHeaderView == null && position == getItemCount()) return TYPE_FOOTER;
        return TYPE_NORMAL;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public CategoryAdapter(List<CategoryBean> categoryBeen) {
        this.mCategoryBeen = categoryBeen;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            return new FooterViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 1.重写onBindViewHolder(VH holder, int position, List<Object> payloads)这个方法
     * <p>
     * 2.执行两个参数的notifyItemChanged，第二个参数随便什么都行，只要不让它为空就OK~，
     * 这样就可以实现只刷新item中某个控件了！！！
     * payload 的解释为：如果为null，则刷新item全部内容
     * 那言外之意就是不为空就可以局部刷新了~！
     *
     * @param holder  服用的holder
     * @param position  当前位置
     * @param payloads  如果为null，则刷新item全部内容  否则局部刷新
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        if (holder instanceof ViewHolder) {
            ViewHolder holder1 = (ViewHolder) holder;
            if (pos == mCategoryBeen.size()) {
                return;
            }
            CategoryBean categoryBean = mCategoryBeen.get(pos);
            holder1.mCategoryDes.setText(categoryBean.getCategory_des());
            holder1.mCategoryTitle.setText(categoryBean.getCategory_title());
            holder1.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));

                    }
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == TYPE_HEADER) return;
//        final int pos = getRealPosition(holder);
//        if (holder instanceof ViewHolder) {
//            ViewHolder holder1 = (ViewHolder) holder;
//            if (pos == mCategoryBeen.size()) {
//                return;
//            }
//            CategoryBean categoryBean = mCategoryBeen.get(pos);
//            holder1.mCategoryDes.setText(categoryBean.getCategory_des());
//            holder1.mCategoryTitle.setText(categoryBean.getCategory_title());
//            holder1.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());
//            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));
//
//                    }
//                }
//            });
//        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    //    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (getItemViewType(position) == TYPE_HEADER) return;
//        final int pos = getRealPosition(holder);
//        if (holder instanceof ViewHolder) {
//            CategoryBean categoryBean = mCategoryBeen.get(pos);
//            holder.mCategoryDes.setText(categoryBean.getCategory_des());
//            holder.mCategoryTitle.setText(categoryBean.getCategory_title());
//            holder.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());
//            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mOnItemClickListener != null){
//                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));
//
//                    }
//                }
//            });
//        }
//
//    }
    @Override
    public int getItemCount() {


        return mHeaderView == null ? mCategoryBeen.size() + 1 : mCategoryBeen.size() + 2;
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

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
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
