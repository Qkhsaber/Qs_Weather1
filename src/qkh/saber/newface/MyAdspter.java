package qkh.saber.newface;

import java.util.List;
import java.util.Map;

import saber.qkh.newweather.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdspter extends BaseAdapter {

	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	private Context context;
	public MyAdspter(Context context,List<Map<String, Object>> data){
		this.context=context;
		this.data=data;
		this.layoutInflater=LayoutInflater.from(context);
	}
	public final class Zujian{
		public ImageView image;
		public TextView title;
		public Button view;
		public TextView info;
	}
	@Override
	public int getCount() {
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Zujian zujian=null;
		if(convertView==null){
			zujian=new Zujian();
			convertView=layoutInflater.inflate(R.layout.list_item, null);
			
			zujian.title=(TextView)convertView.findViewById(R.id.itemsTitle);
			convertView.setTag(zujian);
		}else{
			zujian=(Zujian)convertView.getTag();
		}
		zujian.title.setText((String)data.get(position).get("title"));
		return convertView;
	}

}
