package gianfranco.progettomonk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gianfranco on 06/04/2017.
 */

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkVH> {

    private List<Link> dataSet = new ArrayList<>();

    @Override
    public LinkVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_web_view, parent, false);
        return new LinkVH(v);
    }

    @Override
    public void onBindViewHolder(LinkVH holder, int position) {
        Link link = dataSet.get(position);
        holder.webView.loadUrl(link.getUrl());
        holder.dataTV.setText(link.getData());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class LinkVH extends RecyclerView.ViewHolder {
        private WebView webView;
        private TextView dataTV;

        public LinkVH(View itemView) {
            super(itemView);
            webView = (WebView) itemView.findViewById(R.id.item_web_view_WV);
            dataTV = (TextView) itemView.findViewById(R.id.item_textView);
        }
    }
}
