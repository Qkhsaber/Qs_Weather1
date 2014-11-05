package qkh.saber.weather_setting;

import qkh.saber.connect.Http_GET;
import qkh.saber.data_process.StringList;
import qkh.saber.data_process.Stringsplit;
import qkh.saber.data_process.Stringwea;
import qkh.saber.data_process.Weather_code_city;
import qkh.saber.http_client.Http_Client;
import qkh.saber.newface.Login;
import qkh.saber.newface.SlidingMenuNestTabActivity;
import saber.qkh.newweather.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting_weather extends Activity {
	String str_city_code = "null";// ���д���
	String str_city_name = "null";// ��������

	String str_1_min_temperature = "null";// ����ĵ�����������
	String str_1_max_temperature = "null";
	String str_2_min_temperature = "null";
	String str_2_max_temperature = "null";
	String str_3_min_temperature = "null";
	String str_3_max_temperature = "null";
	String str_4_min_temperature = "null";
	String str_4_max_temperature = "null";

	String str_1_temperature = "null";// ��ϵ�����
	String str_2_temperature = "null";
	String str_3_temperature = "null";
	String str_4_temperature = "null";

	String str_real_time_temperature = "null";// ʵʱ�¶�
	String str_feel_like = "null";// ����¶�

	String str_taday_weather = "null";// ���������״��

	String str_1_weather = "null";// �����������״��
	String str_2_weather = "null";
	String str_3_weather = "null";
	String str_4_weather = "null";

	String str_taday_weather_name = "null";// ���������״�������ģ�

	String str_visibility = "null";// ������ܼ���

	String str_mor_rianprobability = "null";// �������ϵ����꼸��
	String str_aft_rianprobability = "null";// ������������꼸��
	String str_mor_humidness = "null";// �������ϵ�ʪ��
	String str_aft_humidness = "null";// �������ϵ�ʪ��
	String str_wind_speed = "null";// ʵʱ����
	String str_wind_direction = "null";// ʵʱ����

	String str_sun_up = "null";// ������ճ�ʱ��
	String str_sun_set = "null";// ���������ʱ��
	String str_uptada_time = "null";// �������r�g
	String str_uptada_time1 = "null";// �������r�g

	static final int SLEEP_TIME = 3 * 1000;

	private static final String PREFS_NAME = "weather_info";

	EditText weather_city;
	Button search_city;
	String city;// ����
	String city_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_weather_setting1);

		search_city = (Button) findViewById(R.id.search_button_city);
		weather_city = (EditText) findViewById(R.id.search_edittext_city);

		search_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				city = weather_city.getText().toString();
				city_code = new Weather_code_city().getcity(city);
				if (city_code.equals("null")) {
					Toast.makeText(Setting_weather.this, "ϵͳ��ʱδ��¼�����꣬���������������",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(Setting_weather.this, "�������óɹ�",
							Toast.LENGTH_SHORT).show();
					SaveUserDate();
				}
				new Thread() {// ��ʼ������ȡ����
					@Override
					public void run() {
						if (InterneTable()) {

							if (str_real_time_temperature == "null") {
								String result = new Http_GET().send(
										Http_Client.gethttpclient(),
										"http://wxdata.weather.com/wxdata/weather/local/"
												+ city_code + "?cc=*&unit=m&dayf=8");// �����ַ��������
								Log.v("--------------------------------",
										"��ȡ����");
								str_city_name = new StringList()
										.zhuanhuan(city);
								if (str_city_name.equals("null")) {
									str_city_name = new Stringsplit()
											.name(result);
								}// �������ƴ���
								str_1_max_temperature = new Stringsplit()
										.temp1(result)[2];// ����������С�¶�
								str_2_max_temperature = new Stringsplit()
										.temp1(result)[3];
								str_3_max_temperature = new Stringsplit()
										.temp1(result)[4];
								str_4_max_temperature = new Stringsplit()
										.temp1(result)[5];
								str_1_min_temperature = new Stringsplit()
										.temp2(result)[2];// �������ݽ����и�
								str_2_min_temperature = new Stringsplit()
										.temp2(result)[3];
								str_3_min_temperature = new Stringsplit()
										.temp2(result)[4];
								str_4_min_temperature = new Stringsplit()
										.temp2(result)[5];

								str_1_temperature = str_1_max_temperature + "~"// ������һ������¶�
										+ str_1_min_temperature + "��C";
								str_2_temperature = str_2_max_temperature + "~"
										+ str_2_min_temperature + "��C";
								str_3_temperature = str_3_max_temperature + "~"
										+ str_3_min_temperature + "��C";
								str_4_temperature = str_4_max_temperature + "~"
										+ str_4_min_temperature + "��C";

								str_feel_like = new Stringsplit().date(result);// ���������¶�
								str_real_time_temperature = new Stringsplit()
										.true_temp(result);

								str_visibility = new Stringsplit().see(result)[0];// �ܼ���
								str_mor_rianprobability = new Stringsplit()
										.rian_1(result)[3];// һ������ĸ���
								str_aft_rianprobability = new Stringsplit()
										.rian_1(result)[4];

								str_wind_speed = new Stringsplit().wind(result)[4];// ����
								str_wind_direction = new Stringsplit()
										.win2(result)[9];// ����
								str_sun_up = new Stringsplit().rich(result)[4];// �ճ������ʱ��
								str_sun_set = new Stringsplit().ril(result)[4];

								str_mor_humidness = new Stringsplit()
										.rian_2(result)[3];// һ��Ŀ���ʪ��
								str_aft_humidness = new Stringsplit()
										.rian_2(result)[4];
								str_taday_weather = new Stringsplit()
										.tianqi(result)[0];// ���������
								str_1_weather = new Stringsplit()
										.tianqi(result)[8];
								str_2_weather = new Stringsplit()
										.tianqi(result)[12];
								str_3_weather = new Stringsplit()
										.tianqi(result)[16];
								str_4_weather = new Stringsplit()
										.tianqi(result)[20];
								str_uptada_time = new Stringsplit()
										.up_data_time(result)[0];

								for (int i = 0; i < str_uptada_time.length(); i++) {// ���и���ʱ���ַ�
									if (str_uptada_time.charAt(i) == 'M') {
										str_uptada_time = str_uptada_time
												.substring(9, i + 1);
									}

								}
								
								str_taday_weather_name = new Stringwea()// ����ת��Ϊ����
										.zhuanhuan(str_taday_weather);

								if (str_taday_weather_name.equals("null")) {
									str_taday_weather_name = new Stringsplit()
											.tianqi(result)[0];
								}

								setdata();

								
							} else {

								setdata();
							}
						} else {
							try {
								sleep(SLEEP_TIME);// �������ʧ�ܻ�˯��30s��������
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}

					}

				}.start();
				//

			}
		});

	}

	private void setdata() {

		Intent intent = new Intent(Setting_weather.this,
				SlidingMenuNestTabActivity.class);
		Bundle bd = new Bundle();

		bd.putString("city_name", str_city_name);// �����Ƿ��b����
		bd.putString("temperature1", str_1_temperature);
		bd.putString("temperature2", str_2_temperature);
		bd.putString("temperature3", str_3_temperature);
		bd.putString("temperature4", str_4_temperature);
		bd.putString("real_time_temperature", str_real_time_temperature);
		bd.putString("taday_weather", str_taday_weather);
		bd.putString("taday_weather_name", str_taday_weather_name);
		bd.putString("weather1", str_1_weather);
		bd.putString("weather2", str_2_weather);
		bd.putString("weather3", str_3_weather);
		bd.putString("weather4", str_4_weather);
		bd.putString("visibility", str_visibility);
		bd.putString("wind_speed", str_wind_speed);
		bd.putString("wind_direction", str_wind_direction);
		bd.putString("sun_up", str_sun_up);
		bd.putString("sun_set", str_sun_set);
		bd.putString("mor_humidness", str_mor_humidness);
		bd.putString("aft_humidness", str_aft_humidness);
		bd.putString("mor_rianprobability", str_mor_rianprobability);
		bd.putString("aft_rianprobability", str_aft_rianprobability);
		bd.putString("updata_time", str_uptada_time);
		bd.putString("feel_like", str_feel_like);
		intent.putExtra("weather", bd);
		Setting_weather.this.startActivity(intent);
		Setting_weather.this.finish();
	}

	private void SaveUserDate() {
		// ���������ļ�
		SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
		// д�������ļ�
		Editor spEd = sp.edit();
		spEd.putBoolean("isSave", true);
		spEd.putString("weather_city", city_code);

		spEd.commit();
	}

	// �ж��Ƿ�����
	boolean InterneTable() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			// do something
			// ������
			return true;
		} else {
			// do something
			// ��������
			return false;
		}
	}
	// ע�����������Է��쳣�˳�
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {

			}
			if (keyCode == KeyEvent.KEYCODE_MENU) {
			}
			if (keyCode == KeyEvent.KEYCODE_HOME) {
			}

			return false;
		}
}
