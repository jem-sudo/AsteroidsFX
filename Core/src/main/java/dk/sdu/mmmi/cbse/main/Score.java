package dk.sdu.mmmi.cbse.main;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.MediaType;
import java.util.Arrays;

public class Score {
    private static int destroyedAsteroids = 0;
    private static final RestTemplate restTemplate;
    private static final String baseUrl = "http://localhost:9090";

    static {
        restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(converter);
    }

    public static void incrementAsteroids() {
        destroyedAsteroids++;
        restTemplate.postForObject(baseUrl + "/score/increment", null, Integer.class);
    }

    public static int getDestroyedAsteroids() {
        Integer score = restTemplate.getForObject(baseUrl + "/score", Integer.class);
        return score != null ? score : destroyedAsteroids;
    }

    public static void reset() {
        destroyedAsteroids = 0;
    }
}