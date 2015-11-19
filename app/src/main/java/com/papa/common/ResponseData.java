package com.papa.common;

import java.util.List;

/**
 * Created by Administrator on 2015/11/19.
 */
public class ResponseData {

    public int status;
    public ProvinceListData data;


    public static class ProvinceListData {
        public String version;
        public List<ProvinceData> provinceList;
    }

    public static class ProvinceData {
        public long id;
        public String name;
        public List<CityData> cityList;
    }


    public static class CityData{
        public long id;
        public String name;
        public String english_name;
        public int hot;
        public List<DistrictData> districList;
        public List<CommercialData> commercialList;
    }


    public static class DistrictData{
        public long id;
        public String name;
    }

    public static class CommercialData{
        public long id;
        public String name;
    }





}
