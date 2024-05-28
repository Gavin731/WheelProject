package com.common.wheel.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionUtil
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2019/11/19 14:43
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/19 14:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ExceptionUtil {

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try{
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally{
            pw.close();
        }
    }

}
