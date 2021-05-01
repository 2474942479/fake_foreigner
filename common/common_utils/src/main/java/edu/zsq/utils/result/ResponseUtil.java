package edu.zsq.utils.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 张
 */
public class ResponseUtil {

    public static <T> void out(HttpServletResponse response, JsonResult<T> jsonResult) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        try {
            mapper.writeValue(response.getWriter(), jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
