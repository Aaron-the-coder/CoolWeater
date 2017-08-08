package com.dale.coolweather.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dale.coolweather.R;
import com.dale.coolweather.db.City;
import com.dale.coolweather.db.County;
import com.dale.coolweather.db.Province;
import com.dale.coolweather.util.HttpUtil;
import com.dale.coolweather.util.LogUtil;
import com.dale.coolweather.util.UrlHelper;
import com.dale.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 选择省市县
 */
public class ChooseAreaFragment extends Fragment {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_area_list)
    ListView lvAreaList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList = new ArrayList<>();//显示在列表上的内容的集合

    private static int CURRENT_LEVEL;
    private static int LEVEL_PROVINCE = 0;//省
    private static int LEVEL_CITY = 1;//市
    private static int LEVEL_COUNTY = 2;//县

    private Province selectedProvince;//当前选择的省
    private City selectedCity;//当前选择的市
    private County selectedCounty;//当前选择的县

    private ArrayList<Province> provinceList;//省列表
    private ArrayList<City> cityList;//市列表
    private ArrayList<County> countyList;//县列表
    private ProgressDialog progressDialog;

    private static final String TYPE_PROVINCE = "province";//用来区别网络请求类型
    private static final String TYPE_CITY = "city";
    private static final String TYPE_COUNTY = "county";

    private static final String TAG = "ChooseAreaFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);

        lvAreaList.setAdapter(adapter);
        lvAreaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (CURRENT_LEVEL == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (CURRENT_LEVEL == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    LogUtil.e(TAG, "==="+selectedCity.toString());
                    queryCounties();
                }
            }
        });
        queryProvince();
    }

    /**
     * this
     * 查询省信息
     */
    private void queryProvince() {
        //先用当前选择的省到数据库中查询
        ivBack.setVisibility(View.GONE);
        provinceList = (ArrayList<Province>) DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            //数据库查询成功更新UI
//            provinceList = (ArrayList<Province>) provinces;
            for (Province province : provinceList) {
                dataList.add(province.getName());
            }
            adapter.notifyDataSetChanged();
            CURRENT_LEVEL = LEVEL_PROVINCE;
        } else {
            //网络获取
            queryFromServer(UrlHelper.getAreaUrl(), TYPE_PROVINCE);
        }
    }

    /**
     * 查询城市信息
     */
    private void queryCities() {
        //先用当前选择的省到数据库中查询
        ivBack.setVisibility(View.VISIBLE);
        int id = 0;
        if (selectedProvince != null) {
            id = selectedProvince.getProvinceCode();
        }
        cityList = (ArrayList<City>) DataSupport.where("provinceId = ?", String.valueOf(id)).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            //数据库查询成功更新UI
//            cityList = (ArrayList<City>) cities;
            for (City city : cityList) {
                dataList.add(city.getName());
            }
            adapter.notifyDataSetChanged();
            CURRENT_LEVEL = LEVEL_CITY;
        } else {
            //网络获取
            String areaUrl = UrlHelper.getAreaUrl() +"/" + selectedProvince.getProvinceCode();
            queryFromServer(areaUrl, TYPE_CITY);
        }
    }

    /**
     * 查询县
     */
    private void queryCounties() {
        ivBack.setVisibility(View.VISIBLE);
        int id = 0;
        if (selectedCity != null) {
            id = selectedCity.getCityCode();
        }
        countyList = (ArrayList<County>) DataSupport.where("cityId = ?", String.valueOf(id)).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            //数据库查询成功更新UI
//            countyList = (ArrayList<County>) counties;
            for (County county : countyList) {
                dataList.add(county.getName());
            }
            adapter.notifyDataSetChanged();
            CURRENT_LEVEL = LEVEL_COUNTY;
        } else {
            //网络获取
            String areaUrl = UrlHelper.getAreaUrl() +"/"+ selectedProvince.getProvinceCode() +"/" + selectedCity.getCityCode();
            queryFromServer(areaUrl, TYPE_COUNTY);
        }
    }

    /**
     * 网络获取省市县数据
     */
    private void queryFromServer(String areaUrl, final String type) {
        showProgressDialog();
        HttpUtil.sendOkhttpRequest(areaUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, "请求失败..." + e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()){
                            hideProgressDialog();
                        }
                        Toast.makeText(getActivity(), "请求失败...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resStr = response.body().string();
                LogUtil.e(TAG, "请求到的数据如下:\n" + resStr);
                boolean result;
                if (TextUtils.equals(TYPE_PROVINCE, type)) {
                    result = Utility.handleProvinceResponse(resStr);
                } else if (TextUtils.equals(TYPE_CITY, type)) {
                    result = Utility.handleCityResponse(resStr, selectedProvince.getProvinceCode());
                } else {
                    result = Utility.handleCountyResponse(resStr, selectedCity.getCityCode());
                }
                if (result) {
                    //到此说明请求成功并且返回的数据有效,且保存到数据库成功
                    //重新查询数据库并设置UI
                    //切换到主线程
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideProgressDialog();
                            if (TextUtils.equals(TYPE_PROVINCE, type)) {
                                queryProvince();
                            } else if (TextUtils.equals(TYPE_CITY, type)) {
                                queryCities();
                            } else {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示一个网络请求进度条
     *
     * @param
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在拼命加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 隐藏网络请求进度条
     *
     * @param
     */
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_back})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (CURRENT_LEVEL == LEVEL_COUNTY) {
                    queryCities();
                } else if (CURRENT_LEVEL == LEVEL_CITY) {
                    queryProvince();
                }
                break;
        }
    }
}
