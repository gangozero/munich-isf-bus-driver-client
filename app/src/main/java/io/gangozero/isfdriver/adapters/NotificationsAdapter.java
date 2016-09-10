package io.gangozero.isfdriver.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.gangozero.isfdriver.R;
import io.gangozero.isfdriver.models.NotificationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

	private Context context;
	private List<NotificationModel> notifications = new ArrayList<>();

	public NotificationsAdapter(Context context) {
		this.context = context;
	}

	public void add(NotificationModel notification) {
		notifications.add(notification);
		notifyItemInserted(0);
		notifyDataSetChanged();
	}

	public void remove(NotificationModel notification, int position) {
		notifications.remove(notification);
		notifyItemRemoved(position);
	}

	@Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
		return new ViewHolder(view);
	}

	@Override public void onBindViewHolder(ViewHolder holder, int position) {
		NotificationModel model = notifications.get(position);
		holder.btnHide.setOnClickListener(v -> remove(model, position));
		holder.textView.setText(model.message);
	}

	@Override public int getItemCount() {
		return notifications.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.text_message) TextView textView;
		@BindView(R.id.btn_hide) Button btnHide;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
