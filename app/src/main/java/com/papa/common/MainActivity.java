package com.papa.common;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.papa.library.data.DatabaseManager;
import com.papa.library.data.EventCenter;
import com.papa.library.netstatus.NetUtils;
import com.papa.library.okhttp.request.OkHttpRequest;
import com.papa.library.sql.db.HotelCity;
import com.papa.library.sql.db.HotelCommercial;
import com.papa.library.sql.db.HotelDistrict;
import com.papa.library.ui.BaseActivity;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;

public class MainActivity extends BaseActivity {

    private static final String APIKEY = "40287ae447680a6b0147680a6b580000";

    public static Map<String, String> getCommonRequestParams() {
        String timestamp = String.valueOf(Calendar.getInstance()
                .getTimeInMillis());
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("version", "1.0.0");
//        params.put("clientType", "ANDROID");
//
//
//        params.put("signature", getSignature(params, timestamp));
//        params.put("timestamp", timestamp);
//        params.put("clientVersion","45");
        params.put("version", "1");

//        params.put(HttpConstant.KEY_CLIENT_VERSION,
//                String.valueOf(getAppVersionCode(IApp.getIntance())));
        return params;
    }

    private static String getSignature(Map<String, String> params,
                                       String timestamp) {
        StringBuilder builder = new StringBuilder(APIKEY).append(
                getSortValue(params)).append(timestamp);
        return Md5Util.md5(builder.toString());
    }

    private static String getSortValue(Map<String, String> params) {
        List<String> list = new ArrayList<String>(params.values());
        Collections.sort(list, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                } else {
                    return o1.compareTo(o2);
                }
            }
        });
        StringBuffer keyString = new StringBuffer();
        for (String str : list) {
            keyString.append(str);
        }

        return keyString.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @OnClick(R.id.test_request_btn)
    public void onRequest() {
        new OkHttpRequest.Builder()
                .url("http://hotel-app.teshehui.com/hotelGeoDataGet.action")
                .params(getCommonRequestParams())
                .tag(getClass().getName())
                .post(new DefaultResultCallback<ResponseData>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(ResponseData response) {
                        Logger.d("request success");
                        saveDb(response);
                    }
                });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private void saveDb(ResponseData data) {
        Logger.d("save db start");
        DatabaseManager.getAsyncSession().setListener(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                Logger.d("save db end:%s", operation.getDuration());
            }
        });
        try {
            List<ResponseData.ProvinceData> provinceDatas = data.data.provinceList;
            ArrayList<HotelCity> cities = new ArrayList<HotelCity>();
            ArrayList<HotelCommercial> commercials = new ArrayList<HotelCommercial>();
            ArrayList<HotelDistrict> districts = new ArrayList<HotelDistrict>();
            for (ResponseData.ProvinceData item : provinceDatas) {
                if (item.cityList != null) {
                    for (ResponseData.CityData cityItem : item.cityList) {
                        cities.add(new HotelCity(cityItem.id, cityItem.hot, item.id, cityItem
                                .english_name, cityItem.name, "sort"));
                        if (cityItem.commercialList != null) {
                            for (ResponseData.CommercialData commercialitem : cityItem
                                    .commercialList) {
                                commercials.add(new HotelCommercial(commercialitem.id,
                                        commercialitem.name, cityItem.id));
                            }
                            for (ResponseData.DistrictData districtItem : cityItem
                                    .districList) {
                                districts.add(new HotelDistrict(districtItem.id,
                                        districtItem.name, cityItem.id));
                            }
                        }
                    }
                }
            }
            DatabaseManager.getAsyncSession().insertInTx(HotelCity.class, cities);
            DatabaseManager.getAsyncSession().insertInTx(HotelCommercial.class, commercials);
            DatabaseManager.getAsyncSession().insertInTx(HotelDistrict.class, districts);

        } catch (Exception e) {

        }


    }


}
