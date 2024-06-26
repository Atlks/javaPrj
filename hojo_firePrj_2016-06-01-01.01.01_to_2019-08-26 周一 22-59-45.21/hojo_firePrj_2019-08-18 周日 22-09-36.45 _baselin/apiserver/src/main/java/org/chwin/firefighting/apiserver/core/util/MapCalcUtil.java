package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 地图计算工具类
 * <p>具体说明</p>
 *
 * @author wangjunma
 * @version $Id: MapCalcUtil,v 0.1 2018/1/5 16:53 Exp $$
 */
public class MapCalcUtil {

    static CacheService cacheService;
    /**
     * 地球半径(单位km)
     */
    private static final double EARTH_R = 6371;
    /**
     * 赤道半径(单位m)
     */
    private static final double EARTH_RADIUS = 6378137;

    private static double x_pi = Math.PI * 3000.0 / 180.0;
    /**
     * TODO 可配置
     * 中心点半径范围,0.5km
     */
    private static double MAP_RADIUS=0.5;
    static{
        cacheService= (CacheService) SpringUtil.getBean("cacheService");
    }
    /**
     * 根据当前经纬度计算N米范围内四个角经纬度
     *
     * @param longitude 经度
     * @param latitude  维度
     * @return 四个角经纬度
     */
    public static Map findNeighPosition(double longitude, double latitude) {
        String mapRadius=cacheService.findSysParams("MAP_RADIUS");
        if(StringUtils.isNotBlank(mapRadius)){
            MAP_RADIUS=Double.parseDouble(cacheService.findSysParams("MAP_RADIUS"));
        }
        //先计算查询点的经纬度范围
        double dlng = 2 * Math.asin(Math.sin(MAP_RADIUS / (2 * EARTH_R)) / Math.cos(latitude * Math.PI / 180));
        //角度转为弧度
        dlng = dlng * 180 / Math.PI;
        double dlat = MAP_RADIUS / EARTH_R;
        dlat = dlat * 180 / Math.PI;
        double minlat = latitude - dlat;
        double maxlat = latitude + dlat;
        double minlng = longitude - dlng;
        double maxlng = longitude + dlng;
        return new HashMap(8) {{
            put("MIN_LAT", minlat);
            put("MAX_LAT", maxlat);
            put("MIN_LNG", minlng);
            put("MAX_LNG", maxlng);
        }};
    }

    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 基于余弦定理求两经纬度距离
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double lantitudeLongitudeDist(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double radLon1 = rad(lon1);
        double radLon2 = rad(lon2);

        if (radLat1 < 0)
            // south
            radLat1 = Math.PI / 2 + Math.abs(radLat1);
        if (radLat1 > 0)
            // north
            radLat1 = Math.PI / 2 - Math.abs(radLat1);
        if (radLon1 < 0)
            // west
            radLon1 = Math.PI * 2 - Math.abs(radLon1);
        if (radLat2 < 0)
            // south
            radLat2 = Math.PI / 2 + Math.abs(radLat2);
        if (radLat2 > 0)
            // north
            radLat2 = Math.PI / 2 - Math.abs(radLat2);
        if (radLon2 < 0)
            // west
            radLon2 = Math.PI * 2 - Math.abs(radLon2);
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);

        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);

        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        //余弦定理求夹角
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        double dist = theta * EARTH_RADIUS;
        //单位：千米 保留一位小数
//        dist= Math.round(dist / 100d) / 10d;
        return Math.ceil(dist);
    }

    /**
     * 对double类型数据保留小数点后多少位
     * 高德地图转码返回的就是 小数点后6位，为了统一封装一下
     *
     * @param digit 位数
     * @param in    输入
     * @return 保留小数位后的数
     */
    static double dataDigit(int digit, double in) {
        return new BigDecimal(in).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将火星坐标转变成百度坐标
     *
     * @param lngLat_gd {"longitude":120.748312,"lantitude":120.748312,} 火星坐标（高德、腾讯地图坐标等）
     * @return 百度坐标
     */

    public static Map<String, Double> bd_encrypt(Map<String, Double> lngLat_gd) {
        double x = lngLat_gd.get("LONGITUDE"), y = lngLat_gd.get("LATITUDE");
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        return new HashMap<String, Double>(4) {{
            put("LONGITUDE", dataDigit(6, z * Math.cos(theta) + 0.0065));
            put("LATITUDE", dataDigit(6, z * Math.sin(theta) + 0.006));
        }};
    }

    /**
     * 将百度坐标转变成火星坐标
     *
     * @param lngLat_gd {"longitude":120.748312,"lantitude":120.748312} 百度坐标（百度地图坐标）
     * @return 火星坐标(高德 、 腾讯地图等)
     */
    public static  Map<String, Double> bd_decrypt(Map<String, Double> lngLat_gd) {
        double x = lngLat_gd.get("LONGITUDE") - 0.0065, y =  lngLat_gd.get("LATITUDE") - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        return new HashMap<String, Double>(4) {{
            put("LONGITUDE", dataDigit(6, z * Math.cos(theta)));
            put("LATITUDE", dataDigit(6, z * Math.sin(theta)));
        }};
    }

    public static void main(String[] args) {
        System.out.println(lantitudeLongitudeDist(120.116701, 30.291729, 120.113933, 30.290062));
        Map baiduMap=new HashMap<String, Double>(4) {{
            put("LONGITUDE", 120.123569);
            put("LATITUDE",30.297066);
        }};
        System.out.println(JSONParser.obj2Json(bd_decrypt(baiduMap)));
    }

}
