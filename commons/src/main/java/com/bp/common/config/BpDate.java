package com.bp.common.config;

        import org.springframework.web.bind.annotation.ResponseBody;

        import java.text.SimpleDateFormat;
        import java.util.Date;

/**
 *  自定义时间类型,重写toString方法 方便显示日期;
 * @auther: 钟欣凯
 * @date: 2019/3/18 13:35
 */

public class BpDate extends Date {
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(this);
    }
}
