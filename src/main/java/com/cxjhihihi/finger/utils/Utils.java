package com.cxjhihihi.finger.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.netease.vipnew.common.util.RequestWrapper;

/**
 * 工具�?
 *
 * @author chenmengjiang
 * @version Feb 12, 2015 5:01:58 PM
 */
public class Utils {
    // 日志记录对象
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    /**
     * 返回响应结果
     * 
     * @param request
     * @param response
     * @param callback
     * @param jv
     */
    public static void writeBack(HttpServletRequest request,
        HttpServletResponse response, JSONObject jv) {
        try {
            String callback = request.getParameter("callback");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(Utils.getJsonResult(callback, jv));
        } catch (Exception ex) {}
    }

    /**
     * 得到json字符串返�?
     * 
     * @param callback
     * @param json
     * @return
     */
    public static String getJsonResult(String callback, JSONObject json) {
        if (StringUtils.isEmpty(callback)) {
            return json.toJSONString();
        }
        return RequestWrapper.cleanKeywords(callback) + "("
            + json.toJSONString() + ")";
    }
}
