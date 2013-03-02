
package com.crossle.imagefetcherdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import me.crossle.imagefetcher.ImageFetcher;
import me.crossle.imagefetcher.Utils;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the list fragment and add it as our sole content.
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            ArrayListFragment list = ArrayListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
        }

    }

    public static class ArrayListFragment extends ListFragment implements AbsListView.OnScrollListener {

        ImageFetcher mImageFetcher;

        public ArrayListFragment() {

        }

        public final static ArrayListFragment newInstance()
        {
            ArrayListFragment f = new ArrayListFragment();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImageFetcher = Utils.getImageFetcher(getActivity());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new PhotoAdapter(getActivity(), mImageFetcher));

        }
        
        @Override
        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            // Pause disk cache access to ensure smoother scrolling
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING ||
                    scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                mImageFetcher.setPauseWork(true);
            } else {
                mImageFetcher.setPauseWork(false);
            }
        }
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }

        @Override
        public void onPause() {
            super.onPause();
            mImageFetcher.flushCache();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mImageFetcher.closeCache();
        }

				@Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	        
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}

class PhotoAdapter extends BaseAdapter {

    private Context mContext;

    private ImageFetcher mImageFetcher;

    public PhotoAdapter(Context c, ImageFetcher imageFetcher) {
        mContext = c;
        mImageFetcher = imageFetcher;
    }

    public int getCount() {
        return 38;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.two_line_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.imageView1);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        mImageFetcher.loadImage(Images.imageThumbUrls[position], viewHolder.icon);

        return convertView;
    }

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
