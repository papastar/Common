package com.example;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static final String SQL_DB = "com.papa.library.sql.db";
    public static final String SQL_DAO = "com.papa.library.sql.dao";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, SQL_DB);
        schema.setDefaultJavaPackageDao(SQL_DAO);
        schema.enableKeepSectionsByDefault();
        createTable(schema);
        new DaoGenerator().generateAll(schema, getPath());
    }

    private static void createTable(Schema schema) {
        Entity hotelCity = schema.addEntity("HotelCity");
        hotelCity.addIdProperty().primaryKey().autoincrement();
        hotelCity.addIntProperty("hot");
        hotelCity.addLongProperty("provinceId");
        hotelCity.addStringProperty("englishName");
        hotelCity.addStringProperty("name");
        hotelCity.addStringProperty("sortKey");
        hotelCity.setHasKeepSections(true);

        Entity hotelCommercial = schema.addEntity("HotelCommercial");
        hotelCommercial.addIdProperty().primaryKey().autoincrement();
        hotelCommercial.addStringProperty("name");

        Property commercialCityIdProperty = hotelCommercial.addLongProperty("cityId").getProperty();
        hotelCity.addToMany(hotelCommercial, commercialCityIdProperty);
        hotelCommercial.addToOne(hotelCity,commercialCityIdProperty);

        Entity hotelDistrict = schema.addEntity("HotelDistrict");
        hotelDistrict.addIdProperty().primaryKey().autoincrement();
        hotelDistrict.addStringProperty("name");

        Property districtCityIdProperty = hotelDistrict.addLongProperty("cityId").getProperty();
        hotelCity.addToMany(hotelDistrict,districtCityIdProperty);
        hotelDistrict.addToOne(hotelCity,districtCityIdProperty);
    }

    /**
     * 获取程序的根目录
     *
     * @return
     */
    private static String getPath() {

        String path = new StringBuilder()
                .append("library")
                .append(File.separator)
                .append("src")
                .append(File.separator)
                .append("main")
                .append(File.separator)
                .append("java")
                .append(File.separator).toString();
        return new File(path).getAbsolutePath();
    }
}
