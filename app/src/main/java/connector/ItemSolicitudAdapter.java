package connector;

import java.util.ArrayList;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.scc.testgrability.R;

public class ItemSolicitudAdapter extends BaseAdapter {
	protected Activity				activity;
	protected ArrayList<ImageViews>	items;
	protected int					list;

	private ArrayList<ImageViews>	arraylist;

	public ItemSolicitudAdapter(Activity activity, ArrayList<ImageViews> items, int list) {
		this.activity = activity;
		this.items = items;
		this.list = list;

		this.arraylist = new ArrayList<ImageViews>();
		this.arraylist.addAll(items);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getidSolicitud();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (list == 1) {
				vi = inflater.inflate(R.layout.list_view_phone, null);
			}

		}

		ImageViews item = items.get(position);


		return vi;
	}

	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		items.clear();
		if (charText.length() == 0) {
			items.addAll(arraylist);
		} else {
			for (ImageViews wp : arraylist) {

			}
		}
		notifyDataSetChanged();
	}
	
	public int posFilter(String servicio) {
		int result = -1;
		int pos = 0;

			for (ImageViews wp : arraylist) {
				if (wp.getidSolicitud() == Long.parseLong(servicio)) {
					result = pos;
				}
				pos = pos + 1;
			}
		return result;
	}
}
