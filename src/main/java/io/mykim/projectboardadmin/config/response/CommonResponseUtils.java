package io.mykim.projectboardadmin.config.response;

import io.mykim.projectboardadmin.config.response.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CommonResponseUtils {
    public void sendApiResponse(HttpServletResponse response, CommonResponse result) throws HttpMessageNotWritableException, IOException {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        if(jsonConverter.canWrite(result.getClass(), jsonMimeType)) {
            jsonConverter.write(result, jsonMimeType, new ServletServerHttpResponse(response));
        }
    }

}
