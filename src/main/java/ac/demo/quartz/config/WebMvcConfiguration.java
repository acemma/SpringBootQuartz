package ac.demo.quartz.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 配置spring拦截器
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //定义一个转换消息的对象
        JsonHttpMessageConverter fastConverter = new JsonHttpMessageConverter("yyyy-MM-dd HH:mm:ss");

        //添加fastjson的配置信息 比如 ：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat);

        //在转换器中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //将转换器添加到converters中
        converters.add(fastConverter);
    }

    /**
     * 解决fastjson的 @JSONField(format = "yyyy-MM-dd") 不生效问题
     */
    private class JsonHttpMessageConverter extends FastJsonHttpMessageConverter {

        /**
         * @param globalDefalutDateFormat 全局默认的日期格式
         */
        public JsonHttpMessageConverter(String globalDefalutDateFormat) {
            super();
            JSON.DEFFAULT_DATE_FORMAT = globalDefalutDateFormat;
        }

        @Override
        protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            String json = JSON.toJSONString(obj, //
                    super.getFastJsonConfig().getSerializeConfig(), //
                    super.getFastJsonConfig().getSerializeFilters(), //
                    null, //
                    JSON.DEFAULT_GENERATE_FEATURE, //
                    super.getFastJsonConfig().getSerializerFeatures());

            HttpHeaders headers = outputMessage.getHeaders();

            byte[] bytes = json.getBytes(super.getFastJsonConfig().getCharset());
            ByteArrayOutputStream outnew = new ByteArrayOutputStream();
            outnew.write(bytes);
            int len = bytes.length;
            headers.setContentLength(len);
            OutputStream out = outputMessage.getBody();
            outnew.writeTo(out);
            outnew.close();
        }
    }
}