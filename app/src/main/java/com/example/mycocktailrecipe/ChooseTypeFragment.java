package com.example.mycocktailrecipe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mycocktailrecipe.db.Type;
import com.example.mycocktailrecipe.util.HttpUtil;
import com.example.mycocktailrecipe.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * project name：MyCocktailRecipe
 * className：
 * author：shuoyang
 * Date：2019-08-05 04:26
 */
public class ChooseTypeFragment extends Fragment {


    private static final String TAG = "ChooseTypeFragment";

    public static final int LEVEL_TYPE = 0;


    private ProgressDialog progressDialog;

    private TextView titleText;

    private Button backButton;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    //list of type
    private List<Type> typeList;

    private Type selectedType;

    private int currentLevel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_type, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_TYPE) {
                    selectedType = typeList.get(position);
                    //                    queryCities();

//
//                    String weatherId = typeList.get(position).getWeatherId();
//                    if (getActivity() instanceof MainActivity) {
//                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
//                        intent.putExtra("weather_id", weatherId);
//                        startActivity(intent);
//                        getActivity().finish();
//                    }
//                    else if (getActivity() instanceof WeatherActivity) {
//                        WeatherActivity activity = (WeatherActivity) getActivity();
//                        activity.drawerLayout.closeDrawers();
//                        activity.swipeRefresh.setRefreshing(true);
//                        activity.requestWeather(weatherId);
//                    }
                }

            }
        });
        queryTypes();

    }
//                else if (currentLevel == LEVEL_CITY) {
//                    selectedCity = cityList.get(position);
//                    queryCounties();
//                }
//                else if (currentLevel == LEVEL_COUNTY) {
//                    String weatherId = countyList.get(position).getWeatherId();
//                    if (getActivity() instanceof MainActivity) {
//                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
//                        intent.putExtra("weather_id", weatherId);
//                        startActivity(intent);
//                        getActivity().finish();
//                    } else if (getActivity() instanceof WeatherActivity) {
//                        WeatherActivity activity = (WeatherActivity) getActivity();
//                        activity.drawerLayout.closeDrawers();
//                        activity.swipeRefresh.setRefreshing(true);
//                        activity.requestWeather(weatherId);
//                    }
//                }


//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentLevel == LEVEL_COUNTY) {
//                    queryCities();
//                } else if (currentLevel == LEVEL_CITY) {
//                    queryTypes();
//                }
//            }
//        }



    /**
     * query all types, first from db,then from server
     */
    private void queryTypes() {
        titleText.setText("all types of cocktails");
        backButton.setVisibility(View.GONE);
        typeList = DataSupport.findAll(Type.class);
        if (typeList.size() > 0) {
            dataList.clear();
            for (Type type : typeList) {
                dataList.add(type.getType());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_TYPE;
        } else {
            String address = "http://127.0.0.1/recipes.json";
            queryFromServer(address, "type");
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据。
     */
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("type".equals(type)) {
                    result = Utility.handleTypeResponse(responseText);
                }
//                else if ("city".equals(type)) {
//                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
//                } else if ("county".equals(type)) {
//                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
//                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("type".equals(type)) {
                                queryTypes();
                            }
//                            else if ("city".equals(type)) {
//                                queryCities();
//                            } else if ("county".equals(type)) {
//                                queryCounties();
//                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // runOnUiThread() back to main thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "fail to load", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * open dialog
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * close dialog
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
